package com.ruanshuo.news.adapter;

import java.util.List;

import com.ruanshuo.news.fragment.BaseImageFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PhotoFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<BaseImageFragment> list;

	public PhotoFragmentPagerAdapter(FragmentManager fm, List<BaseImageFragment> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

}
