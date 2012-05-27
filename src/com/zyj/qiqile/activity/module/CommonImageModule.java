package com.zyj.qiqile.activity.module;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zyj.qiqile.R;
import com.zyj.qiqile.constant.Constants;
import com.zyj.qiqile.task.DownloadImageTask;
import com.zyj.qiqile.task.GenericAferExcutedListener;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.SimpleDownloadImageTask;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.FileHelper;
import com.zyj.qiqile.tools.MD5Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

/** 处理图片模块 */
public class CommonImageModule {
	private final static String USER_PIC_DIRECTORY = "user_pic";
	private final static String ACTIVITY_PIC_DIRECTORY = "activity_pic";
	private final static int IO_BUFFER_SIZE = 1024 * 1024;
	private Context context;
	private String imageName;
	// Picture
	private File localImageFile;
	private File imageFile;
	private Uri imageUri;

	private String TAG;
	private PicType picType;
	private String directory;
	private int thumbnailSize;
	private File path;

	public enum PicType {
		USER, ACTIVITY
	};

	public CommonImageModule(Context context, String tag) {
		this.context = context;
		this.TAG = tag;
	}

	public CommonImageModule(Context context, String tag, PicType picType) {
		this(context, tag);
		this.picType = picType;
		switch (picType) {
		case USER:
			directory = this.USER_PIC_DIRECTORY;
			thumbnailSize = Constants.ME_PROFILE_IMAGE_SIZE;
			path = FileHelper.getUserPicDirectory();
			break;
		case ACTIVITY:
			directory = this.ACTIVITY_PIC_DIRECTORY;
			thumbnailSize = Constants.ACTIVITY_IMAGE_SIZE;
			path = FileHelper.getActivityPicDirectory();
			break;
		}

	}

	private String getPhotoFilename(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddKms");
		return dateFormat.format(date);
	}

	/** 用户头像的图片命名 */
	public String getUserPicName(String nick) {
		if (nick != null) {
			return MD5Helper.MD5(nick);
		}
		return null;
	}

	/** 活动图片命名 */
	public String getActivityPicName(String name, String nick) {
		if (name != null && nick != null) {
			return MD5Helper.MD5(name + nick);
		}
		return null;
	}

