package com.zyj.qiqile.activity.more;

import android.widget.EditText;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicWriteActivity;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;

public class FeedbackActivity extends BasicWriteActivity {

	private EditText feedbackEdit;
	private String feedback;

	@Override
	protected void initView() {
		super.initView();
		feedbackEdit = (EditText) findViewById(R.id.feedback);
		setTitleString(getString(R.string.omenu_feedback));
	}

	@Override
	protected void setContext() {
		this.setContext(FeedbackActivity.this);
		QiqileApplication.getInstance().context = this;
		ActivityManagerApplication.getInstance().addActvity(this);
	}

	@Override
	protected Boolean validate() {
		Boolean result = Boolean.FALSE;
		feedback = feedbackEdit.getText().toString();
		if (feedback != null && !feedback.equals("")) {
			result = Boolean.TRUE;
		} else {
			Toast.makeText(this, R.string.feedback_hint, Toast.LENGTH_SHORT);
		}
		return result;
	}

	@Override
	protected void beforeBack() {

	}

	@Override
	protected void beforeFinish() {
		// TODO提交到服务器
		onBackPressed();
	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.feedback);
	}

}
