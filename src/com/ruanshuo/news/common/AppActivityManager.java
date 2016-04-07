package com.ruanshuo.news.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * App的Activity管理类
 * 
 * @author zhou
 */
public class AppActivityManager {

	private static AppActivityManager mInstance;

	/** 记录打开的activity **/
	private List<Activity> activityList = new ArrayList<Activity>();

	private AppActivityManager() {
	}

	public synchronized static AppActivityManager getInstance() {
		if (mInstance == null) {
			mInstance = new AppActivityManager();
		}
		return mInstance;
	}

	/**
	 * 添加Activity
	 */
	public void addActivity(Activity activity) {
		if (activityList == null) {
			activityList = new ArrayList<Activity>();
		}
		activityList.add(activity);
	}

	/**
	 * 遍历所有Activity并finish
	 */
	public void exit() {
		if (activityList != null) {
			for (Activity activity : activityList) {
				if (activity != null)
					activity.finish();
			}
			System.exit(0);
		}
	}

	/**
	 * 遍历所有Activity并finish
	 */
	public void exitAll() {
		if (activityList != null) {
			for (Activity activity : activityList) {
				if (activity != null)
					activity.finish();
			}
		}
	}

	public List<Activity> getActivityList() {
		return activityList;
	}
}
