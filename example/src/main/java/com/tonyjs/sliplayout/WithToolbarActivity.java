package com.tonyjs.sliplayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tonyjs.sliplayout.lib.MultiSlipScrollView;


public class WithToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MultiSlipScrollView scrollView = (MultiSlipScrollView) findViewById(R.id.scroll_view);

        int actionBarSize = (int) (getResources().getDisplayMetrics().density * 56);
        scrollView.addOnScrollCallback(
                MultiSlipScrollView.getScrollCallback(this, toolbar, actionBarSize));
        View emptyView = findViewById(R.id.empty_view);
        scrollView.addOnScrollCallback(
                MultiSlipScrollView.getScrollCallback(this, emptyView, actionBarSize));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_with_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
