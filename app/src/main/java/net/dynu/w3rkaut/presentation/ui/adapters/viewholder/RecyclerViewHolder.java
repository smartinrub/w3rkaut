package net.dynu.w3rkaut.presentation.ui.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.dynu.w3rkaut.databinding.ItemLocationRecyclerviewBinding;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.ui.adapters.RecyclerBindingAdapter;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private final ItemLocationRecyclerviewBinding binding;

    public RecyclerViewHolder(ItemLocationRecyclerviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind (final Location item,
                      final RecyclerBindingAdapter.OnItemClickListener listener) {
        binding.setLocation(item);
        binding.executePendingBindings();

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }
}