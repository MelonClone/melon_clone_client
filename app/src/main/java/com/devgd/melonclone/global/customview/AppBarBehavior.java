package com.devgd.melonclone.global.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.devgd.melonclone.utils.view.ScreenUtils;

public class AppBarBehavior extends CoordinatorLayout.Behavior<ViewGroup> {
    private int toolbarHeight;

    public AppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.toolbarHeight = ScreenUtils.getToolbarHeight(context);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull ViewGroup viewGroup, @NonNull View dependency) {
        return dependency instanceof ViewGroup;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull ViewGroup viewGroup, @NonNull View dependency) {
        Log.d("COORDI_TEST", "Coordi view changed");
        if (dependency instanceof ViewGroup) {
            Log.d("COORDI_TEST", "Coordi viewGroup");
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) viewGroup.getLayoutParams();
            int viewTopMargin = lp.topMargin;
            int distanceToScroll = viewGroup.getHeight() + viewTopMargin;
            float ratio = (float) dependency.getY() / (float) toolbarHeight;
            viewGroup.setTranslationY(distanceToScroll * ratio);
            Log.d("COORDI_TEST", "TransY : "+(distanceToScroll * ratio));
        }
        return true;
    }
/*
    private void offsetChildAsNeeded(@NonNull View child, @NonNull View dependency) {
        final CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.BaseBehavior) {
            // Offset the child, pinning it to the bottom the header-dependency, maintaining
            // any vertical gap and overlap
            final AppBarLayout.BaseBehavior ablBehavior = (AppBarLayout.BaseBehavior) behavior;
            ViewCompat.offsetTopAndBottom(
                    child,
                    (dependency.getBottom() - child.getTop())
                            + ablBehavior.offsetDelta
                            + getVerticalLayoutGap()
                            - getOverlapPixelsForOffset(dependency));
        }
    }

    private void updateLiftedStateIfNeeded(View child, View dependency) {
        if (dependency instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) dependency;
            if (appBarLayout.isLiftOnScroll()) {
                appBarLayout.setLiftedState(appBarLayout.shouldLift(child));
            }
        }
    }*/
}
