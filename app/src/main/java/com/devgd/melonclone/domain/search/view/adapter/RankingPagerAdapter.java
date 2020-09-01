package com.devgd.melonclone.domain.search.view.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.domain.Music;

import org.watermelon.framework.global.model.view.adapter.BaseListPagerAdapter;
import org.watermelon.framework.utils.image.GlideImgManager;
import org.watermelon.framework.utils.image.ImageSource;

public class RankingPagerAdapter extends BaseListPagerAdapter<Music> {

    private Context mContext;

    public RankingPagerAdapter(Context context, int layoutId) {
        super(layoutId);
        mContext = context;
    }

    @Override
    public void setView(ViewGroup viewGroup, int position) {
        ImageView albumImageView = viewGroup.findViewById(R.id.ranking_album_img);
        TextView musicTitleView = viewGroup.findViewById(R.id.ranking_title);
        TextView artistNameView = viewGroup.findViewById(R.id.ranking_artist);

        Music pagerItem = getList().get(position);
        GlideImgManager.getInstance().setImages(mContext, albumImageView,
                new ImageSource(
                        pagerItem.getAlbum().getAlbumJacketUrl(),
                        ImageView.ScaleType.CENTER_CROP,
                        ImageSource.SourceType.DRAWABLE
                )
        );

        musicTitleView.setText(pagerItem.getMusicTitleName());
        artistNameView.setText(pagerItem.getArtist().getArtistName());
    }
}
