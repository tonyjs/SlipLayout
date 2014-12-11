package com.tonyjs.sliplayout.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;

/**
 * Created by tony.park on 14. 11. 12..
 */
public class MultiSlipScrollView extends ScrollView {
    public interface OnScrollCallback {
        public void onScroll(int amountOfScroll);
    }

    public MultiSlipScrollView(Context context) {
        super(context);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public MultiSlipScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public MultiSlipScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private OnScrollCallback mOnScrollCallback;

    public void setOnScrollCallback(OnScrollCallback onScrollListener) {
        mOnScrollCallback = onScrollListener;
    }

    private ArrayList<OnScrollCallback> mCallbacks = new ArrayList<OnScrollCallback>();

    public void addOnScrollCallback(OnScrollCallback onScrollCallback) {
        if (!mCallbacks.contains(onScrollCallback)) {
            mCallbacks.add(onScrollCallback);
        }
    }

    public ArrayList<OnScrollCallback> getCallback() {
        return mCallbacks;
    }

    public void clear() {
        mCallbacks.clear();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int scrollY = getScrollY();
        if (mOnScrollCallback != null) {
            mOnScrollCallback.onScroll(scrollY);
        }
        if (mCallbacks.size() > 0) {
            for (OnScrollCallback onScrollListener : mCallbacks) {
                onScrollListener.onScroll(scrollY);
            }
        }
    }

    private boolean isScrollable() {
        int height = getHeight();
        int contentsHeight = getChildAt(0).getHeight();
        int padding = getPaddingTop() + getPaddingBottom();
        return height < contentsHeight + padding;
    }

    public void showAllTargetView() {
        for (OnScrollCallback callBack : mCallbacks) {
            if (callBack instanceof ScrollCallback) {
                ((ScrollCallback) callBack).show();
            }
        }
    }

    public void onScrollForcibly(int scroll) {
        for (OnScrollCallback callBack : mCallbacks) {
            if (callBack instanceof ScrollCallback) {
                callBack.onScroll(scroll);
            } else {
                callBack.onScroll(scroll);
            }
        }
    }

    public static ScrollCallback getScrollCallback(
            Context context, View targetView, int slipDistance) {
        return new ScrollCallback(context, targetView, slipDistance);
    }

    public static ScrollCallback getScrollCallback(
            Context context, View targetView,
            int slipDistance, ScrollCallback.Direction direction) {
        return new ScrollCallback(context, targetView, slipDistance, direction);
    }

    public static class ScrollCallback implements OnScrollCallback {
        public enum Direction {
            UP, DOWN
        }

        private Context mContext;
        private int mSlipDistance;
        private View mTargetView;
        private Direction mDirection = Direction.UP;
        public ScrollCallback(Context context, View targetView, int slipDistance) {
            mContext = context;
            mTargetView = targetView;
            mSlipDistance = slipDistance;
        }

        public ScrollCallback(
                Context context, View targetView, int slipDistance, Direction direction) {
            mContext = context;
            mTargetView = targetView;
            mSlipDistance = slipDistance;
            mDirection = direction;
        }

        @Override
        public void onScroll(int amountOfScroll) {
            int newTop = Math.max(-mSlipDistance, -amountOfScroll);
            mTargetView.setTranslationY(newTop);
        }

        public int getPixelFromDp(float dp) {
            return (int) (mContext.getResources().getDisplayMetrics().density * dp);
        }

        public void show() {
            mTargetView.setTranslationY(0);
        }

        public View getTargetView() {
            return mTargetView;
        }

        public boolean isOnOriginalPosition(){
            return mTargetView.getTop() == 0;
        }
    }
}
