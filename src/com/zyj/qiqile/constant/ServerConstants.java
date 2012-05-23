package com.zyj.qiqile.constant;

/** 跟服务端有关的常量 */
public interface ServerConstants {
	public static final String HTTP = "http://";
	public static final String HTTPS = "https://";

	public final static String ERROR_CODE = "err_code";
	public final static String ERROR_MESSAGE = "err_message";
	public final static String DATA = "data";
	public final static String PROFILE = "profile";
	public final static String TOKEN = "token";

	/** 服务器地址 */
	public static final String SERVER_URL = "zyjforever.sinaapp.com/api/";

	/** 用户头像服务器地址 */
	public static final String USER_PIC_SERVER_URL = "zyjforever.sinaapp.com/server/core/upload/user_pic_upload.php";

	/** 活动图片服务器地址 */
	public static final String ACTIVITY_PIC_SERVER_URL = "zyjforever.sinaapp.com/server/core/upload/activity_pic_upload.php";

	/** 公共模块 */
	public static final String LIST = "list/";
	public static final String UPDATE = "update/";
	public static final String INSERT = "insert/";
	public static final String REMOVE = "remove/";

	/** 用户模块 */
	public static final String USER = "user/";
	public static final String LOGIN = "get_token/";
	public static final String REGISTER = "insert/";
	public static final String QUERY_BY_ID = "querybyid/";
	public static final String UPDATE_PASSWORD = "updatepassword/";

	/** 活动模块 */
	public static final String ACTIVITY = "activity/";
	public static final String ACTIVITY_JOIN = "activityjoin/";
	public static final String QUERY_BY_CITY_AND_TIME = "querybycityandtime/";
	public static final String IS_JOIN = "isjoin/";

}
