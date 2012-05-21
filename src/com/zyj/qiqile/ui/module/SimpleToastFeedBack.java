package com.zyj.qiqile.ui.module;

import com.zyj.qiqile.task.TaskResult;

import android.content.Context;

public class SimpleToastFeedBack implements Feedback{
	
	private Context context;
	
	@Override
	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void start(CharSequence text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel(CharSequence text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void success(CharSequence text,TaskResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void failed(CharSequence text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIndeterminate(boolean indeterminate) {
		// TODO Auto-generated method stub
		
	}

}
