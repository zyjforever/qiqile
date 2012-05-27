package com.zyj.qiqile.activity.meprofile;

import java.io.IOException;
import java.sql.Date;
import java.util.logging.FileHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicWriteActivity;
import com.zyj.qiqile.activity.module.CommonImageModule;
import com.zyj.qiqile.activity.module.CommonImageModule.PicType;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.Constants;
import com.zyj.qiqile.constant.ServerConstants;
import com.zyj.qiqile.constant.ServerErrorConstants;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.manager.UserManager;
import com.zyj.qiqile.task.GenericAferExcutedListener;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.TaskListener;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.task.UploadImageTask;
import com.zyj.qiqile.tools.FileHelper;
import com.zyj.qiqile.tools.MD5Helper;

public class MeProfileEditActivity extends BasicWriteActivity {

	private final static String TAG = "MeProfielEditActivity";
	// button
	private ImageView userImageButton;
	private TextView userNickButton;
	private TextView userBirthdayButton;
	private TextView userSexButton;
	private TextView userCityButton;
	private TextView userSignatureButton;

	// listener
	private OnClickListener userImageListener;

	// module
	private CommonImageModule commonImageModule;

	private UserBO userBO;
	// data
	private String nick;
	private Date birthday;
	private Integer sex;// 1:男 0:女 -1其他
	private String signature;
	private String picUrl;
	private String picName;
	private String city;

	private String newPicName;
	private Boolean flag;// 表示是否进行下一步更新（如果图片上传失败，就不更新）

