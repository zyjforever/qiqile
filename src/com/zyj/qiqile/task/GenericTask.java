package com.zyj.qiqile.task;

import java.util.Observable;

import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.ui.module.Feedback;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * ������һ����������������task���Ը��õ�ʵ�֣�      
 * 
 * �Ѿ��������޹��߼����̺���ʾЧ������ɲ�ͬ��������ȥʵ�֣� �����ô������̶ȵ����á�
 * 
 * ��Щ����Ƚϼ򵥣����ܲ���Ҫ��ʾ����������ôֱ����TaskListener���涨��򵥵�Toast��ʾ���ɡ�
 * 
 * @author zyj
 * */
public abstract class GenericTask extends
		AsyncTask<TaskParams, Object, TaskResult> {
	private static final String TAG = "TaskManager";

	/** �����������ǰ����߼� */
	private TaskListener taskListener = null;
	/** ִ����������ϵı仯 */
	private Feedback feedback = null;
	private boolean isCancelable = true;

	/** ������ɵ������߼� */
	abstract protected TaskResult _doInBackground(TaskParams... params);

	public void setListener(TaskListener taskListener) {
		this.taskListener = taskListener;
	}

	public TaskListener getListener() {
		return taskListener;
	}

	public void doPublishProgress(Object... values) {
		super.publishProgress(values);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();

		if (taskListener != null) {
			taskListener.onCancelled(this);
		}
		Log.d(TAG, taskListener.getName() + " has been Cancelled.");
		Toast.makeText(QiqileApplication.context, taskListener.getName()
				+ " has been cancelled", Toast.LENGTH_SHORT);
	}

	@Override
	protected void onPostExecute(TaskResult result) {
		super.onPostExecute(result);

		if (taskListener != null) {
			taskListener.onPostExecute(this, result);
		}

		if (feedback != null) {
			feedback.success("",result);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		if (taskListener != null) {
			taskListener.onPreExecute(this);
		}

		if (feedback != null) {
			feedback.start("");
		}
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);

		if (taskListener != null) {
			if (values != null && values.length > 0) {
				taskListener.onProgressUpdate(this, values[0]);
			}
		}

		if (feedback != null) {
			feedback.update(values[0]);
		}
	}

	@Override
	protected TaskResult doInBackground(TaskParams... params) {
		TaskResult result = _doInBackground(params);
		if (feedback != null) {
			feedback.update(99);
		}
		return result;
	}

	public void update(Observable o, Object arg) {
		if (TaskManager.CANCEL_ALL == (Integer) arg && isCancelable) {
			if (getStatus() == GenericTask.Status.RUNNING) {
				cancel(true);
			}
		}
	}

	public void setCancelable(boolean flag) {
		isCancelable = flag;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

}
