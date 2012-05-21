package com.zyj.qiqile.activity.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicBackActivity;
import com.zyj.qiqile.activity.module.CommonImageModule;
import com.zyj.qiqile.activity.module.CommonImageModule.PicType;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.tools.TimeHelper;

public class ActivityProfileActivity extends BasicBackActivity {

	public static final String TAG = "ActivityEdit7Activity";
	private TextView activityNameText;
	private TextView activityContextText;
	private TextView activityPeopleLimitText;
	private TextView activityStartTimeText;
	private TextView activityEndTimeText;
	private TextView activityLocationText;
	private TextView activityUserNameText;
	private ImageView activityImage;
	private ImageView activityUserImage;

	// button
	private ImageButton editButton;

	// module
	private CommonImageModule commonImageModule;
	private ActivityBO activityBO;
	private UserBO userBO;

	@Override
	public void init() {
		activityBO = QiqileApplication.getInstance().getCurrentLookActivity();
		userBO = QiqileApplication.getInstance().getUserBO();
		super.init();
	}

	@Override
	protected void initView() {
		super.initView();
		activityNameText = (TextView) findViewById(R.id.activity_name);
		activityContextText = (TextView) findViewById(R.id.activity_context);
		activityPeopleLimitText = (TextView) findViewById(R.id.activity_people_limit);
		activityStartTimeText = (TextView) findViewById(R.id.activity_start_time);
		activityEndTimeText = (TextView) findViewById(R.id.activity_end_time);
		activityLocationText = (TextView) findViewById(R.id.activity_location);
		activityUserNameText = (TextView) findViewById(R.id.activity_user_name);
		activityImage = (ImageView) findViewById(R.id.activity_image);
		activityUserImage = (ImageView) findViewById(R.id.activity_user_image);
		editButton = (ImageButton) findViewById(R.id.writeMessage);
		commonImageModule = new CommonImageModule(this, TAG);

		// 把内容显示到view上面去
		activityNameText.setText(activityBO.getName());
		activityContextText.setText(activityBO.getContext());
		activityPeopleLimitText.setText(getPeopleLimitString(activityBO
				.getPeopleLimit()));
		activityStartTimeText.setText(TimeHelper.getDateString(activityBO
				.getStartTime()));
		activityEndTimeText.setText(TimeHelper.getDateString(activityBO
				.getEndTime()));
		activityLocationText.setText(activityBO.getCity() + "("
				+ activityBO.getLocation() + ")");
		activityUserNameText.setText(activityBO.getUserName());
		commonImageModule.showImage(activityImage, activityBO.getPicName(),
				activityBO.getPicUrl(), PicType.ACTIVITY);
		commonImageModule.showImage(activityUserImage,
				activityBO.getUserPicName(), activityBO.getUserPicUrl(),
				PicType.USER);
		if (userBO == null || !activityBO.getUserId().equals(userBO.getId())) {
			editButton.setVisibility(View.INVISIBLE);
		} else {
			editButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ActivityProfileActivity.this,
							ActivityEdit1Activity.class);
					QiqileApplication.getInstance().setCurrentEditActivity(
							activityBO);
					startActivity(intent);
				}
			});
		}
	}

	@Override
	protected void setContext() {
		this.setContext(this);
	}

	@Override
	protected void beforeBack() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.activity_profile);
	}

	private String getPeopleLimitString(Integer limit) {
		String result = "";
		switch (limit) {
		case 0:
			result = getString(R.string.people_limit_no_string);
			break;
		case 1:
			result = getString(R.string.people_limit_ten_string);
			break;
		case 2:
			result = getString(R.string.people_limit_twenty_string);
			break;
		case 3:
			result = getString(R.string.people_limit_fifty_string);
			break;
		}
		return result;
	}
}
