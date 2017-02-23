package com.ronin.rlib.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * activity基类
 * author：ronindong
 */
public abstract class BaseActivity extends AppCompatActivity {


    protected StaticInnerHandler mHandler = new StaticInnerHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadViewLayout();
        ButterKnife.bind(this);
        findView();
        setListener();
    }

    public abstract void handleMessage(Message msg);
    /**
     *
     */
    protected abstract void loadViewLayout();

    /**
     *
     */
    protected abstract void findView();

    /**
     *
     */
    protected abstract void setListener();



    /**
     * handler使用若引用方式，避免内存泄漏
     */
    public static class StaticInnerHandler extends Handler {

        private BaseActivity mAy;
        private WeakReference<BaseActivity> weakReference = null;

        public StaticInnerHandler(BaseActivity baseActivity) {
            if (weakReference == null) {
                weakReference = new WeakReference<BaseActivity>(baseActivity);
            }
            mAy = weakReference.get();
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mAy != null) {
                mAy.handleMessage(msg);
            }
        }
    }

}
