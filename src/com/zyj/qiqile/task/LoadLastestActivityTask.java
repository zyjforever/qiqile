package com.zyj.qiqile.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.manager.ActivityManager;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.TimeHelper;

public class LoadLastestActivityTask extends GenericTask {

	private Context context;
	private ProgressDialog progressDialog;

	public LoadLastestActivityTask(Context context,
			ProgressDialog progressDialog) {
		this.context = context;
		this.progressDialog = progressDialog;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Toast.makeText(context, context.getString(R.string.status_loading),
		// Toast.LENGTH_SHORT).show();
		progressDialog.show();
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
		Toast.makeText(context, context.getString(R.string.status_loading),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected TaskResult _doInBackground(TaskParams... params) {
		TaskResult result = null;
		ActivityManager activityManager = QiqileApplication.getInstance()
				.getActivityManager();
		if (params[0].get("city") != null && params[0].get("time") != null) {
			result = activityManager.queryActivityByCityAndTime(
					params[0].get("city").toString(),
					(Date) params[0].get("time"));
		}
		return result;
	}

	@Override
	protected void onPostExecute(TaskResult result) {
		super.onPostExecute(result);
		if (result != null) {
			ResultCode resultCode = result.getResult();
			if (resultCode == ResultCode.SUCCESS) {
				progressDialog.dismiss();
				Toast.makeText(context,
						context.getString(R.string.status_success),
						Toast.LENGTH_SHORT).show();
				List<ActivityBO> activityBOList = (List<ActivityBO>) result
						.get("activityBOList");
				String city = result.get("city").toString();
				Date time = (Date) result.get("time");
				if (activityBOList != null && activityBOList.size() > 0) {
					Map<String, List<List<ActivityBO>>> mapList = QiqileApplication
							.getInstance().getActivityCityMapList();
					List<List<ActivityBO>> cityActivityList = null;
					if (mapList.containsKey(city)) {
						cityActivityList = mapList.get(city);
					} else {
						cityActivityList = new ArrayList<List<ActivityBO>>();
						mapList.put(city, cityActivityList);
					}
					Date now = TimeHelper.getCurrentTime(0);
					if (cityActivityList.size() > TimeHelper.compare(now, time))
						cityActivityList.add(TimeHelper.compare(now, time),
								activityBOList);// 根据天数放到不同的位置，每一天，一份数据
					else
						cityActivityList.add(activityBOList);
				} else {
					Toast.makeText(context,
							context.getString(R.string.activity_status_empty),
							Toast.LENGTH_SHORT).show();
				}

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
			Toast.makeText(context, context.getString(R.string.error_unknow),
					Toast.LENGTH_SHORT).show();
		}
	}
}
