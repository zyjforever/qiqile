package com.zyj.qiqile.activity.activity;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicActivity;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.tools.TimeHelper;

public class ActivityEdit4Activity extends BasicActivity {
	private final static String TAG = "ActivityEdit4Activity";
	private final static int STARTDAY = 1;
	private final static int STARTTIME = 2;
	private final static int ENDDAY = 3;
	private final static int ENDTIME = 4;
	private Button topPreButton;
	private Button topNextButton;
	private TextView startTimeButton;
	private TextView endTimeButton;

	private ActivityBO activityBO;
	private Calendar startTime;
	private Calendar endTime;

	@Override
	public void init() {
		activityBO = QiqileApplication.getInstance().getCurrentEditActivity();
		startTime = Calendar.getInstance();
		endTime = Calendar.getInstance();
		if (activityBO.getStartTime() != null) {
			startTime.setTime(activityBO.getStartTime());
		} else {
			activityBO.setStartTime(startTime.getTime());
		}
		if (activityBO.getEndTime() != null) {
			endTime.setTime(activityBO.getEndTime());
		}
		else{
			activityBO.setEndTime(endTime.getTime());
		}
		super.init();
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_edit_4);
	}

	@Override
	protected void initView() {
		topPreButton = (Button) findViewById(R.id.top_pre);
		topNextButton = (Button) findViewById(R.id.top_next);
		startTimeButton = (TextView) findViewById(R.id.start_time);
		endTimeButton = (TextView) findViewById(R.id.end_time);
		((TextView) findViewById(R.id.title))
				.setText(R.string.input_activity_time_header);
		if (activityBO.getStartTime() != null) {
			startTimeButton.setText(TimeHelper.getDateString(activityBO
					.getStartTime()));
		}
		if (activityBO.getEndTime() != null) {
			endTimeButton.setText(TimeHelper.getDateString(activityBO
					.getEndTime()));
		}
	}

	@Override
	protected void initListener() {
		topPreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityEdit4Activity.this.onBackPressed();
			}
		});
		topNextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validate()) {
					activityBO.setStartTime(startTime.getTime());
					activityBO.setEndTime(endTime.getTime());
					Intent intent = new Intent(ActivityEdit4Activity.this,
							ActivityEdit5Activity.class);
					startActivity(intent);
				}
			}
		});
		startTimeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(STARTDAY);
			}
		});
		endTimeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(ENDDAY);
			}
		});

	}

	private Boolean validate() {
		Boolean result = Boolean.FALSE;
		if (startTimeButton.getText().toString() != null) {
			if (endTimeButton.getText().toString() != null) {
				if (!startTime.after(endTime)) {
					result = Boolean.TRUE;
				} else {
					Toast.makeText(this, R.string.start_time_error,
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(this, R.string.end_time_hint, Toast.LENGTH_LONG)
						.show();
			}
		} else {
			Toast.makeText(this, R.string.start_time_hint, Toast.LENGTH_LONG)
					.show();
		}
		return result;
	}

	@Override
	protected void bindListener() {
		// TODO Auto-generated method stub
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar c = Calendar.getInstance();
		Dialog dialog = null;
		switch (id) {
		case STARTDAY:
			dialog = new DatePickerDialog(this, new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					startTime.set(year, monthOfYear, dayOfMonth);
					startTimeButton.setText(TimeHelper.getDateString(startTime
							.getTime()));
					showDialog(STARTTIME);
				}
			}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
			break;
		case STARTTIME:
			dialog = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {
						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							startTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
							startTime.set(Calendar.MINUTE, minute);
							startTimeButton.setText(TimeHelper
									.getDateString(startTime.getTime()));
						}
					}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
					true);
			break;
		case ENDDAY:
			dialog = new DatePickerDialog(this, new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					endTime.set(year, monthOfYear, dayOfMonth);
					endTimeButton.setText(TimeHelper.getDateString(endTime
							.getTime()));
					showDialog(ENDTIME);
				}
			}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
			break;
		case ENDTIME:
			dialog = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {
						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							endTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
							endTime.set(Calendar.MINUTE, minute);
							endTimeButton.setText(TimeHelper
									.getDateString(endTime.getTime()));
						}
					}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
					true);
			break;
		}
		return dialog;
	}
}
