package com.zyj.qiqile.activity.meprofile;

import java.sql.Date;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicWriteActivity;
import com.zyj.qiqile.domain.bo.UserBO;

public class MeProfileEditBirthdayActivity extends BasicWriteActivity {

	private TextView birthdayView;

	private Date birthday;

	@Override
	protected void initView() {
		birthdayView = (TextView) findViewById(R.id.user_birthday);
		super.initView();
	}

	@Override
	protected void bindListener() {
		birthdayView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(0);
			}
		});
		super.bindListener();
	}

	@Override
	protected void setContext() {
		setContext(MeProfileEditBirthdayActivity.this);
	}

	@Override
	protected Boolean validate() {
		return Boolean.TRUE;
	}

	@Override
	protected void beforeFinish() {
		Intent intent = new Intent(MeProfileEditBirthdayActivity.this,
				MeProfileEditActivity.class);
		intent.putExtra(UserBO.BIRTHDAY, birthday);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	protected void setContentView() {
		setTitleString(getString(R.string.input_birthday_header));
		setContentView(R.layout.me_profile_edit_birthday);
	}

	@Override
	protected void beforeBack() {
		setResult(RESULT_CANCELED, null);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar c = Calendar.getInstance();
		return new DatePickerDialog(this, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				birthday=new Date(year-1900,monthOfYear,dayOfMonth);
				birthdayView.setText(birthday.toString());
			}
		}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH));
	}

}
