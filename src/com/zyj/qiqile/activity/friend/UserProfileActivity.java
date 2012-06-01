package com.zyj.qiqile.activity.friend;

import java.io.File;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicBackActivity;
import com.zyj.qiqile.activity.module.CommonImageModule;
import com.zyj.qiqile.activity.module.CommonImageModule.PicType;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.UserAttentionBO;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.domain.vo.UserVO;
import com.zyj.qiqile.manager.UserAttentionManager;
import com.zyj.qiqile.manager.UserManager;
import com.zyj.qiqile.manager.impl.UserAttentionManagerImpl;
import com.zyj.qiqile.manager.impl.UserManagerImpl;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.FileHelper;

public class UserProfileActivity extends BasicBackActivity {
	public static final String TAG = "UserProfileActivity";

	// text
	private ImageView userImageView;
	private TextView userNickView;
	private TextView userAgeView;
	private TextView userSexView;
	private TextView userStateView;
	private TextView userCityView;
	private TextView userSignatureView;
	private Button addFriendButton;
	private CommonImageModule commonImageModule;
	private ProgressDialog progressDialog;
	private GenericTask loadProfileTask;
	private GenericTask addFriendTask;

	private String userId;

	@Override
	protected void initView() {
		super.initView();
		userAgeView = (TextView) findViewById(R.id.user_age);
		userCityView = (TextView) findViewById(R.id.user_city);
		userImageView = (ImageView) findViewById(R.id.user_image);
		userStateView = (TextView) findViewById(R.id.user_state);
		userSignatureView = (TextView) findViewById(R.id.user_signature);
		userSexView = (TextView) findViewById(R.id.user_sex);
		userNickView = (TextView) findViewById(R.id.user_nick);
		addFriendButton = (Button) findViewById(R.id.button_add_friend);
		commonImageModule = new CommonImageModule(this, TAG, PicType.USER);
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getString(R.string.status_loading));
		userId = getIntent().getExtras().getString(UserVO.USERID);
		refresh();
	}

	// 加载用户信息
	private void refresh() {
		loadProfileTask = new LoadUserProfileTask(context, progressDialog);
		TaskParams taskParams = new TaskParams();
		taskParams.put(UserVO.USERID, userId);
		loadProfileTask.execute(taskParams);
	}

	private void pushTOView(UserBO userBO) {
		if (userBO != null) {
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
				commonImageModule.showImageWithNoAlert(userImageView, new File(
						FileHelper.getUserPicDirectory(), userBO.getPicName()),
						userBO.getPicUrl());
			}
		}
	}

	@Override
	protected void initListener() {
		super.initListener();
		addFriendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (QiqileApplication.getInstance().isLogin()) {
					boolean flag = true;
					if (QiqileApplication.getInstance().getMyAttentionList() != null) {
						for (UserAttentionBO uab : QiqileApplication
								.getInstance().getMyAttentionList()) {
							if (uab.getAttentionId().equals(userId)) {
								flag = false;
								break;
							}
						}
					}
					if (flag) {
						progressDialog
								.setMessage(getString(R.string.commit_status_in));
						addFriendTask = new AddFriendTask(getBaseContext(),
								progressDialog);
						TaskParams params = new TaskParams();
						params.put(UserAttentionBO.USERID, QiqileApplication
								.getInstance().getUserBO().getId());
						params.put(UserAttentionBO.ATTENTIONID, userId);
						addFriendTask.execute(params);
					} else {
						Toast.makeText(getBaseContext(),
								R.string.error_is_your_friend,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(context, R.string.please_login,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void setContext() {
		this.context = this;
	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.friend_profile);
	}

	/** 加载用户信息的任务 */
	class LoadUserProfileTask extends GenericTask {

		private Context context;
		private ProgressDialog progressDialog;

		public LoadUserProfileTask(Context context,
				ProgressDialog progressDialog) {
			this.context = context;
			this.progressDialog = progressDialog;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			super.onPostExecute(result);
			if (result != null && result.getResult() == ResultCode.SUCCESS) {
				UserBO userBO = (UserBO) result.get("userBO");
				pushTOView(userBO);
				progressDialog.dismiss();
			} else {
				Toast.makeText(context, R.string.status_fail,
						Toast.LENGTH_SHORT);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected TaskResult _doInBackground(TaskParams... params) {
			TaskResult taskResult = null;
			if (params != null) {
				UserManager userManager = new UserManagerImpl();
				taskResult = userManager.queryUserBOById((String) params[0]
						.get(UserVO.USERID));
			}
			return taskResult;
		}
	}

	/** 添加好友的任务 */
	class AddFriendTask extends GenericTask {

		private Context context;
		private ProgressDialog progressDialog;

		public AddFriendTask(Context context, ProgressDialog progressDialog) {
			this.context = context;
			this.progressDialog = progressDialog;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			super.onPostExecute(result);
			if (result != null && result.getResult() == ResultCode.SUCCESS) {
				progressDialog.dismiss();
				Toast.makeText(context, R.string.commit_status_success,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, R.string.commit_status_fail,
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected TaskResult _doInBackground(TaskParams... params) {
			TaskResult taskResult = null;
			if (params != null) {
				UserAttentionManager userAttentionManager = new UserAttentionManagerImpl();
				taskResult = userAttentionManager.addAttenttion(
						(String) params[0].get(UserAttentionBO.USERID),
						(String) params[0].get(UserAttentionBO.ATTENTIONID));
			}
			return taskResult;
		}
	}
}
