package net.dynu.w3rkaut.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.dynu.w3rkaut.R;

/**
 * This class allows displaying the user image profile in the recyclerview
 *
 * @author Sergio Martin Rubio
 */
public class BindingUtils {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).error(R.drawable.ic_face_black_24dp).into(view);
    }
}