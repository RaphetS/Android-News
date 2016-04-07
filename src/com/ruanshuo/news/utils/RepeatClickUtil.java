package com.ruanshuo.news.utils;

public class RepeatClickUtil {
	private static long lastClickTime;

	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 1000*60*60*10) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
