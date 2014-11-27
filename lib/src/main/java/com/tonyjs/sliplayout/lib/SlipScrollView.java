package com.tonyjs.sliplayout.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
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
    }

    public SlipScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlipScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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

    float mLastX;
    float mLastY;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getX();
                mLastY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float x = ev.getX();
                float y = ev.getY();
                float distanceY = y - mLastY;
                float absDistanceY = Math.abs(distanceY);
                if (mOnScrollListener != null) {
                    mOnScrollListener.onScroll((int) distanceY);
                }

                if (mCallbacks.size() > 0) {
                    for (OnScrollListener onScrollListener : mCallbacks) {
                        onScrollListener.onScroll((int) distanceY);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastX = 0;
                mLastY = 0;
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int distance = oldt - t;
//        if (mOnScrollListener != null) {
//            mOnScrollListener.onScroll(distance);
//        }
//
//        if (mCallbacks.size() > 0) {
//            for (OnScrollListener onScrollListener : mCallbacks) {
//                onScrollListener.onScroll(distance);
//            }
//        }
    }

    public void showAllTargetView() {
        for (OnScrollListener callBack : mCallbacks) {
            if (callBack instanceof ScrollCallback) {
                ((ScrollCallback) callBack).show();
            }
        }
    }

    public static ScrollCallback getScrollCallback(Context context, View targetView,
                                                   int slipDistance) {
        return new ScrollCallback(context, targetView, slipDistance);
    }

    public static ScrollCallback getScrollCallback(Context context, View targetView,
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

        public ScrollCallback(Context context, View targetView, int slipDistance, Direction direction) {
            mContext = context;
            mTargetView = targetView;
            mSlipDistance = slipDistance;
            mDirection = direction;
        }

        @Override
        public void onScroll(int amountOfScroll) {
            Log.e("jsp", "onScroll - " + amountOfScroll);
            MarginLayoutParams lp = (MarginLayoutParams) mTargetView.getLayoutParams();
            int absAmountOfScroll = Math.abs(amountOfScroll);
            if (absAmountOfScroll <= 2) {
                return;
            }

            int margin = mDirection == Direction.UP ? lp.topMargin : lp.bottomMargin;

//            Log.e("jsp", "margin = " + margin);
//            Log.e("jsp", "mSlipDistance = " + mSlipDistance);
//            Log.e("jsp", "amountOfScroll = " + amountOfScroll);
            int newMargin = 0;
            if (amountOfScroll > 0) {
                if (absAmountOfScroll >= mSlipDistance) {
                    newMargin = 0;
                } else {
                    if (margin < 0) {
                        newMargin = margin + amountOfScroll;
                        if (newMargin >= 0) {
                            newMargin = 0;
                        }
                    } else {
                        newMargin = 0;
                    }
                }
            } else {
                if (absAmountOfScroll >= mSlipDistance) {
                    newMargin = -mSlipDistance;
                } else {
                    if (margin > -mSlipDistance) {
                        newMargin = margin + amountOfScroll;
                        if (newMargin <= -mSlipDistance) {
                            newMargin = -mSlipDistance;
                        }
                    } else {
                        newMargin = -mSlipDistance;
                    }
                }
            }

//            Log.e("jsp", "newMargin = " + newMargin);
            if (mDirection == Direction.UP) {
                lp.topMargin = newMargin;
            } else {
                lp.bottomMargin = newMargin;
            }
            mTargetView.setLayoutParams(lp);
        }

        public int getPixelFromDp(float dp) {
            return (int) (mContext.getResources().getDisplayMetrics().density * dp);
        }

        public void show() {
            MarginLayoutParams lp = (MarginLayoutParams) mTargetView.getLayoutParams();
            if (mDirection == Direction.UP) {
                lp.topMargin = 0;
            } else {
                lp.bottomMargin = 0;
            }
            mTargetView.setLayoutParams(lp);
        }
    }
}
