package com.zyj.qiqile.activity.activity;

import java.util.List;

import org.json.JSONException;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicMapActivity;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.customview.PullToRefreshListView;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.tools.LocationHelper;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityListActivity extends ActivityGroup {

	public static final String TAG = "ActivityListActivity";

	private TextView headerTitle;
	private LinearLayout containerBody;
	private ImageButton editButton;
	private ImageButton mapListButton;
//	private ImageButton refreshButton;
	private TextView locationView;

	// Task
	private GenericTask loadCityTask;

	public void init() {
		setContentView();// 设置contentView
		initView();// 初始化view
		initListener();// 初始化监听器
		bindListener();// 绑定监听器
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		ActivityManagerApplication.getInstance().addActvity(this);
		QiqileApplication.getInstance().context = this;
		headerTitle.setText(R.string.app_name);
		showActivityView();
	}

	public void setContentView() {
		super.setContentView(R.layout.activity_list);
	}

	public void refresh() {
		//加载最新的活动
		
		
	}

	protected void initView() {
		headerTitle = (TextView) findViewById(R.id.header_title);
		editButton = (ImageButton) findViewById(R.id.edit_button);
		mapListButton = (ImageButton) findViewById(R.id.map_list_button);
//		refreshButton = (ImageButton) findViewById(R.id.refresh_button);
		containerBody = (LinearLayout) findViewById(R.id.containerBody);
		locationView = (TextView) findViewById(R.id.location);
		
		if (QiqileApplication.getInstance().getCity() != null) {
			locationView.setText(QiqileApplication.getInstance().getCity());
		} else {
			loadCityTask = new GenericTask() {
				@Override
				protected TaskResult _doInBackground(TaskParams... params) {
					TaskResult taskResult = new TaskResult();
					String city = LocationHelper
							.getCurrentCity(QiqileApplication.context);
					taskResult.put("city", city);
					return taskResult;
				}

				@Override
				protected void onPostExecute(TaskResult result) {
					super.onPostExecute(result);
					String city = "";
					if (result.get("city") != null) {
						city = result.get("city").toString();
					}
					locationView.setText(city);
					QiqileApplication.getInstance().setCity(city);
				}
			};
			loadCityTask.execute(null);
		}
	}

	protected void initListener() {
	}

	protected void bindListener() {
		editButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (QiqileApplication.getInstance().isLogin()) {
					Intent intent = new Intent(ActivityListActivity.this,
							ActivityEdit1Activity.class);
					startActivity(intent);
				} else {
					Toast.makeText(ActivityListActivity.this,
							R.string.please_login, Toast.LENGTH_LONG).show();
				}
			}
		});
		mapListButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QiqileApplication.getInstance().setIsMapList(
						!QiqileApplication.getInstance().getIsMapList());
				showActivityView();
			}
		});
	}

	private void showActivityView() {
		if (QiqileApplication.getInstance().getIsMapList()) {
			Intent intent = new Intent(ActivityListActivity.this,
					ActivityMapListViewActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			containerBody.removeAllViews();
			containerBody.addView(getLocalActivityManager().startActivity(
					"BMap", intent).getDecorView());
			mapListButton.setImageResource(R.drawable.view_list);
		} else {
			containerBody.removeAllViews();
			// containerBody.addView(new PullToRefreshListView(this));
			mapListButton.setImageResource(R.drawable.map_button);
		}
	}
}