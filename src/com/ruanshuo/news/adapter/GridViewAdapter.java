package com.ruanshuo.news.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ruanshuo.news.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter {
	private Context context;
	private List<String> bitmaps;

	public GridViewAdapter(Context ctx, List<String> datas) {
		context = ctx;
		bitmaps = datas;

	}

	public void addData(String pic) {
		bitmaps.add(pic);
	}

	@Override
	public int getCount() {

		return (bitmaps.size() + 1);
	}

	@Override
	public Object getItem(int position) {
		if (position <= bitmaps.size()) {
			return bitmaps.get(position);
		} else {
			return null;
		}

	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_gridview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading_pic)
				.showImageOnFail(R.drawable.load_pic_fail).cacheInMemory(true).cacheOnDisk(true).build();
		if (position == bitmaps.size()) {
			ImageLoader.getInstance().displayImage("drawable://" + R.drawable.add_pic, holder.iv, options);

		} else {
			ImageLoader.getInstance().displayImage(bitmaps.get(position), holder.iv, options);
		}

		return convertView;
	}

	class ViewHolder {
		ImageView iv;
	}
}
