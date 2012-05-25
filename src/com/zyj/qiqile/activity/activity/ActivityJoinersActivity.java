package com.zyj.qiqile.activity.activity;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicBackActivity;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.ActivityBO;

public class ActivityJoinersActivity extends BasicBackActivity {

	public static final String TAG = "ActivityJoinersActivity";

	private ActivityBO activityBO;

	@Override
	public void init() {
		activityBO = QiqileApplication.getInstance().getCurrentLookActivity();
		super.init();
	}

	@Override
	protected void initView() {
		super.initView();
	}

	@Override
	protected void setContext() {
		this.setContext(this);

	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.activity_joiners);
		this.setTitleString(getString(R.string.activity_join));
	}
}
