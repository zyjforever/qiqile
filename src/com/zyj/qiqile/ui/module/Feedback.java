package com.zyj.qiqile.ui.module;

import com.zyj.qiqile.task.TaskResult;

/**抽象了异步任务执行时，操作提示框的基本行为*/
public interface Feedback {
	public boolean isAvailable();

	public void start(CharSequence text);

	public void cancel(CharSequence text);

	public void success(CharSequence text,TaskResult result);

	public void failed(CharSequence text);

	public void update(Object arg0);

	public void setIndeterminate(boolean indeterminate);
}
