package com.zyj.qiqile.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.zyj.qiqile.R;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.BMapManagerApplication;

/**
 * 有基本地图功能的activity，里面有我的定位信息，其他需要用到mapView的activity都可以直接继承
 * */
public class BasicMapActivity extends MapActivity {

	protected MapView mMapView;
	protected LocationListener mLocationListener;// onResume时注册此listener，onPause时需要Remove
	protected MyLocationOverlay mLocationOverlay; // 定位图层
	protected BMapManagerApplication bMapManagerApplication;
	protected ImageButton locationButton;// 定位重新加载地图
	protected Location myLocation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		ActivityManagerApplication.getInstance().addActvity(this);
		bMapManagerApplication = BMapManagerApplication.getInstance();
		if (bMapManagerApplication.mBMapMan == null) {
			bMapManagerApplication.mBMapMan = new BMapManager(getApplication());
			bMapManagerApplication.mBMapMan.init(
					bMapManagerApplication.mStrKey,
					new BMapManagerApplication.MyGeneralListener());
		}
		bMapManagerApplication.mBMapMan.start();
		// 如果使用地图SDK，请初始化地图Activity
		super.initMapActivity(bMapManagerApplication.mBMapMan);

		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		// 设置在缩放动画过程中也显示overlay,默认为不绘制
		mMapView.setDrawOverlayWhenZooming(true);

		// 添加定位图层
		mLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMapView.getOverlays().add(mLocationOverlay);
		// 注册定位事件
		mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				reloadLocation(location);
			}
		};

		locationButton = (ImageButton) findViewById(R.id.location_button);

		locationButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Location location = bMapManagerApplication.mBMapMan
						.getLocationManager().getLocationInfo();
				reloadLocation(location);
			}
		});
	}

	@Override
	protected void onPause() {
		bMapManagerApplication.mBMapMan.getLocationManager().removeUpdates(
				mLocationListener);
		mLocationOverlay.disableMyLocation();
		mLocationOverlay.disableCompass(); // 关闭指南针
		bMapManagerApplication.mBMapMan.stop();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// 注册定位事件，定位后将地图移动到定位点
		bMapManagerApplication.mBMapMan.getLocationManager()
				.requestLocationUpdates(mLocationListener);
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass(); // 打开指南针
		bMapManagerApplication.mBMapMan.start();
		super.onResume();
	}

	@Override
	protected void onRestart() {
		// 注册定位事件，定位后将地图移动到定位点
		bMapManagerApplication.mBMapMan.getLocationManager()
				.requestLocationUpdates(mLocationListener);
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass(); // 打开指南针
		bMapManagerApplication.mBMapMan.start();
		super.onRestart();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onNewIntent(Intent arg0) {
		// 注册定位事件，定位后将地图移动到定位点
		bMapManagerApplication.mBMapMan.getLocationManager()
				.requestLocationUpdates(mLocationListener);
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass(); // 打开指南针
		bMapManagerApplication.mBMapMan.start();
		super.onNewIntent(arg0);
	}

	protected void reloadLocation(Location location) {
		if (location != null) {
			myLocation = location;
			GeoPoint pt = new GeoPoint((int) (location.getLatitude() * 1e6),
					(int) (location.getLongitude() * 1e6));

			mMapView.getController().animateTo(pt);
		}
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确定要退出吗?");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						ActivityManagerApplication.getInstance().exit();
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
}
