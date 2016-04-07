package com.ruanshuo.news.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ruanshuo.news.R;
import com.ruanshuo.news.bean.CommentBean;
import com.ruanshuo.news.bean.NewModle;
import com.ruanshuo.news.bean.NewsItem;
import com.ruanshuo.news.view.NewItemView;
import com.ruanshuo.news.view.NewItemView_;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class ListNewsAdapter extends BaseAdapter {

	private List<NewModle> mDatas;
	private Context ctx;
	private String currentItem;

	public ListNewsAdapter(List<NewModle> newsItems, Context context) {
		this.mDatas = newsItems;

		ctx = context;
	}

	public void addAll(List<NewModle> newsItems) {
		this.mDatas.addAll(newsItems);
		notifyDataSetChanged();
	}

	public void setData(List<NewModle> newsItems) {
		this.mDatas.clear();
		this.mDatas.addAll(newsItems);
		notifyDataSetChanged();
	}

	public void clear() {
		mDatas.clear();
		notifyDataSetChanged();
	}

	public void currentItem(String item) {
		this.currentItem = item;
	}

	@Override
	public int getCount() {

		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		NewItemView newItemView;
		if (convertView == null) {
			newItemView = NewItemView_.build(ctx);
		} else {
			newItemView = (NewItemView) convertView;
		}

		NewModle newModle = mDatas.get(position);
		if (newModle.getImagesModle() == null) {
			newItemView.setTexts(newModle.getTitle(), newModle.getDigest(), newModle.getImgsrc(), currentItem);
		} else {
			newItemView.setImages(newModle);
		}

		return newItemView;
	}

}
