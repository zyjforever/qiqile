package com.zyj.qiqile.activity;

import com.zyj.qiqile.R;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.task.GenericAferExcutedListener;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.LoginTask;
import com.zyj.qiqile.task.TaskListener;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.tools.LocationHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class WelcomeActivity extends Activity {

	// Preferences.
	private SharedPreferences mPreferences;

	// Task
	private GenericTask loadCityTask;

	private TaskListener taskListener = new GenericAferExcutedListener() {

		@Override
		public void onPostExecute(GenericTask task, TaskResult result) {
			init();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.welcome);
		ActivityManagerApplication.getInstance().addActvity(this);
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		if (checkConnention()) {
			loadCityTask = new LoadCityTask();
			loadCityTask.execute(new TaskParams());// 加载城市
		} else {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setMessage("无法连接到网络，请检查网络配置！");
			alert.setPositiveButton("点击关闭", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
					System.exit(0);
				}
			});
			alert.show();
		}
		super.onCreate(savedInstanceState);
	}

	private boolean checkConnention() {
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

	private void autoLogin() {
		if (mPreferences != null) {
			String email = mPreferences.getString(UserBO.EMAIL, null);
			String password = mPreferences.getString(UserBO.PASSWORD, null);
			if (email != null && password != null) {// 需要登录，登陆后启动主页，否则，直接启动
				GenericTask task = new LoginTask(this);
				task.setListener(taskListener);
				TaskParams params = new TaskParams();
				params.put(UserBO.PASSWORD, password);
				params.put(UserBO.EMAIL, email);
				task.execute(params);
			} else {
				init();
			}
		}
	}

	private void init() {
		Intent intent = new Intent(this, QiqileMainActivity.class);
		startActivity(intent);
	}

	class LoadCityTask extends GenericTask {
		Context context = getBaseContext();

		@Override
		protected TaskResult _doInBackground(TaskParams... params) {
			TaskResult taskResult = new TaskResult();
			String city = LocationHelper.getCurrentCity(context);
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
			QiqileApplication.getInstance().setCity(city);
			autoLogin();// 需要自动登录，登陆后启动主页，否则，直接启动
		}
	};
}
