package com.ronin.rlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ronin.rlib.manager.AppManager;

/**
 * Created by Administrator on 2016/11/28.
 */

public class NButton extends Button implements View.OnClickListener {

    private Context mContext;
    IOnClickListener listener;
    private String text = "网络异常,请检查网络！";

    private boolean isNetwork = false;


    public NButton(Context context) {
        super(context);
        init(context);
    }

    public NButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.setOnClickListener(this);
    }

    /**
     * 点击事件
     *
     * @param listener
     */
    public void setOnClickListener(IOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (AppManager.isNetworkConnected()) {
            if (listener != null) {
                listener.onClick(v);
            }
        } else {
            Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
        }
    }

    public interface IOnClickListener {
        void onClick(View v);
    }

}
