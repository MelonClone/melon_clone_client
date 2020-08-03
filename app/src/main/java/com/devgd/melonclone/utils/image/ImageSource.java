package com.devgd.melonclone.utils.image;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import lombok.Getter;
import lombok.Setter;


@Getter
public class ImageSource {
    public enum SourceType { GIF, BITMAP, DRAWABLE, FILE }

    private int lowSize = 512;

    private Drawable drawable;
    private String url = "";
    private ImageView.ScaleType scaleType;

    @Setter
    private SourceType sourceType;

    @Setter
    private boolean isRounded;

    @Setter
    private boolean isLow = false;

    boolean isUrl() {
        try {
            URI uri = new URI(url);
            if (uri.isAbsolute()) {
                uri.toURL();
                return true;
            }
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return false;
    }

    private void findSource(String url) {
        String format = url.trim().toLowerCase();
        if (format.endsWith(".gif")) {
            sourceType = SourceType.GIF;
        } else if (format.endsWith(".jpg") || format.endsWith(".jpeg") || format.endsWith(".png")) {
            sourceType = SourceType.BITMAP;
        }
    }

    public ImageSource(Drawable drawable) {
        this(drawable, ImageView.ScaleType.CENTER_CROP);
    }

    public ImageSource(Drawable drawable, ImageView.ScaleType scaleType) {
        this(drawable, scaleType, SourceType.DRAWABLE);
    }

    public ImageSource(Drawable drawable, ImageView.ScaleType scaleType, SourceType sourceType) {
        this.drawable = drawable;
        this.scaleType = scaleType;
        this.sourceType = sourceType;
    }

    public ImageSource(String url) {
        this(url, ImageView.ScaleType.CENTER_CROP);
    }

    public ImageSource(String url, ImageView.ScaleType scaleType) {
        this.url = url;
        findSource(url);
        this.scaleType = scaleType;
    }

    public ImageSource(String url, ImageView.ScaleType scaleType, SourceType sourceType) {
        this.url = url;
        findSource(url);
        this.scaleType = scaleType;
        this.sourceType = sourceType;
    }
}
