package com.zyj.qiqile.app;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class ActivityManagerApplication extends Application{
	//所有的activity列表
	private List<Activity> activityList=new LinkedList<Activity>();
	
	private static ActivityManagerApplication instance;
	
	public static ActivityManagerApplication getInstance(){
		if(null==instance){
			instance=new ActivityManagerApplication();
		}
		return instance;
	}
	
	//添加一个activity
	public void addActvity(Activity activity){
		activityList.add(activity);
	}
	
	//遍历所有的Activity并finish
	public void exit(){
		finishAll();
		System.exit(0);
	}
	//finish所有的Activity
	public void finishAll(){
		for(Activity activity:activityList){
			activity.finish();
		}
	}
	
}
