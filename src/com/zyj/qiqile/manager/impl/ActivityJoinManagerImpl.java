package com.zyj.qiqile.manager.impl;

import java.util.Map;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zyj.qiqile.constant.ServerConstants;
import com.zyj.qiqile.constant.ServerErrorConstants;
import com.zyj.qiqile.domain.bo.ActivityJoinBO;
import com.zyj.qiqile.manager.ActivityJoinManager;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.converter.ActivityJoinConverter;

public class ActivityJoinManagerImpl extends BasicManageImpl implements
		ActivityJoinManager {

	public final static String TAG = "ActivityJoinManagerImpl";

	public final static String URL = ServerConstants.HTTP
			+ ServerConstants.SERVER_URL + ServerConstants.ACTIVITY_JOIN;

	@Override
	public TaskResult join(ActivityJoinBO activityJoinBO) {
		if (activityJoinBO == null) {
			return null;
		}

		Map<String, String> params = ActivityJoinConverter
				.activityJoinBOTOMap(activityJoinBO);
		TaskResult result = new TaskResult();
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
				Log.e(TAG, "joinActivity", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

	@Override
	public TaskResult activityJoin(ActivityJoinBO activityJoinBO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskResult activityComment(ActivityJoinBO activityJoinBO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskResult isJoin(ActivityJoinBO activityJoinBO) {
		if (activityJoinBO == null) {
			return null;
		}

		Map<String, String> params = ActivityJoinConverter
				.activityJoinBOTOMap(activityJoinBO);
		TaskResult result = new TaskResult();
		JSONObject jsonObject = null;
		try {
			jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
					+ ServerConstants.IS_JOIN, params);
		} catch (HttpException e1) {
			result.setResult(ResultCode.NETWORK_ERROR);
			return result;
		}
		if (jsonObject != null) {
			try {
				int errorCode = jsonObject.getInt(ServerConstants.ERROR_CODE);
				if (errorCode == ServerErrorConstants.CODE_SUCCESS) {
					result.setResult(ResultCode.SUCCESS);
					result.put("data", jsonObject.getString("data"));
				} else {
					result.setResult(ResultCode.FAILED);
					result.setErrorCode(errorCode);
				}
			} catch (JSONException e) {
				Log.e(TAG, "isJoinActivity", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

	@Override
	public TaskResult cancelJoin(ActivityJoinBO activityJoinBO) {
		if (activityJoinBO == null) {
			return null;
		}

		Map<String, String> params = ActivityJoinConverter
				.activityJoinBOTOMap(activityJoinBO);
		TaskResult result = new TaskResult();
		JSONObject jsonObject = null;
		try {
			jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
					+ ServerConstants.REMOVE, params);
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
				Log.e(TAG, "joinActivity", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

}
