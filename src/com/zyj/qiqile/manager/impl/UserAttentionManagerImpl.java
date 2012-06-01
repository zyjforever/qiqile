package com.zyj.qiqile.manager.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zyj.qiqile.constant.ServerConstants;
import com.zyj.qiqile.constant.ServerErrorConstants;
import com.zyj.qiqile.domain.bo.UserAttentionBO;
import com.zyj.qiqile.manager.UserAttentionManager;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.converter.UserAttentionConverter;

public class UserAttentionManagerImpl extends BasicManageImpl implements
		UserAttentionManager {
	public final static String TAG = "UserAttentionManagerImpl";

	public final static String URL = ServerConstants.HTTP
			+ ServerConstants.SERVER_URL + ServerConstants.USER_ATTENTION;

	@Override
	public TaskResult addAttenttion(String userId, String attentionId) {
		if (userId == null || attentionId == null) {
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put(UserAttentionBO.USERID, userId);
		params.put(UserAttentionBO.ATTENTIONID, attentionId);
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
				Log.e(TAG, "addAttenttion", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

	@Override
	public TaskResult queryUserAttentionList(String userId) {
		if (userId == null)
			return null;

		Map<String, String> params = new HashMap<String, String>();
		params.put(UserAttentionBO.USERID, userId);
		TaskResult result = new TaskResult();
		JSONObject jsonObject = null;
		try {
			jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
					+ ServerConstants.QUERY_BY_USRID, params);
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
								"userAttentionBOList",
								UserAttentionConverter.jsonTOUserAttentionList(jsonObject
										.getJSONArray(ServerConstants.DATA)));
				} else {
					result.setResult(ResultCode.FAILED);
					result.setErrorCode(errorCode);
				}
			} catch (JSONException e) {
				Log.e(TAG, "queryUserAttentionList", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

}
