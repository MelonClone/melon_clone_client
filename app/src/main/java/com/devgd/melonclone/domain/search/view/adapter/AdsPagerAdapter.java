package com.devgd.melonclone.domain.search.view.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.search.domain.Ads;
import com.devgd.melonclone.global.model.view.adapter.BaseListPagerAdapter;
import com.devgd.melonclone.utils.image.GlideImgManager;
import com.devgd.melonclone.utils.image.ImageSource;

public class AdsPagerAdapter extends BaseListPagerAdapter<Ads> {

    private AppCompatActivity mContext;

    public AdsPagerAdapter(AppCompatActivity context, int layoutId) {
        super(layoutId);
        mContext = context;
    }

    @Override
    public void setView(ViewGroup viewGroup, int position) {
        ImageView adsImageView = viewGroup.findViewById(R.id.ads_img);

        Ads pagerItem = getList().get(position);
        GlideImgManager.getInstance().setImages(mContext, adsImageView,
                new ImageSource(
                        pagerItem.getAdsImgUrl(),
                        ImageView.ScaleType.CENTER_CROP,
                        ImageSource.SourceType.DRAWABLE
                )
        );
    }
}