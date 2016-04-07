package com.ruanshuo.news.common;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ImageLoaderConfiguration configuration=ImageLoaderConfiguration.createDefault(this);
		ImageLoader.getInstance().init(configuration);
	}
}
