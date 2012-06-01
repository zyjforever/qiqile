package com.zyj.qiqile.activity.activity;

import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicWriteActivity;
import com.zyj.qiqile.activity.QiqileMainActivity;
import com.zyj.qiqile.activity.module.CommonImageModule;
import com.zyj.qiqile.activity.module.CommonImageModule.PicType;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.ServerConstants;
import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.manager.ActivityManager;
import com.zyj.qiqile.task.GenericAferExcutedListener;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.TaskListener;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.task.UploadImageTask;
import com.zyj.qiqile.tools.TimeHelper;

public class ActivityEdit7Activity extends BasicWriteActivity {
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
	// module
	private CommonImageModule commonImageModule;
	private ActivityBO activityBO;
	private UserBO userBO;
	private String picUrl;

	// task
	private GenericTask uploadImageTask;
	private GenericTask addOrUpadteActivityTask;

	private ProgressDialog progressDiaglog;

	// taks listener
	private TaskListener uploadImageListener = new GenericAferExcutedListener() {
		@Override
		public void onPostExecute(GenericTask task, TaskResult result) {
			if (result.getResult() == ResultCode.SUCCESS) {
				picUrl = (String) result.get("picUrl");
				activityBO.setPicUrl(picUrl);
				addOrUpdate();
			}
		}
	};

	@Override
	public void init() {
		activityBO = QiqileApplication.getInstance().getCurrentEditActivity();
		userBO = QiqileApplication.getInstance().getUserBO();
		super.init();
	}

	protected void addOrUpdate() {
		TaskParams params = new TaskParams();
		progressDiaglog = new ProgressDialog(context);
		progressDiaglog.setMessage(getString(R.string.commit_status_in));
		if (activityBO.getId() != null) {
			// 编辑
			params.put("activityBO", activityBO);
			addOrUpadteActivityTask = new UpdateActivityTask(context,
					progressDiaglog);
			addOrUpadteActivityTask.execute(params);
		} else {
			activityBO.setTime(new Date());
			params.put("activityBO", activityBO);
			addOrUpadteActivityTask = new AddActivityTask(context,
					progressDiaglog);
			addOrUpadteActivityTask.execute(params);
		}
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
		activityUserNameText.setText(userBO.getNick());
		commonImageModule.showImage(activityImage, activityBO.getPicFile());
		commonImageModule.showImage(activityUserImage, userBO.getPicName(),
				PicType.USER);
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
	protected void setContext() {
		this.setContext(ActivityEdit7Activity.this);
	}

	@Override
	protected Boolean validate() {
		// 前面每个步骤已经检测过了，这个就直接过了。
		Boolean result = Boolean.TRUE;
		return result;
	}

	@Override
	protected void beforeBack() {

	}

	@Override
	protected void beforeFinish() {
		if (activityBO.getNewPic() != null && activityBO.getNewPic()) {
			// 提交图片
			uploadImageTask = new UploadImageTask(context);
			TaskParams params = new TaskParams();
			params.put("file", activityBO.getPicFile());
			params.put("url", ServerConstants.HTTP
					+ ServerConstants.ACTIVITY_PIC_SERVER_URL);
			uploadImageTask.setListener(uploadImageListener);
			uploadImageTask.execute(params);
		} else {
			addOrUpdate();
		}
	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.activity_edit_7);
		this.setTitleString(getString(R.string.activity_preview));
	}

	class AddActivityTask extends GenericTask {
		private Context context;
		private ProgressDialog progressDialog;

		public AddActivityTask(Context context, ProgressDialog progressDialog) {
			this.context = context;
			this.progressDialog=progressDialog;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected TaskResult _doInBackground(TaskParams... params) {
			ActivityManager activityManager = QiqileApplication.getInstance()
					.getActivityManager();
			TaskResult taskResult = activityManager
					.addActivity((ActivityBO) params[0].get("activityBO"));
			return taskResult;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			QiqileApplication.getInstance().setCurrentEditActivity(null);
			progressDialog.dismiss();
			if (result != null) {
				ResultCode resultCode = result.getResult();
				if (resultCode == ResultCode.SUCCESS) {
					Toast.makeText(context,
							context.getString(R.string.commit_status_success),
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(context,
							QiqileMainActivity.class);
					context.startActivity(intent);
					((Activity) context).finish();// 返回主页
					QiqileApplication.getInstance().setCurrentEditActivity(null);
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

	class UpdateActivityTask extends GenericTask {
		private Context context;
		private ProgressDialog progressDialog;

		public UpdateActivityTask(Context context, ProgressDialog progressDialog) {
			this.context = context;
			this.progressDialog = progressDialog;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected TaskResult _doInBackground(TaskParams... params) {
			ActivityManager activityManager = QiqileApplication.getInstance()
					.getActivityManager();
			TaskResult taskResult = activityManager
					.updateActivity((ActivityBO) params[0].get("activityBO"));
			return taskResult;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			QiqileApplication.getInstance().setCurrentEditActivity(null);
			progressDialog.dismiss();
			if (result != null) {
				ResultCode resultCode = result.getResult();
				if (resultCode == ResultCode.SUCCESS) {
					Toast.makeText(context,
							context.getString(R.string.commit_status_success),
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(context,
							QiqileMainActivity.class);
					context.startActivity(intent);
					((Activity) context).finish();// 返回主页
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
}