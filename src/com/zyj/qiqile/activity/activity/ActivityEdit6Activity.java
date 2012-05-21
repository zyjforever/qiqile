package com.zyj.qiqile.activity.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicActivity;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.ActivityBO;

public class ActivityEdit6Activity extends BasicActivity {
	private Button topPreButton;
	private Button topNextButton;
	private TextView peopleLimitText;
	private Button peopleLimitNoButton;
	private Button peopleLimitTenButton;
	private Button peopleLimitTwentyButton;
	private Button peopleLimitFiftyButton;

	private Integer peopleLimit;

	private ActivityBO activityBO;

	@Override
	public void init() {
		activityBO = QiqileApplication.getInstance().getCurrentEditActivity();
		peopleLimit = activityBO.getPeopleLimit();
		if (peopleLimit == null) {
			peopleLimit = 0;
		}
		super.init();
	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.activity_edit_6);
	}

	@Override
	protected void initView() {
		topPreButton = (Button) findViewById(R.id.top_pre);
		topNextButton = (Button) findViewById(R.id.top_next);
		peopleLimitText = (TextView) findViewById(R.id.people_limit);
		((TextView) findViewById(R.id.title))
				.setText(R.string.input_activity_people_limit_header);
		peopleLimitNoButton = (Button) findViewById(R.id.people_limit_no);
		peopleLimitTenButton = (Button) findViewById(R.id.people_limit_ten);
		peopleLimitTwentyButton = (Button) findViewById(R.id.people_limit_twenty);
		peopleLimitFiftyButton = (Button) findViewById(R.id.people_limit_fifty);
		setPeopleLimitEdit();
	}

	@Override
	protected void initListener() {
		topPreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityEdit6Activity.this.onBackPressed();
			}
		});
		topNextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validate()) {
					activityBO.setPeopleLimit(peopleLimit);
					Intent intent = new Intent(ActivityEdit6Activity.this,
							ActivityEdit7Activity.class);
					startActivity(intent);
				}
			}
		});
		peopleLimitNoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				peopleLimit = 0;
				setPeopleLimitEdit();
			}
		});
		peopleLimitTenButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				peopleLimit = 1;
				setPeopleLimitEdit();
			}
		});
		peopleLimitTwentyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				peopleLimit = 2;
				setPeopleLimitEdit();
			}
		});
		peopleLimitFiftyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				peopleLimit = 3;
				setPeopleLimitEdit();
			}
		});
	}

	private void setPeopleLimitEdit() {
		switch (peopleLimit) {
		case 0:
			peopleLimitText.setText(R.string.people_limit_no);
			break;
		case 1:
			peopleLimitText.setText(R.string.people_limit_ten);
			break;
		case 2:
			peopleLimitText.setText(R.string.people_limit_twenty);
			break;
		case 3:
			peopleLimitText.setText(R.string.people_limit_fifty);
			break;
		}
	}

	protected boolean validate() {
		return true;
	}

	@Override
	protected void bindListener() {
		// TODO Auto-generated method stub

	}
}
