package com.ronin.rlib.base;


import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Description 通用ViewHolde类
 * @fileName ViewHolder.java
 * @author ronindong
 * @Date 2015年10月12日 上午10:20:54
 */
public class CViewHolder {

	/**
	 * listview 单击item的位置
	 */
	private int mPosition;
	private View mConvertView;
	private SparseArray<View> mViews;

	public CViewHolder(Context mContext, ViewGroup parent, int layoutId,
			int position) {
		super();
		this.mPosition = position;
		mViews = new SparseArray<>();
		mConvertView = LayoutInflater.from(mContext).inflate(layoutId, parent,
				false);
		mConvertView.setTag(this);

	}

	public static CViewHolder get(Context mContext, int layoutId,
			View convertView, ViewGroup parent, int position) {
		if (convertView == null) {
			return new CViewHolder(mContext, parent, layoutId, position);
		} else {
			CViewHolder holder = (CViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}

	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int resId) {
		View view = mViews.get(resId);
		if (view == null) {
			view = mConvertView.findViewById(resId);
			mViews.put(resId, view);
		}
		return (T) view;
	}

	public View getmConvertView() {
		return mConvertView;
	}
	
	public int getmPosition() {
		return mPosition;
	}
	
	public void setmPosition(int mPosition) {
		this.mPosition = mPosition;
	}
	/**
	 * 获取<b>TextView<b>控件
	 * 
	 * @param resId
	 * @return
	 */
	public TextView getTextView(int resId) {
		TextView tv = getView(resId);
		return tv;
	}
	/**
	 * 获取<b>TextView<b>控件,并设置text
	 * 
	 * @param resId
	 * @param text
	 *            若为空串或者null，则设置为"-"
	 * @return
	 */
	public TextView getTextView(int resId, String text) {
		TextView tv = getTextView(resId);
		if (!TextUtils.isEmpty(text)) {
			tv.setText(text);
		} else {
			tv.setText("-");
		}
		return tv;
	}

	/**
	 * 获取<b>ImageView<b>控件
	 * 
	 * @param resId
	 * @return
	 */
	public ImageView getImageView(int resId) {
		ImageView iv = getView(resId);
		return iv;
	}

	/**
	 * 获取<b>Button<b>控件
	 * 
	 * @param resId
	 * @return
	 */
	public Button getButton(int resId) {
		Button btn = getView(resId);
		return btn;
	}

}
