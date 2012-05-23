package com.zyj.qiqile.activity;

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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	protected boolean isRouteDisplayed() {
		return false;
	}

	protected void reloadLocation(Location location) {
		if (location != null) {
			// GeoPoint pt = new GeoPoint((int) (location.getLatitude() * 1e6),
			// (int) (location.getLongitude() * 1e6));

			GeoPoint pt = new GeoPoint((int) (37.422006D * 1e6),
					(int) (-122.084095D * 1e6));
			mMapView.getController().animateTo(pt);
		}
	}

}
