package com.zyj.qiqile.activity.meprofile;

import java.io.File;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicActivity;
import com.zyj.qiqile.activity.BasicMainActivity;
import com.zyj.qiqile.activity.module.CommonImageModule;
import com.zyj.qiqile.activity.module.CommonImageModule.PicType;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.task.DownloadImageTask;
import com.zyj.qiqile.task.GenericAferExcutedListener;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.TaskListener;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.DownloadHelper;
import com.zyj.qiqile.tools.FileHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MeProfileActivity extends BasicMainActivity {

	public static final String TAG = "MeProfileActivity";

	// button
	private ImageButton editMeProfileButton;

	// text
	private ImageView userImageView;
	private TextView userNickView;
	private TextView userAgeView;
	private TextView userSexView;
	private TextView userStateView;
	private TextView userCityView;
	private TextView userSignatureView;

	// listener
	private OnClickListener editMeProfileListener;

	private CommonImageModule commonImageModule;

	private UserBO userBO;

	private File imageFile;

	private GenericTask loadProfileTask;

	private TaskListener afterDownloadListener = new GenericAferExcutedListener() {
		@Override
		public void onPostExecute(GenericTask task, TaskResult result) {
			if (result.getResult() == ResultCode.SUCCESS)
				userImageView.setImageBitmap(commonImageModule
						.getBitmapByFile(imageFile));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityManagerApplication.getInstance().addActvity(this);
		userBO = QiqileApplication.getInstance().getUserBO();
		QiqileApplication.getInstance().context = this;
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.me_profile);
	}

	@Override
	protected void initView() {
		super.initView();
		editMeProfileButton = (ImageButton) findViewById(R.id.edit_me_profile);
		userAgeView = (TextView) findViewById(R.id.user_age);
		userCityView = (TextView) findViewById(R.id.user_city);
		userImageView = (ImageView) findViewById(R.id.user_image);
		userStateView = (TextView) findViewById(R.id.user_state);
		userSignatureView = (TextView) findViewById(R.id.user_signature);
		userSexView = (TextView) findViewById(R.id.user_sex);
		userNickView = (TextView) findViewById(R.id.user_nick);
		commonImageModule = new CommonImageModule(this, TAG, PicType.USER);
		refresh();
	}

	@Override
	protected void initListener() {
		super.initListener();
		editMeProfileListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MeProfileActivity.this,
						MeProfileEditActivity.class);
				startActivity(intent);
			}
		};
	}

	@Override
	protected void bindListener() {
		super.bindListener();
		editMeProfileButton.setOnClickListener(editMeProfileListener);
	}

	@Override
	protected void setContext() {
		context = MeProfileActivity.this;
	}

	private void refresh() {
		if (userBO.getNick() != null) {
			userNickView.setText(userBO.getNick());
		}
		if (userBO.getCity() != null) {
			userCityView.setText(userBO.getCity());
		}
		if (userBO.getSignature() != null) {
			userSignatureView.setText(userBO.getSignature());
		}
		if (userBO.getSex() != null) {
			userSexView.setText(userBO.getSexString());
		}
		if (userBO.getBirthday() != null) {
			userAgeView.setText(userBO.getAgeString());
		}
		if (userBO.getPicUrl() != null) {
			imageFile = new File(FileHelper.getUserPicDirectory(),
					userBO.getPicName());
			commonImageModule.showImage(userImageView, imageFile,
					userBO.getPicUrl());
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		refresh();
	}

}
