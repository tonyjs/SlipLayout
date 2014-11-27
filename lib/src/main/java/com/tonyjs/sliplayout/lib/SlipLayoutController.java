package com.tonyjs.sliplayout.lib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by tony.js on 2014. 9. 17..
 */
public class SlipLayoutController
        implements AbsListView.OnScrollListener, SlipScrollView.OnScrollListener {

    public static final int DIRECTION_TO_UP = 0;
    public static final int DIRECTION_TO_BOTTOM = 1;

    private Context mContext;
    public SlipLayoutController(Context context) {
        mContext = context;
    }

    private int mDirection = DIRECTION_TO_BOTTOM;

    public void setDirection(int direction) {
        mDirection = direction;
    }

    protected SlipScrollView mScrollView;

    public void setScrollView(SlipScrollView scrollView) {
        mScrollView = scrollView;
        mScrollView.addOnScrollListener(this);
    }

    @Override
    public void onScroll(int amountOfScroll) {
        calculateAndSlipLayout(amountOfScroll);
    }

    protected ListView mListView;

    public void setListView(ListView listView) {
        mListView = listView;
        mListView.setOnScrollListener(this);
    }

    private RecyclerView mRecyclerView;

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setOnScrollListener(new RecyclerScrollListener());
    }

    protected View mTargetView;

    public void setTargetView(View view) {
        mTargetView = view;
    }

    public View getTargetView() {
        return mTargetView;
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

        int scroll = -firstView.getTop() + firstVisibleItem * firstView.getHeight();

        deliverScrollY(scroll);
    }

    protected int mLastScrollY = 0;
    protected void deliverScrollY(int scrollY) {
        if (mTargetView == null) {
            return;
        }
        if (mLastScrollY == 0) {
            mLastScrollY = scrollY;
        }

        int amountOfScrollY = mLastScrollY - scrollY;
//        int amountOfScrollY = scrollY - mLastScrollY;

        mLastScrollY = scrollY;

        calculateAndSlipLayout(amountOfScrollY);
    }

    protected void calculateAndSlipLayout(int amountOfScrollY) {
        Log.e("jsp", "onScroll - " + amountOfScrollY);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mTargetView.getLayoutParams();
        int absAmountOfScroll = Math.abs(amountOfScrollY);
        if (absAmountOfScroll <= 2) {
            return;
        }

        int targetViewHeight = mTargetView.getHeight();
        int margin = mDirection == DIRECTION_TO_UP ? lp.topMargin : lp.bottomMargin;

//            Log.e("jsp", "margin = " + margin);
//            Log.e("jsp", "mSlipDistance = " + mSlipDistance);
//            Log.e("jsp", "amountOfScroll = " + amountOfScroll);
        int newMargin = 0;
        if (amountOfScrollY > 0) {
            if (absAmountOfScroll >= targetViewHeight) {
                newMargin = 0;
            } else {
                if (margin < 0) {
                    newMargin = margin + amountOfScrollY;
                    if (newMargin >= 0) {
                        newMargin = 0;
                    }
                } else {
                    newMargin = 0;
                }
            }
        } else {
            if (absAmountOfScroll >= targetViewHeight) {
                newMargin = -targetViewHeight;
            } else {
                if (margin > -targetViewHeight) {
                    newMargin = margin + amountOfScrollY;
                    if (newMargin <= -targetViewHeight) {
                        newMargin = -targetViewHeight;
                    }
                } else {
                    newMargin = -targetViewHeight;
                }
            }
        }

//            Log.e("jsp", "newMargin = " + newMargin);
        if (mDirection == DIRECTION_TO_UP) {
            lp.topMargin = newMargin;
        } else {
            lp.bottomMargin = newMargin;
        }
        mTargetView.setLayoutParams(lp);
    }

    public void showTargetView() {
        if (mTargetView == null) {
            return;
        }

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mTargetView.getLayoutParams();

        if (mDirection == DIRECTION_TO_BOTTOM) {
            lp.bottomMargin = 0;
        } else {
            lp.topMargin = 0;
        }

        mTargetView.setLayoutParams(lp);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public int getPixelFromDp(float dp) {
        return (int) (mContext.getResources().getDisplayMetrics().density * dp);
    }

    private class RecyclerScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            calculateAndSlipLayout(-dy);
        }
    }
}
