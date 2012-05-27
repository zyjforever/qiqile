package com.zyj.qiqile.activity.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicListActivity;
import com.zyj.qiqile.activity.activity.ActivityCommentListActivity.CommentItemAdapter;
import com.zyj.qiqile.activity.friend.UserProfileActivity;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.ActivityCommentBO;
import com.zyj.qiqile.domain.bo.ActivityJoinBO;
import com.zyj.qiqile.domain.vo.UserVO;
import com.zyj.qiqile.manager.ActivityCommentManager;
import com.zyj.qiqile.manager.ActivityJoinManager;
import com.zyj.qiqile.manager.impl.ActivityCommentManagerImpl;
import com.zyj.qiqile.manager.impl.ActivityJoinManagerImpl;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.ui.adapter.UserListItemAdapter;

public class ActivityJoinersActivity extends BasicListActivity {

	public static final String TAG = "ActivityJoinersActivity";

	private Button backButton;
	private ProgressDialog progressDialog;

	// 加载活动参与者的task
	private GenericTask loadJoinTask;

	@Override
	public void init() {
		super.init();
		getDatas();
	}

	@Override
	protected void initView() {
		backButton = (Button) findViewById(R.id.top_back);
		listView = new ListView(this);
		((TextView) findViewById(R.id.title))
				.setText(R.string.activity_join_header);
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getString(R.string.status_loading));
	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.activity_joiners);
	}

	@Override
	protected void setItemLayout() {
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
	}

	@Override
	protected void bindListener() {
	}

	@Override
	protected void getDatas() {
		this.loadJoinTask = new LoadActivityJoinListTask(context,
				progressDialog);
		TaskParams params = new TaskParams();
		params.put(ActivityJoinBO.ACTIVITY_ID, QiqileApplication.getInstance()
				.getCurrentLookActivity().getId());
		loadJoinTask.execute(params);
	}

	// 把列表交给控件显示
	public void pushJoinList(List<ActivityJoinBO> activityJoinBOList) {
		if (activityJoinBOList != null && activityJoinBOList.size() > 0) {
			List<UserVO> userVOList = new ArrayList<UserVO>();
			for (ActivityJoinBO activityJoinBO : activityJoinBOList) {
				userVOList.add(activityJoinBO.getUserVO());
			}
			this.adapter = new UserListItemAdapter(getLayoutInflater(),
					userVOList);
			setListAdapter(adapter);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (QiqileApplication.getInstance().getUserBO()== null
				|| !QiqileApplication.getInstance().getUserBO().getId()
						.equals(id)) {
			Intent intent = new Intent(this, UserProfileActivity.class);
			intent.putExtra(UserVO.USERID, String.valueOf(id));
			startActivity(intent);
		} else {
			Toast.makeText(context, R.string.error_is_yourself,
					Toast.LENGTH_SHORT).show();
		}
	}

	/** 加载评论列表的类 */
	class LoadActivityJoinListTask extends GenericTask {
		private Context context;
		private ProgressDialog progressDialog;

		public LoadActivityJoinListTask(Context context,
				ProgressDialog progressDialog) {
			this.context = context;
			this.progressDialog = progressDialog;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			super.onPostExecute(result);
			if (result != null && result.getResult() == ResultCode.SUCCESS) {
				List<ActivityJoinBO> activityJoinBOList = (List<ActivityJoinBO>) result
						.get("activityJoinBOList");
				pushJoinList(activityJoinBOList);
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
				ActivityJoinManager activityJoinManagerManager = new ActivityJoinManagerImpl();
				taskResult = activityJoinManagerManager
						.queryActivityJoinBOByActivityId((String) params[0]
								.get(ActivityCommentBO.ACTIVITY_ID));
			}
			return taskResult;
		}
	}

}
