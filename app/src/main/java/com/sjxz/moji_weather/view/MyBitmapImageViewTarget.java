package com.sjxz.moji_weather.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2017/2/10.
 * Role:避免glide加载图片大小不一致
 */
public class MyBitmapImageViewTarget extends BitmapImageViewTarget {

    public MyBitmapImageViewTarget(ImageView view) {
        super(view);
    }

    @Override
    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
        if (bitmap != null && view.getScaleType() != ImageView.ScaleType.FIT_XY) {
            view.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        super.onResourceReady(bitmap, anim);
    }

    @Override
    protected void setResource(Bitmap resource) {
        super.setResource(resource);
    }

    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        if (errorDrawable != null && view != null && view.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        super.onLoadFailed(e, errorDrawable);
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        if (placeholder != null && placeholder != null && view != null && view.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        super.onLoadStarted(placeholder);
    }

    @Override
    public void onLoadCleared(Drawable placeholder) {
        if (placeholder != null && placeholder != null && view != null && view.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        super.onLoadCleared(placeholder);
    }


}
