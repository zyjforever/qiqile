package com.zyj.qiqile.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * activity 基本的初始化操作
 * @author zyj
 * @time 2012.5.3 21:40
 * */
public abstract class BasicActivity extends Activity{


	/** 设置contentView */
	protected abstract void setContentView();

	/** 初始化view */
	protected abstract void initView();

	/** 初始化监听器 ,假如是简单的监听器，可以跳过定义，直接用匿名内部类*/
	protected abstract void initListener();

	/** 绑定监听器 */
	protected abstract void bindListener();
	
	protected void init() {
		setContentView();// 设置contentView
		initView();// 初始化view
		initListener();// 初始化监听器
		bindListener();// 绑定监听器
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
}
