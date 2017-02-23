package com.ronin.rlib.listener;

import android.os.Handler;
import android.view.View;

/**
 * Created by Administrator on 2016/9/23.
 */
public abstract class OnDoubleClickListener implements View.OnClickListener {

    private boolean mFlag = false;
    private int restTime = 500;
    private Handler mHandler = new Handler();

    @Override
    public void onClick(View view) {
        if (!mFlag) {
            mFlag = true;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFlag = false;
                }
            }, restTime);
        } else {
            mFlag = false;
            onDoubleClick(view);
        }
    }

    public abstract void onDoubleClick(View view);
}
