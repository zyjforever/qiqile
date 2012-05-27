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
import com.zyj.qiqile.tools.LocationHelper;
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

	class OverItemT extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> geoList;
		private List<ActivityBO> activityBOList;
		private Context mContext;
		private int count;
		private int countItem;

		public OverItemT(Drawable marker, Context context,
				List<OverlayItem> geoList, List<ActivityBO> activityBOList) {
			super(boundCenterBottom(marker));
			this.activityBOList = activityBOList;
			this.mContext = context;
			this.geoList = geoList;
			
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
			if (i == countItem) {
				count++;
			}

			if (count >= 2) {
				count = 0;
				Intent intent = new Intent(QiqileApplication.context,
						ActivityProfileActivity.class);
				QiqileApplication.getInstance().setCurrentLookActivity(
						activityBOList.get(i));
				QiqileApplication.getInstance().context.startActivity(intent);
			} else {
				count = 1;
				countItem = i;
				int distance = (int) LocationHelper.getDistance(activityBOList
						.get(i).getLatitude(), activityBOList.get(i)
						.getLongitude(), myLocation.getLatitude(), myLocation
						.getLongitude());
				String distanceString = "距离您当前位置"+distance+"米" + "\n";
				Toast.makeText(this.mContext,
						distanceString + geoList.get(i).getSnippet() + "(再次点击进入详情)",
						Toast.LENGTH_SHORT).show();
			}
			return true;
		}
	}
}
