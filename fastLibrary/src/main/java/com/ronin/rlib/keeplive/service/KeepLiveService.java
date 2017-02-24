package com.ronin.rlib.keeplive.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ronin.rlib.keeplive.helper.ScreenBroadcastListener;
import com.ronin.rlib.keeplive.helper.ScreenManager;

/**
 * Created by ronindong on 2017/2/23.
 */

public class KeepLiveService extends Service implements IKeepLiveListener {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //屏幕关闭的时候启动一个1像素的Activity，开屏的时候关闭Activity
        final ScreenManager screenManager = ScreenManager.getInstance(KeepLiveService.this);
        ScreenBroadcastListener listener = new ScreenBroadcastListener(this);
        listener.registerListener(new ScreenBroadcastListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                screenManager.finishActivity();
            }

            @Override
            public void onScreenOff() {
                screenManager.startActivity();
            }
        });
        return START_REDELIVER_INTENT;
    }

    /**
     * 程序启动时，开启保活服务KeepLiveService
     */
    @Override
    public void startKeepLiveService(Context cx) {
        Intent intent = new Intent(cx, KeepLiveService.class);
        cx.startService(intent);
    }
}
