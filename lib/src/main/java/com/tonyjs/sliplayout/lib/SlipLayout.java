package com.tonyjs.sliplayout.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by im026 on 2014. 9. 17..
 */
public class SlipLayout extends FrameLayout implements AbsListView.OnScrollListener{
    public SlipLayout(Context context) {
        super(context);
    }

    public SlipLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlipLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected ListView mListView;

    public void setListView(ListView listView) {
        mListView = listView;
        mListView.setOnScrollListener(this);
    }

    public ListView getListView(){
        return mListView;
    }

    protected View mTargetView;

    public void setTargetView(View view) {
        mTargetView = view;
    }

    public View getTargetView() {
        return mTargetView;
    }

    private int mTargetViewHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mTargetViewHeight = mTargetView.getMeasuredHeight();
    }

    protected int mBottomMargin = 0;

    protected int mLastScrollY = 0;

    protected void deliverScrollY(int scrollY) {
        if (mLastScrollY == 0) {
            mLastScrollY = scrollY;
        }

        int amountOfScrollY = mLastScrollY - scrollY;

        mLastScrollY = scrollY;

//        Log.e("jsp", "amountOfScrollY = " + amountOfScrollY);
        calculateAndElevateLayout(amountOfScrollY);
    }

    protected void calculateAndElevateLayout(int amountOfScrollY) {
        if (amountOfScrollY == 0) {
            return;
        }

        if (Math.abs(amountOfScrollY) >= mTargetViewHeight) {
            if (amountOfScrollY > 0) {
                mBottomMargin = 0;
            } else {
                mBottomMargin = -mTargetViewHeight;
            }
        } else {
            mBottomMargin = mBottomMargin + amountOfScrollY;
            if (Math.abs(mBottomMargin) >= mTargetViewHeight) {
                if (mBottomMargin > 0) {
                    mBottomMargin = 0;
                } else {
                    mBottomMargin = -mTargetViewHeight;
                }
            } else {
                if (mBottomMargin > 0) {
                    mBottomMargin = 0;
                }
            }
        }

        Log.e("jsp", "mBottomMargin = " + mBottomMargin);
        LayoutParams params = (LayoutParams) mTargetView.getLayoutParams();
        params.bottomMargin = mBottomMargin;
        mTargetView.setLayoutParams(params);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

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
}
