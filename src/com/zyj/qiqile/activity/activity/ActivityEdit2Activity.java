package com.zyj.qiqile.activity.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicActivity;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.Constants;
import com.zyj.qiqile.domain.bo.ActivityBO;

public class ActivityEdit2Activity extends BasicActivity {

	private Button topPreButton;
	private Button topNextButton;
	private EditText activityContextEdit;

	private ActivityBO activityBO;
	private String activityContext;

	@Override
	public void init() {
		activityBO = QiqileApplication.getInstance().getCurrentEditActivity();
		super.init();
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_edit_2);
	}

	@Override
	protected void initView() {
		topPreButton = (Button) findViewById(R.id.top_pre);
		topNextButton = (Button) findViewById(R.id.top_next);
		activityContextEdit = (EditText) findViewById(R.id.activity_context);
		((TextView) findViewById(R.id.title))
				.setText(R.string.activity_context_header);
		if (activityBO != null && activityBO.getContext() != null) {
			activityContext = activityBO.getContext();
			activityContextEdit.setText(activityContext);
		}
	}

	@Override
	protected void initListener() {
		topPreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityEdit2Activity.this.onBackPressed();
			}
		});
		topNextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validate()) {
					activityBO.setContext(activityContext);
					Intent intent = new Intent(ActivityEdit2Activity.this,
							ActivityEdit3Activity.class);
					startActivity(intent);
				}
			}
		});
	}

	private Boolean validate() {
		Boolean result = Boolean.FALSE;
		activityContext = activityContextEdit.getText() == null ? null
				: activityContextEdit.getText().toString();
		if (activityContext != null&&!activityContext.equals("")) {
			if (activityContext.length() <= Constants.ACTIVITY_CONTEXT_MAXSIZE) {
				result = Boolean.TRUE;
			} else {
				Toast.makeText(ActivityEdit2Activity.this,
						R.string.error_activity_context_toolong,
						Toast.LENGTH_LONG).show();
			}

		} else {
			Toast.makeText(ActivityEdit2Activity.this,
					R.string.activity_context_hint, Toast.LENGTH_LONG).show();
		}
		return result;
	}

	@Override
	protected void bindListener() {

	}

}
