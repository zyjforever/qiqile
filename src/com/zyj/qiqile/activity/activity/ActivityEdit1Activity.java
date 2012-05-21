package com.zyj.qiqile.activity.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicBackActivity;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.ActivityBO;

public class ActivityEdit1Activity extends BasicBackActivity {

	private TextView nextButton;
	private EditText activityNameEdit;

	private String activityName;
	private ActivityBO activityBO;

	@Override
	public void init() {
		activityBO = QiqileApplication.getInstance().getCurrentEditActivity();
		this.setTitleString(getString(R.string.input_activity_name_header));
		super.init();
	}

	@Override
	protected void initView() {
		nextButton = (TextView) findViewById(R.id.top_next);
		activityNameEdit = (EditText) findViewById(R.id.activity_name);
		if (activityBO != null && activityBO.getName() != null) {
			activityName = activityBO.getName();
			activityNameEdit.setText(activityName);
		}
		super.initView();
	}

	@Override
	protected void bindListener() {
		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activityName = activityNameEdit.getText().toString();
				if (activityName != null && activityName.length() > 0) {
					activityBO.setName(activityName);
					Intent intent = new Intent(ActivityEdit1Activity.this,
							ActivityEdit2Activity.class);
					startActivity(intent);
				} else {
					Toast.makeText(context, R.string.activity_name_hint,
							Toast.LENGTH_LONG).show();
				}
			}
		});
		super.bindListener();
	}

	@Override
	protected void setContext() {
		setContext(ActivityEdit1Activity.this);

	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_edit_1);
	}

	@Override
	protected void beforeBack() {
		if (activityBO.getId() != null) {
			new AlertDialog.Builder(this)
					.setTitle("确认")
					.setMessage("放弃编辑活动？")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									ActivityEdit1Activity.this.beforeBack();
								}
							}).setNegativeButton("否", null).show();
		} else {
			super.beforeBack();
		}
	}

}
