package com.zyj.qiqile.task;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.UploadHelper;

public class UploadImageTask extends GenericTask {
	private Context context;

	public UploadImageTask() {
		this.context = QiqileApplication.context;
	}

	public UploadImageTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Toast.makeText(context,
				context.getString(R.string.upload_img_status_in),
				Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
		Toast.makeText(context,
				context.getString(R.string.upload_img_status_in),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected TaskResult _doInBackground(TaskParams... params) {
		TaskResult taskResult = new TaskResult();
		taskResult.setResult(ResultCode.FAILED);
		String result = UploadHelper.uploadFile((File) params[0].get("file"),
				(String) params[0].get("url"));
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				result = jsonObject.getString("result");
				if (!result.equals("false") && !result.equals("")) {
					taskResult.setResult(ResultCode.SUCCESS);
					taskResult.put("picUrl", result);
				}
			} catch (JSONException e) {
				return taskResult;
			}
		}
		return taskResult;
	}

	@Override
	protected void onPostExecute(TaskResult result) {
		super.onPostExecute(result);
		if (result != null) {
			ResultCode resultCode = result.getResult();
			if (resultCode == ResultCode.SUCCESS) {
				Toast.makeText(context,
						context.getString(R.string.upload_img_status_success),
						Toast.LENGTH_SHORT).show();
			} else if (resultCode == ResultCode.FAILED) {
				Toast.makeText(context,
						context.getString(R.string.upload_img_status_fail),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context,
						context.getString(R.string.error_unknow),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context,
					context.getString(R.string.upload_img_status_fail),
					Toast.LENGTH_SHORT).show();
		}
	}
}
