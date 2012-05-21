package com.zyj.qiqile.app;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class ActivityManagerApplication extends Application{
	//���е�activity�б�
	private List<Activity> activityList=new LinkedList<Activity>();
	
	private static ActivityManagerApplication instance;
	
	public static ActivityManagerApplication getInstance(){
		if(null==instance){
			instance=new ActivityManagerApplication();
		}
		return instance;
	}
	
	//���һ��activity
	public void addActvity(Activity activity){
		activityList.add(activity);
	}
	
	//�������е�Activity��finish
	public void exit(){
		finishAll();
		System.exit(0);
	}
	//finish���е�Activity
	public void finishAll(){
		for(Activity activity:activityList){
			activity.finish();
		}
	}
	
}
