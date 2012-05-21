package com.zyj.qiqile.activity.activity;

import java.io.File;
import java.util.Date;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicActivity;
import com.zyj.qiqile.activity.meprofile.MeProfileEditActivity;
import com.zyj.qiqile.activity.module.CommonImageModule;
import com.zyj.qiqile.activity.module.CommonImageModule.PicType;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.Constants;
import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.tools.FileHelper;

public class ActivityEdit3Activity extends BasicActivity {

	private final static String TAG = "ActivityEdit3Activity";
	private Button topPreButton;
	private Button topNextButton;
	private Button uploadImgButton;
	// module
	private CommonImageModule commonImageModule;
	private ActivityBO activityBO;
	private ImageView imageView;
	private String picUrl;
	private String picName;
	private File imageFile;

	@Override
	public void init() {
		activityBO = QiqileApplication.getInstance().getCurrentEditActivity();
		super.init();
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_edit_3);
	}

	@Override
	protected void initView() {
		topPreButton = (Button) findViewById(R.id.top_pre);
		topNextButton = (Button) findViewById(R.id.top_next);
		uploadImgButton = (Button) findViewById(R.id.upload_img_button);
		imageView = (ImageView) findViewById(R.id.imageView);
		((TextView) findViewById(R.id.title))
				.setText(R.string.activity_picUrl_header);
		commonImageModule = new CommonImageModule(ActivityEdit3Activity.this,
				TAG, PicType.ACTIVITY);
		if (activityBO != null && activityBO.getPicName() != null) {
			showImage(activityBO.getPicName());
		}
	}

	@Override
	protected void initListener() {
		topPreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityEdit3Activity.this.onBackPressed();
			}
		});
		topNextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validate()) {
					activityBO.setPicUrl(picUrl);
					activityBO.setPicName(picName);
					activityBO.setPicFile(imageFile);
					Intent intent = new Intent(ActivityEdit3Activity.this,
							ActivityEdit4Activity.class);
					startActivity(intent);
				} else {
					Toast.makeText(QiqileApplication.getInstance().context,
							R.string.add_activity_image, Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		uploadImgButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				commonImageModule.createInsertPhotoDialog(commonImageModule
						.getActivityPicName(activityBO.getName(),
								QiqileApplication.getInstance().getUserBO()
										.getNick()));
			}
		});
	}

	private boolean validate() {
		if (imageFile != null && imageFile.exists())
			return true;
		else
			return false;
	}

	@Override
	protected void bindListener() {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case Constants.REQUEST_IMAGE_CAPTURE:
				commonImageModule.copyImage();
				activityBO.setNewPic(Boolean.TRUE);
				showImage(commonImageModule.getImageName());
				break;
			case Constants.REQUEST_PHOTO_LIBRARY:
				commonImageModule.setImageUri(data.getData());
				commonImageModule.copyImage();
				activityBO.setNewPic(Boolean.TRUE);
				showImage(commonImageModule.getImageName());
				break;
			}
		}
	}

	private void showImage(String imageName) {
		this.picName = commonImageModule.getImageName();
		imageFile = new File(FileHelper.getActivityPicDirectory(), imageName);
		commonImageModule.showImage(imageView, imageFile);
	}
}
