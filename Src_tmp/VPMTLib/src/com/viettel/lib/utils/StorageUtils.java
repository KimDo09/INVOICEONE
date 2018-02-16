/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.os.Environment;

/**
 *  Lop chua cac ham xu ly luu tru du lieu o bo nho trong hoac bo nho ngoai
 *  (External and Internal)
 *  @author: Nhantd
 *  @version: 1.0
 *  @since: 1.0
 */
public abstract class StorageUtils {

	// ---------------------------------------------------------------------------
	// Cac bien dung chung trong StringUtil
	// ---------------------------------------------------------------------------
	public static final String EXT_STORAGE_ROOT_PREFIX = "/Android/data";
	
	private static final String EXT_STORAGE_ROOT_SUFFIX = "/files";
	
	private static final String INT_STORAGE_DATA = "/data/data/";
	
	private static final String INT_STORAGE_DATABASE = "/database/";
	
	public static final String SD_CARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	
	private static StringBuilder sStoragePath = new StringBuilder();
	
	private static final String ALTERNATE_SDCARD_MOUNTS[] = {
		"/emmc",				// Internal storage on Droid Incredible, Nook Color/CyanogenMod, some other devices
		"/sdcard/ext_sd",		// Newer (2011) HTC devices (Flyer, Rezound)
		"/sdcard-ext",			// Some Motorola devices (RAZR)
		"/sdcard/sd",			// Older Samsung Galaxy S (Captivate)
		"/sdcard/sdcard"		// Archos tablets
	};
	
	// ---------------------------------------------------------------------------
	// Cac ham xu ly trong StorageUtils
	// ---------------------------------------------------------------------------
	
	private StorageUtils() {}
	
