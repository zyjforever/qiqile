package com.zyj.qiqile.activity.activity;

import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicWriteActivity;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.ActivityCommentBO;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.manager.ActivityCommentManager;
import com.zyj.qiqile.manager.impl.ActivityCommentManagerImpl;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.Validator;

public class ActivityCommentWriteActivity extends BasicWriteActivity {
	private EditText commentEdit;

	private String commentContext;

	private GenericTask addActivityCommentTask;

	@Override
	protected void setContext() {
		this.context = this;
	}

	@Override
	protected Boolean validate() {
		Boolean result = Boolean.FALSE;
		commentContext = commentEdit.getText().toString();
		if (commentContext != null) {
			if (Validator.isActivityComment(commentContext)) {
				result = Boolean.TRUE;
			} else {
				Toast.makeText(this, R.string.error_activity_comment_too_long,
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, R.string.write_label_send_hint,
					Toast.LENGTH_SHORT).show();
		}
		return result;
	}

	@Override
	protected void initView() {
		super.initView();
		commentEdit = (EditText) findViewById(R.id.comment_edit);
	}

	@Override
	protected void beforeBack() {
		finish();
	}

	@Override
	protected void beforeFinish() {
		ProgressDialog progressDiaglog = new ProgressDialog(this);
		progressDiaglog.setMessage(getString(R.string.status_loading));
		this.addActivityCommentTask = new AddActivityCommentTask(context,
				progressDiaglog);
		UserBO userBO = QiqileApplication.getInstance().getUserBO();
		ActivityCommentBO activityCommentBO = new ActivityCommentBO();
		activityCommentBO.setUserId(userBO.getId());
		activityCommentBO.setContext(commentContext);
		activityCommentBO.setTime(new Date());
		activityCommentBO.setActivityId(QiqileApplication.getInstance()
				.getCurrentLookActivity().getId());
		activityCommentBO.setUserName(userBO.getNick());
		activityCommentBO.setUserPicName(userBO.getPicName());
		activityCommentBO.setUserPicUrl(userBO.getPicUrl());
		TaskParams taskParams = new TaskParams();
		taskParams.put("activityCommentBO", activityCommentBO);
		addActivityCommentTask.execute(taskParams);
	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.activity_comment_write);
	}

	class AddActivityCommentTask extends GenericTask {
		private Context context;
		private ProgressDialog progressDialog;
		private ActivityCommentBO activityCommentBO;

		public AddActivityCommentTask(Context context,
				ProgressDialog progressDialog) {
			this.context = context;
			this.progressDialog = progressDialog;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			super.onPostExecute(result);
			if (result != null && result.getResult() == ResultCode.SUCCESS) {
				QiqileApplication.getInstance().getCurrentLookActivity()
						.getActivityCommentBOList().add(activityCommentBO);
				Toast.makeText(context, R.string.commit_status_success,
						Toast.LENGTH_SHORT).show();
				progressDialog.dismiss();
				finish();
				onBackPressed();
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
				ActivityCommentManager activityCommentManager = new ActivityCommentManagerImpl();
				activityCommentBO = (ActivityCommentBO) params[0]
						.get("activityCommentBO");
				taskResult = activityCommentManager
						.addActivityComment(activityCommentBO);
			}
			return taskResult;
		}

	}

}