	public void createInsertPhotoDialog(String imageName) {
		this.imageName = imageName;
		final CharSequence[] items = {
				context.getString(R.string.write_label_take_a_picture),
				context.getString(R.string.write_label_choose_a_picture) };
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.write_label_insert_picture));
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:
					openImageCaptureMenu();
					break;
				case 1:
					openPhotoLibraryMenu();
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void openImageCaptureMenu() {
		try {
			imageFile = new File(FileHelper.getBasePath() + "/" + directory,
					imageName);
			imageUri = Uri.fromFile(imageFile);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			((Activity) context).startActivityForResult(intent,
					Constants.REQUEST_IMAGE_CAPTURE);
		} catch (Exception e) {
			Log.e("Image", e.getMessage());
		}
	}

	public void openPhotoLibraryMenu() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		((Activity) context).startActivityForResult(intent,
				Constants.REQUEST_PHOTO_LIBRARY);
	}

	/**
	 * 复制图片
	 * 
	 * @throws IOException
	 */
	public void copyImage() {
		Bitmap bitmap = createThumbnailBitmap(this.imageUri, thumbnailSize);
		try {
			this.imageFile = new File(path, imageName);
			FileHelper.copyImage(bitmap, imageFile);
		} catch (IOException e) {
			Log.e(TAG, "copyImage fail");
		}
	}

	public Bitmap getThumbnailBitmap() {
		return createThumbnailBitmap(this.imageUri, thumbnailSize);
	}

	public Bitmap getBitmapByFile(File file) {
		Bitmap bitmap = null;
		InputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream((int) file.length());
			byte[] data = new byte[(int) file.length()];
			int n;
			while ((n = fis.read(data)) != -1) {
				bos.write(data, 0, n);
			}
			bos.close();
			fis.close();
			bitmap = BitmapFactory
					.decodeByteArray(data, 0, (int) file.length());
		} catch (IOException e) {
			Log.w(TAG, e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					Log.w(TAG, e);
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					Log.w(TAG, e);
				}
			}
		}
		return bitmap;
	}

	public Bitmap getBitmapByImageName(String imgName) {
		File file = new File(path, imgName);
		return getBitmapByFile(file);
	}

	/**
	 * 制作微缩图
	 * 
	 * @param uri
	 * @param size
	 * @return
	 */
	public Bitmap createThumbnailBitmap(Uri uri, int size) {
		InputStream input = null;
		try {
			input = getActivity().getContentResolver().openInputStream(uri);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(input, null, options);
			input.close();

			// Compute the scale.
			int scale = 1;
			while ((options.outWidth / scale > size)
					|| (options.outHeight / scale > size)) {
				scale *= 2;
			}

			options.inJustDecodeBounds = false;
			options.inSampleSize = scale;

			input = getActivity().getContentResolver().openInputStream(uri);

			return BitmapFactory.decodeStream(input, null, options);
		} catch (IOException e) {
			Log.w(TAG, e);
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					Log.w(TAG, e);
				}
			}
		}
	}

	/** 显示图片，如果目标文件不存在，就用默认图像 */
	public void showImage(ImageView v, File file) {
		if (v != null) {
			if (file != null && file.exists()) {
				Bitmap bitmap = this.getBitmapByFile(file);
				v.setImageBitmap(bitmap);
			} else {
				v.setImageDrawable(context.getResources().getDrawable(
						R.drawable.clipping_picture));
			}
		}
	}

	public void showImage(ImageView v, String picName, PicType picType) {
		if (v != null) {
			if (picName != null) {
				File file = null;
				switch (picType) {
				case ACTIVITY:
					file = new File(FileHelper.getActivityPicDirectory(),
							picName);
					break;
				case USER:
					file = new File(FileHelper.getUserPicDirectory(), picName);
					break;
				}
				showImage(v, file);
			}

		} else {
			v.setImageDrawable(context.getResources().getDrawable(
					R.drawable.clipping_picture));
		}
	}

	public void showImage(ImageView v, String picName, String picUrl,
			PicType picType) {
		if (v != null) {
			if (picName != null) {
				File file = null;
				switch (picType) {
				case ACTIVITY:
					file = new File(FileHelper.getActivityPicDirectory(),
							picName);
					break;
				case USER:
					file = new File(FileHelper.getUserPicDirectory(), picName);
					break;
				}
				showImage(v, file, picUrl);
			}

		} else {
			v.setImageDrawable(context.getResources().getDrawable(
					R.drawable.clipping_picture));
		}
	}

	/** 显示图片,如果目标文件不存在，就去源地址下载;下载失败，就用默认图像 */
	public void showImage(final ImageView v, final File file, String url) {
		if (v != null) {
			if (file != null && file.exists()) {
				showImage(v, file);
			} else {
				GenericTask downloadTask = new DownloadImageTask();
				TaskParams params = new TaskParams();
				params.put("file", file);
				params.put("url", url);
				downloadTask.setListener(new GenericAferExcutedListener() {
					@Override
					public void onPostExecute(GenericTask task,
							TaskResult result) {
						if (result.getResult() == ResultCode.SUCCESS)
							showImage(v, file);
					}
				});
				downloadTask.execute(params);
			}
		}
	}
	
	
	/** 显示图片,如果目标文件不存在，就去源地址下载;下载失败，就用默认图像(没有提示版本) */
	public void showImageWithNoAlert(final ImageView v, final File file, String url) {
		if (v != null) {
			if (file != null && file.exists()) {
				showImage(v, file);
			} else {
				GenericTask downloadTask = new SimpleDownloadImageTask();
				TaskParams params = new TaskParams();
				params.put("file", file);
				params.put("url", url);
				downloadTask.setListener(new GenericAferExcutedListener() {
					@Override
					public void onPostExecute(GenericTask task,
							TaskResult result) {
						if (result.getResult() == ResultCode.SUCCESS)
							showImage(v, file);
					}
				});
				downloadTask.execute(params);
			}
		}
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Activity getActivity() {
		return (Activity) context;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public Uri getImageUri() {
		return imageUri;
	}

	public void setImageUri(Uri imageUri) {
		this.imageUri = imageUri;
	}

	public File getLocalImageFile() {
		return localImageFile;
	}

	public void setLocalImageFile(File localImageFile) {
		this.localImageFile = localImageFile;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public PicType getPicType() {
		return picType;
	}

	public void setPicType(PicType picType) {
		this.picType = picType;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}
}
