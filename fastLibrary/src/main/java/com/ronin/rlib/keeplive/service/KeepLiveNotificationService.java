package com.ronin.rlib.keeplive.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;

import com.ronin.rlib.keeplive.helper.ScreenBroadcastListener;
import com.ronin.rlib.keeplive.helper.ScreenManager;
import com.vise.log.ViseLog;

/**
 * Created by ronindong on 2017/2/23.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class KeepLiveNotificationService extends NotificationListenerService
        implements IKeepLiveListener {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        ViseLog.d("KeepLiveNotificationService->onNotificationPosted");
        onKeepLiveActivity();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        ViseLog.d("KeepLiveNotificationService->onNotificationRemoved");
        onKeepLiveActivity();
    }

    /**
     * 程序启动时，开启保活服务KeepLiveService
     *
     * @param cx
     */
    @Override
    public void startKeepLiveService(Context cx) {
        Intent intent = new Intent(cx, KeepLiveNotificationService.class);
        cx.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_REDELIVER_INTENT;
    }

    private void onKeepLiveActivity() {
        //屏幕关闭的时候启动一个1像素的Activity，开屏的时候关闭Activity
        final ScreenManager screenManager = ScreenManager.getInstance(KeepLiveNotificationService.this);
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
    }

}
