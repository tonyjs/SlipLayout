package com.tonyjs.sliplayout.fragment;

import android.app.Activity;
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

    public interface OnViewCreatedListener{
        public void onViewCreated(ListView listView);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private ListView mListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        mListView = (ListView) rootView.findViewById(R.id.list);
        mListView.setAdapter(
                new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, DATA));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Activity activity = getActivity();
        if (activity instanceof OnViewCreatedListener) {
            ((OnViewCreatedListener) activity).onViewCreated(mListView);
        }
    }

    @Override
    public SlipLayout getSlipLayout() {
        return null;
    }
}
