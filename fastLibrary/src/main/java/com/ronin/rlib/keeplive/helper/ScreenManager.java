package com.ronin.rlib.keeplive.helper;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by ronindong on 2017/2/23.
 */

public class ScreenManager {
    private Context mContext;

    private WeakReference<Activity> mAyWeakReference;

    public static ScreenManager sInstance;

    public static ScreenManager getInstance(Context cx) {
        if (sInstance == null) {
            sInstance = new ScreenManager(cx.getApplicationContext());
        }
        return sInstance;
    }

    private ScreenManager(Context cx) {
        this.mContext = cx;
    }

    public void setActivity(Activity ay) {
        mAyWeakReference = new WeakReference<>(ay);
    }

    /**
     * 启动 KeepLiveActivity
     */
    public void startActivity() {
        KeepLiveActivity.actionToLiveActivity(mContext);
    }

    /**
     *  结束掉KeepLiveActivity
     */
    public void finishActivity() {
        if (mAyWeakReference != null) {
            Activity activity = mAyWeakReference.get();
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
