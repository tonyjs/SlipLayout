package com.tonyjs.sliplayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.tonyjs.sliplayout.fragment.FragmentList;
import com.tonyjs.sliplayout.fragment.FragmentRecycler;
import com.tonyjs.sliplayout.fragment.FragmentScroll;
import com.tonyjs.sliplayout.lib.SlipLayoutController;
import com.tonyjs.sliplayout.lib.SlipScrollView;

/**
 * Created by im026 on 2014. 9. 19..
 */
public class MainActivity extends ActionBarActivity
        implements View.OnClickListener,
        FragmentList.OnViewCreatedListener,
        FragmentRecycler.OnViewCreatedListener,
        FragmentScroll.OnViewCreatedListener {

    private SlipLayoutController mSlipLayoutController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlipLayoutController = new SlipLayoutController(findViewById(R.id.v_target));

        replace(Type.LIST);
    }

    @Override
    public void onViewCreated(ListView listView) {
        mSlipLayoutController.setScrollableView(listView);
    }

    @Override
    public void onViewCreated(RecyclerView recyclerView) {
        mSlipLayoutController.setScrollableView(recyclerView);
    }

    @Override
    public void onViewCreated(SlipScrollView slipScrollView) {
        mSlipLayoutController.setScrollableView(slipScrollView);
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
            case R.id.btn_to_with_toolbar_activity:
                startActivity(new Intent(this, WithToolbarActivity.class));
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

    enum Type {
        LIST, RECYCLER, SCROLL
    }
}
