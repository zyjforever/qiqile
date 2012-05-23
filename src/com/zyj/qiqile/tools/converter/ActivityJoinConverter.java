package com.zyj.qiqile.tools.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zyj.qiqile.domain.bo.ActivityJoinBO;

public class ActivityJoinConverter {
	public static final String TAG = "ActivityJoinConverter";

	public static Map<String, String> activityJoinBOTOMap(
			ActivityJoinBO activityJoinBO) {
		Map<String, String> result = null;
		if (activityJoinBO != null) {
			result = new HashMap<String, String>();
			if (activityJoinBO.getActivityId() != null) {
				result.put(ActivityJoinBO.ACTIVITY_ID,
						activityJoinBO.getActivityId());
			}
			if (activityJoinBO.getUserId() != null) {
				result.put(ActivityJoinBO.USER_ID, activityJoinBO.getUserId());
			}
			if (activityJoinBO.getAttribute() != null) {
				result.put(ActivityJoinBO.ATTRIBUTE,
						activityJoinBO.getAttribute());
			}
		}
		return result;
	}

	public static List<ActivityJoinBO> jsonTOActivityBOList(JSONArray jsonArray) {
		List<ActivityJoinBO> activityJoinBOList = null;
		if (jsonArray != null) {
			activityJoinBOList = new ArrayList<ActivityJoinBO>();
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					activityJoinBOList.add(jsonTOActivityJoinBO(jsonArray
							.getJSONObject(i)));
				} catch (JSONException e) {
					Log.e(TAG, "", e);
					return null;
				}
			}
		}
		return activityJoinBOList;
	}

	/** json格式没有进行检查的，如果解析出错，直接返回null */
	public static ActivityJoinBO jsonTOActivityJoinBO(JSONObject jsonObject) {
		ActivityJoinBO activityJoinBO = null;
		if (jsonObject != null) {
			try {
				activityJoinBO = new ActivityJoinBO();
				if (!jsonObject.isNull(ActivityJoinBO.ID)) {
					activityJoinBO.setId(jsonObject
							.getString(ActivityJoinBO.ID));
				}
				if (!jsonObject.isNull(ActivityJoinBO.ACTIVITY_ID)) {
					activityJoinBO.setActivityId(jsonObject
							.getString(ActivityJoinBO.ACTIVITY_ID));
				}
				if (!jsonObject.isNull(ActivityJoinBO.USER_ID)) {
					activityJoinBO.setUserId(jsonObject
							.getString(ActivityJoinBO.USER_ID));
				}
				if (!jsonObject.isNull(ActivityJoinBO.ATTRIBUTE)) {
					activityJoinBO.setAttribute(jsonObject
							.getString(ActivityJoinBO.ATTRIBUTE));
				}
			} catch (JSONException e) {
				Log.e(TAG, "", e);
				return activityJoinBO;
			}
		}
		return activityJoinBO;
	}
}
