package com.zyj.qiqile.activity.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.OverlayItem;
import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicMapActivity;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.task.GenericAferExcutedListener;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.LoadLastestActivityTask;
import com.zyj.qiqile.task.TaskListener;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.TimeHelper;

public class ActivityMapListViewActivity extends BasicMapActivity {
	public static final String TAG = "ActivityMapListViewActivity";

	private Button loadActivityButton;// 定位重新加载活动信息
	private Drawable marker;

	private Date now;
	private Date time;

	// task
	private GenericTask loadActivityListTask;

	private TaskListener loadActivityListener = new GenericAferExcutedListener() {
		@Override
		public void onPostExecute(GenericTask task, TaskResult result) {
			if (result != null && result.getResult() == ResultCode.SUCCESS) {
				Date atime = (Date) result.get("time");
				int i = TimeHelper.compare(now, atime);
				List<ActivityBO> activityBOList = (List<ActivityBO>) result
						.get("activityBOList");
				if (activityBOList != null && activityBOList.size() > 0) {
					List<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
					for (ActivityBO activityBO : activityBOList) {
						GeoPoint point = new GeoPoint(
								(int) (activityBO.getLatitude() * 1E6),
								(int) (activityBO.getLongitude() * 1E6));
						OverlayItem overlayItem = new OverlayItem(point, "",
								activityBO.getName());
						overlayItemList.add(overlayItem);
					}
					/*
					 * if (mMapView.getOverlays().size() > i &&
					 * mMapView.getOverlays().get(i) != null) {
					 * mMapView.getOverlays().remove(i);
					 * mMapView.getOverlays().add( i, new OverItemT(marker,
					 * QiqileApplication.context, overlayItemList,
					 * activityBOList)); } else { mMapView.getOverlays().add(
					 * new OverItemT(marker, QiqileApplication.context,
					 * overlayItemList, activityBOList));
					 * mMapView.postInvalidate(); }
					 */
					mMapView.getOverlays().add(
							new OverItemT(marker, QiqileApplication.context,
									overlayItemList, activityBOList));
					mMapView.postInvalidate();
				}
				time = TimeHelper.getPreviewDate(time);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_map_list);
		super.onCreate(savedInstanceState);
		loadActivityButton = (Button) findViewById(R.id.refresh_button);
		marker = getResources().getDrawable(R.drawable.point_o);
		now = TimeHelper.getCurrentTime(0);
		time = TimeHelper.getCurrentTime(0);
		// mMapView.getOverlays().add(new OverItemT(marker, this));

		loadActivityButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadActivityListTask = new LoadLastestActivityTask();
				TaskParams params = new TaskParams();
				params.put("time", time);
				params.put("city", QiqileApplication.getInstance().getCity());
				loadActivityListTask.setListener(loadActivityListener);
				loadActivityListTask.execute(params);
			}
		});
	}
}

class OverItemT extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> geoList;
	private List<ActivityBO> activityBOList;
	private Context mContext;
	private int count;
	private int countItem;

	// private double mLat1 = 23.054979;// 39.9022; // point1纬度
	// private double mLon1 = 113.413254;// 116.3822; // point1经度
	//
	// private double mLat2 = 23.054989;
	// private double mLon2 = 113.413223;
	//
	// private double mLat3 = 23.054999;
	// private double mLon3 = 113.413212;

	public OverItemT(Drawable marker, Context context,
			List<OverlayItem> geoList, List<ActivityBO> activityBOList) {
		super(boundCenterBottom(marker));
		this.activityBOList = activityBOList;
		this.mContext = context;
		this.geoList = geoList;

		// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
		// GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
		// GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
		// GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
		//
		// GeoList.add(new OverlayItem(p1, "P1", "point1"));
		// GeoList.add(new OverlayItem(p2, "P2", "point2"));
		// GeoList.add(new OverlayItem(p3, "P3", "point3"));
		populate(); // createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}

	@Override
	protected OverlayItem createItem(int i) {
		return geoList.get(i);
	}

	@Override
	public int size() {
		return geoList.size();
	}

	@Override
	// 处理当点击事件
	protected boolean onTap(int i) {
		countItem = i;
		count++;
		if (count >= 2) {
			count = 0;
			Intent intent = new Intent(QiqileApplication.context,
					ActivityProfileActivity.class);
			QiqileApplication.getInstance().setCurrentLookActivity(
					activityBOList.get(i));
			QiqileApplication.getInstance().context.startActivity(intent);
		} else {
			Toast.makeText(this.mContext,
					geoList.get(i).getSnippet() + "(再次点击进入详情)",
					Toast.LENGTH_SHORT).show();
		}
		return true;
	}
}