	// task
	private GenericTask updateProfileTask;
	private GenericTask uploadImageTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTitleString(getString(R.string.profile_me_edit_header));
		ActivityManagerApplication.getInstance().addActvity(this);
		QiqileApplication.getInstance().context = this;
		userBO = QiqileApplication.getInstance().getUserBO();
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case Constants.REQUEST_USER_NICK:
				this.nick = data.getExtras().getString(UserBO.NICK);
				this.userNickButton.setText(nick);
				break;
			case Constants.REQUEST_IMAGE_CAPTURE:
				commonImageModule.copyImage();
				showImage(commonImageModule.getThumbnailBitmap());
				this.newPicName = commonImageModule.getImageName();
				break;
			case Constants.REQUEST_PHOTO_LIBRARY:
				commonImageModule.setImageUri(data.getData());
				commonImageModule.copyImage();
				showImage(commonImageModule.getThumbnailBitmap());
				this.newPicName = commonImageModule.getImageName();
				break;
			case Constants.REQUEST_USER_SIGNATURE:
				this.signature = data.getExtras().getString(UserBO.SIGNATURE);
				this.userSignatureButton.setText(signature);
				break;
			case Constants.REQUEST_USER_SEX:
				this.sex = data.getExtras().getInt(UserBO.SEX);
				switch (sex) {
				case Constants.BOY:
					this.userSexButton.setText(R.string.sex_boy);
					break;
				case Constants.GIRL:
					this.userSexButton.setText(R.string.sex_girl);
					break;
				case Constants.OTHER:
					this.userSexButton.setText(R.string.sex_other);
					break;
				}
				break;
			case Constants.REQUEST_USER_BIRTHDAY:
				this.birthday = (Date) data.getExtras().get(UserBO.BIRTHDAY);
				this.userBirthdayButton.setText(birthday.toString());
			case Constants.REQUEST_USER_CITY:
				this.city = data.getExtras().getString(UserBO.CITY);
				this.userCityButton.setText(city);
				break;
			}
		}
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.me_profile_edit);
	}

	@Override
	protected void initView() {
		userCityButton = (TextView) findViewById(R.id.user_city);
		userImageButton = (ImageView) findViewById(R.id.user_image);
		userSexButton = (TextView) findViewById(R.id.user_sex);
		userSignatureButton = (TextView) findViewById(R.id.user_signature);
		userNickButton = (TextView) findViewById(R.id.user_nick);
		userBirthdayButton = (TextView) findViewById(R.id.user_birthday);
		commonImageModule = new CommonImageModule(MeProfileEditActivity.this,
				TAG, PicType.USER);

		if (userBO.getNick() != null) {
			userNickButton.setText(userBO.getNick());
			this.nick = userBO.getNick();
		}
		if (userBO.getCity() != null) {
			userCityButton.setText(userBO.getCity());
			this.city = userBO.getCity();
		}
		if (userBO.getSignature() != null) {
			userSignatureButton.setText(userBO.getSignature());
			this.signature = userBO.getSignature();
		}
		if (userBO.getSex() != null) {
			userSexButton.setText(userBO.getSexString());
			this.sex = userBO.getSex();
		}
		if (userBO.getBirthday() != null) {
			userBirthdayButton.setText(userBO.getBirthday().toString());
			this.birthday = userBO.getBirthday();
		}
		if (userBO.getPicName() != null) {
			this.picName = userBO.getPicName();// 在上一步操作应当把头像信息下载
			this.picUrl = userBO.getPicUrl();
			showImage(commonImageModule.getBitmapByImageName(picName));
		}
		super.initView();
	}

	@Override
	protected void initListener() {
		userImageListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				commonImageModule.createInsertPhotoDialog(commonImageModule
						.getUserPicName(nick));
			}
		};
		super.initListener();
	}

	@Override
	protected void bindListener() {
		userNickButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MeProfileEditActivity.this,
						MeProfileEditNickActivity.class);
				startActivityForResult(intent, Constants.REQUEST_USER_NICK);
			}
		});
		userBirthdayButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MeProfileEditActivity.this,
						MeProfileEditBirthdayActivity.class);
				startActivityForResult(intent, Constants.REQUEST_USER_BIRTHDAY);
			}
		});
		;
		userSexButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MeProfileEditActivity.this,
						MeProfileEditSexActivity.class);
				startActivityForResult(intent, Constants.REQUEST_USER_SEX);
			}
		});
		userCityButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MeProfileEditActivity.this,
						MeProfileEditCityActivity.class);
				startActivityForResult(intent, Constants.REQUEST_USER_CITY);
			}
		});
		userSignatureButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MeProfileEditActivity.this,
						MeProfileEditSignatureActivity.class);
				startActivityForResult(intent, Constants.REQUEST_USER_SIGNATURE);
			}
		});
		userImageButton.setOnClickListener(userImageListener);
		super.bindListener();
	}

	@Override
	protected void setContext() {
		setContext(MeProfileEditActivity.this);
	}

	@Override
	protected Boolean validate() {
		Boolean result = Boolean.FALSE;
		if (nick != null && !nick.equals("")) {
			result = Boolean.TRUE;
		} else {
			Toast.makeText(context, R.string.nick_hint, Toast.LENGTH_SHORT)
					.show();
		}
		return result;
	}

	@Override
	protected void beforeFinish() {
		final UserBO nuserBO = new UserBO();
		nuserBO.setNick(nick);
		nuserBO.setBirthday(birthday);
		nuserBO.setSex(sex);
		nuserBO.setSignature(signature);
		nuserBO.setCity(city);
		nuserBO.setPicUrl(picUrl);
		nuserBO.setPicName(picName);
		if (this.newPicName != null) {
			uploadImageTask = new UploadImageTask();
			uploadImageTask.setListener(new GenericAferExcutedListener() {
				@Override
				public void onPostExecute(GenericTask task, TaskResult result) {
					if (result.getResult() == ResultCode.SUCCESS) {
						flag = Boolean.TRUE;
						picUrl = (String) result.get("picUrl");
						picName = newPicName;
						nuserBO.setPicUrl(picUrl);
						nuserBO.setPicName(picName);
						updateProfileTask = new UpdateProfileTask();
						TaskParams params = new TaskParams();
						params.put("token", QiqileApplication.getInstance()
								.getToken());
						params.put("userBO", nuserBO);
						updateProfileTask.execute(params);
					} else {
						flag = Boolean.FALSE;
					}
				}
			});
			TaskParams params = new TaskParams();
			params.put("file", commonImageModule.getImageFile());
			params.put("url", ServerConstants.HTTP
					+ ServerConstants.USER_PIC_SERVER_URL);
			uploadImageTask.execute(params);
		} else {
			updateProfileTask = new UpdateProfileTask();
			TaskParams params = new TaskParams();
			params.put("token", QiqileApplication.getInstance().getToken());
			params.put("userBO", nuserBO);
			updateProfileTask.execute(params);
		}
		QiqileApplication.getInstance().setUserBO(nuserBO);
	}

	@Override
	protected void beforeBack() {
		setResult(RESULT_CANCELED, null);
	}

	/** 显示图片 */
	public void showImage(Bitmap bitmap) {
		userImageButton.setImageBitmap(bitmap);
	}

}

class UpdateProfileTask extends GenericTask {

	private Context context;

	public UpdateProfileTask() {
		context = QiqileApplication.context;
	}

	public UpdateProfileTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Toast.makeText(context, context.getString(R.string.commit_status_in),
				Toast.LENGTH_LONG).show();
	}

	@Override
	protected TaskResult _doInBackground(TaskParams... params) {
		UserManager userManager = QiqileApplication.getInstance()
				.getUserManager();
		TaskResult taskResult = userManager.update(
				(UserBO) params[0].get("userBO"),
				(String) params[0].get("token"));
		return taskResult;
	}

	@Override
	protected void onPostExecute(TaskResult result) {
		if (result != null) {
			ResultCode resultCode = result.getResult();
			if (resultCode == ResultCode.SUCCESS) {
				Toast.makeText(context,
						context.getString(R.string.commit_status_success),
						Toast.LENGTH_SHORT).show();
			} else if (resultCode == ResultCode.NETWORK_ERROR) {
				Toast.makeText(
						context,
						context.getString(R.string.login_status_network_or_connection_error),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context,
						context.getString(R.string.error_unknow),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context,
					context.getString(R.string.commit_status_fail),
					Toast.LENGTH_SHORT).show();
		}
	}
}
