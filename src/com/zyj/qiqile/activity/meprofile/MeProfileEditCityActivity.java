package com.zyj.qiqile.activity.meprofile;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicWriteActivity;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.tools.CityList;

public class MeProfileEditCityActivity extends BasicWriteActivity {

	private Spinner citySpinner;
	private Spinner provinceSpinner;
	private ArrayAdapter<String> provinceAdapter;
	private ArrayAdapter<String> cityAdapter;
	private String city;
	private String province;

	@Override
	protected void initView() {
		citySpinner = (Spinner) findViewById(R.id.city_spinner);
		provinceSpinner = (Spinner) findViewById(R.id.province_spinner);

		provinceAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, CityList.PROVINCELIST);
		provinceAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		provinceSpinner.setAdapter(provinceAdapter);
		super.initView();
	}

	@Override
	protected void bindListener() {
		provinceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				province = (CityList.PROVINCELIST)[arg2];
				changeCitySpanner();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				city = (CityList.CITYS.get(province))[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		super.bindListener();
	}

	@Override
	protected void setContext() {
		setContext(MeProfileEditCityActivity.this);
	}

	@Override
	protected Boolean validate() {
		Boolean result = Boolean.FALSE;
		if (city != null && !city.equals("")) {
			result = Boolean.TRUE;
		}
		return result;
	}

	@Override
	protected void beforeFinish() {
		Intent intent = new Intent(MeProfileEditCityActivity.this,
				MeProfileEditActivity.class);
		intent.putExtra(UserBO.CITY, city);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	protected void setContentView() {
		setTitleString(getString(R.string.input_city_header));
		setContentView(R.layout.me_profile_edit_city);
	}

	@Override
	protected void beforeBack() {
		setResult(RESULT_CANCELED, null);
	}

	private void changeCitySpanner() {
		cityAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				CityList.CITYS.get(province));
		cityAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		citySpinner.setAdapter(cityAdapter);

	}

}
