package com.devgd.melonclone.utils.image;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import lombok.Getter;
import lombok.Setter;


@Getter
public class ImageSource {
    private int lowSize = 512;

    private Drawable drawable;
    private String url = "";
    private ImageView.ScaleType scaleType;

    @Setter
    private boolean isRounded;
    @Setter
    private boolean isLow = false;

    boolean isUrl() {
        return true;
    }

    boolean isGif() {
        return url.lastIndexOf(".gif") == url.length()-4;
    }

    public ImageSource(Drawable drawable) {
        this(drawable, ImageView.ScaleType.CENTER_CROP);
    }

    public ImageSource(Drawable drawable, ImageView.ScaleType scaleType) {
        this.drawable = drawable;
        this.scaleType = scaleType;
    }

    public ImageSource(String url) {
        this(url, ImageView.ScaleType.CENTER_CROP);
    }

    public ImageSource(String url, ImageView.ScaleType scaleType) {
        this.url = url;
        this.scaleType = scaleType;
    }
}
