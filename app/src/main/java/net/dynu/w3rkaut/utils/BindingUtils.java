package net.dynu.w3rkaut.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.dynu.w3rkaut.R;

public class BindingUtils {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).error(R.mipmap.ic_launcher).into(view);
    }
}