package com.devgd.melonclone.domain.search.view.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.devgd.melonclone.R;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.global.model.view.adapter.BaseListPagerAdapter;
import com.devgd.melonclone.utils.image.GlideImgManager;
import com.devgd.melonclone.utils.image.ImageSource;

public class RankingPagerAdapter extends BaseListPagerAdapter<Music> {

    private AppCompatActivity mContext;

    public RankingPagerAdapter(AppCompatActivity context, int layoutId) {
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
