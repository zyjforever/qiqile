package com.zyj.qiqile.tools.converter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zyj.qiqile.domain.bo.ActivityJoinBO;
import com.zyj.qiqile.domain.bo.UserAttentionBO;
import com.zyj.qiqile.domain.vo.UserVO;
import com.zyj.qiqile.tools.TimeHelper;

public class UserAttentionConverter {
	public final static String TAG = "UserAttentionConverter";

	public static List<UserAttentionBO> jsonTOUserAttentionList(
			JSONArray jsonArray) {
		List<UserAttentionBO> userAttentionBOList = null;
		if (jsonArray != null) {
			userAttentionBOList = new ArrayList<UserAttentionBO>();
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					userAttentionBOList.add(jsonTOUserAttentionBO(jsonArray
							.getJSONObject(i)));
				} catch (JSONException e) {
					Log.e(TAG, "", e);
					return null;
				}
			}
		}
		return userAttentionBOList;
	}

	/** json格式没有进行检查的，如果解析出错，直接返回null */
	public static UserAttentionBO jsonTOUserAttentionBO(JSONObject jsonObject) {
		UserAttentionBO userAttentionBO = null;
		if (jsonObject != null) {
			try {
				userAttentionBO = new UserAttentionBO();
				if (!jsonObject.isNull(UserAttentionBO.ATTRIBUTE)) {
					userAttentionBO.setAttribute(jsonObject
							.getString(UserAttentionBO.ATTRIBUTE));
				}
				if (!jsonObject.isNull(UserAttentionBO.ATTENTIONID)) {
					userAttentionBO.setAttentionId(jsonObject
							.getString(UserAttentionBO.ATTENTIONID));
				}
				userAttentionBO.setUserVO(UserVOConverter
						.jsonTOUserVO(jsonObject));
			} catch (JSONException e) {
				Log.e(TAG, "", e);
				return userAttentionBO;
			}
		}
		return userAttentionBO;
	}

}
