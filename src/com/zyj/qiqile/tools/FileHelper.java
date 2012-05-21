package com.zyj.qiqile.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class FileHelper {
	private static final String TAG = "FileHelper";
	private final static String USER_PIC_DIRECTORY = "user_pic";
	private final static String ACTIVITY_PIC_DIRECTORY = "activity_pic";

	/** 应用主目录 */
	private static final String BASE_PATH = "qiqile";

	public static File getBasePath() {
		File basePath = new File(Environment.getExternalStorageDirectory(),
				BASE_PATH);
		if (!basePath.exists()) {
			if (!basePath.mkdirs()) {
				Log.e(TAG,
						String.format("%s cannot be created!",
								basePath.toString()));
			}
		}

		if (!basePath.isDirectory()) {
			Log.e(TAG, String.format("%s is not a directory!",
					basePath.toString()));
		}

		return basePath;
	}

	public static File getUserPicDirectory() {
		File path = new File(getBasePath(), USER_PIC_DIRECTORY);
		if (!path.exists()) {
			if (!path.mkdirs()) {
				Log.e(TAG,
						String.format("%s cannot be created!", path.toString()));
			}
		}

		if (!path.isDirectory()) {
			Log.e(TAG, String.format("%s is not a directory!", path.toString()));
		}
		return path;
	}

	public static File getActivityPicDirectory() {
		File path = new File(getBasePath(), ACTIVITY_PIC_DIRECTORY);
		if (!path.exists()) {
			if (!path.mkdirs()) {
				Log.e(TAG,
						String.format("%s cannot be created!", path.toString()));
			}
		}

		if (!path.isDirectory()) {
			Log.e(TAG, String.format("%s is not a directory!", path.toString()));
		}
		return path;
	}

	public static void copyImage(Bitmap bitmap, File file) throws IOException {
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, os);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(os.toByteArray());
		fos.flush();
		fos.close();
		bitmap.recycle();
	}
}
