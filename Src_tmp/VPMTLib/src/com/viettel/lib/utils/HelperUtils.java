/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.RawContacts;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Lop chua cac ham xu ly ho tro
 * 
 * @author: Nhantd
 * @version: 1.0
 * @since: 1.0
 */
public class HelperUtils {

	// ---------------------------------------------------------------------------
	// Cac bien dung chung trong HelperUtils
	// ---------------------------------------------------------------------------

	// Orientation hysteresis amount used in rounding, in degrees
	public static final int ORIENTATION_HYSTERESIS = 5;

	public static final String REVIEW_ACTION = "com.android.camera.action.REVIEW";

	public static final String ACTION_NEW_PICTURE = "android.hardware.action.NEW_PICTURE";

	public static final String ACTION_NEW_VIDEO = "android.hardware.action.NEW_VIDEO";

	// Fields from android.hardware.Camera.Parameters
	public static final String FOCUS_MODE_CONTINUOUS_PICTURE = "continuous-picture";
	public static final String RECORDING_HINT = "recording-hint";
	public static final String AUTO_EXPOSURE_LOCK_SUPPORTED = "auto-exposure-lock-supported";
	public static final String AUTO_WHITE_BALANCE_LOCK_SUPPORTED = "auto-whitebalance-lock-supported";
	public static final String VIDEO_SNAPSHOT_SUPPORTED = "video-snapshot-supported";
	public static final String SCENE_MODE_HDR = "hdr";
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	public static final int DIRECTION_LEFT = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_UP = 2;
	public static final int DIRECTION_DOWN = 3;

	@SuppressWarnings("unused")
	private static float sPixelDensity = 1;



	// Cac bien dung trong bluetooth
	private static final int LOOP_WAIT_TIME = 500;
	private static final int MAX_REPETITIONS_COUNT = 30;

	// ---------------------------------------------------------------------------
	// Cac ham contructs trong HelperUtils
	// ---------------------------------------------------------------------------

	private HelperUtils() {
	}

	/**
	 * 
	 * Get pixel density
	 * 
	 * @author: Nhantd
	 * @param context
	 *            - Application context
	 * @return
	 * @return: float
	 * @throws:
	 */
	private static float getPixelDensity(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		return (sPixelDensity = metrics.density);
	}

	// ---------------------------------------------------------------------------
	// Cac ham kiem tra ho tro trong HelperUtils
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * Kiem tra co duoc support hay khong
	 * 
	 * @author: Nhantd
	 * @param value
	 *            - Gia tri muon kiem tra kieu String
	 * @param supported
	 *            - List cac gia tri support
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	private static boolean isSupported(String value, List<String> supported) {
		return supported == null ? false : supported.indexOf(value) >= 0;
	}

