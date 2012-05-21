package com.zyj.qiqile.task;

/**һ��������еĻ�������*/
public interface TaskListener {
	String getName();

	void onPreExecute(GenericTask task);

	void onPostExecute(GenericTask task, TaskResult result);

	void onProgressUpdate(GenericTask task, Object param);

	void onCancelled(GenericTask task);
}
