package com.zyj.qiqile.task;

/**成功之前之后，需要的其他逻辑操作*/
public abstract class GenericAferExcutedListener implements TaskListener {
	@Override
	public void onProgressUpdate(GenericTask task, Object param) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPreExecute(GenericTask task) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onCancelled(GenericTask task) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		return null;
	}
}
