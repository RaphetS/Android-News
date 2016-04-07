package com.ruanshuo.news.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ruanshuo.news.R;
import com.ruanshuo.news.adapter.PhotoFragmentPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class ImageFragment extends Fragment {
	private ViewPager mViewPager;
	private RadioButton rbtnJx, rbtnQt, rbtnMt, rbtnGs;
	private List<BaseImageFragment> list;
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_image, null);
		findView();
		initData();
		InitListener();

		return v;
	}

	private void findView() {
		mViewPager = (ViewPager) v.findViewById(R.id.viewPager_img);
		rbtnJx = (RadioButton) v.findViewById(R.id.rbtnJingxuan);
		rbtnQt = (RadioButton) v.findViewById(R.id.rbtnQutu);
		rbtnMt = (RadioButton) v.findViewById(R.id.rbtnMeitu);
		rbtnGs = (RadioButton) v.findViewById(R.id.rbtnStory);

	}

	private void initData() {
		list = new ArrayList<BaseImageFragment>();
		BaseImageFragment JXFragment = new BaseImageFragment(1);
		BaseImageFragment MTFragment = new BaseImageFragment(2);
		BaseImageFragment QTFragment = new BaseImageFragment(3);
		BaseImageFragment TYFragment = new BaseImageFragment(4);
		list.add(JXFragment);
		list.add(MTFragment);
		list.add(QTFragment);
		list.add(TYFragment);
		PhotoFragmentPagerAdapter adapter = new PhotoFragmentPagerAdapter(getChildFragmentManager(), list);
		mViewPager.setAdapter(adapter);

	}

	private void InitListener() {
		rbtnJx.setOnClickListener(new RBtnClickListenr());
		rbtnMt.setOnClickListener(new RBtnClickListenr());
		rbtnQt.setOnClickListener(new RBtnClickListenr());
		rbtnGs.setOnClickListener(new RBtnClickListenr());
		mViewPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {

			switch (arg0) {
			case 0:
				setRadioBtnFlag(true, false, false, false);
				break;
			case 1:
				setRadioBtnFlag(false, true, false, false);
				break;
			case 2:
				setRadioBtnFlag(false, false, true, false);
				break;
			case 3:
				setRadioBtnFlag(false, false, false, true);
				break;

			default:
				break;
			}

		}

		private void setRadioBtnFlag(boolean b1, boolean b2, boolean b3, boolean b4) {
			rbtnJx.setChecked(b1);
			rbtnMt.setChecked(b2);
			rbtnQt.setChecked(b3);
			rbtnGs.setChecked(b4);

		}

	}

	private class RBtnClickListenr implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.rbtnJingxuan:
				mViewPager.setCurrentItem(0);
				break;
			case R.id.rbtnMeitu:
				mViewPager.setCurrentItem(1);
				break;
			case R.id.rbtnQutu:
				mViewPager.setCurrentItem(2);
				break;
			case R.id.rbtnStory:
				mViewPager.setCurrentItem(3);
				break;

			default:
				break;
			}
		}
	}
}
