package com.tonyjs.sliplayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tonyjs.sliplayout.fragment.FragmentList;
import com.tonyjs.sliplayout.fragment.FragmentRecycler;
import com.tonyjs.sliplayout.fragment.FragmentScroll;
import com.tonyjs.sliplayout.fragment.SlipLayoutFragment;
import com.tonyjs.sliplayout.lib.SlipLayout;

import java.util.ArrayList;

/**
 * Created by im026 on 2014. 9. 19..
 */
public class MainActivity extends ActionBarActivity
        implements SlipLayoutFragment.OnSlipLayoutCreatedListener, View.OnClickListener{

    enum Type {
        LIST, RECYCLER, SCROLL
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replace(Type.LIST);
    }

    @Override
    public void onSlipLayoutCreated(SlipLayout slipLayout) {
        slipLayout.setTargetView(getTargetView());
    }

    public View getTargetView() {
        return findViewById(R.id.v_target);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_list:
                replace(Type.LIST);
                break;
            case R.id.btn_recycler:
                replace(Type.RECYCLER);
                break;
            case R.id.btn_scroll:
                replace(Type.SCROLL);
                break;
        }
    }

    void replace(Type type) {
        Fragment fragment = null;
        switch (type) {
            case LIST:
                fragment = new FragmentList();
                break;
            case RECYCLER:
                fragment = new FragmentRecycler();
                break;
            case SCROLL:
                fragment = new FragmentScroll();
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.container, fragment)
                .commitAllowingStateLoss();
    }
}
