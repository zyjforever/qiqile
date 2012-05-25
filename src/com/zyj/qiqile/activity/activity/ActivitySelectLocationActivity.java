package com.zyj.qiqile.activity.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.Overlay;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;
import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicMapActivity;
import com.zyj.qiqile.activity.LoginActivity;
import com.zyj.qiqile.activity.meprofile.MeProfileEditActivity;
import com.zyj.qiqile.activity.meprofile.MeProfileEditSignatureActivity;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.tools.LocationHelper;

public class ActivitySelectLocationActivity extends BasicMapActivity {
	public static final String TAG = "ActivitySelectLocationActivity";

	protected TextView backButton;
	protected TextView confirmButton;
	protected LocationOverlay locationOverlay;
	private Double latitude;// 经度
	private Double longitude;// 纬度
	private String city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_select_location);
		super.onCreate(savedInstanceState);
		((TextView) findViewById(R.id.title))
				.setText(R.string.button_select_location);
		backButton = (TextView) findViewById(R.id.top_back);
		confirmButton = (TextView) findViewById(R.id.top_confirm_btn);

		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivitySelectLocationActivity.this.onBackPressed();
			}
		});
		confirmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validate()) {
					beforeFinish();
					ActivitySelectLocationActivity.this.finish();// 结束当前的activity
				}
			}
		});
		locationOverlay = new LocationOverlay(BitmapFactory.decodeResource(
				getResources(), R.drawable.point_o), this, mMapView);
		mMapView.getOverlays().add(locationOverlay);
	}

	protected Boolean validate() {
		Boolean result = Boolean.FALSE;
		GeoPoint p = locationOverlay.getP();
		latitude = Double.valueOf((double) p.getLatitudeE6() / 1000000);
		longitude = Double.valueOf((double) p.getLongitudeE6() / 1000000);
		try {
			city = LocationHelper.getCity(latitude.toString(),
					longitude.toString());
		} catch (JSONException e) {
			Log.e(TAG, "", e);
		}
		if (latitude != null && longitude != null && city != null) {
			result = Boolean.TRUE;
		} else {
			Toast.makeText(this, R.string.button_select_location,
					Toast.LENGTH_LONG).show();
		}
		return result;
	}

	protected void beforeFinish() {
		Intent intent = new Intent(ActivitySelectLocationActivity.this,
				ActivityEdit5Activity.class);
		intent.putExtra(ActivityBO.LATITUDE, latitude);
		intent.putExtra(ActivityBO.LONGITUDE, longitude);
		intent.putExtra(ActivityBO.CITY, city);
		setResult(RESULT_OK, intent);
	}

	class LocationOverlay extends Overlay {
		private Context context;
		private MapView mapView;
		private Bitmap drawable;
		private GeoPoint p;

		public LocationOverlay(Bitmap drawable, Context context, MapView mapView) {
			this.mapView = mapView;
			this.context = context;
			this.drawable = drawable;
		}

		@Override
		// 处理当点击事件
		public boolean onTap(GeoPoint p, MapView mapView) {
			this.p = p;
//			latitude = Double.valueOf((double) p.getLatitudeE6() / 1000000);
//			longitude = Double.valueOf((double) p.getLongitudeE6() / 1000000);
			// Toast.makeText(QiqileApplication.context, city,
			// Toast.LENGTH_LONG)
			// .show();
			return super.onTap(p, mapView);
		}

		@Override
		public void draw(Canvas arg0, MapView arg1, boolean arg2) {
			if (p != null) {
				super.draw(arg0, arg1, arg2);
				Point point = mapView.getProjection().toPixels(p, null);
				Paint paint = new Paint();
				arg0.drawBitmap(drawable, point.x, point.y, paint);
			}
		}

		public GeoPoint getP() {
			return p;
		}

		public void setP(GeoPoint p) {
			this.p = p;
		}
	}

}
