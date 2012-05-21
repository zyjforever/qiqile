package com.zyj.qiqile.app;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.*;

public class BMapManagerApplication extends Application {

	private static BMapManagerApplication instance;// �򵥵���

	// �ٶ�MapAPI�Ĺ�����
	public BMapManager mBMapMan = null;

	// ��ȨKey
	public String mStrKey = "3D3589B6521063563D126760341579B13C880A2A";
	boolean m_bKeyRight = true; // ��ȨKey��ȷ����֤ͨ��

	public static BMapManagerApplication getInstance() {
		if (instance == null) {
			instance = new BMapManagerApplication();
		}
		return instance;
	}

	// �����¼���������������ͨ�������������Ȩ��֤�����
	public static class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			Log.d("MyGeneralListener", "onGetNetworkState error is " + iError);
			Toast.makeText(
					BMapManagerApplication.instance.getApplicationContext(),
					"���������������", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPermissionState(int iError) {
			Log.d("MyGeneralListener", "onGetPermissionState error is "
					+ iError);
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// ��ȨKey����
				Toast.makeText(
						BMapManagerApplication.instance.getApplicationContext(),
						"����BMapApiDemoApp.java�ļ�������ȷ����ȨKey��", Toast.LENGTH_LONG)
						.show();
				BMapManagerApplication.instance.m_bKeyRight = false;
			}
		}
	}

	@Override
	public void onCreate() {
		instance = this;
		mBMapMan = new BMapManager(this);
		mBMapMan.init(this.mStrKey, new MyGeneralListener());
		super.onCreate();
	}

	@Override
	// ��������app���˳�֮ǰ����mapadpi��destroy()�����������ظ���ʼ��������ʱ������
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onTerminate();
	}

}
