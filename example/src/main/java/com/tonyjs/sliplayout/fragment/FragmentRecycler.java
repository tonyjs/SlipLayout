package com.tonyjs.sliplayout.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tonyjs.sliplayout.R;

/**
 * Created by tony.park on 14. 11. 13..
 */
public class FragmentRecycler extends Fragment {

    public static final String[] DATA =
            ("allin ball calculator dog facebook google hashtagram instagram jake wharton"
                    + " korea lolipop man nineold orc pushbullet quip recyclerview sliplayout trello"
                    + " umano vingle wechat xiaomi youtube zxing").split(" ");

    public interface OnViewCreatedListener{
        public void onViewCreated(RecyclerView recyclerView);
    }

    private RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(
                        getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new RecyclerAdapter(inflater, DATA));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Activity activity = getActivity();
        if (activity instanceof OnViewCreatedListener) {
            ((OnViewCreatedListener) activity).onViewCreated(mRecyclerView);
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
        private String[] mItems;
        private LayoutInflater mInflater;
        public RecyclerAdapter(LayoutInflater inflater, String[] items) {
            mInflater = inflater;
            mItems = items;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(mInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            String s = mItems[i];
            viewHolder.getTvTitle().setText(s);
        }

        @Override
        public int getItemCount() {
            return mItems.length;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public TextView getTvTitle() {
            return mTvTitle;
        }
    }
}