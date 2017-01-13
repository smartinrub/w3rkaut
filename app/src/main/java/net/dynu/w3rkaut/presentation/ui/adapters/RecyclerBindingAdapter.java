package net.dynu.w3rkaut.presentation.ui.adapters;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import net.dynu.w3rkaut.databinding.ItemLocationRecyclerviewBinding;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.ui.adapters.viewholder.RecyclerViewHolder;

import java.util.Comparator;
import java.util.List;

public class RecyclerBindingAdapter extends
        RecyclerView.Adapter<RecyclerViewHolder> {

    private final SortedList<Location> sortedList =
            new SortedList<>(Location.class, new SortedList.Callback<Location>
                    () {
                @Override
                public int compare(Location a, Location b) {
                    return comparator.compare(a, b);
                }

                @Override
                public void onChanged(int position, int count) {
                    notifyItemRangeChanged(position, count);
                }

                @Override
                public boolean areContentsTheSame(Location oldItem, Location newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areItemsTheSame(Location item1, Location item2) {
                    return true;
                }

                @Override
                public void onInserted(int position, int count) {
                    notifyItemRangeInserted(position, count);
                }

                @Override
                public void onRemoved(int position, int count) {
                    notifyItemRangeRemoved(position, count);
                }

                @Override
                public void onMoved(int fromPosition, int toPosition) {
                    notifyItemMoved(fromPosition, toPosition);
                }
            });

    public interface OnItemClickListener {
        void onItemClick(Location item);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(Location item);
    }

    private final LayoutInflater inflater;
    private final Comparator<Location> comparator;
    private final OnItemClickListener listener;
    private final OnItemLongClickListener longClickListener;

    public RecyclerBindingAdapter(Context context,
                                  Comparator<Location> comparator,
                                  OnItemClickListener listener,
                                  OnItemLongClickListener longClickListener) {
        inflater = LayoutInflater.from(context);
        this.comparator = comparator;
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ItemLocationRecyclerviewBinding binding =
                ItemLocationRecyclerviewBinding.inflate(inflater, parent, false);
        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final Location location = sortedList.get(position);
        holder.bind(location, listener, longClickListener);
    }

    public void add(List<Location> locations) {
        sortedList.addAll(locations);
    }

    public void replaceAll(List<Location> locations) {
        sortedList.beginBatchedUpdates();
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            final Location location = sortedList.get(i);
            if (!locations.contains(location)) {
                sortedList.remove(location);
            }
        }
        sortedList.addAll(locations);
        sortedList.endBatchedUpdates();
    }

    public void remove(Location location) {
        sortedList.remove(location);
    }

    public void remove(List<Location> locations) {
        sortedList.beginBatchedUpdates();
        for (Location location : locations) {
            sortedList.remove(location);
        }
        sortedList.endBatchedUpdates();
    }


    @Override
    public int getItemCount() {
        return sortedList.size();
    }
}