	/**
	 * 
	 * Kiem tra xem co ho tro tu dong khoa hay khong
	 * 
	 * @author: Nhantd
	 * @param params
	 *            - Cac tham so camera truyen vao (Camera.Parameters)
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isAutoExposureLockSupported(Parameters params) {
		return TRUE.equals(params.get(AUTO_WHITE_BALANCE_LOCK_SUPPORTED));
	}

	/**
	 * 
	 * Kiem tra xem co ho tro tu dong khoa can bang trang hay khong
	 * 
	 * @author: Nhantd
	 * @param params
	 *            - Cac tham so camera truyen vao (Camera.Parameters)
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isAutoWhiteBalanceLockSupported(Parameters params) {
		return TRUE.equals(params.get(AUTO_WHITE_BALANCE_LOCK_SUPPORTED));
	}

	/**
	 * 
	 * Kiem tra xem co ho tro snapshot hay khong
	 * 
	 * @author: Nhantd
	 * @param params
	 *            - Cac tham so camera truyen vao (Camera.Parameters)
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isVideoSnapshotSupported(Parameters params) {
		return TRUE.equals(params.get(VIDEO_SNAPSHOT_SUPPORTED));
	}

	/**
	 * 
	 * Kiem tra xem co ho tro Camere HDR (HIGH DYNAMIC RANGE) hay khong
	 * 
	 * @author: Nhantd
	 * @param params
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isCameraHdrSupported(Parameters params) {
		List<String> supported = params.getSupportedSceneModes();
		return (supported != null) && supported.contains(SCENE_MODE_HDR);
	}

	/**
	 * 
	 * Kiem tra xem co ho tro do dien tich hay khong
	 * 
	 * @author: Nhantd
	 * @param params
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	@TargetApi(ApiHelper.VERSION_CODES.ICE_CREAM_SANDWICH)
	public static boolean isMeteringAreaSupported(Parameters params) {
		if (ApiHelper.HAS_CAMERA_METERING_AREA) {
			return params.getMaxNumMeteringAreas() > 0;
		}
		return false;
	}

	/**
	 * 
	 * Kiem tra xem co ho tro focus area hay khong
	 * 
	 * @author: Nhantd
	 * @param params
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	@TargetApi(ApiHelper.VERSION_CODES.ICE_CREAM_SANDWICH)
	public static boolean isFocusAreaSupported(Parameters params) {
		if (ApiHelper.HAS_CAMERA_FOCUS_AREA) {
			return (params.getMaxNumFocusAreas() > 0 && isSupported(
					Parameters.FOCUS_MODE_AUTO, params.getSupportedFocusModes()));
		}
		return false;
	}

	/**
	 * 
	 * Kiem tra xem thiet bi co cho phep voice-capable (MMS)
	 * 
	 * @author: Nhantd
	 * @param context
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isMMSCapable(Context context) {
		TelephonyManager telephoneManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephoneManager == null) {
			return false;
		}
		try {
			Class<?> partypes[] = new Class[0];
			Method sIsVoiceCapable = TelephonyManager.class.getMethod(
					"isVoiceCapable", partypes);

			Object arglist[] = new Object[0];
			Object retobj = sIsVoiceCapable.invoke(telephoneManager, arglist);
			return (Boolean) retobj;
		} catch (java.lang.reflect.InvocationTargetException ite) {
			// Failure, must be another device.
			// Assume that it is voice capable.
		} catch (IllegalAccessException iae) {
			// Failure, must be an other device.
			// Assume that it is voice capable.
		} catch (NoSuchMethodException nsme) {
		}
		return true;
	}

	

	// ---------------------------------------------------------------------------
	// Cac ham xu ly voi camera trong HelperUtils
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * Convert dp to pixel
	 * 
	 * @author: Nhantd
	 * @param context
	 *            - Application context;
	 * @param dp
	 *            - dp muon convert
	 * @return
	 * @return: int
	 * @throws:
	 */
	public static int convertdpToPixel(Context context, int dp) {
		return Math.round(getPixelDensity(context) * dp);
	}

	/**
	 * 
	 * Rotate bitmap boi degree cu the. Neu mot bitmap moi duoc tao, bitmap goc
	 * se duoc lap lai
	 * 
	 * @author: Nhantd
	 * @param b
	 *            - bitmap
	 * @param degrees
	 *            - So do muon rotate. Ex : 90, -90, 180, -180.
	 * @return
	 * @return: Bitmap
	 * @throws:
	 */
	public static Bitmap rotate(Bitmap b, int degrees) {
		return rotateAndMirror(b, degrees, false);
	}

	/**
	 * 
	 * Rotate va/hay mirror bitmap. Neu mot bitmap moi duoc tao, bitmap goc se
	 * duoc lap lai
	 * 
	 * @author: Nhantd
	 * @param b
	 *            - bitmap
	 * @param degrees
	 *            - So do muon rotate. Ex : 90, -90, 180, -180;
	 * @param mirror
	 *            - true : co mirror. - false : khong mirror
	 * @return
	 * @return: Bitmap
	 * @throws:
	 */
	public static Bitmap rotateAndMirror(Bitmap b, int degrees, boolean mirror) {
		if ((degrees != 0 || mirror) && b != null) {
			Matrix m = new Matrix();
			// Mirror first.
			// Horizontal flip + rotation = -rotation + horizontal flip
			if (mirror) {
				m.postScale(-1, 1);
				degrees = (degrees + 360) % 360;
				if (degrees == 0 || degrees == 180) {
					m.postTranslate(b.getWidth(), 0);
				} else if (degrees == 90 || degrees == 270) {
					m.postTranslate(b.getHeight(), 0);
				} else {
					throw new IllegalArgumentException("Invalid degrees = "
							+ degrees);
				}
			}
			if (degrees != 0) {
				// clockwise
				m.postRotate(degrees, (float) b.getWidth() / 2,
						(float) b.getHeight() / 2);
			}

			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}
		return b;
	}

