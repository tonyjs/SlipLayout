package com.tonyjs.sliplayout.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tonyjs.sliplayout.R;
import com.tonyjs.sliplayout.lib.SlipLayout;
import com.tonyjs.sliplayout.lib.SlipScrollView;

/**
 * Created by tony.park on 14. 11. 13..
 */
public class FragmentScroll extends SlipLayoutFragment {

    private SlipLayout mSlipLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scroll, container, false);
        mSlipLayout = (SlipLayout) rootView.findViewById(R.id.slip_layout);
        mSlipLayout.setScrollView(((SlipScrollView) rootView.findViewById(R.id.scroll)));
        return rootView;
    }

    @Override
    public SlipLayout getSlipLayout() {
        return mSlipLayout;
    }
}