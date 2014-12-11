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
import com.tonyjs.sliplayout.lib.SlipLayout;
import com.tonyjs.sliplayout.lib.SlipLayoutController;
import com.tonyjs.sliplayout.lib.SlipScrollView;

/**
 * Created by im026 on 2014. 9. 19..
 */
public class MainActivity extends ActionBarActivity
        implements View.OnClickListener,
                    FragmentList.OnViewCreatedListener,
                    FragmentRecycler.OnViewCreatedListener,
                    FragmentScroll.OnViewCreatedListener{

    enum Type {
        LIST, RECYCLER, SCROLL
    }

    private SlipLayoutController mSlipLayoutController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlipLayoutController = new SlipLayoutController(this);
        mSlipLayoutController.setTargetView(getTargetView());
        replace(Type.LIST);
    }

    @Override
    public void onViewCreated(ListView listView) {
        mSlipLayoutController.setListView(listView);
    }

    @Override
    public void onViewCreated(RecyclerView recyclerView) {
        mSlipLayoutController.setRecyclerView(recyclerView);
    }

    @Override
    public void onViewCreated(SlipScrollView slipScrollView) {
        mSlipLayoutController.setScrollView(slipScrollView);
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
}
