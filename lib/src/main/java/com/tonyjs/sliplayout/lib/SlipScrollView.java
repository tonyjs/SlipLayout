package com.tonyjs.sliplayout.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by tony.park on 14. 11. 12..
 */
public class SlipScrollView extends ScrollView {
    public interface OnScrollListener {
        public void onScroll(int amountOfScroll);
    }

    public SlipScrollView(Context context) {
        super(context);
        init();
    }

    public SlipScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlipScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    int mSpare;
    private void init() {
        mSpare = (int) (10 * getContext().getResources().getDisplayMetrics().density);
    }

    private OnScrollListener mOnScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int distance = oldt - t;
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(distance);
        }
    }
//    float mLastX;
//    float mLastY;
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mLastX = ev.getX();
//                mLastY = ev.getY();
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                float x = ev.getX();
//                float y = ev.getY();
////                float distanceX = Math.abs(x - mLastX);
//                float distanceY = y - mLastY;
////                float absDistanceY = Math.abs(distanceY);
////                Log.e(((Object) this).getClass().getSimpleName(), "distanceY =" + distanceY);
//                if (mOnScrollListener != null) {
//                    mOnScrollListener.onScroll((int) distanceY);
//                }
//                mLastX = x;
//                mLastY = y;
//                break;
//
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                mLastX = 0;
//                mLastY = 0;
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }

}
