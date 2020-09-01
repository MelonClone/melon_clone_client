package com.devgd.melonclone.domain.search.view.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.search.domain.Ads;

import org.watermelon.framework.global.model.view.adapter.BaseListPagerAdapter;
import org.watermelon.framework.utils.image.GlideImgManager;
import org.watermelon.framework.utils.image.ImageSource;

public class AdsPagerAdapter extends BaseListPagerAdapter<Ads> {

    private Context mContext;

    public AdsPagerAdapter(Context context, int layoutId) {
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