	/**
	 * 
	 *  Returns specified application cache directory. Cache directory will be created
	 *  on SD card by defined path if card is mounted with name "cache".
	 *  @author: Sergey Tarasevich
	 *  @param context
	 *  	-	Application context.
	 *  @return
	 *  @return: File
	 *  @throws:
	 */
	private static File getExternalCacheDir(Context context) {
		File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
		File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				return null;
			}
			try {
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (Exception e) {
				
			}
		}
		return appCacheDir;
	}
	
	/**
	 * 
	 *  Returns specified application cache directory. Cache directory will be created
	 *  on SD card by defined path if card is mounted. Else - Android defines cache
	 *  directory on device's file system.
	 *  @author: Sergey Tarasevich
	 *  @param context
	 *  	-	Application context.
	 *  @param cacheDir
	 *  	- Cache directory path (Ex: "AppCacheDir","AppDir/cache/images").
	 *  @return
	 *  @return: File
	 *  @throws:
	 */
	public static File getOwnCacheDirectory(Context context, String cacheDir) {
		File appCacheDir = null;
		if (Environment.getExternalStorageDirectory().equals(android.os.Environment.MEDIA_MOUNTED)) {
			appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
		}
		if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
			appCacheDir = context.getCacheDir();
		}
		return appCacheDir;
	}
	
	/**
	 * 
	 *  Return application cache directory. Cache directory will be created on SD card
	 *  ("/Android/data/[app_package_name]/cache") if card is mounted.
	 *  Else - Android defines cache directory on device's file system.
	 *  @author: Sergey Tarasevich
	 *  @param context
	 *  	-	Application context.
	 *  @return
	 *  @return: File
	 *  @throws:
	 */
	public static File getCacheDirectory(Context context) {
		File appCacheDir = null;
		if (Environment.getExternalStorageDirectory().equals(android.os.Environment.MEDIA_MOUNTED)) {
			appCacheDir = getExternalCacheDir(context);
		}
		if (appCacheDir == null) {
			appCacheDir = context.getCacheDir();
		}
		return appCacheDir;
	}
	
	/**
	 * 
	 *  Use older Android APIs to put data in the same relative directory location
	 *  as the 2.2 API. When device upgrades to 2.2, data will auto-delete
	 *  with app uninstall.
	 *  @author: Michael J. Portuesi
	 *  @param context
	 *  @param mountPoint
	 *  @param dirName
	 *  @return
	 *  @return: File
	 *  @throws:
	 */
	private static File buildCacheDirPath(Context context, File mountPoint, String dirName) {
		sStoragePath.setLength(0);
		sStoragePath.append(EXT_STORAGE_ROOT_PREFIX);
		sStoragePath.append(context.getPackageName());
		sStoragePath.append(EXT_STORAGE_ROOT_SUFFIX);
		sStoragePath.append(dirName);
		return new File(mountPoint, sStoragePath.toString());
	}
	
	/**
	 * 
	 *  Return a File object containing the folder to use for storing
	 *  data on the external SD card. Falls back to the internal
	 *  application cache if SD is not available or writeable.
	 *  @author: Michael J. Portuesi
	 *  @param context
	 *  	-	Application context.
	 *  @param dirName
	 *  	-	name of sub-folder within the SD card root.
	 *  @return
	 *  @return: File
	 *  @throws:
	 */
	public static File getSDCacheDir(Context context, String dirName) {
		File cacheDir = null;
		
		// Check to see if SD Card is mounted and read/write accessible
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageDirectory())) {
			// Get the directory on the SD card to store content
			// Attempt to use getExternalFilesDir() if we are on Android 2.2 or
			// newer. Data stored in this location will auto-delete
			// with app uninstall
			Method getExternalFilesDirMethod = null;
			try {
				getExternalFilesDirMethod = Context.class.getMethod("getExternalFilesDir", String.class);
				cacheDir = (File) getExternalFilesDirMethod.invoke(context, dirName);
			} catch (NoSuchMethodException e) {
				// Android 2.1 and earlier - use old APIs
				cacheDir = buildCacheDirPath(context, android.os.Environment.getExternalStorageDirectory(), dirName);
			} catch (IllegalArgumentException e) {
				cacheDir = buildCacheDirPath(context, android.os.Environment.getExternalStorageDirectory(), dirName);
			} catch (IllegalAccessException e) {
				cacheDir = buildCacheDirPath(context, android.os.Environment.getExternalStorageDirectory(), dirName);
			} catch (InvocationTargetException e) {
				cacheDir = buildCacheDirPath(context, android.os.Environment.getExternalStorageDirectory(), dirName);
			}
		}
		
		if (cacheDir == null) {
			// Atempting to find the default external storage was a failure.
			// Look for another suitable external filesystem where we can store our crap
			for (int i = 0; i < ALTERNATE_SDCARD_MOUNTS.length; i++) {
				File alternateDir = new File(ALTERNATE_SDCARD_MOUNTS[i]);
				if (alternateDir.exists() && alternateDir.isDirectory() &&
						alternateDir.canRead() && alternateDir.canWrite()) {
					cacheDir = buildCacheDirPath(context, alternateDir, dirName);
					break;
				}
			}
		}
		
		// Atempt to create folder on external storage if it does not exist
		if (cacheDir != null && !cacheDir.exists()) {
			if (!cacheDir.mkdirs()) {
				cacheDir = null; // Failed to create folder
			}
		}
		
		// Fall back on internal cache as a last resort
		if (cacheDir == null) {
			cacheDir = new File(context.getCacheDir() + File.separator + dirName);
			cacheDir.mkdirs();
		}
		
		return cacheDir;
	}
	
	/**
	 * 
	 *  Clear files from SD cache.
	 *  @author: Michael J. Portuesi
	 *  @param context
	 *  @param dirName
	 *  @return
	 *  @return: boolean
	 *  @throws:
	 */
	public static boolean clearSDCache(Context context, String dirName) {
		File cacheDir = getSDCacheDir(context, dirName);
		File[] files = cacheDir.listFiles();
		for (File f : files) {
			if (!f.delete()) {
				return false;
			};
		}
		return cacheDir.delete();
	}
	
	/**
	 * 
	 *  Get file database
	 *  @author: Michael J. Portuesi
	 *  @param context
	 *  @return
	 *  @return: File
	 *  @throws:
	 */
	public static File getDatabasePath(Context context) {
		StringBuilder sbl = new StringBuilder();
		sbl.append(INT_STORAGE_DATA);
		sbl.append(context.getPackageName());
		sbl.append(INT_STORAGE_DATABASE);
		return (new File(sbl.toString()));
	}
	
	/**
	 * 
	 *  Get file database external
	 *  @author: Nhantd
	 *  @param context
	 *  	-	Application context
	 *  @param name
	 *  	-	Ten thu muc database
	 *  @return
	 *  @return: File
	 *  @throws:
	 */
	public static File getDatabasePathExternal(Context context, String name) {
		File path = new File(context.getExternalCacheDir().getAbsolutePath() + "/" + name);
		if (!path.exists()) {
			path.mkdir();
		}
		return path;
	}
	
	/**
	 * 
	 *  Get file database external with directory name is DATABASE
	 *  @author: Nhantd
	 *  @param context
	 *  @return
	 *  @return: File
	 *  @throws:
	 */
	public static File getDatabasePathExternal(Context context) {
		return getDatabasePathExternal(context, "DATABASE");
	}
	
	/**
	 * 
	 *  Move mot file luu tru trong cache vao internal storage duoc chi dinh boi context
	 *  @author: Nhantd
	 *  @param context
	 *  @param cacheFile
	 *  @param internalStorageName
	 *  @return
	 *  @return: boolean
	 *  @throws:
	 */
	public static boolean moveCacheFile(Context context, File cacheFile, String internalStorageName) {
		boolean ret = false;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			fis = new FileInputStream(cacheFile);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			byte[] buffer = new byte[1024];
			int read = -1;
			while ((read = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, read);
			}
			baos.close();
			fis.close();
			
			fos = context.openFileOutput(internalStorageName, Context.MODE_PRIVATE);
			baos.writeTo(fos);
			fos.close();
			
			// delete cache
			cacheFile.delete();
			
			ret = true;
		} catch (Exception e) {
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {}
		}
		return ret;
	}
	
	/**
	 * 
	 *  Tao file trong SDCard voi ten file va ten thu muc da cho
	 *  @author: Nhantd
	 *  @param fileName
	 *  	-	Ten file.
	 *  @param dir
	 *  	-	Ten thu muc chua file
	 *  @return
	 *  @throws IOException
	 *  @return: File
	 *  	-	Neu thu muc khong co thi bo qua thu muc.
	 *  	-	Neu file khong co thi tra ve null.
	 *  	-	Tra ve file neu tao thanh cong.
	 *  @throws:
	 */
	public static File createFileInSDCard(String fileName, String dir) throws IOException {
		if (dir == null) {
			dir = "";
		}
		if (!StringUtils.hasLength(fileName)) {
			return null;
		}
		File file = new File(SD_CARD_ROOT + dir + File.separator + fileName);
		file.createNewFile();
		return file;
	}
	
	/**
	 * 
	 *  Kiem tra xem file co ton tai trong SDCard theo duong dan da cho hay khong
	 *  @author: Nhantd
	 *  @param path
	 *  	-	Duong dan muon kiem tra ton tai cua file (directory)
	 *  @return
	 *  @return: boolean
	 *  @throws:
	 */
	public static boolean isFileExistInSDCard(String path) {
		File file = new File(SD_CARD_ROOT + path);
		return file.exists();
	}
}  
