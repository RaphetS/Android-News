package com.ruanshuo.news.adapter;

import java.util.List;

import com.ruanshuo.news.bean.PicuterModle;
import com.ruanshuo.news.view.PhotoItemView;
import com.ruanshuo.news.view.PhotoItemView_;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListImageAdapter extends BaseAdapter {
	private List<PicuterModle> lists;
	private Context context;

	public ListImageAdapter(List<PicuterModle> lists, Context context) {
		this.lists = lists;
		this.context = context;
	}

	public void addAll(List<PicuterModle> list) {
		this.lists.addAll(list);
		notifyDataSetChanged();
	}

	public void setData(List<PicuterModle> list) {
		this.lists.clear();
		this.lists.addAll(list);

		notifyDataSetChanged();
	}

	public void clear() {
		this.lists.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		PhotoItemView photoItemView;

		if (convertView == null) {
			photoItemView = PhotoItemView_.build(context);
		} else {
			photoItemView = (PhotoItemView) convertView;
		}

		PicuterModle picuterModle = lists.get(position);

		photoItemView.setData(picuterModle.getTitle(), picuterModle.getPic());

		return photoItemView;
	}
}
