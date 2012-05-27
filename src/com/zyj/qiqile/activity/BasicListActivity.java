package com.zyj.qiqile.activity;

import java.util.List;
import java.util.Map;

import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class BasicListActivity extends ListActivity {
	protected ListView listView;
	protected Context context;

	public BasicListActivity() {
		context = this;
	}

	/** list 里面的item样式 */
	protected int listItemLayout;

	/** 能够适配对应item样式的适配器 */
	protected BaseAdapter adapter;

	protected List<Map<String, Object>> datas;

	/** 设置contentView */
	protected abstract void setContentView();

	/** 设置list item */
	protected abstract void setItemLayout();

	/** 初始化view */
	protected abstract void initView();

	/** 初始化监听器 ,假如是简单的监听器，可以跳过定义，直接用匿名内部类 */
	protected abstract void initListener();

	/** 绑定监听器 */
	protected abstract void bindListener();

	/** 获取数据，应当采用异步拉取形式 */
	protected abstract void getDatas();

	protected void init() {
		setContentView();// 设置contentView
		setItemLayout();
		initView();// 初始化view
		initListener();// 初始化监听器
		bindListener();// 绑定监听器
		QiqileApplication.context = context;
		ActivityManagerApplication.getInstance().addActvity((Activity) context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
}
