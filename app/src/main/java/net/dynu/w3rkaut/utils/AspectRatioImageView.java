package net.dynu.w3rkaut.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * This class resize ImageView to fix them to the screen width
 *
 * @author Sergio Martin Rubio
 * @since 2016/11/12
 */
public class AspectRatioImageView extends ImageView {

    public AspectRatioImageView(Context context){
        super(context);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
        setMeasuredDimension(width, height);
    }
}