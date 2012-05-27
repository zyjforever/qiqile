package com.zyj.qiqile.activity.module.item;

import java.io.File;

import com.zyj.qiqile.activity.module.CommonImageModule;
import com.zyj.qiqile.activity.module.CommonImageModule.PicType;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.ServerConstants;
import com.zyj.qiqile.domain.bo.ActivityCommentBO;
import com.zyj.qiqile.tools.FileHelper;
import com.zyj.qiqile.tools.TimeHelper;

import android.widget.ImageView;
import android.widget.TextView;

public class ActivityCommentItem {
	private TextView userNameText;
	private TextView commentTimeText;
	private TextView commentContext;
	private ImageView userImageView;
	private TextView replyButton;

	private CommonImageModule commonImageModule;

	public void initView(ActivityCommentBO activityComment) {
		if (activityComment != null) {
			userNameText.setText(activityComment.getUserName());
			commentTimeText.setText(TimeHelper.getDateString(activityComment
					.getTime()));
			commentContext.setText(activityComment.getContext());
			commonImageModule = new CommonImageModule(
					QiqileApplication.context, "", PicType.USER);
			commonImageModule
					.showImageWithNoAlert(userImageView,
							new File(FileHelper.getUserPicDirectory(),
									activityComment.getUserPicName()),
							activityComment.getUserPicUrl());
		}

	}

	public TextView getUserNameText() {
		return userNameText;
	}

	public void setUserNameText(TextView userNameText) {
		this.userNameText = userNameText;
	}

	public TextView getCommentTimeText() {
		return commentTimeText;
	}

	public void setCommentTimeText(TextView commentTimeText) {
		this.commentTimeText = commentTimeText;
	}

	public TextView getCommentContext() {
		return commentContext;
	}

	public void setCommentContext(TextView commentContext) {
		this.commentContext = commentContext;
	}

	public ImageView getUserImageView() {
		return userImageView;
	}

	public void setUserImageView(ImageView userImageView) {
		this.userImageView = userImageView;
	}

	public TextView getReplyButton() {
		return replyButton;
	}

	public void setReplyButton(TextView replyButton) {
		this.replyButton = replyButton;
	}
}