	/**
	 * 
	 * Tinh toan sample size nhu mot tinh nang cua minSideLength va
	 * maxNumOfPixels.
	 * 
	 * @author: Nhantd
	 * @param options
	 * @param minSideLength
	 *            - Xac dinh chiu rong va chieu cao thoi thieu cua mot bitmap.
	 * @param maxNumOfPixels
	 *            - Xac dinh kich thuoc toi da cac pixel chp phep luu tru trong
	 *            bo nho.
	 * @return
	 * @return: int - sample size dua tren constraints.
	 * @throws:
	 */
	public static int computeSimpleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	/**
	 * 
	 * Tinh toan initial sample size nhu mot tinh nang cua minSideLength va
	 * maxNumOfPixels.
	 * 
	 * @author: Nhantd
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 * @return: int
	 * @throws:
	 */
	public static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels < 0) ? 1 : (int) Math.ceil(Math.sqrt(w
				* h / maxNumOfPixels));
		int upperBound = (minSideLength < 0) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if (maxNumOfPixels < 0 && minSideLength < 0) {
			return 1;
		} else if (minSideLength < 0) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 
	 * Tinh toan khoang cach tu 1 diem (x,y) den 1 diem (sx,sy)
	 * 
	 * @author: Nhantd
	 * @param x
	 *            - Hoanh do diem thu 1
	 * @param y
	 *            - Tung do diem thu 1
	 * @param sx
	 *            - Hoanh do diem thu 2
	 * @param sy
	 *            - Tung do diem thu 2
	 * @return
	 * @return: float
	 * @throws:
	 */
	public static float calculateDistance(float x, float y, float sx, float sy) {
		float dx = x - sx;
		float dy = y - sy;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * 
	 * Tao bitmap
	 * 
	 * @author: Nhantd
	 * @param jpegData
	 *            - Mang byte chua image
	 * @param maxNumOfPixels
	 *            - So pixel toi da muon tao bitmap.
	 * @return
	 * @return: Bitmap
	 * @throws:
	 */
	public static Bitmap makeBitmap(byte[] jpegData, int maxNumOfPixels) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory
					.decodeByteArray(jpegData, 0, jpegData.length, options);
			if (options.mCancel || options.outWidth == -1
					|| options.outHeight == -1) {
				return null;
			}

			options.inSampleSize = computeSimpleSize(options, -1,
					maxNumOfPixels);
			options.inJustDecodeBounds = false;
			options.inDither = false;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;

			return BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length,
					options);
		} catch (OutOfMemoryError ex) {
			return null;
		}
	}

	/**
	 * 
	 * Close closable
	 * 
	 * @author: Nhantd
	 * @param c
	 * @return: void
	 * @throws:
	 */
	public static void closeSilently(Closeable c) {
		if (c == null) {
			return;
		}
		try {
			c.close();
		} catch (Throwable t) {
			// do nothing
		}
	}

	/**
	 * 
	 * Get rotation cua man hinh
	 * 
	 * @author: Nhantd
	 * @param activity
	 * @return
	 * @return: int - Tra ve rotation cua man hinh. Ex: 0,90,180,270.
	 * @throws:
	 */
	public static int getDisplayRotation(Activity activity) {
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		switch (rotation) {
		case DIRECTION_LEFT:
			return 0;
		case DIRECTION_RIGHT:
			return 90;
		case DIRECTION_UP:
			return 180;
		case DIRECTION_DOWN:
			return 270;
		}
		return 0;
	}

	/**
	 * 
	 * Get rotation cua camera
	 * 
	 * @author: Nhantd
	 * @param cameraId
	 * @return
	 * @return: int - Tra ve rotation cua camera. Ex: 0, 90, 180, 270.
	 * @throws:
	 */
	public static int getCameraOrientation(int cameraId) {
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(cameraId, info);
		return info.orientation;
	}

	/**
	 * 
	 * Tra ve size mac dinh cua activity hien tai.
	 * 
	 * @author: Nhantd
	 * @param activity
	 *            - activity muon biet size
	 * @param size
	 *            - Doi tuong Point nhan thong tin size
	 * @return
	 * @return: Point
	 * @throws:
	 */
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static Point getDefaultDisplaySize(Activity activity, Point size) {
		Display d = activity.getWindowManager().getDefaultDisplay();
		if (Build.VERSION.SDK_INT >= ApiHelper.VERSION_CODES.HONEYCOMB_MR2) {
			d.getSize(size);
		} else {
			size.set(d.getWidth(), d.getHeight());
		}
		return size;
	}

	/**
	 * 
	 * Tra ve size image lon nhat phu hop voi ti le ratio nhat dinh
	 * 
	 * @author: Nhantd
	 * @param size
	 *            - List cac kich thuoc size.
	 * @param targetRatio
	 *            - ti le mong muon.
	 * @return
	 * @return: Size
	 * @throws:
	 */
	public static Size getOptimalVideoSnapshotPictureSize(List<Size> sizes,
			double targetRatio) {
		// Use a very small tolerance because we want an exact match
		final double ASPECT_TOLERANCE = 0.001;
		if (sizes == null) {
			return null;
		}
		Size optimalSize = null;
		// Try to find a size matches aspect ratio and has the largest width
		for (Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
				continue;
			}
			if (optimalSize == null || size.width > optimalSize.width) {
				optimalSize = size;
			}
		}

		// Cannot find ont that matches the aspect ratio.
		// This should not happen. Ignore the requirement.
		if (optimalSize == null) {
			for (Size size : sizes) {
				if (optimalSize == null || size.width > optimalSize.width) {
					optimalSize = size;
				}
			}
		}
		return optimalSize;
	}

	/**
	 * 
	 * Thiet lap orientation display cho camera
	 * 
	 * @author: Nhantd
	 * @param activity
	 * @param cameraId
	 * @param camera
	 * @return: void
	 * @throws:
	 */
	public static void setCameraDisplayOrientation(Activity activity,
			int cameraId, Camera camera) {
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(cameraId, info);
		int degrees = getDisplayRotation(activity);
		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else {
			// back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}

	/**
	 * 
	 * Open camera
	 * 
	 * @author: Nhantd
	 * @param activity
	 *            - activity muon open camera
	 * @param title
	 *            - title cua camera
	 * @param description
	 *            - description cua camera
	 * @param mypicture
	 *            - Uri chua du lieu image chup dc
	 * @param requestCode
	 *            - dinh danh cho intent
	 * @return: void
	 * @throws:
	 */
	public static void openCamera(Activity activity, String title,
			String description, Uri mypicture, int requestCode) {
		ContentValues values = new ContentValues();
		values.put(Media.TITLE, title);
		values.put(Media.DESCRIPTION, description);
		mypicture = activity.getContentResolver().insert(
				Media.EXTERNAL_CONTENT_URI, values);
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT, mypicture);
		activity.startActivityForResult(i, requestCode);
	}

	// ---------------------------------------------------------------------------
	// Cac ham xu ly voi View trong HelperUtils
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * slide in (hien ra)
	 * 
	 * @author: Nhantd
	 * @param view
	 *            - view muon slide
	 * @param from
	 *            - Vi tri muon hien ra tu "from". - 0 : DIRECTION_LEFT - 1 :
	 *            DIRECTION_RIGHT - 2 : DIRECTION_UP - 3 : DIRECTION_DOWN
	 * @return
	 * @return: Animation
	 * @throws:
	 */
	public static Animation slideIn(View view, int from) {
		view.setVisibility(View.VISIBLE);
		Animation anim;
		switch (from) {
		case DIRECTION_LEFT:
			anim = new TranslateAnimation(-view.getWidth(), 0, 0, 0);
			break;
		case DIRECTION_RIGHT:
			anim = new TranslateAnimation(view.getWidth(), 0, 0, 0);
			break;
		case DIRECTION_UP:
			anim = new TranslateAnimation(0, 0, -view.getWidth(), 0);
			break;
		case DIRECTION_DOWN:
			anim = new TranslateAnimation(0, 0, view.getWidth(), 0);
			break;
		default:
			throw new IllegalArgumentException(Integer.toString(from));
		}
		anim.setDuration(500);
		view.startAnimation(anim);
		return anim;
	}

	/**
	 * 
	 * slide out (thoat ra)
	 * 
	 * @author: Nhantd
	 * @param view
	 *            - view muon slide
	 * @param to
	 *            - vi tri muon thoat ra den "to". - 0 : DIRECTION_LEFT - 1 :
	 *            DIRECTION_RIGHT - 2 : DIRECTION_UP - 3 : DIRECTION_DOWN
	 * @return
	 * @return: Animation
	 * @throws:
	 */
	public static Animation slideOut(View view, int to) {
		view.setVisibility(View.VISIBLE);
		Animation anim;
		switch (to) {
		case DIRECTION_LEFT:
			anim = new TranslateAnimation(0, -view.getWidth(), 0, 0);
			break;
		case DIRECTION_RIGHT:
			anim = new TranslateAnimation(0, view.getWidth(), 0, 0);
			break;
		case DIRECTION_UP:
			anim = new TranslateAnimation(0, 0, 0, -view.getWidth());
			break;
		case DIRECTION_DOWN:
			anim = new TranslateAnimation(0, 0, 0, view.getWidth());
			break;
		default:
			throw new IllegalArgumentException(Integer.toString(to));
		}
		anim.setDuration(500);
		view.startAnimation(anim);
		return anim;
	}

	/**
	 * 
	 * Fade in
	 * 
	 * @author: Nhantd
	 * @param view
	 * @param startAlpha
	 * @param endAlpha
	 * @param duration
	 *            - Thoi gian thuc hien
	 * @return: void
	 * @throws:
	 */
	public static void fadeIn(View view, float startAlpha, float endAlpha,
			long duration) {
		if (view.getVisibility() == View.VISIBLE) {
			return;
		}
		view.setVisibility(View.VISIBLE);
		Animation animation = new AlphaAnimation(startAlpha, endAlpha);
		animation.setDuration(duration);
		view.startAnimation(animation);
	}

	/**
	 * 
	 * Fade in
	 * 
	 * @author: Nhantd
	 * @param view
	 * @return: void
	 * @throws:
	 */
	public static void fadeIn(View view) {
		fadeIn(view, 0F, 1F, 400);
		view.setEnabled(true);
	}

	/**
	 * 
	 * Fade out and set View.GONE
	 * 
	 * @author: Nhantd
	 * @param view
	 * @return: void
	 * @throws:
	 */
	public static void fadeOut(View view) {
		if (view.getVisibility() != View.VISIBLE) {
			return;
		}
		view.setEnabled(false);
		Animation animation = new AlphaAnimation(1F, 0F);
		animation.setDuration(400);
		view.startAnimation(animation);
		view.setVisibility(View.GONE);
	}

	
	// ---------------------------------------------------------------------------
	// Cac ham xu ly voi Bluetooth trong HelperUtils
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * Doi cho den khi BluetoothApdapter co trang thai STATE_ON
	 * 
	 * @author: Nhantd
	 * @param state
	 *            - Trang thai.
	 * @param remainingLoops
	 *            - Thoi gian required.
	 * @throws Exception
	 * @return: void
	 * @throws:
	 */
	private static void waitUntilBluetoothAdapterIsInState(int state,
			int remainingLoops) throws Exception {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();

		if (remainingLoops > 0) {
			switch (state) {
			case BluetoothAdapter.STATE_OFF:
				if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_OFF) {
					bluetoothWaitNMillis(LOOP_WAIT_TIME);
					waitUntilBluetoothAdapterIsInState(
							BluetoothAdapter.STATE_OFF, remainingLoops - 1);
				} else if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
					return;
				} else {
					bluetoothAdapter.disable();
					waitUntilBluetoothAdapterIsInState(
							BluetoothAdapter.STATE_OFF, remainingLoops - 1);
				}
				break;
			case BluetoothAdapter.STATE_ON:
				if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_TURNING_ON) {
					bluetoothWaitNMillis(LOOP_WAIT_TIME);
					waitUntilBluetoothAdapterIsInState(
							BluetoothAdapter.STATE_ON, remainingLoops - 1);
				} else if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
					return;
				} else {
					bluetoothAdapter.disable();
					waitUntilBluetoothAdapterIsInState(
							BluetoothAdapter.STATE_ON, remainingLoops - 1);
				}
				break;
			default:
				throw new Exception(
						"You can check only final states of BluetoothAdapter(STATE_ON|STATEOFF).");
			}
		} else {
			return;
		}
	}

	/**
	 * 
	 * deley bluetooth n milliseconds
	 * 
	 * @author: Nhantd
	 * @param n
	 * @return: void
	 * @throws:
	 */
	private static void bluetoothWaitNMillis(long n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Start bluetooth voi trang thai STATE_ON
	 * 
	 * @author: Nhantd
	 * @return: void
	 * @throws:
	 */
	public static void startBluetoothAdapter() {
		try {
			waitUntilBluetoothAdapterIsInState(BluetoothAdapter.STATE_ON,
					MAX_REPETITIONS_COUNT);
		} catch (Exception e) {

		}
	}

	/**
	 * 
	 * Stop bluetooth voi trang thai STATE_OFF
	 * 
	 * @author: Nhantd
	 * @return: void
	 * @throws:
	 */
	public static void stopBluetoothAdapter() {
		try {
			waitUntilBluetoothAdapterIsInState(BluetoothAdapter.STATE_OFF,
					MAX_REPETITIONS_COUNT);
		} catch (Exception e) {

		}
	}

	// ---------------------------------------------------------------------------
	// Cac ham xu ly voi System memory trong HelperUtils
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * Ham xu ly cac yeu cau gui tu cmd va thuc hien trong workdirectory
	 * 
	 * @author: Nhantd
	 * @param cmd
	 * @param workdirectory
	 * @return
	 * @throws IOException
	 * @return: String
	 * @throws:
	 */
	private static synchronized String runForMemory(String[] cmd,
			String workdirectory) throws IOException {
		String result = "";
		try {
			ProcessBuilder builder = new ProcessBuilder(cmd);
			// Set working directory
			if (workdirectory != null) {
				builder.directory(new File(workdirectory));
			}
			builder.redirectErrorStream(true);
			Process process = builder.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[1024];
			while (in.read(re) != -1) {
				result = result + new String(re);
			}
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 *  Lay thong tin memory
	 *  @author: Nhantd
	 *  @return
	 *  @return: String
	 *  	-	Chuoi string chua thong tin memory.
	 *  @throws:
	 */
	public static String getMemoryInfo() {
		String result = null;
		try {
			String[] args = {"/system/bin/cat","/proc/meminfo"};
			result = runForMemory(args, "/system/bin/");
		} catch (IOException ex) {}
		return result;
	}
	
	/**
	 * 
	 *  Lay thong tin cpu
	 *  @author: Nhantd
	 *  @return
	 *  @return: String
	 *  @throws:
	 */
	public static String getCPUInfo() {
		String result = null;
		try {
			String[] args = {"/system/bin/cat","/proc/cpuinfo"};
			result = runForMemory(args, "/system/bin/");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 *  Lay tong dung luong memory
	 *  @author: Nhantd
	 *  @return
	 *  @return: long
	 *  @throws:
	 */
	public static long getTotalMemorySize() {
		long size = -1L;
		try {
			Runtime info = Runtime.getRuntime();
			size = info.totalMemory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}
	
	/**
	 * 
	 *  Lay dung luong free cua memory
	 *  @author: Nhantd
	 *  @return
	 *  @return: long
	 *  @throws:
	 */
	public static long getFreeMemorySize() {
		long size = -1L;
		try {
			Runtime info = Runtime.getRuntime();
			size = info.freeMemory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}
}
