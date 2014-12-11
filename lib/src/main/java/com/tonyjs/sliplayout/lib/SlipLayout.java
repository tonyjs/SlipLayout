package com.tonyjs.sliplayout.lib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by tony.js on 2014. 9. 17..
 */
@Deprecated
public class SlipLayout extends FrameLayout
        implements AbsListView.OnScrollListener {

    public static final int DIRECTION_TO_UP = 0;
    public static final int DIRECTION_TO_BOTTOM = 1;

    public static final int DEFAULT_SPARE = 10;
    public SlipLayout(Context context) {
        super(context);
        init();
    }

    public SlipLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlipLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private int mSpare;
    private void init() {
        mSpare = (int) (DEFAULT_SPARE * getContext().getResources().getDisplayMetrics().density);
    }

    private int mDirection = DIRECTION_TO_BOTTOM;

    public void setDirection(int direction) {
        if (direction != DIRECTION_TO_UP || direction != DIRECTION_TO_BOTTOM) {
            try {
                throw new Exception("direction must to be DIRECTION__TO_UP or DIRECTION_TO_BOTTOM");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mDirection = direction;
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
        invalidate();
    }

    public View getTargetView() {
        return mTargetView;
    }

    private int mTargetViewHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mTargetView != null && mTargetViewHeight <= 0) {
            mTargetViewHeight = mTargetView.getHeight();
        }
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

    protected int mMargin = 0;
    protected int mLastScrollY = 0;

    protected void deliverScrollY(int scrollY) {
        if (mTargetView == null) {
            return;
        }
        if (mLastScrollY == 0) {
            mLastScrollY = scrollY;
        }

        int amountOfScrollY = mLastScrollY - scrollY;

        mLastScrollY = scrollY;

        calculateAndSlipLayout(amountOfScrollY);
    }

    protected void calculateAndSlipLayout(int amountOfScrollY) {
        if (amountOfScrollY == 0) {
            return;
        }

        if (Math.abs(amountOfScrollY) >= mTargetViewHeight) {
            if (amountOfScrollY > 0) {
                mMargin = 0;
            } else {
                mMargin = -mTargetViewHeight;
            }
        } else {
            mMargin = mMargin + amountOfScrollY;
            if (Math.abs(mMargin) >= mTargetViewHeight) {
                if (mMargin > 0) {
                    mMargin = 0;
                } else {
                    mMargin = -mTargetViewHeight;
                }
            } else {
                if (mMargin > 0) {
                    mMargin = 0;
                }
            }
        }

        if (mTargetView != null) {
            MarginLayoutParams params = (MarginLayoutParams) mTargetView.getLayoutParams();
            if (mDirection == DIRECTION_TO_BOTTOM) {
                params.bottomMargin = mMargin;
            } else {
                params.topMargin = mMargin;
            }
            mTargetView.setLayoutParams(params);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public class RecyclerScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            calculateAndSlipLayout(-dy);
        }
    }
}
