package com.tonyjs.sliplayout.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.tonyjs.sliplayout.R;
import com.tonyjs.sliplayout.lib.SlipLayout;
import com.tonyjs.sliplayout.lib.SlipScrollView;

/**
 * Created by tony.park on 14. 11. 13..
 */
public class FragmentScroll extends Fragment {

    public interface OnViewCreatedListener{
        public void onViewCreated(SlipScrollView slipScrollView);
    }

    private SlipScrollView mSlipScrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scroll, container, false);
        mSlipScrollView = (SlipScrollView) rootView.findViewById(R.id.scroll);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Activity activity = getActivity();
        if (activity instanceof OnViewCreatedListener) {
            ((OnViewCreatedListener) activity).onViewCreated(mSlipScrollView);
        }
    }
}