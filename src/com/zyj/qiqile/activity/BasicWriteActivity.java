package com.zyj.qiqile.activity;

import com.zyj.qiqile.R;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * header��һ��back��ť��һ��confirm��ť��activity���̳������
 * */
public abstract class BasicWriteActivity extends BasicActivity {
	protected TextView backButton;
	protected TextView confirmButton;
	protected TextView headTitle;
	protected Context context;
	protected OnClickListener backListener;
	protected OnClickListener confirmListener;
	protected String titleString;

	public TextView getBackButton() {
		return backButton;
	}

	public void setBackButton(TextView backButton) {
		this.backButton = backButton;
	}

	public TextView getHeadTitle() {
		return headTitle;
	}

	public void setHeadTitle(TextView headTitle) {
		this.headTitle = headTitle;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public OnClickListener getBackListener() {
		return backListener;
	}

	public void setBackListener(OnClickListener backListener) {
		this.backListener = backListener;
	}

	public String getTitleString() {
		return titleString;
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	public void init() {
		setContext();// ����context
		super.init();
		setHeadTitle();
		QiqileApplication.getInstance().context = context;
		ActivityManagerApplication.getInstance().addActvity((Activity) context);
	}

	@Override
	protected void initView() {
		backButton = (TextView) findViewById(R.id.top_back);
		headTitle = (TextView) findViewById(R.id.title);
		confirmButton = (TextView) findViewById(R.id.top_confirm_btn);
	}

	@Override
	protected void initListener() {
		backListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				beforeBack();
				((Activity) context).onBackPressed();
			}
		};
		confirmListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validate()) {
					beforeFinish();
//					((Activity) context).finish();// ������ǰ��activity
				}
			}
		};

	}

	@Override
	protected void bindListener() {
		backButton.setOnClickListener(backListener);
		confirmButton.setOnClickListener(confirmListener);
	}

	private void setHeadTitle() {
		headTitle.setText(titleString);
	}

	/** ����context */
	protected abstract void setContext();

	/*** ��֤���ص����ݣ����û��������Ҫ��֤��ֱ�ӷ���true */
	protected abstract Boolean validate();

	protected abstract void beforeBack();

	/** activity finish ǰ��ɵ����� */
	protected abstract void beforeFinish();
	
	protected void hiddenSoftInput(EditText edit){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        imm.hideSoftInputFromWindow(edit.getWindowToken(),0);
	}

}
