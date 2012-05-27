package com.zyj.qiqile.task;

import java.io.File;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.DownloadHelper;

public class SimpleDownloadImageTask extends GenericTask {

	public static final String TAG = "SimpleDownloadImageTask";

	private Context context;

	public SimpleDownloadImageTask() {
		this.context = QiqileApplication.context;
	}

	public SimpleDownloadImageTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
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
				Log.i(TAG, "load image success");
			} else if (resultCode == ResultCode.FAILED) {
				Log.w(TAG, "load image failed");
			} else {
				Log.w(TAG, "load image failed");
			}
		} else {
			Log.i(TAG, "load image failed");
		}
	}

}
