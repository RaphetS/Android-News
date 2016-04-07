package com.ruanshuo.news.adapter;

import com.ruanshuo.news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommunityMenuAdapter extends BaseAdapter {
	private String[] menus=new String[]{"社区动态"};
	private LayoutInflater inflater;
	
	public CommunityMenuAdapter(Context context) {
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return menus.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return menus[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.community_menu_item, null);
			holder.tvMenu=(TextView) convertView.findViewById(R.id.tv_community_menu);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvMenu.setText(menus[position]);
		return convertView;
	}
	class ViewHolder{
		TextView tvMenu;
		ImageView iv;
	}
}
