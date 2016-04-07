package com.ruanshuo.news.activity;

import java.util.ArrayList;
import java.util.List;

import com.ruanshuo.news.R;
import com.ruanshuo.news.base.BaseActivity;
import com.ruanshuo.news.bean.Collection;
import com.ruanshuo.news.bean._User;
import com.ruanshuo.news.utils.NetWorkUtil;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class MyCollectionActivity extends BaseActivity {
	private ListView lvCollection;
	private _User currentUser;
	private List<Collection> collections;
	private ImageButton btnBack;
	private TextView tvNoData;

	@Override
	public void initViews() {
		setContentView(R.layout.activity_my_collection);
		lvCollection = (ListView) findViewById(R.id.lv_my_collection);
		btnBack = (ImageButton) findViewById(R.id.imgbtn_back_my_collection);
		tvNoData = (TextView) findViewById(R.id.tv_no_data_collection);
	}

	@Override
	public void initDatas() {
		if (!NetWorkUtil.hasNetWrok(getApplicationContext())) {
			tvNoData.setText("无网络连接");
			tvNoData.setVisibility(View.VISIBLE);
		} else {

			currentUser = BmobUser.getCurrentUser(getApplicationContext(), _User.class);
			BmobQuery<Collection> query = new BmobQuery<Collection>();
			query.addWhereEqualTo("userId", currentUser.getObjectId());
			query.findObjects(getApplicationContext(), new FindListener<Collection>() {

				@Override
				public void onSuccess(List<Collection> arg0) {
					collections = arg0;
					List<String> titles = new ArrayList<String>();
					for (Collection collection : arg0) {
						titles.add(collection.getNewsTitle());
					}
					if (titles.size() == 0) {
						tvNoData.setVisibility(View.VISIBLE);
					} else {
						ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
								android.R.layout.simple_list_item_1, titles);
						lvCollection.setAdapter(adapter);
					}

				}

				@Override
				public void onError(int arg0, String arg1) {
					tvNoData.setVisibility(View.VISIBLE);
				}
			});
		}
	}

	@Override
	public void initListener() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		lvCollection.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (collections != null) {
					Intent intent = new Intent(MyCollectionActivity.this, NewsDetailActivity.class);
					intent.putExtra("link", collections.get(position).getNewsUrl());
					intent.putExtra("newsId", collections.get(position).getNewsId());
					intent.putExtra("newsTitle", collections.get(position).getNewsTitle());
					startActivity(intent);
				}

			}
		});
	}

}
