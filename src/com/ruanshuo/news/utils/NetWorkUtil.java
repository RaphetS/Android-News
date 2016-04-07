package com.ruanshuo.news.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {
	// 判断当前是否有网络
	public static boolean hasNetWrok(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo aInfo = connectivityManager.getActiveNetworkInfo();
		if (aInfo == null) {
			return false;
		} else {
			return aInfo.isAvailable();
		}

	}

	public static int typeOfNetWork(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo aInfo = connectivityManager.getActiveNetworkInfo();
		return aInfo.getType();

	}
}
