package com.tonyjs.sliplayout.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.tonyjs.sliplayout.lib.SlipLayout;

/**
 * Created by tony.park on 14. 11. 13..
 */
public abstract class SlipLayoutFragment extends Fragment {
    public static final String[] DATA =
            ("allin ball calculator dog facebook google hashtagram instagram jake wharton"
            + " korea lolipop man nineold orc pushbullet quip recyclerview sliplayout trello"
            + " umano vingle wechat xiaomi youtube zxing").split(" ");

    public interface OnSlipLayoutCreatedListener {
        public void onSlipLayoutCreated(SlipLayout slipLayout);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof OnSlipLayoutCreatedListener) {
            ((OnSlipLayoutCreatedListener) getActivity()).onSlipLayoutCreated(getSlipLayout());
        }
    }

    public abstract SlipLayout getSlipLayout();
}
