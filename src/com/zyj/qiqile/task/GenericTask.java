package com.zyj.qiqile.task;

import java.util.Observable;

import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.ui.module.Feedback;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * 抽象了一个基本任务，这里让task可以更好地实现，      
 * 
 * 把具体任务无关逻辑过程和显示效果分离成不同的两个类去实现， 可以让代码最大程度地重用。
 * 
 * 有些任务比较简单，可能不需要显示进度条，那么直接在TaskListener里面定义简单的Toast提示即可。
 * 
 * @author zyj
 * */
public abstract class GenericTask extends
		AsyncTask<TaskParams, Object, TaskResult> {
	private static final String TAG = "TaskManager";

	/** 定义完成任务前后的逻辑 */
	private TaskListener taskListener = null;
	/** 执行任务界面上的变化 */
	private Feedback feedback = null;
	private boolean isCancelable = true;

	/** 具体完成的任务逻辑 */
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
