package com.ronin.rlib.base;

import android.app.Application;
import android.util.Log;

import com.ronin.rlib.keeplive.KeepLiveManager;
import com.ronin.rlib.manager.AppManager;
import com.ronin.rlib.manager.SPManager;
import com.umeng.analytics.MobclickAgent;
import com.vise.log.ViseLog;
import com.vise.log.inner.DefaultTree;
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
        initLogConfig();
        AppManager.init(this);
        SPManager.init(this);
        configOkhttp();
//        initUmengAnalytics();
        startKeepLiveService();     //开启保活服务

    }

    /**
     * 初始化日志配置
     */
    private void initLogConfig() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(true)//是否排版显示
                .configTagPrefix("ViseLog")//设置标签前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                .configLevel(Log.VERBOSE);//设置日志最小输出级别，默认Log.VERBOSE
        ViseLog.plant(new DefaultTree());//添加打印日志信息到Logcat的树
    }

    /**
     *
     */
    protected  void startKeepLiveService(){
        KeepLiveManager.getInstance().init(this, KeepLiveManager.ServiceType.JOB_SERVICE);
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
