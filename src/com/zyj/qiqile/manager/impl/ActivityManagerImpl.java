package com.zyj.qiqile.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.ServerConstants;
import com.zyj.qiqile.constant.ServerErrorConstants;
import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.manager.ActivityManager;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.TimeHelper;
import com.zyj.qiqile.tools.converter.ActivityConverter;

public class ActivityManagerImpl extends BasicManageImpl implements
		ActivityManager {

	public final static String TAG = "ActivityManagerImpl";

	public final static String URL = ServerConstants.HTTP
			+ ServerConstants.SERVER_URL + ServerConstants.ACTIVITY;

	@Override
	public TaskResult addActivity(ActivityBO activityBO) {
		if (activityBO == null) {
			return null;
		}
		Map<String, String> params = ActivityConverter
				.activityBOTOMap(activityBO);
		TaskResult result = new TaskResult();
		if (params != null) {
			params.put(ActivityBO.USERID, QiqileApplication.getInstance()
					.getUserBO().getId());
		}
		JSONObject jsonObject = null;
		try {
			jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
					+ ServerConstants.INSERT, params);
		} catch (HttpException e1) {
			result.setResult(ResultCode.NETWORK_ERROR);
			return result;
		}
		if (jsonObject != null) {
			try {
				int errorCode = jsonObject.getInt(ServerConstants.ERROR_CODE);
				if (errorCode == ServerErrorConstants.CODE_SUCCESS) {
					result.setResult(ResultCode.SUCCESS);
				} else {
					result.setResult(ResultCode.FAILED);
					result.setErrorCode(errorCode);
				}
			} catch (JSONException e) {
				Log.e(TAG, "addActivity", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

	@Override
	public TaskResult queryActivityByCityAndTime(String city, Date time) {
		if (city == null || time == null) {
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put(ActivityBO.CITY, city);
		params.put(ActivityBO.STARTTIME, TimeHelper.getDateString(time));
		params.put(ActivityBO.ENDTIME,
				TimeHelper.getDateString(TimeHelper.getNextDate(time)));
		TaskResult result = new TaskResult();
		JSONObject jsonObject = null;
		try {
			jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
					+ ServerConstants.QUERY_BY_CITY_AND_TIME, params);
		} catch (HttpException e1) {
			result.setResult(ResultCode.NETWORK_ERROR);
			return result;
		}
		if (jsonObject != null) {
			try {
				int errorCode = jsonObject.getInt(ServerConstants.ERROR_CODE);
				if (errorCode == ServerErrorConstants.CODE_SUCCESS) {
					result.setResult(ResultCode.SUCCESS);
					if (!jsonObject.isNull(ServerConstants.DATA))
						result.put("activityBOList", ActivityConverter
								.jsonTOActivityBOList(jsonObject
										.getJSONArray(ServerConstants.DATA)));
					result.put("time", time);
					result.put("city", city);
				} else {
					result.setResult(ResultCode.FAILED);
					result.setErrorCode(errorCode);
				}
			} catch (JSONException e) {
				Log.e(TAG, "queryActivityByCityAndTime", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

}
