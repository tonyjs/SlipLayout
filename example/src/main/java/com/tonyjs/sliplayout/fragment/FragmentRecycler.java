package com.tonyjs.sliplayout.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tonyjs.sliplayout.R;
import com.tonyjs.sliplayout.lib.SlipLayout;

/**
 * Created by tony.park on 14. 11. 13..
 */
public class FragmentRecycler extends SlipLayoutFragment {

    private SlipLayout mSlipLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        mSlipLayout = (SlipLayout) rootView.findViewById(R.id.slip_layout);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerAdapter(inflater, DATA));
        mSlipLayout.setRecyclerView(recyclerView);
        return rootView;
    }

    @Override
    public SlipLayout getSlipLayout() {
        return mSlipLayout;
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