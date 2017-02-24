package com.ronin.rlib.keeplive.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.vise.log.ViseLog;

/**
 * 保活actvity
 * Created by ronindong on 2017/2/23.
 *
 * @TODO APP进程
 * 注：(进入adb shell后，)
 * （1）ps|grep com.ronin.fastlib ——查看包名对应app的进程信息
 * —— 例如ps|grep com.ronin.fastlib
 * u0_a1011（USER进程当前用户）  24477（进程ID） 465（进程的父进程ID）
 * 2142344（进程的虚拟内存大小） 19984（实际驻留”在内存中”的内存大小） ffffffff 00000000 S com.ronin.fastlib:pushservice
 * u0_a1011  24586 465   2131500 15868 ffffffff 00000000 S com.ronin.fastlib:push
 * u0_a1011  28247 465   2227636 167384 ffffffff 00000000 S com.ronin.fastlib
 * （2）cat proc/24477/oom_adj ——oom_adj的值越小，进程的优先级越高，普通进程oom_adj值是大于等于0的，
 * 而系统进程oom_adj的值是小于0的，我们可以通过cat /proc/进程id/oom_adj可以看到当前进程的adj值。
 */

public class KeepLiveActivity extends Activity {
    public static final String TAG = KeepLiveActivity.class.getSimpleName();

    public static void actionToLiveActivity(Context pContext) {
        Intent intent = new Intent(pContext, KeepLiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViseLog.d("KeepLiveActivity-->onCreate");

        Window window = getWindow();
        //放在左上角
        window.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams attributes = window.getAttributes();
        //宽高设计为1个像素
        attributes.width = 1;
        attributes.height = 1;
        //起始坐标
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);

        ScreenManager.getInstance(this).setActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViseLog.d("KeepLiveActivity-->onDestroy");
    }

}
