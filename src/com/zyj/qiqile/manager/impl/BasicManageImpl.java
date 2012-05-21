package com.zyj.qiqile.manager.impl;

import com.zyj.qiqile.tools.HttpConnectionHelper;
import com.zyj.qiqile.constant.ServerConstants;


public class BasicManageImpl {
	protected HttpConnectionHelper httpConnectionHelper;
	protected boolean USE_SSL;

	public static final String TOKEN="token";
	BasicManageImpl(){
		httpConnectionHelper=HttpConnectionHelper.getInstance();
	}
}
