/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.lang.reflect.Field;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.hardware.Camera;
import android.os.Build;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.WindowManager;

/**
 *  Lop ho tro Api chua cac bien nhu version device, check cac dieu kien cua device
 *  nhu co ho tro xoay camera, ho tro chup anh hdr, ho tro tu dong lay net,
 *  ho tro video size,... hay khong
 *  Copyright (C) 2012 The Android Open Source Project
 *  @author: Nhantd
 *  @version: 1.0
 *  @since: 1.0
 */
public class ApiHelper {
	
	public static interface VERSION_CODES {
		// These value are copied from Build.VERSION_CODES
		public static final int BASE = 1;
		public static final int BASE_1_1 = 2;
		public static final int CUPCAKE = 3;
		public static final int CUR_DEVELOPMENT = 10000;
		public static final int DONUT = 4;
		public static final int ECLAIR = 5;
		public static final int ECLAIR_0_1 = 6;
		public static final int ECLAIR_MR1 = 7;
		public static final int FROYO = 8;
		public static final int GINGERBREAD = 9;
		public static final int GINGERBREAD_MR1 = 10;
		public static final int HONEYCOMB = 11;
		public static final int HONEYCOMB_MR1 = 12;
		public static final int HONEYCOMB_MR2 = 13;
		public static final int ICE_CREAM_SANDWICH = 14;
		public static final int ICE_CREAM_SANDWICH_MR1 = 15;
		public static final int JELLY_BEAN = 16;
		public static final int JELLY_BEAN_MR1 = 17;
		public static final int JELLY_BEAN_MR2 = 18;
		public static final int KITKAT = 19;
	}
	
	public static final boolean AT_LEAST_16 = Build.VERSION.SDK_INT >= 16;
	
	public static final boolean USE_888_PIXEL_FORMAT = 
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
			
	public static final boolean ENABLE_PHOTO_EDITOR =
			Build.VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
	
	public static final boolean HAS_VIEW_SYSTEM_UI_FLAG_LAYOUT_STABLE =
			hasField(View.class, "SYSTEM_UI_FLAG_LAYOUT_STABLE");
	
	public static final boolean HAS_VIEW_SYSTEM_UI_FLAG_HIDE_NAVIGATION =
			hasField(View.class, "SYSTEM_UI_FLAG_HIDE_NAVIGATION");
	
	public static final boolean HAS_MEDIA_COLUMNS_WIDTH_AND_HEIGHT =
			hasField(MediaColumns.class, "WIDTH");
	
	public static final boolean HAS_REUSING_BITMAP_IN_BITMAP_REGION_DECODER =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
			
	public static final boolean HAS_REUSING_BITMAP_IN_BITMAP_FACTORY =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
			
	public static final boolean HAS_SET_BEAM_PUSH_URIS =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
			
	public static final boolean HAS_SET_DEFAULT_BUFFER_SIZE = hasMethod(
			"android.graphics.SurfaceTexture","setDefaultBufferSize",
			int.class, int.class);
	
	public static final boolean HAS_RELEASE_SURFACE_TEXTURE = hasMethod(
			"android.graphics.SurfaceTexture","release");
	
	public static final boolean HAS_SURFACE_TEXTURE =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean HAS_MTP =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
			
	public static final boolean HAS_AUTO_FOCUS_MOVE_CALLBACK =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
			
	public static final boolean HAS_REMOTE_VIEWS_SERVICE =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean HAS_INTENT_EXTRA_LOCAL_ONLY =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean HAS_SET_SYSTEM_UI_VISIBILITY =
			hasMethod(View.class,"setSystemUiVisibility", int.class);
	
	public static final boolean HAS_FACE_DETECTION;
	static {
		boolean hasFaceDetection = false;
		try {
			Class<?> listenerClass = Class.forName(
					"android.hardware.Camera$FaceDetectionListener");
			hasFaceDetection =
					hasMethod(Camera.class, "setFaceDetectionListener", listenerClass) &&
					hasMethod(Camera.class, "startFaceDetection") &&
					hasMethod(Camera.class, "stopFaceDetection") &&
					hasMethod(Camera.Parameters.class, "getMaxNumDetectedFaces");
		} catch (Throwable t) {
		}
		HAS_FACE_DETECTION = hasFaceDetection;
	}
	
	public static final boolean HAS_GET_CAMERA_DISABLED =
			hasMethod(DevicePolicyManager.class, "getCameraDisabled", ComponentName.class);
	
