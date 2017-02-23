package com.ronin.rlib.base;

import android.app.Application;

import com.ronin.rlib.keeplive.KeepLiveService;
import com.ronin.rlib.manager.AppManager;
import com.ronin.rlib.manager.SPManager;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by ronin on 2016/12/2.
 */

public abstract class AbsApplication extends Application {

    private static boolean DEBUG = false;

    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.init(this);
        SPManager.init(this);
        configOkhttp();
        initUmengAnalytics();
        startKeepLiveService();     //开启保活服务
    }

    /**
     *
     */
    protected  void startKeepLiveService(){
        KeepLiveService.startLiveService(this);
    }


    /**
     * 配置okhttp
     */
    private void configOkhttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }

    /**
     *
     */
    private void initUmengAnalytics() {
        MobclickAgent.setDebugMode(DEBUG);
        String umengKey = AppManager.getMetaValue("UMENG_APPKEY");
        String umengChannel = AppManager.getMetaValue("UMENG_CHANNEL");
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(this,
                umengKey, umengChannel, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.startWithConfigure(config);
    }

}
