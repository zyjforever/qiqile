package com.zyj.qiqile.tools.converter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zyj.qiqile.domain.bo.ActivityJoinBO;
import com.zyj.qiqile.domain.vo.UserVO;
import com.zyj.qiqile.tools.TimeHelper;

public class UserVOConverter {
	public static final String TAG = "UserVOConverter";

	
	/**�õ�һ���û�������б������û����������غ����б���ע���б�����ݽ���*/
	public static List<UserVO> jsonTOUserVOList(JSONArray jsonArray) {
		List<UserVO> userVOList = null;
		if (jsonArray != null) {
			userVOList = new ArrayList<UserVO>();
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					userVOList.add(jsonTOUserVO(jsonArray.getJSONObject(i)));
				} catch (JSONException e) {
					Log.e(TAG, "", e);
					return null;
				}
			}
		}
		return userVOList;
	}

	/** json��ʽû�н��м��ģ������������ֱ�ӷ���null */
	public static UserVO jsonTOUserVO(JSONObject jsonObject) {
		UserVO userVO = null;
		if (jsonObject != null) {
			try {
				userVO = new UserVO();

				if (!jsonObject.isNull(ActivityJoinBO.USER_ID)) {
					userVO.setUserId(jsonObject
							.getString(ActivityJoinBO.USER_ID));
				}
				if (!jsonObject.isNull(UserVO.USERNAME)) {
					userVO.setUserName(jsonObject.getString(UserVO.USERNAME));
				}
				if (!jsonObject.isNull(UserVO.USERPICNAME)) {
					userVO.setUserPicName(jsonObject
							.getString(UserVO.USERPICNAME));
				}
				if (!jsonObject.isNull(UserVO.USERPICURL)) {
					userVO.setUserPicUrl(jsonObject
							.getString(UserVO.USERPICURL));
				}
				if (!jsonObject.isNull(UserVO.USERSEX)) {
					userVO.setUserSex(jsonObject.getInt(UserVO.USERSEX));
				}
				if (!jsonObject.isNull(UserVO.USERSIGNATURE)) {
					userVO.setUserSignature(jsonObject
							.getString(UserVO.USERSIGNATURE));
				}
				if (!jsonObject.isNull(UserVO.USERBIRTHDAY)) {
					userVO.setUserBirthday(new Date(TimeHelper
							.getSqlDateFromString(
									jsonObject.getString(UserVO.USERBIRTHDAY))
							.getTime()));
				}
			} catch (JSONException e) {
				Log.e(TAG, "", e);
				return userVO;
			}
		}
		return userVO;
	}
}
