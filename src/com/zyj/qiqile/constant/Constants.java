package com.zyj.qiqile.constant;

public interface Constants {
	
	
	//内部返回值
	/**用户完成昵称输入*/
	public static final int REQUEST_USER_NICK=1;
	/**从相机获取图片*/
	public static final int REQUEST_IMAGE_CAPTURE = 2;
	/**从本地库中获取图片*/
	public static final int REQUEST_PHOTO_LIBRARY = 3;
	/**用户完成签名输入*/
	public static final int REQUEST_USER_SIGNATURE=4;
	/**用户完成性别输入*/
	public static final int REQUEST_USER_SEX=5;
	/**用户完成生日输入*/
	public static final int REQUEST_USER_BIRTHDAY=6;
	/**用户完成城市输入*/
	public static final int REQUEST_USER_CITY=7;

	/**选择活动地点*/
	public static final int REQUEST_ACTIVITY_LOCATION=8;

	/**活动评论*/
	public static final int REQUEST_ACTIVITY_COMMENT=9;
	
	/**个人信息中图片大小*/
	public static final int ME_PROFILE_IMAGE_SIZE=200;
	
	/**活动图片大小*/
	public static final int ACTIVITY_IMAGE_SIZE=300;
	
	/**个人签名长度限制*/
	public static final int SIGNATURE_MAXSIZE=100;
	
	/**昵称长度限制*/
	public static final int NICK_MAXSIZE=20;
	public static final int NICK_MINSIZE=2;
	
	/**性别*/
	public static final int BOY=1;
	public static final int GIRL=0;
	public static final int OTHER=-1;
	
	/**活动名称限制*/
	public static final int ACTIVITY_NAME_MAXSIZE=10;
	public static final int ACTIVITY_CONTEXT_MAXSIZE=300;
	
	/**活动评论长度限制*/
	public static final int ACTIVITY_COMMENT_MAXSIZE=140;
}	
