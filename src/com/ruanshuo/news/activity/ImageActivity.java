package com.ruanshuo.news.activity;

import java.util.List;

import com.ruanshuo.news.R;
import com.ruanshuo.news.adapter.ImageAdapter_;
import com.ruanshuo.news.bean.NewDetailModle;
import com.ruanshuo.news.bean.NewModle;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

public class ImageActivity extends Activity {
	private TextView tvTitle;
	private ViewPager mViewPager;
	private List<String> imgList;
	private NewDetailModle newDetailModle;
	private String titleString;
	private NewModle newModle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
		InitListener();
	}

	private void initView() {
		setContentView(R.layout.activity_image);
		tvTitle = (TextView) findViewById(R.id.new_title);
		// mViewPager = (ViewPager) findViewById(R.id.viewPager_image);
	}

	private void initData() {

		getData();
		setData();
	}

	private void setData() {
		tvTitle.setText(titleString);
		MyPagerAdapter adapter=new MyPagerAdapter();
		
		
	}

	private void getData() {
		try {
			if (getIntent().getExtras().getSerializable("newDetailModle") != null) {
				newDetailModle = (NewDetailModle) getIntent().getExtras().getSerializable("newDetailModle");
				imgList = newDetailModle.getImgList();
				titleString = newDetailModle.getTitle();
			} else {
				newModle = (NewModle) getIntent().getExtras().getSerializable("newModle");
				imgList = newModle.getImagesModle().getImgList();
				titleString = newModle.getTitle();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void InitListener() {

	}

	protected class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}

		@Override
		public Object instantiateItem(View container, int position) {
//			((ViewPager)container).addView(imgList.get(position));
			return super.instantiateItem(container, position);
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			super.destroyItem(container, position, object);
		}

	}
}
