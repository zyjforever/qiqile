package com.zyj.qiqile.activity.meprofile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicBackActivity;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.constant.Constants;
import com.zyj.qiqile.domain.bo.UserBO;

public class MeProfileEditSexActivity extends BasicBackActivity {

	private RadioButton boyRadioButton;
	private RadioButton girlRadioButton;
	private RadioButton otherRadioButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManagerApplication.getInstance().addActvity(this);
	}

	@Override
	protected void initView() {
		super.initView();
		boyRadioButton = (RadioButton) findViewById(R.id.boy_radioButton);
		girlRadioButton = (RadioButton) findViewById(R.id.girl_radioButton);
		otherRadioButton = (RadioButton) findViewById(R.id.other_radioButton);
	}

	@Override
	protected void bindListener() {
		super.bindListener();
		boyRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					returnTOMain(Constants.BOY);
				}
			}
		});
		girlRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					returnTOMain(Constants.GIRL);
				}
			}
		});
		otherRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					returnTOMain(Constants.OTHER);
				}
			}
		});
	}

	@Override
	protected void setContext() {
		setContext(MeProfileEditSexActivity.this);

	}

	@Override
	protected void setContentView() {
		setTitleString(getString(R.string.input_sex_header));
		setContentView(R.layout.me_profile_edit_sex);
	}
	
	private void returnTOMain(int sex){
		Intent intent = new Intent(MeProfileEditSexActivity.this,
				MeProfileEditActivity.class);
		intent.putExtra(UserBO.SEX, sex);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	@Override
	protected void beforeBack() {
		setResult(RESULT_CANCELED, null);
	}

}
