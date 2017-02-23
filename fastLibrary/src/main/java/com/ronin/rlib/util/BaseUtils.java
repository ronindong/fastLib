package com.ronin.rlib.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ronindong
 * @fileName BaseUtils.java
 * @Description 工具类
 * @Date 2015年9月30日 下午3:02:59
 */
public final class BaseUtils {

    public enum DATE_FORMAT {
        DATE_TIME, DATE_MM_dd, DATE, DATE_HH_MM
    }

    private static SimpleDateFormat mSdf = new SimpleDateFormat();

    /**
     * 检查手机号码是否合法 <br/>
     * 规则一：^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$ <br/>
     * 规则二：^((1[3,5,8][0-9])|(14[5,7])|(17[0,,6,7,8]))\\d{8}$ <br/>
     * 增加对14，17段的支持<br/>
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNum(String mobiles) {
        String pattern1 = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        String pattern2 = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,,6,7,8]))\\d{8}$";
        Pattern p = Pattern.compile(pattern2);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * @param milliseconds 时间戳
     * @param format       格式：yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static final String longToDateTime(long milliseconds,
                                              DATE_FORMAT format) {
        switch (format) {
            case DATE_HH_MM:
                mSdf.applyPattern("MM-dd  HH:mm");
                break;
            case DATE:
                mSdf.applyPattern("yyyy-MM-dd");
                break;
            case DATE_TIME:
                mSdf.applyPattern("yyyy-MM-dd HH:mm");
                break;

            case DATE_MM_dd:
                mSdf.applyPattern("yyyy年MM月dd日");
                break;
        }
        Date date = new Date(milliseconds);
        return mSdf.format(date);
    }



    /**
     * 把对象转成string
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static String objToString(Object obj) {

        ObjectOutputStream objos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            objos = new ObjectOutputStream(bos);
            objos.writeObject(obj);

            String listStr = new String(Base64.encode(bos.toByteArray(),
                    Base64.DEFAULT));
            if (!TextUtils.isEmpty(listStr)) {
                return listStr;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (null != objos) {
                    objos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 把对象转成string
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object strToObj(String str) {

        Object obj = null;
        if (!TextUtils.isEmpty(str)) {

            ObjectInputStream objis = null;
            try {
                byte[] buf = Base64.decode(str.getBytes(), Base64.DEFAULT);
                ByteArrayInputStream bis = new ByteArrayInputStream(buf);
                objis = new ObjectInputStream(bis);
                obj = objis.readObject();
            } catch (StreamCorruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OptionalDataException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    if (null != objis) {
                        objis.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * @param imgPath
     * @param imgFormat 图片格式
     * @return
     */
    public static String imgToBase64(String imgPath, CompressFormat imgFormat) {
        Bitmap bitmap = null;
        if (imgPath != null && imgPath.length() > 0) {
            bitmap = readBitmap(imgPath);
        }
        if (bitmap == null) {
            // bitmap not found!!
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            if (null != bitmap) {
                bitmap.compress(imgFormat, 100, out);
            }
            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
