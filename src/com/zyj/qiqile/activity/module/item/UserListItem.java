package com.zyj.qiqile.activity.module.item;

import java.io.File;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.module.CommonImageModule;
import com.zyj.qiqile.activity.module.CommonImageModule.PicType;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.ServerConstants;
import com.zyj.qiqile.domain.bo.ActivityCommentBO;
import com.zyj.qiqile.domain.bo.ActivityJoinBO;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.domain.vo.UserVO;
import com.zyj.qiqile.tools.FileHelper;
import com.zyj.qiqile.tools.TimeHelper;

import android.widget.ImageView;
import android.widget.TextView;

/** 用户列表的每一行记录 */
public class UserListItem {
	private TextView userNickText;
	private ImageView userImageView;
	private TextView userSignatureText;
	private TextView userAgeText;
	private TextView userSexText;
	private CommonImageModule commonImageModule;

	public void initView(UserVO userVO) {
		if (userVO != null) {
			commonImageModule = new CommonImageModule(
					QiqileApplication.context, "", PicType.USER);
			if (userVO.getUserPicName() != null) {
				commonImageModule.showImageWithNoAlert(
						userImageView,
						new File(FileHelper.getUserPicDirectory(), userVO
								.getUserPicName()), userVO.getUserPicUrl());
			}
			else{
				userImageView.setBackgroundResource(R.drawable.clipping_picture);
			}
			userNickText.setText(userVO.getUserName());
			userSignatureText.setText(userVO.getUserSignature());
			userAgeText.setText(UserBO.getAgeString(userVO.getUserBirthday()));
			userSexText.setText(UserBO.getSexString(userVO.getUserSex()));
		}

	}

	public TextView getUserNickText() {
		return userNickText;
	}

	public void setUserNickText(TextView userNickText) {
		this.userNickText = userNickText;
	}

	public ImageView getUserImageView() {
		return userImageView;
	}

	public void setUserImageView(ImageView userImageView) {
		this.userImageView = userImageView;
	}

	public TextView getUserSignatureText() {
		return userSignatureText;
	}

	public void setUserSignatureText(TextView userSignatureText) {
		this.userSignatureText = userSignatureText;
	}

	public TextView getUserAgeText() {
		return userAgeText;
	}

	public void setUserAgeText(TextView userAgeText) {
		this.userAgeText = userAgeText;
	}

	public TextView getUserSexText() {
		return userSexText;
	}

	public void setUserSexText(TextView userSexText) {
		this.userSexText = userSexText;
	}

}
