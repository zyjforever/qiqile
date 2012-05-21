package com.zyj.qiqile.app;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.*;

public class BMapManagerApplication extends Application {

	private static BMapManagerApplication instance;// 简单单例

	// 百度MapAPI的管理类
	public BMapManager mBMapMan = null;

	// 授权Key
	public String mStrKey = "3D3589B6521063563D126760341579B13C880A2A";
	boolean m_bKeyRight = true; // 授权Key正确，验证通过

	public static BMapManagerApplication getInstance() {
		if (instance == null) {
			instance = new BMapManagerApplication();
		}
		return instance;
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			Log.d("MyGeneralListener", "onGetNetworkState error is " + iError);
			Toast.makeText(
					BMapManagerApplication.instance.getApplicationContext(),
					"您的网络出错啦！", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPermissionState(int iError) {
			Log.d("MyGeneralListener", "onGetPermissionState error is "
					+ iError);
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(
						BMapManagerApplication.instance.getApplicationContext(),
						"请在BMapApiDemoApp.java文件输入正确的授权Key！", Toast.LENGTH_LONG)
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
	// 建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onTerminate();
	}

}
