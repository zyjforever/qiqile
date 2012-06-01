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
 * �л�����ͼ���ܵ�activity���������ҵĶ�λ��Ϣ��������Ҫ�õ�mapView��activity������ֱ�Ӽ̳�
 * */
public class BasicMapActivity extends MapActivity {

	protected MapView mMapView;
	protected LocationListener mLocationListener;// onResumeʱע���listener��onPauseʱ��ҪRemove
	protected MyLocationOverlay mLocationOverlay; // ��λͼ��
	protected BMapManagerApplication bMapManagerApplication;
	protected ImageButton locationButton;// ��λ���¼��ص�ͼ
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
		// ���ʹ�õ�ͼSDK�����ʼ����ͼActivity
		super.initMapActivity(bMapManagerApplication.mBMapMan);

		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		// ���������Ŷ���������Ҳ��ʾoverlay,Ĭ��Ϊ������
		mMapView.setDrawOverlayWhenZooming(true);

		// ��Ӷ�λͼ��
		mLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMapView.getOverlays().add(mLocationOverlay);
		// ע�ᶨλ�¼�
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
		mLocationOverlay.disableCompass(); // �ر�ָ����
		bMapManagerApplication.mBMapMan.stop();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// ע�ᶨλ�¼�����λ�󽫵�ͼ�ƶ�����λ��
		bMapManagerApplication.mBMapMan.getLocationManager()
				.requestLocationUpdates(mLocationListener);
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass(); // ��ָ����
		bMapManagerApplication.mBMapMan.start();
		super.onResume();
	}

	@Override
	protected void onRestart() {
		// ע�ᶨλ�¼�����λ�󽫵�ͼ�ƶ�����λ��
		bMapManagerApplication.mBMapMan.getLocationManager()
				.requestLocationUpdates(mLocationListener);
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass(); // ��ָ����
		bMapManagerApplication.mBMapMan.start();
		super.onRestart();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onNewIntent(Intent arg0) {
		// ע�ᶨλ�¼�����λ�󽫵�ͼ�ƶ�����λ��
		bMapManagerApplication.mBMapMan.getLocationManager()
				.requestLocationUpdates(mLocationListener);
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass(); // ��ָ����
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
		builder.setMessage("ȷ��Ҫ�˳���?");
		builder.setPositiveButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						ActivityManagerApplication.getInstance().exit();
					}
				});
		builder.setNegativeButton("ȡ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
}
