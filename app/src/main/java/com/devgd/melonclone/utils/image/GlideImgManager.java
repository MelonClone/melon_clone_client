package com.devgd.melonclone.utils.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.devgd.melonclone.R;


/**
 * Created by ryuyoungsoo on 2018. 6. 17..
 */

public class GlideImgManager {

    public static int defaultPlaceHolder = R.drawable.loading_image;

    private static GlideImgManager INSTANCE;

    public static GlideImgManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlideImgManager();
        }
        return INSTANCE;
    }

    private GlideImgManager() {

    }

    public void clear(Context context) {
        Glide.get(context).clearMemory();
    }

    public void trim(Context context, int level) {
        Glide.get(context).trimMemory(level);
    }

    /**
     * Round IMAGES
     * GIF IMAGES
     * JPG/PNG IMAGES
     */
    public void setImages(Context context, ImageView imageView, ImageSource imageSource) {
        setImages(context, imageView, imageSource, -1, false);
    }
    public void setImages(Context context, ImageView imageView, ImageSource imageSource, boolean placeholderOn) {
        setImages(context, imageView, imageSource, -1, placeholderOn);
    }

    public void setImages(Context context, ImageView imageView, ImageSource imageSource, int refreshImageId) {
        setImages(context, imageView, imageSource, refreshImageId, false);
    }

    public void setImages(Context context, ImageView imageView, ImageSource imageSource, int refreshImageId, boolean placeholderOn) {
        if (placeholderOn) {
            setImages(context, imageView, imageSource, refreshImageId, defaultPlaceHolder);
        } else {
            setImages(context, imageView, imageSource, refreshImageId, -1);
        }
    }

    public void setImages(Context context, ImageView imageView, ImageSource imageSource, int refreshImageId, int placeholderImageId) {
        if (placeholderImageId == -1) {
            GlideImgBuilder.getInstance().build(context, imageView, imageSource,
                    refreshImageId == -1 ? null : new ImageSource(context.getDrawable(refreshImageId)));
        } else {
            GlideImgBuilder.getInstance().buildWithPlaceHolder(context, imageView, imageSource,
                    refreshImageId == -1 ? null : new ImageSource(context.getDrawable(refreshImageId)),
                    new ImageSource(context.getDrawable(placeholderImageId)));
        }
    }
}

