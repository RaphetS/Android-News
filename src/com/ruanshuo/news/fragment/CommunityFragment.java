package com.ruanshuo.news.fragment;

import com.ruanshuo.news.R;
import com.ruanshuo.news.activity.DynimicActivity;
import com.ruanshuo.news.adapter.CommunityMenuAdapter;
import com.ruanshuo.news.base.BaseFragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CommunityFragment extends BaseFragment {
	private ListView lvMenu;

	@Override
	protected int getContentView() {
		return R.layout.fragment_community;
	}

	@Override
	protected void initData() {
		CommunityMenuAdapter adapter = new CommunityMenuAdapter(getContext());
		lvMenu.setAdapter(adapter);
	}

	@Override
	protected void initListener() {
		lvMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == 0) {
					Intent intent = new Intent(getActivity(), DynimicActivity.class);
					startActivity(intent);
				}
			}

		});

	}

	@Override
	protected void initView() {
		lvMenu = (ListView) rootView.findViewById(R.id.lv_community_menu);

	}

}
