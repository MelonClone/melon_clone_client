package com.devgd.melonclone.utils.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.ALL;


/**
 * Created by ryuyoungsoo on 2018. 6. 17..
 */

public class GlideImgBuilder {

    private static GlideImgBuilder INSTANCE;

    public static GlideImgBuilder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlideImgBuilder();
        }

        return INSTANCE;
    }

    private GlideImgBuilder() {
    }

    public void build(Context context, ImageView imageView, ImageSource imageSource) {
        build(context, imageView, imageSource, null);
    }

    public void build(Context context, ImageView imageView, ImageSource imageSource, ImageSource refreshSource) {

        RequestBuilder thumbnailBuilder = getRequestBuilder(Glide.with(context), imageSource);
        setLoad(thumbnailBuilder, imageSource);
        setOption(thumbnailBuilder, imageSource);

        RequestBuilder requestBuilder = getRequestBuilder(Glide.with(context), imageSource);
        setLoad(requestBuilder, imageSource);
        setThumbnail(requestBuilder, thumbnailBuilder);
        setOption(requestBuilder, imageSource);

        if (refreshSource != null) {
            RequestBuilder refreshBuilder = getRequestBuilder(Glide.with(context), refreshSource);
            setLoad(refreshBuilder, refreshSource);
            setOption(refreshBuilder, refreshSource);
            setError(requestBuilder, refreshBuilder);
        }
        setTargetImage(context.getResources(), imageView, imageSource, requestBuilder);
    }

    public void buildWithPlaceHolder(Context context, ImageView imageView, ImageSource imageSource, ImageSource refreshSource, ImageSource placeholderSource) {

        RequestBuilder thumbnailBuilder = getRequestBuilder(Glide.with(context), placeholderSource);
        setLoad(thumbnailBuilder, placeholderSource);
        setOption(thumbnailBuilder, placeholderSource);

        RequestBuilder refreshBuilder = getRequestBuilder(Glide.with(context), refreshSource);
        setLoad(refreshBuilder, refreshSource);
        setOption(refreshBuilder, refreshSource);


        RequestBuilder requestBuilder = getRequestBuilder(Glide.with(context), imageSource);
        setLoad(requestBuilder, imageSource);
        setThumbnail(requestBuilder, thumbnailBuilder);
        setOption(requestBuilder, imageSource);
        if (refreshSource != null)
            setError(requestBuilder, refreshBuilder);
        setTargetImage(context.getResources(), imageView, imageSource, requestBuilder);
    }


    private RequestBuilder getRequestBuilder(RequestManager rm, ImageSource imageSource) {
        if (imageSource.isUrl() && imageSource.isGif()) {
            return rm.asGif();
        } else {
            return rm.asBitmap();
        }
    }

    private void setLoad(RequestBuilder rb, ImageSource imageSource) {
        if (imageSource.isUrl()) {
            rb.load(imageSource.getUrl());
        } else {
            rb.load(imageSource.getDrawable());
        }
    }

    private void setThumbnail(RequestBuilder requestBuilder, RequestBuilder thumbnailBuilder) {
        requestBuilder.thumbnail(thumbnailBuilder);
    }

    private void setScaleType(RequestOptions options, ImageView.ScaleType scaleType) {

        switch (scaleType) {
            case CENTER_CROP:
                options.centerCrop();
                break;
            case CENTER_INSIDE:
            case FIT_XY:
                options.centerInside();
                break;
            case FIT_CENTER:
            case FIT_START:
            case FIT_END:
                options.fitCenter();
                break;
            case CENTER:
            case MATRIX:
            default:
                options.centerCrop();
        }
    }

    private void setOption(RequestBuilder requestBuilder, ImageSource imageSource) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(ALL);
        setScaleType(options, imageSource.getScaleType());

        if (imageSource.isLow()) {
            options.override(imageSource.getLowSize());
        }

        requestBuilder.apply(options);
    }

    private void setError(RequestBuilder requestBuilder, RequestBuilder refreshBuilder) {
        if (refreshBuilder != null) {
//            .error(setLoad(getBuilder(Glide.with(context))))
            requestBuilder.error(refreshBuilder);
        }
    }

    private void setTargetImage(Resources resources, ImageView imageView, ImageSource imageSource, RequestBuilder rb) {
        if (imageSource.isRounded()) {
            rb.into(new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, resource);

                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        } else {
            rb.into(imageView);
        }
    }
}

