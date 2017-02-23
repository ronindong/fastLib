package com.ronin.rlib.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @Description ListView控件，基础抽象adapter，实现基本的操作方法
 * @Filename ListViewAbstractBaseAdapter.java
 * @author dhl
 * @Date 2015年5月17日 下午8:56:55
 * @param <T>
 */
public abstract class AbstractBaseAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> mData;

	public AbstractBaseAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public T getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public final View getView(int position, View convertView, ViewGroup parent) {

		final T t = getItem(position);
		CViewHolder holder = CViewHolder.get(mContext, getLayoutId(),
				convertView, parent, position);
		convert(holder, t);

		return holder.getmConvertView();
	}

	/**
	 * 获取布局文件资源id
	 * 
	 * @return
	 */
	public abstract int getLayoutId();

	public abstract void convert(final CViewHolder holder, final T t);

	public List<T> getmData() {
		return mData;
	}

	public void setmData(List<T> mData) {
		this.mData = mData;
	}

}
