package com.zyj.qiqile.manager.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zyj.qiqile.constant.ServerConstants;
import com.zyj.qiqile.constant.ServerErrorConstants;
import com.zyj.qiqile.domain.bo.ActivityCommentBO;
import com.zyj.qiqile.manager.ActivityCommentManager;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.converter.ActivityCommentConverter;

public class ActivityCommentManagerImpl extends BasicManageImpl implements
		ActivityCommentManager {

	public final static String TAG = "ActivityCommentManagerImpl";

	public final static String URL = ServerConstants.HTTP
			+ ServerConstants.SERVER_URL + ServerConstants.ACTIVITY_COMMENT;

	@Override
	public TaskResult addActivityComment(ActivityCommentBO activityCommentBO) {
		if (activityCommentBO == null) {
			return null;
		}
		Map<String, String> params = ActivityCommentConverter
				.activityCommentBOTOMap(activityCommentBO);
		TaskResult result = new TaskResult();
		JSONObject jsonObject = null;
		if (params != null) {
			try {
				jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
						+ ServerConstants.INSERT, params);
			} catch (HttpException e) {
				result.setResult(ResultCode.NETWORK_ERROR);
				return result;
			}
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
				Log.e(TAG, "addActivityComment", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

	@Override
	public TaskResult queryActivityCommentByActivityId(String activityId) {
		if (activityId == null) {
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put(ActivityCommentBO.ACTIVITY_ID, activityId);
		TaskResult result = new TaskResult();
		JSONObject jsonObject = null;
		try {
			jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
					+ ServerConstants.QUERY_BY_ACTIVITYID, params);
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
						result.put(
								"activityCommentBOList",
								ActivityCommentConverter.jsonTOActivityCommentBOList(jsonObject
										.getJSONArray(ServerConstants.DATA)));
				} else {
					result.setResult(ResultCode.FAILED);
					result.setErrorCode(errorCode);
				}
			} catch (JSONException e) {
				Log.e(TAG, "queryActivityCommentByActivityId", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

}
