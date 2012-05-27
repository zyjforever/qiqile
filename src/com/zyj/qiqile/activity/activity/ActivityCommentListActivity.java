package com.zyj.qiqile.activity.activity;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicListActivity;
import com.zyj.qiqile.activity.module.item.ActivityCommentItem;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.Constants;
import com.zyj.qiqile.domain.bo.ActivityCommentBO;
import com.zyj.qiqile.manager.ActivityCommentManager;
import com.zyj.qiqile.manager.impl.ActivityCommentManagerImpl;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;

public class ActivityCommentListActivity extends BasicListActivity {

	private Button backButton;
	private ImageButton editButton;
	private ProgressDialog progressDiaglog;

	private GenericTask loadCommentListTask;

	@Override
	protected void init() {
		super.init();
		getDatas();
	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.activity_comment_list);
	}

	@Override
	protected void setItemLayout() {
		this.listItemLayout = R.layout.activity_comment_item;
	}

	@Override
	protected void initView() {
		backButton = (Button) findViewById(R.id.top_back);
		editButton = (ImageButton) findViewById(R.id.writeMessage);
		listView = new ListView(this);
		progressDiaglog = new ProgressDialog(this);
		progressDiaglog.setMessage(getString(R.string.status_loading));
	}

	@Override
	protected void initListener() {
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();// 结束当前的activity
				onBackPressed();
			}
		});
		editButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (QiqileApplication.getInstance().isLogin()) {
					Intent intent = new Intent(
							ActivityCommentListActivity.this,
							ActivityCommentWriteActivity.class);
					startActivityForResult(intent,
							Constants.REQUEST_ACTIVITY_COMMENT);
				} else {
					Toast.makeText(context, R.string.please_login,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		getDatas();
	}

	@Override
	protected void bindListener() {
	}

	@Override
	protected void getDatas() {
		loadCommentListTask = new LoadActivityCommentListTask(this,
				progressDiaglog);
		TaskParams params = new TaskParams();
		params.put(ActivityCommentBO.ACTIVITY_ID, QiqileApplication
				.getInstance().getCurrentLookActivity().getId());
		loadCommentListTask.execute(params);
	}
	//把列表交给控件显示
	private void pushCommentList(List<ActivityCommentBO> activitityCommentBOList) {
		this.adapter = new CommentItemAdapter(context, activitityCommentBOList);
		setListAdapter(adapter);
	}

	/** 适配器 */
	class CommentItemAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private List<ActivityCommentBO> activitityCommentBOList;

		public CommentItemAdapter(Context context,
				List<ActivityCommentBO> activitityCommentBOList) {
			this.mInflater = LayoutInflater.from(context);
			this.activitityCommentBOList = activitityCommentBOList;
		}

		@Override
		public int getCount() {
			int size = 0;
			if (activitityCommentBOList != null) {
				size = activitityCommentBOList.size();
			}
			return size;
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ActivityCommentItem holder = new ActivityCommentItem();
			convertView = mInflater.inflate(R.layout.activity_comment_item,
					null);
			holder.setCommentContext((TextView) convertView
					.findViewById(R.id.comment_context));
			holder.setCommentTimeText((TextView) convertView
					.findViewById(R.id.comment_time));
			holder.setUserImageView((ImageView) convertView
					.findViewById(R.id.user_image));
			holder.setUserNameText((TextView) convertView
					.findViewById(R.id.user_name));
			holder.setReplyButton((TextView) convertView
					.findViewById(R.id.reply_button));
			holder.initView(this.activitityCommentBOList.get(position));
			convertView.setTag(holder);
			return convertView;
		}
	}

	/** 加载评论列表的类 */
	class LoadActivityCommentListTask extends GenericTask {
		private Context context;
		private ProgressDialog progressDialog;

		public LoadActivityCommentListTask(Context context,
				ProgressDialog progressDialog) {
			this.context = context;
			this.progressDialog = progressDialog;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			super.onPostExecute(result);
			if (result != null && result.getResult() == ResultCode.SUCCESS) {
				List<ActivityCommentBO> activityCommentBOList = (List<ActivityCommentBO>) result
						.get("activityCommentBOList");
				QiqileApplication.getInstance().getCurrentLookActivity()
						.setActivityCommentBOList(activityCommentBOList);
				pushCommentList(activityCommentBOList);
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
				ActivityCommentManager activityCommentManager = new ActivityCommentManagerImpl();
				taskResult = activityCommentManager
						.queryActivityCommentByActivityId((String) params[0]
								.get(ActivityCommentBO.ACTIVITY_ID));
			}
			return taskResult;
		}
	}
}