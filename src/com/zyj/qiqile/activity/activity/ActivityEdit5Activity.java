package com.zyj.qiqile.activity.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicActivity;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.Constants;
import com.zyj.qiqile.domain.bo.ActivityBO;

public class ActivityEdit5Activity extends BasicActivity {
	private Button topPreButton;
	private Button topNextButton;
	private Button selectLocationOnMapButton;
	private EditText locationEdit;
	private ActivityBO activityBO;

	private String location;
	private Double latitude;// ¾­¶È
	private Double longitude;// Î³¶È
	private String city;

	@Override
	public void init() {
		activityBO = QiqileApplication.getInstance().getCurrentEditActivity();
		location = activityBO.getLocation();
		super.init();
	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.activity_edit_5);
	}

	@Override
	protected void initView() {
		topPreButton = (Button) findViewById(R.id.top_pre);
		topNextButton = (Button) findViewById(R.id.top_next);
		locationEdit = (EditText) findViewById(R.id.activity_location);
		selectLocationOnMapButton = (Button) findViewById(R.id.select_location_on_map);
		if (location != null && !location.equals("")) {
			locationEdit.setText(location);
		}
		((TextView) findViewById(R.id.title))
				.setText(R.string.input_activity_location_header);
	}

	@Override
	protected void initListener() {
		topPreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityEdit5Activity.this.onBackPressed();
			}
		});
		topNextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validate()) {
					activityBO.setLocation(location);
					activityBO.setLatitude(latitude);
					activityBO.setLongitude(longitude);
					activityBO.setCity(city);
					Intent intent = new Intent(ActivityEdit5Activity.this,
							ActivityEdit6Activity.class);
					startActivity(intent);
				}
			}
		});
		selectLocationOnMapButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityEdit5Activity.this,
						ActivitySelectLocationActivity.class);
				startActivityForResult(intent,
						Constants.REQUEST_ACTIVITY_LOCATION);
			}
		});
	}

	protected Boolean validate() {
		Boolean result = Boolean.FALSE;
		location = locationEdit.getText().toString();
		if (location != null && !location.equals("")) {
			if (latitude != null && longitude != null) {
				result = Boolean.TRUE;
			} else {
				Toast.makeText(this, R.string.button_select_location,
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, R.string.location_hint, Toast.LENGTH_LONG)
					.show();
		}
		return result;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK
				&& requestCode == Constants.REQUEST_ACTIVITY_LOCATION) {
			latitude = data.getExtras().getDouble(ActivityBO.LATITUDE);
			longitude = data.getExtras().getDouble(ActivityBO.LONGITUDE);
			city = data.getExtras().getString(ActivityBO.CITY);
		}
	}

	@Override
	protected void bindListener() {
		// TODO Auto-generated method stub
	}

}
