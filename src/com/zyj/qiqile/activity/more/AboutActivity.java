package com.zyj.qiqile.activity.more;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicBackActivity;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;

public class AboutActivity extends BasicBackActivity {

	@Override
	protected void setContext() {
		this.setContext(AboutActivity.this);
		this.setTitleString(getString(R.string.omenu_about));
		QiqileApplication.getInstance().context = this;
		ActivityManagerApplication.getInstance().addActvity(this);
	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.about);
	}

}
