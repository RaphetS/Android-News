package com.ruanshuo.news.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.ruanshuo.news.R;
import com.ruanshuo.news.adapter.ListDynimicAdapter;
import com.ruanshuo.news.base.BaseActivity;
import com.ruanshuo.news.bean.DynimicBean;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

public class DynimicActivity extends BaseActivity implements IXListViewRefreshListener, IXListViewLoadMore {
	private XListView xlv;
	private ImageButton btnBack, btnAdd;
	private TextView tvNoData;
	private List<DynimicBean> dynimics;
	private ListDynimicAdapter adapter;

	@Override
	public void initViews() {
		setContentView(R.layout.activity_dynimic);
		xlv = (XListView) findViewById(R.id.xlv_dynimic);
		btnBack = (ImageButton) findViewById(R.id.imgbtn_back_dynimic);
		btnAdd = (ImageButton) findViewById(R.id.imgBtn_add_dynimic);
		tvNoData = (TextView) findViewById(R.id.tv_no_data_dynimic);
	}

	@Override
	public void initDatas() {
		dynimics = new ArrayList<DynimicBean>();
		adapter = new ListDynimicAdapter(getApplicationContext(), dynimics);
		xlv.setAdapter(adapter);
		xlv.setPullRefreshEnable(this);
		xlv.setPullLoadEnable(this);
		xlv.startRefresh();

	}

	@Override
	public void initListener() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DynimicActivity.this, AddDynimicActivity.class);
				startActivity(intent);

			}
		});
	}

	@Override
	public void onLoadMore() {
		xlv.stopLoadMore();
	}

	@Override
	public void onRefresh() {
		BmobQuery<DynimicBean> dynimicQuery = new BmobQuery<DynimicBean>();
		dynimicQuery.order("-createdAt");
		dynimicQuery.setLimit(20);
		dynimicQuery.findObjects(getApplicationContext(), new FindListener<DynimicBean>() {

			@Override
			public void onSuccess(List<DynimicBean> arg0) {
				if (arg0.size() == 0) {
					tvNoData.setVisibility(View.VISIBLE);
				} else {
					adapter.setData(arg0);
					adapter.notifyDataSetChanged();
					// xlv.setAdapter(adapter);
					xlv.stopRefresh();
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				tvNoData.setVisibility(View.VISIBLE);
			}
		});
	}

}
