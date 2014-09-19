package com.tonyjs.sliplayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.tonyjs.sliplayout.lib.SlipLayout;

import java.util.ArrayList;

/**
 * Created by im026 on 2014. 9. 19..
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlipLayout slipLayout = (SlipLayout) findViewById(R.id.slip_layout);
        ListView listView = (ListView) findViewById(R.id.list_view);
        View targetView = findViewById(R.id.v_target);
        slipLayout.setListView(listView);
        slipLayout.setTargetView(targetView);

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sList));
    }

    private static final ArrayList<String> sList = new ArrayList<String>() {
        {
            add("a");
            add("b");
            add("c");
            add("d");
            add("e");
            add("f");
            add("g");
            add("h");
            add("i");
            add("j");
            add("k");
            add("l");
            add("m");
            add("n");
            add("o");
            add("p");
            add("q");
            add("r");
            add("s");
            add("t");
            add("u");
            add("v");
            add("w");
            add("x");
            add("y");
            add("z");
        }
    };


}
