package com.tonyjs.sliplayout.lib;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by tony.js on 2014. 9. 17..
 */
public class SlipLayoutController
        implements AbsListView.OnScrollListener, SlipScrollView.OnScrollCallback {

    public static final int DIRECTION_TO_UP = 0;
    public static final int DIRECTION_TO_BOTTOM = 1;

    private View mTargetView;

    private int mDirection = DIRECTION_TO_BOTTOM;
    private int mLastScrollY = 0;

    private SlipLayoutController() {
    }

    public SlipLayoutController(View targetView) {
        this(targetView, DIRECTION_TO_BOTTOM);
    }

    public SlipLayoutController(View targetView, int direction) {
        mTargetView = targetView;
        mDirection = direction;
    }

    public void setScrollableView(SlipScrollView scrollView) {
        scrollView.setOnScrollCallback(this);
    }

    public void setScrollableView(ListView listView) {
        listView.setOnScrollListener(this);
    }

    public void setScrollableView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerScrollListener());
    }

    public void setTargetView(View view) {
        mTargetView = view;
    }

    public View getTargetView() {
        return mTargetView;
    }

    public void setDirection(int direction) {
        mDirection = direction;
    }

    @Override
    public void onScroll(int amountOfScroll) {
        calculateAndSlipLayout(amountOfScroll);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view.getChildCount() <= 0) {
            return;
        }

        View firstView = view.getChildAt(0);
        if (firstView == null) {
            return;
        }

        int scrollY = -firstView.getTop() + firstVisibleItem * firstView.getHeight();

        if (mLastScrollY == 0) {
            mLastScrollY = scrollY;
        }

        int amountOfScrollY = scrollY - mLastScrollY;

        mLastScrollY = scrollY;

        calculateAndSlipLayout(-amountOfScrollY);
    }

    protected void calculateAndSlipLayout(int amountOfScrollY) {
        if (amountOfScrollY == 0 || mTargetView == null) {
            return;
        }

        int targetViewHeight = mTargetView.getHeight();

        boolean directionToBottom = mDirection == DIRECTION_TO_BOTTOM;
        
        float newTop = mTargetView.getTranslationY() +
                (directionToBottom ? -amountOfScrollY : amountOfScrollY);

        float translateY = directionToBottom
                ? Math.min(targetViewHeight, Math.max(0, newTop))
                : Math.min(0, Math.max(-targetViewHeight, newTop));

        mTargetView.setTranslationY(translateY);
    }

    public void showTargetView() {
        if (mTargetView == null) {
            return;
        }

        mTargetView.setTranslationY(0);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    private class RecyclerScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            calculateAndSlipLayout(-dy);
        }
    }
}
