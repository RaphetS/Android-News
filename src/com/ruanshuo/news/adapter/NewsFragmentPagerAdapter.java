package com.ruanshuo.news.adapter;

import java.util.List;

import com.ruanshuo.news.fragment.BaseNewsFragment;
import com.ruanshuo.news.http.Url;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
	private String[] titles = new String[] { "热点", "北京", "社会", "军事", "财经", "科技", "体育 ", "娱乐 ", "时尚 ", "笑话 " };
	private List<BaseNewsFragment> list;

	public NewsFragmentPagerAdapter(FragmentManager fm, List<BaseNewsFragment> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {

		return list.get(arg0);
	}

	@Override
	public int getCount() {

		return titles.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {

		return titles[position % titles.length];
	}

}
