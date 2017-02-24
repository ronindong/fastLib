package com.ronin.rlib.keeplive.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by ronindong on 2017/2/23.
 */

public class ScreenBroadcastListener {
    private Context mContext;

    private ScreenBroadcastReceiver mScreenReceiver;

    private static ScreenStateListener mListener;

    public ScreenBroadcastListener(Context context) {
        mContext = context.getApplicationContext();
        mScreenReceiver = new ScreenBroadcastReceiver();
    }

    public interface ScreenStateListener {

        void onScreenOn();

        void onScreenOff();
    }

    /**
     * screen状态广播接收者
     */
    private static class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)
                    ||Intent.ACTION_USER_PRESENT.equals(action)) { // 开屏 或 用户解锁
                mListener.onScreenOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
                mListener.onScreenOff();
            }
        }
    }

    public void registerListener(ScreenStateListener listener) {
        mListener = listener;
        registerListener();
    }

    private void registerListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mContext.registerReceiver(mScreenReceiver, filter);
    }
}
