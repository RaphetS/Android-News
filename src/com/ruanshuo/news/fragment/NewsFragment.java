package com.ruanshuo.news.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ruanshuo.news.R;
import com.ruanshuo.news.adapter.NewsFragmentPagerAdapter;
import com.ruanshuo.news.http.Url;
import com.viewpagerindicator.TabPageIndicator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class NewsFragment extends Fragment {
	private ViewPager vp_news;
	private TabPageIndicator indicator;
	private ImageButton btn_next;
	private List<BaseNewsFragment> list = new ArrayList<BaseNewsFragment>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news, null);
		vp_news = (ViewPager) view.findViewById(R.id.vp_news);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		btn_next = (ImageButton) view.findViewById(R.id.btn_next);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		BaseNewsFragment hot = new BaseNewsFragment("");
		BaseNewsFragment bjFragment = new BaseNewsFragment(Url.BeiJingId);
		BaseNewsFragment shFragment = new BaseNewsFragment(Url.SheHuiId);
		BaseNewsFragment jsFragment = new BaseNewsFragment(Url.JunShiId);
	
		BaseNewsFragment cjFragment = new BaseNewsFragment(Url.CaiJingId);
		BaseNewsFragment kjFragment = new BaseNewsFragment(Url.KeJiId);
		BaseNewsFragment tyFragment = new BaseNewsFragment(Url.TiYuId);
		BaseNewsFragment ylFragment = new BaseNewsFragment(Url.YuLeId);
		BaseNewsFragment ssFragment = new BaseNewsFragment(Url.ShiShangId);
		BaseNewsFragment xhFragment = new BaseNewsFragment(Url.XiaoHuaId);
		list.add(hot);
		list.add(bjFragment);
		list.add(shFragment);
		list.add(jsFragment);
		list.add(cjFragment);
		list.add(kjFragment);
		list.add(tyFragment);
		list.add(ylFragment);
		list.add(ssFragment);
		list.add(xhFragment);

		FragmentPagerAdapter adapter = new NewsFragmentPagerAdapter(getChildFragmentManager(),list);
		vp_news.setAdapter(adapter);
		indicator.setViewPager(vp_news);
		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int currentItem = vp_news.getCurrentItem();
				vp_news.setCurrentItem(++currentItem);

			}
		});
	}

}
