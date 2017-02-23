package com.ronin.rlib.manager;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * Created by Administrator on 2016/11/28.
 */
public final class AppManager {

    private static Context mContext;

    public static void init(Context context) {
        if (mContext == null) {
            mContext = context;
        }
    }

/*    public static AppManager getInstance() {
        return SingletonHolder.INSTANCE;
    }*/

    private AppManager() {
    }

    /**
     * 手机网络是否可用
     *
     * @return
     */
    public static boolean isNetworkConnected() {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * wifi是否连接
     *
     * @return
     */
    public static boolean isWifiConnected() {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 移动网络是否连接
     *
     * @return
     */
    public static boolean isMobileConnected() {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 随机产生一个汉字
     *
     * @return
     */
    public static char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            str = "汉";
        }
        return str.charAt(0);
    }

    /**
     * 判断当前app是否在在后台运行
     *
     * @return true-后台， false-前台
     */
    public static boolean isBackground() {
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(mContext.getPackageName())) {
                if (appProcess.importance == ActivityManager
                        .RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        // Calculate inSampleSize
        options.inSampleSize = 2;
        // calculateInSampleSize(options, 480, 480);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        // BitmapFactory.decodeFile(filePath, options);

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * @param metaName
     * @return
     */
    public static String getMetaValue(String metaName) {

        PackageManager packageManager =
                mContext.getPackageManager();
        try {
            ApplicationInfo info = packageManager.getApplicationInfo(mContext.getPackageName(),
                    PackageManager.GET_META_DATA);
            String metaValue = String.valueOf(info.metaData.getString(String.valueOf(metaName)));
            return metaValue;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return String.valueOf(metaName);
        }
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        return wm.getDefaultDisplay().getWidth() | dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return wm.getDefaultDisplay().getHeight() | dm.heightPixels;
    }

    /**
     * @param dp
     * @return
     */
    public static float dip2px(float dp) {
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }


    /**
     * @param px
     * @return
     */
    public static float px2dip(float px) {
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }


    /**
     * 得到netconfig.properties配置文件中的所有配置属性
     *
     * @return Properties对象
     */
    @SuppressLint("NewApi")
    public static Properties getConfProperties(int rawId) {
        Properties props = new Properties();
        try (InputStream in = mContext.getResources().openRawResource(rawId)) {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return props;
    }

    /**
     * 模拟按键
     *
     * @param KeyCode
     */
    public static void simulateKey(final int KeyCode) {
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     *
     */
    private static class SingletonHolder {
        private static AppManager INSTANCE = new AppManager();
    }
}
