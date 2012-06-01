package com.zyj.qiqile.activity.friend;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicListActivity;
import com.zyj.qiqile.activity.friend.UserProfileActivity;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.UserAttentionBO;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.domain.vo.UserVO;
import com.zyj.qiqile.manager.UserAttentionManager;
import com.zyj.qiqile.manager.impl.UserAttentionManagerImpl;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.ui.adapter.UserListItemAdapter;

public class FriendListActivity extends BasicListActivity {

	public static final String TAG = "FriendListActivity";

	private ImageButton refreshButton;
	private ProgressDialog progressDialog;

	// 加载活动参与者的task
	private GenericTask loadAttentionListTask;

	@Override
	public void init() {
		super.init();
		getDatas();
	}

	@Override
	protected void initView() {
		refreshButton = (ImageButton) findViewById(R.id.refresh_button);
		listView = new ListView(this);
		((TextView) findViewById(R.id.title)).setText(R.string.friend_header);
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getString(R.string.status_loading));
	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.friend_list);
	}

	@Override
	protected void setItemLayout() {
	}

	@Override
	protected void initListener() {
		refreshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getDatas();
			}
		});
	}

	@Override
	protected void bindListener() {
	}

	@Override
	protected void getDatas() {
		this.loadAttentionListTask = new LoadAttentionListTask(context,
				progressDialog);
		TaskParams params = new TaskParams();
		params.put(UserBO.ID, QiqileApplication.getInstance().getUserBO()
				.getId());
		loadAttentionListTask.execute(params);
	}

	// 把列表交给控件显示
	public void pushAttentionList(List<UserAttentionBO> userAttentionBOList) {
		if (userAttentionBOList != null && userAttentionBOList.size() > 0) {
			List<UserVO> userVOList = new ArrayList<UserVO>();
			for (UserAttentionBO userAttentionBO : userAttentionBOList) {
				userVOList.add(userAttentionBO.getUserVO());
			}
			this.adapter = new UserListItemAdapter(getLayoutInflater(),
					userVOList);
			setListAdapter(adapter);
		} else {
			Toast.makeText(this, R.string.empty_attention, Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, UserProfileActivity.class);
		intent.putExtra(UserVO.USERID, String.valueOf(id));
		startActivity(intent);
	}

	/** 加载评论列表的类 */
	class LoadAttentionListTask extends GenericTask {
		private Context context;
		private ProgressDialog progressDialog;

		public LoadAttentionListTask(Context context,
				ProgressDialog progressDialog) {
			this.context = context;
			this.progressDialog = progressDialog;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			super.onPostExecute(result);
			if (result != null && result.getResult() == ResultCode.SUCCESS) {
				List<UserAttentionBO> attentionBOList = (List<UserAttentionBO>) result
						.get("userAttentionBOList");
				pushAttentionList(attentionBOList);
				QiqileApplication.getInstance().setMyAttentionList(
						attentionBOList);
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
				UserAttentionManager userAttentionManager = new UserAttentionManagerImpl();
				taskResult = userAttentionManager
						.queryUserAttentionList((String) params[0]
								.get(UserBO.ID));
			}
			return taskResult;
		}
	}

}
