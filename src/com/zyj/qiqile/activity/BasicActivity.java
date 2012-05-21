package com.zyj.qiqile.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * activity �����ĳ�ʼ������
 * @author zyj
 * @time 2012.5.3 21:40
 * */
public abstract class BasicActivity extends Activity{


	/** ����contentView */
	protected abstract void setContentView();

	/** ��ʼ��view */
	protected abstract void initView();

	/** ��ʼ�������� ,�����Ǽ򵥵ļ������������������壬ֱ���������ڲ���*/
	protected abstract void initListener();

	/** �󶨼����� */
	protected abstract void bindListener();
	
	protected void init() {
		setContentView();// ����contentView
		initView();// ��ʼ��view
		initListener();// ��ʼ��������
		bindListener();// �󶨼�����
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
}
