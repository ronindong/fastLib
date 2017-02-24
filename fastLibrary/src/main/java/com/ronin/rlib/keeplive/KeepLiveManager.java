package com.ronin.rlib.keeplive;

import android.content.Context;

import com.ronin.rlib.keeplive.service.IKeepLiveListener;
import com.ronin.rlib.keeplive.service.KeepLiveForeService;
import com.ronin.rlib.keeplive.service.KeepLiveJobService;
import com.ronin.rlib.keeplive.service.KeepLiveNotificationService;
import com.ronin.rlib.keeplive.service.KeepLiveService;

/**
 * Created by ronindong on 2017/2/24.
 */

public final class KeepLiveManager {

    private Context mContext;
    private IKeepLiveListener mListener;
    private static KeepLiveManager mInstance;

    private KeepLiveManager() {

    }

    public static KeepLiveManager getInstance() {
        if (mInstance == null) {
            synchronized (KeepLiveManager.class) {
                if (mInstance == null) {
                    mInstance = new KeepLiveManager();
                }
            }
        }
        return mInstance;
    }


    /**
     * @param cx
     * @param type
     */
    public void init(Context cx, ServiceType type) {
        if (cx != null) {
            mContext = cx.getApplicationContext();
            if (null != type) {
                switch (type) {
                    case NORMAL:
                        mListener = new KeepLiveService();
                        break;
                    case FORE_SERVICE:
                        mListener = new KeepLiveForeService();
                        break;
                    case NOTIFICATION_SERVICE:
                        mListener = new KeepLiveNotificationService();
                        break;
                    case JOB_SERVICE:
                        mListener = new KeepLiveJobService();
                        break;
                }

                if (mListener != null) {
                    mListener.startKeepLiveService(mContext);
                }
            } else {
                throw new RuntimeException("ServiceType is error and startKeepLiveService failed!");
            }

        } else {
            throw new RuntimeException("Context cann't null");
        }
    }


    public enum ServiceType {
        NORMAL, //解锁屏幕启动一个透明1px的activity的保活方式
        FORE_SERVICE, //前台通知保活 --oom_adj=2
        NOTIFICATION_SERVICE,   //api大于18，系统任意通知的发送和移除的方式保活
        JOB_SERVICE     //SheduleJob服务方式保活
    }

}