	public static final boolean HAS_MEDIA_ACTION_SOUND =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
			
	public static final boolean HAS_TIME_LAPSE_RECORDING =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean HAS_ZOOM_WHEN_RECORDING =
			Build.VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
			
	public static final boolean HAS_CAMERA_FOCUS_AREA =
			Build.VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
			
	public static final boolean HAS_CAMERA_METERING_AREA =
			Build.VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
			
	public static final boolean HAS_MOTION_EVENTS_TRANSFORM =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean HAS_EFFECTS_RECORDING = false;
	
	// "Background" filter does not have "context" input port in jelly bean.
	public static final boolean HAS_EFFECTS_RECORDING_CONTEXT_INPUT =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1;
			
	public static final boolean HAS_GET_SUPPORTED_VIDEO_SIZE =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean HAS_SET_ICON_ATTRIBUTE =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean HAS_MEDIA_PROVIDER_FILES_TABLE =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean HAS_SURFACE_TEXTURE_RECORDING =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
			
	public static final boolean HAS_ACTION_BAR =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	// Ex: View.setTranslationX.
	public static final boolean HAS_VIEW_TRANSFORM_PROPERTIES =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean HAS_CAMERA_HDR =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1;
			
	public static final boolean HAS_OPTION_IN_MUTABLE =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean CAN_START_PREVIEW_IN_JPEG_CALLBACK =
			Build.VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
			
	public static final boolean HAS_VIEW_PROPERTY_ANIMATOR =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
			
	public static final boolean HAS_POST_ON_ANIMATION =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
			
	public static final boolean HAS_ANNOUNCE_FOR_ACCESSIBILITY =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
			
	public static final boolean HAS_OBJECT_ANIMATION =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean HAS_GLES20_REQUIRED =
			Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
			
	public static final boolean HAS_ROTATION_ANIMATION =
			hasField(WindowManager.LayoutParams.class, "rotationAnimation");
	
	public static final boolean HAS_ORIENTATION_LOCK =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2;
			
	public static final boolean HAS_CANCELLATION_SIGNAL =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
			
	public static final boolean HAS_MEDIA_MUXER =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2;
			
	public static final boolean HAS_DISPLAY_LISTENER =
			Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1;
	
	/**
	 * 
	 *  Lay vi tri int cua field neu ton tai.
	 *  @author: Nhantd
	 *  @param kclass
	 *  @param fieldName
	 *  @param obj
	 *  @param defaultVal
	 *  @return
	 *  @return: int
	 *  @throws:
	 */
	public static int getIntFieldIfExists(Class<?> kclass, String fieldName,
			Class<?> obj, int defaultVal) {
		try {
			Field f = kclass.getDeclaredField(fieldName);
			return f.getInt(obj);
		} catch (Exception e) {
			return defaultVal;
		}
	}
	
	/**
	 * 
	 *  Kiem tra field co ton tai hay khong
	 *  @author: Nhantd
	 *  @param kclass
	 *  @param fieldName
	 *  @return
	 *  @return: boolean
	 *  @throws:
	 */
	private static boolean hasField(Class<?> kclass, String fieldName) {
		try {
			kclass.getDeclaredField(fieldName);
			return true;
		} catch (NoSuchFieldException e) {
			return false;
		}
	}
	
	/**
	 * 
	 *  Kiem tra xem phuong thuc co ton tai hay khong
	 *  @author: Nhantd
	 *  @param className
	 *  @param methodName
	 *  @param parameterTypes
	 *  @return
	 *  @return: boolean
	 *  @throws:
	 */
	private static boolean hasMethod(String className, String methodName,
			Class<?>... parameterTypes) {
		try {
			Class<?> kclass = Class.forName(className);
			kclass.getDeclaredMethod(methodName, parameterTypes);
			return true;
		} catch (Throwable th) {
			return false;
		}
	}
	
	/**
	 * 
	 *  Kiem tra xem phuong thuc co ton tai hay khong
	 *  @author: Nhantd
	 *  @param className
	 *  @param methodName
	 *  @param parameterTypes
	 *  @return
	 *  @return: boolean
	 *  @throws:
	 */
	private static boolean hasMethod(Class<?> kclass, String methodName,
			Class<?>... paramTypes) {
		try {
			kclass.getDeclaredMethod(methodName, paramTypes);
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}
}
