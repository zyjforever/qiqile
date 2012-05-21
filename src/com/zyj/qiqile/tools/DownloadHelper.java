package com.zyj.qiqile.tools;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DownloadHelper {
	private static final String TAG = "downloadfile";
	private static final int TIME_OUT = 10 * 1000; // 超时时间

	private static final String CHARSET = "utf-8"; // 设置编码

	/**
	 * android下载文件到服务器
	 * 
	 * @param file本地文件
	 * 
	 * @param RequestURL请求的rul
	 * 
	 * @return 返回响应的内容
	 */

	public static boolean downloadFile(File file, String RequestURL) {
		boolean result = false;
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);
			if (file != null && !file.exists()) {
				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				int res = conn.getResponseCode();
				Log.i(TAG, "response code:" + res);
				if (res == 200) {
					// FileOutputStream fos = new FileOutputStream(file);
					// Log.i(TAG, "request success");
					// InputStream input = conn.getInputStream();
					// byte bytes[]=new byte[1024];
					// while (input.read(bytes) != -1) {
					// fos.write(bytes);
					// }
					// input.close();
					// fos.flush();
					// fos.close();
					// result = true;
					// Log.i(TAG, "result : " + result);
					Bitmap bitmap = BitmapFactory.decodeStream(conn
							.getInputStream());
					FileHelper.copyImage(bitmap, file);
					Log.i(TAG, "result : " + result);
					result = true;
				}
			}
		} catch (MalformedURLException e) {
			Log.e(TAG, "result : " + result, e);
			return result;
		} catch (IOException e) {
			Log.e(TAG, "result : " + result, e);
			return result;
		}
		return result;
	}
}
