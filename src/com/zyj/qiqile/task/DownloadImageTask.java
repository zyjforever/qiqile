package com.zyj.qiqile.task;

import java.io.File;


import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.DownloadHelper;

public class DownloadImageTask extends GenericTask {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Toast.makeText(QiqileApplication.context,
				QiqileApplication.context.getString(R.string.status_loading),
				Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
		Toast.makeText(QiqileApplication.context,
				QiqileApplication.context.getString(R.string.status_loading),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected TaskResult _doInBackground(TaskParams... params) {
		TaskResult taskResult = new TaskResult();
		taskResult.setResult(ResultCode.FAILED);
		boolean result = DownloadHelper.downloadFile(
				(File) params[0].get("file"), (String) params[0].get("url"));
		if (result) {
			taskResult.setResult(ResultCode.SUCCESS);
			taskResult.put("picUrl", result);

		}
		return taskResult;
	}

	@Override
	protected void onPostExecute(TaskResult result) {
		super.onPostExecute(result);
		if (result != null) {
			ResultCode resultCode = result.getResult();
			if (resultCode == ResultCode.SUCCESS) {
				Toast.makeText(
						QiqileApplication.context,
						QiqileApplication.context
								.getString(R.string.status_success),
						Toast.LENGTH_SHORT).show();
			} else if (resultCode == ResultCode.FAILED) {
				Toast.makeText(
						QiqileApplication.context,
						QiqileApplication.context
								.getString(R.string.status_fail),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(
						QiqileApplication.context,
						QiqileApplication.context
								.getString(R.string.error_unknow),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(
					QiqileApplication.context,
					QiqileApplication.context
							.getString(R.string.status_fail),
					Toast.LENGTH_SHORT).show();
		}
	}

}
