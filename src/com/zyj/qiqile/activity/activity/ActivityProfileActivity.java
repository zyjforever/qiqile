package com.zyj.qiqile.activity.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicBackActivity;
import com.zyj.qiqile.activity.module.CommonImageModule;
import com.zyj.qiqile.activity.module.CommonImageModule.PicType;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.domain.bo.ActivityJoinBO;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.manager.ActivityJoinManager;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
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
	private TextView joinButton;
	private TextView activityJoinButton;
	private TextView activityCommentButton;

	// module
	private CommonImageModule commonImageModule;
	private ActivityBO activityBO;
	private UserBO userBO;
	private ActivityJoinBO activityJoinBO;

	private GenericTask joinTask;
	private GenericTask isJoinTask;
	private Boolean isJoin;

	private ProgressDialog progressDiaglog;

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
		joinButton = (TextView) findViewById(R.id.button_join);
		activityJoinButton = (TextView) findViewById(R.id.button_activity_join);
		activityCommentButton = (TextView) findViewById(R.id.button_activity_comment);
		commonImageModule = new CommonImageModule(this, TAG);

		progressDiaglog = new ProgressDialog(this);

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
					if (QiqileApplication.getInstance().isLogin()) {
						Intent intent = new Intent(
								ActivityProfileActivity.this,
								ActivityEdit1Activity.class);
						QiqileApplication.getInstance().setCurrentEditActivity(
								activityBO);
						startActivity(intent);
					} else {
						Toast.makeText(context, R.string.please_login,
								Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		isJoin();
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
		this.setTitleString(getString(R.string.actvity_profile));
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

	@Override
	protected void onResume() {
		super.onResume();
		setContext();
	}

	@Override
	protected void initListener() {
		super.initListener();
		joinButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isJoin == null) {
					Toast.makeText(context, R.string.please_login,
							Toast.LENGTH_SHORT).show();
				} else if (isJoin) {
					comfirmCancelJoin();
				} else {
					join();
				}
			}
		});
		activityJoinButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QiqileApplication.context,
						ActivityJoinersActivity.class);
				startActivity(intent);
			}
		});
		activityCommentButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityProfileActivity.this,
						ActivityCommentListActivity.class);
				startActivity(intent);
			}
		});
	}

	private void join() {
		progressDiaglog.setMessage(getString(R.string.commit_status_in));
		joinTask = new JoinTask(this,progressDiaglog);
		TaskParams params = new TaskParams();
		activityJoinBO = new ActivityJoinBO();
		activityJoinBO.setActivityId(activityBO.getId());
		activityJoinBO.setUserId(userBO.getId());
		params.put("activityJoinBo", activityJoinBO);
		joinTask.execute(params);
	}

	private void cancelJoin() {
		progressDiaglog.setMessage(getString(R.string.commit_status_in));
		joinTask = new CancelJoinTask(this, progressDiaglog);
		TaskParams params = new TaskParams();
		activityJoinBO = new ActivityJoinBO();
		activityJoinBO.setActivityId(activityBO.getId());
		activityJoinBO.setUserId(userBO.getId());
		params.put("activityJoinBo", activityJoinBO);
		joinTask.execute(params);
	}

	// 显示按钮
	private void isJoin() {
		if (QiqileApplication.getInstance().isLogin()) {
			if (activityBO.getIsJoin() == null) {
				progressDiaglog.setMessage(getString(R.string.status_loading));
				isJoinTask = new IsJoinTask(this,progressDiaglog);
				TaskParams params = new TaskParams();
				activityJoinBO = new ActivityJoinBO();
				activityJoinBO.setActivityId(activityBO.getId());
				activityJoinBO.setUserId(userBO.getId());
				params.put("activityJoinBo", activityJoinBO);
				isJoinTask.execute(params);
			} else if (activityBO.getIsJoin()) {
				isJoin = activityBO.getIsJoin();
				getJoinButton().setText(R.string.join_activity_cancel);

			} else {
				getJoinButton().setText(R.string.join_activity);
			}
		} else {
			getJoinButton().setText(R.string.join_activity);
		}
	}

	protected void comfirmCancelJoin() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(R.string.join_activity_comfirm_cancel);
		builder.setPositiveButton(getString(R.string.button_confirm),
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						cancelJoin();
					}
				});
		builder.setNegativeButton(getString(R.string.button_back),
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	public TextView getJoinButton() {
		return joinButton;
	}

	public boolean getIsJoin() {
		return isJoin;
	}

	public void setIsJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}

	class JoinTask extends GenericTask {

		private Context context;
		private ProgressDialog progressDiaglog;

		public JoinTask(Context context, ProgressDialog progressDiaglog) {
			this.context = context;
			this.progressDiaglog = progressDiaglog;
		}

		@Override
		protected void onPreExecute() {
			progressDiaglog.show();
			super.onPreExecute();
		}

		@Override
		protected TaskResult _doInBackground(TaskParams... params) {
			ActivityJoinManager activityJoinManager = QiqileApplication
					.getInstance().getActivityJoinManager();
			TaskResult taskResult = activityJoinManager
					.join((ActivityJoinBO) params[0].get("activityJoinBo"));
			return taskResult;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			super.onPostExecute(result);
			progressDiaglog.dismiss();
			if (result != null) {
				ResultCode resultCode = result.getResult();
				if (resultCode == ResultCode.SUCCESS) {
					Toast.makeText(context,
							context.getString(R.string.join_activity_success),
							Toast.LENGTH_SHORT).show();
					((ActivityProfileActivity) context).getJoinButton()
							.setText(R.string.join_activity_cancel);
				} else {
					Toast.makeText(context,
							context.getString(R.string.error_unknow),
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context,
						context.getString(R.string.error_unknow),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	class CancelJoinTask extends GenericTask {

		private Context context;
		private ProgressDialog progressDiaglog;

		public CancelJoinTask(Context context, ProgressDialog progressDiaglog) {
			this.context = context;
			this.progressDiaglog = progressDiaglog;
		}

		@Override
		protected void onPreExecute() {
			progressDiaglog.show();
			super.onPreExecute();
		}

		@Override
		protected TaskResult _doInBackground(TaskParams... params) {
			ActivityJoinManager activityJoinManager = QiqileApplication
					.getInstance().getActivityJoinManager();
			TaskResult taskResult = activityJoinManager
					.cancelJoin((ActivityJoinBO) params[0]
							.get("activityJoinBo"));
			return taskResult;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			super.onPostExecute(result);
			progressDiaglog.dismiss();
			if (result != null) {
				ResultCode resultCode = result.getResult();
				if (resultCode == ResultCode.SUCCESS) {
					Toast.makeText(
							context,
							context.getString(R.string.join_activity_cancel_success),
							Toast.LENGTH_SHORT).show();
					((ActivityProfileActivity) context).getJoinButton()
							.setText(R.string.join_activity);
				} else {
					Toast.makeText(context,
							context.getString(R.string.error_unknow),
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context,
						context.getString(R.string.error_unknow),
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	/** 判断用户是否加入了这个活动，仅在用户第一次查看本活动时候加载 **/
	class IsJoinTask extends GenericTask {
		private Context context;
		private ProgressDialog progressDiaglog;

		public IsJoinTask(Context context, ProgressDialog progressDiaglog) {
			this.context = context;
			this.progressDiaglog = progressDiaglog;
		}

		@Override
		protected void onPreExecute() {
			progressDiaglog.show();
			super.onPreExecute();
		}

		@Override
		protected TaskResult _doInBackground(TaskParams... params) {
			ActivityJoinManager activityJoinManager = QiqileApplication
					.getInstance().getActivityJoinManager();
			TaskResult taskResult = activityJoinManager
					.isJoin((ActivityJoinBO) params[0].get("activityJoinBo"));
			return taskResult;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			progressDiaglog.dismiss();
			super.onPostExecute(result);
			if (result != null) {
				ResultCode resultCode = result.getResult();
				if (resultCode == ResultCode.SUCCESS) {
					if (result.get("data").equals("1")) {
						((ActivityProfileActivity) context).setIsJoin(true);
						((ActivityProfileActivity) context).getJoinButton()
								.setText(R.string.join_activity_cancel);
						activityBO.setIsJoin(Boolean.TRUE);

					} else {
						((ActivityProfileActivity) context).setIsJoin(false);
						((ActivityProfileActivity) context).getJoinButton()
								.setText(R.string.join_activity);
						activityBO.setIsJoin(Boolean.FALSE);
					}
				} else {
					Toast.makeText(context,
							context.getString(R.string.error_unknow),
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context,
						context.getString(R.string.error_unknow),
						Toast.LENGTH_SHORT).show();
			}
		}

	}

}
