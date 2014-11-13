package com.tonyjs.sliplayout.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tonyjs.sliplayout.R;
import com.tonyjs.sliplayout.lib.SlipLayout;

/**
 * Created by tony.park on 14. 11. 13..
 */
public class FragmentList extends SlipLayoutFragment {

    private SlipLayout mSlipLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(
                new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, DATA));

        mSlipLayout = (SlipLayout) rootView.findViewById(R.id.slip_layout);
        mSlipLayout.setListView(listView);

        return rootView;
    }

    @Override
    public SlipLayout getSlipLayout() {
        return mSlipLayout;
    }
}
