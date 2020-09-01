package com.devgd.melonclone.domain.search.view.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.devgd.melonclone.R;
import com.google.android.material.tabs.TabLayout;

import org.watermelon.framework.global.model.handler.TabMenu;
import org.watermelon.framework.global.model.view.activity.TabToolbarView;

public class SearchTabToolbarView implements TabToolbarView {

    // Views
    TabLayout searchMenuTab;

    // Listener
    TabLayout.TabLayoutOnPageChangeListener tabPageChangeListener;

    @Override
    public void tabLayoutInit(TabLayout tabLayout) {
        searchMenuTab = tabLayout;
        tabPageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(searchMenuTab);
    }

    @Override
    public void setupCustomTab(LayoutInflater inflater, TabMenu tabMenu) {
        View tabView = inflater.inflate(R.layout.search_menu_tab, null);
        TextView tabNameView = tabView.findViewById(R.id.tab_name);
        TextView tabCountView = tabView.findViewById(R.id.tab_count);
        ImageView selectedIconView = tabView.findViewById(R.id.tab_selected_icon);
        tabNameView.setText(tabMenu.getTabName());
        if (tabMenu.tabItemCount > 0) {
            tabCountView.setText(String.valueOf(tabMenu.getTabItemCount()));
            tabCountView.setVisibility(View.VISIBLE);
        } else {
            tabCountView.setVisibility(View.GONE);
        }

        if (tabMenu.getTabPointImage() != null) {
            selectedIconView.setImageDrawable(tabMenu.getTabPointImage());
        }

        if (tabMenu.isSelected()) {
            selectedIconView.setVisibility(View.VISIBLE);
        } else {
            selectedIconView.setVisibility(View.INVISIBLE);
        }

        searchMenuTab.addTab(searchMenuTab.newTab().setCustomView(tabView));
    }

    @Override
    public void optionInit() {
        searchMenuTab.setTabRippleColor(null);
    }

    @Override
    public void addOnTabSelectedListener(Context context, TabSelectedAction action) {
        searchMenuTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View tabView = tab.getCustomView();
                TextView tabNameView = tabView.findViewById(R.id.tab_name);
                tabNameView.setTextColor(context.getColor(R.color.colorPrimary));
                ImageView selectedIconView = tabView.findViewById(R.id.tab_selected_icon);
                selectedIconView.setVisibility(View.VISIBLE);

                action.onSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                View tabView = tab.getCustomView();
                TextView tabNameView = tabView.findViewById(R.id.tab_name);
                tabNameView.setTextColor(context.getColor(R.color.colorDarkPoint));
                ImageView selectedIconView = tabView.findViewById(R.id.tab_selected_icon);
                selectedIconView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public TabLayout getLayout() {
        return searchMenuTab;
    }

    @Override
    public TabLayout.TabLayoutOnPageChangeListener getListener() {
        return tabPageChangeListener;
    }
}