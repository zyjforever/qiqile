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
 * �л�����ͼ���ܵ�activity���������ҵĶ�λ��Ϣ��������Ҫ�õ�mapView��activity������ֱ�Ӽ̳�
 * */
public class BasicMapActivity extends MapActivity {

	protected MapView mMapView;
	protected LocationListener mLocationListener;// onResumeʱע���listener��onPauseʱ��ҪRemove
	protected MyLocationOverlay mLocationOverlay; // ��λͼ��
	protected BMapManagerApplication bMapManagerApplication;
	protected ImageButton locationButton;// ��λ���¼��ص�ͼ

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
