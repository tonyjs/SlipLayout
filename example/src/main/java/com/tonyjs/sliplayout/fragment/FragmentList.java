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

/**
 * Created by tony.park on 14. 11. 13..
 */
public class FragmentList extends Fragment {

    public static final String[] DATA =
            ("allin ball calculator dog facebook google hashtagram instagram jake wharton"
                    + " korea lolipop man nineold orc pushbullet quip recyclerview sliplayout trello"
                    + " umano vingle wechat xiaomi youtube zxing").split(" ");

    public interface OnViewCreatedListener{
        public void onViewCreated(ListView listView);
    }

    private ListView mListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        mListView = (ListView) rootView.findViewById(R.id.list);
        mListView.setAdapter(
                new ArrayAdapter<String>(
                        getActivity().getBaseContext(), android.R.layout.simple_list_item_1, DATA));

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

}
