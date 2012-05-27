package com.zyj.qiqile.manager.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.ServerConstants;
import com.zyj.qiqile.constant.ServerErrorConstants;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.manager.UserManager;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.converter.UserConverter;

public class UserManagerImpl extends BasicManageImpl implements UserManager {

	public final static String TAG = "UserManagerImpl";

	public final static String URL = ServerConstants.HTTP
			+ ServerConstants.SERVER_URL + ServerConstants.USER;

	@Override
	public TaskResult register(String email, String password, String nick) {
		TaskResult result = new TaskResult();
		if (email == null | password == null || nick == null) {
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put(UserBO.EMAIL, email);
		params.put(UserBO.PASSWORD, password);
		params.put(UserBO.NICK, nick);
		JSONObject jsonObject = null;
		try {
			jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
					+ ServerConstants.REGISTER, params);
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
				Log.e(TAG, "register", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

	@Override
	public TaskResult login(String email, String password) {
		TaskResult result = new TaskResult();
		if (email == null || password == null) {
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put(UserBO.EMAIL, email);
		params.put(UserBO.PASSWORD, password);
		JSONObject jsonObject = null;
		try {
			jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
					+ ServerConstants.LOGIN, params);
		} catch (HttpException e1) {
			result.setResult(ResultCode.NETWORK_ERROR);
			return result;
		}
		if (jsonObject != null) {
			try {
				int errorCode = jsonObject.getInt(ServerConstants.ERROR_CODE);
				if (errorCode == ServerErrorConstants.CODE_SUCCESS) {
					result.setResult(ResultCode.SUCCESS);
					result.put(
							TOKEN,
							jsonObject.getJSONObject(ServerConstants.DATA).get(
									TOKEN));
					QiqileApplication.getInstance().setToken(
							jsonObject.getJSONObject(ServerConstants.DATA)
									.getString(TOKEN));
					try {
						QiqileApplication.getInstance().setUserBO(
								UserConverter.jsonTOUserBO(jsonObject));
					} catch (UnsupportedEncodingException e) {
						Log.e(TAG, "register", e);
					}
				} else {
					result.setResult(ResultCode.FAILED);
					// TODO ´íÎóÂë
				}
			} catch (JSONException e) {
				Log.e(TAG, "register", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

	@Override
	public TaskResult update(UserBO userBO, String token) {
		TaskResult result = new TaskResult();
		if (token == null || userBO == null) {
			return null;
		}
		userBO.setId(QiqileApplication.getInstance().getUserBO().getId());
		Map<String, String> params = UserConverter.userBOTOMap(userBO);
		params.put(ServerConstants.TOKEN, token);
		JSONObject jsonObject = null;
		try {
			jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
					+ ServerConstants.UPDATE, params);
		} catch (HttpException e1) {
			result.setResult(ResultCode.NETWORK_ERROR);
			return result;
		}
		if (jsonObject != null) {
			try {
				int errorCode = jsonObject.getInt(ServerConstants.ERROR_CODE);
				if (errorCode == ServerErrorConstants.CODE_SUCCESS) {
					result.setResult(ResultCode.SUCCESS);
					QiqileApplication.getInstance().setUserBO(userBO);
				} else {
					result.setResult(ResultCode.FAILED);
					// TODO ´íÎóÂë
				}
			} catch (JSONException e) {
				Log.e(TAG, "update", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

	@Override
	public TaskResult updatePassword(String email, String password,
			String newPassword) {
		TaskResult result = new TaskResult();
		if (email == null || password == null || newPassword == null) {
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put(UserBO.EMAIL, email);
		params.put(UserBO.PASSWORD, password);
		params.put("newpassword", newPassword);
		JSONObject jsonObject = null;
		try {
			jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
					+ ServerConstants.UPDATE_PASSWORD, params);
		} catch (HttpException e1) {
			result.setResult(ResultCode.NETWORK_ERROR);
			return result;
		}
		if (jsonObject != null) {
			try {
				int errorCode = jsonObject.getInt(ServerConstants.ERROR_CODE);
				if (errorCode == ServerErrorConstants.CODE_SUCCESS) {
					result.setResult(ResultCode.SUCCESS);
					QiqileApplication.getInstance().getUserBO()
							.setPassword(newPassword);
				} else {
					result.setResult(ResultCode.FAILED);
				}
			} catch (JSONException e) {
				Log.e(TAG, "updatePassword", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

	@Override
	public TaskResult queryUserBOById(String id) {
		TaskResult result = new TaskResult();
		if (id == null) {
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put(UserBO.ID, id);
		JSONObject jsonObject = null;
		try {
			jsonObject = httpConnectionHelper.sendPostDataAndResult(URL
					+ ServerConstants.QUERY_BY_ID, params);
		} catch (HttpException e1) {
			result.setResult(ResultCode.NETWORK_ERROR);
			return result;
		}
		if (jsonObject != null) {
			try {
				int errorCode = jsonObject.getInt(ServerConstants.ERROR_CODE);
				if (errorCode == ServerErrorConstants.CODE_SUCCESS) {
					result.setResult(ResultCode.SUCCESS);
					try {
						result.put("userBO",
								UserConverter.jsonTOUserBO2(jsonObject));
					} catch (UnsupportedEncodingException e) {
						Log.e(TAG, "queryUserBOById", e);
					}
				} else {
					result.setResult(ResultCode.FAILED);
					// TODO ´íÎóÂë
				}
			} catch (JSONException e) {
				Log.e(TAG, "queryUserBOById", e);
				result.setResult(ResultCode.UNKNOW_ERROR);
				return result;
			}
		}
		return result;
	}

}
