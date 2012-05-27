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

	/** list �����item��ʽ */
	protected int listItemLayout;

	/** �ܹ������Ӧitem��ʽ�������� */
	protected BaseAdapter adapter;

	protected List<Map<String, Object>> datas;

	/** ����contentView */
	protected abstract void setContentView();

	/** ����list item */
	protected abstract void setItemLayout();

	/** ��ʼ��view */
	protected abstract void initView();

	/** ��ʼ�������� ,�����Ǽ򵥵ļ������������������壬ֱ���������ڲ��� */
	protected abstract void initListener();

	/** �󶨼����� */
	protected abstract void bindListener();

	/** ��ȡ���ݣ�Ӧ�������첽��ȡ��ʽ */
	protected abstract void getDatas();

	protected void init() {
		setContentView();// ����contentView
		setItemLayout();
		initView();// ��ʼ��view
		initListener();// ��ʼ��������
		bindListener();// �󶨼�����
		QiqileApplication.context = context;
		ActivityManagerApplication.getInstance().addActvity((Activity) context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
}
