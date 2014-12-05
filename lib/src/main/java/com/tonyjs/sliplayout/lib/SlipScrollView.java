package com.tonyjs.sliplayout.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;

/**
 * Created by tony.park on 14. 11. 12..
 */
public class SlipScrollView extends ScrollView {
    public interface OnScrollListener {
        public void onScroll(int amountOfScroll);
    }

    public SlipScrollView(Context context) {
        super(context);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public SlipScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public SlipScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private OnScrollListener mOnScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    private ArrayList<OnScrollListener> mCallbacks = new ArrayList<OnScrollListener>();

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        if (!mCallbacks.contains(onScrollListener)) {
            mCallbacks.add(onScrollListener);
        }
    }

    public void clear() {
        mCallbacks.clear();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int scrollY = getScrollY();
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(scrollY);
        }
        if (mCallbacks.size() > 0) {
            for (OnScrollListener onScrollListener : mCallbacks) {
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
        for (OnScrollListener callBack : mCallbacks) {
            if (callBack instanceof ScrollCallback) {
                ((ScrollCallback) callBack).show();
            }
        }
    }

    public void onScrollForcibly(int scroll) {
        for (OnScrollListener callBack : mCallbacks) {
            if (callBack instanceof ScrollCallback) {
                boolean isOnOriginalPosition =
                        ((ScrollCallback) callBack).isOnOriginalPosition();
                if (isOnOriginalPosition) {
                    break;
                } else {
                    callBack.onScroll(scroll);
                }
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

    public static class ScrollCallback implements SlipScrollView.OnScrollListener {
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
//            MarginLayoutParams lp = (MarginLayoutParams) mTargetView.getLayoutParams();
//            int margin = lp.topMargin;
//            if (mDirection == Direction.UP) {
//                margin = lp.topMargin;
//            } else {
//                margin = lp.bottomMargin;
//            }

            return mTargetView.getTop() == 0;
        }
    }
}
