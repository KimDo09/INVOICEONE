/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import org.springframework.util.Assert;

import android.content.Context;
import android.content.res.Resources;

/**
 * Lop chua cac ham xu ly chung cho file, directory
 * 
 * @author: Nhantd
 * @version: 1.0
 * @since: 1.0
 */
public abstract class FileUtils {

	// ---------------------------------------------------------------------------
	// Cac bien dung chung trong FileUtils
	// ---------------------------------------------------------------------------

	public static final int BUFFER_SIZE = 4096;

	private static final Pattern SAFE_FILENAME_PATTERN = Pattern
			.compile("[\\w%+,./=_-]+");

	// ---------------------------------------------------------------------------
	// Cac ham xu ly chung voi file
	// ---------------------------------------------------------------------------

	private FileUtils() {
	}

	/**
	 * 
	 * Write noi dung cua mot inputstream vao mot outputstream da co.
	 * 
	 * @author: Nhantd
	 * @param in
	 * @param out
	 * @return
	 * @throws IOException
	 * @return: long - So byte duoc write.
	 * @throws:
	 */
	public static long write(InputStream in, OutputStream out)
			throws IOException {
		return copyPipe(in, out, BUFFER_SIZE);
	}

	/**
	 * 
	 * Write noi dung cua Reader vao Writer da co.
	 * 
	 * @author: Nhantd
	 * @param in
	 * @param out
	 * @return
	 * @throws IOException
	 * @return: long - So byte duoc write.
	 * @throws:
	 */
	public static long write(Reader in, Writer out) throws IOException {
		Assert.notNull(in, "No Reader specified");
		Assert.notNull(out, "No Writer specified");
		try {
			long byteCount = 0;
			char[] buffer = new char[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();

			return byteCount;
		} finally {
			try {
				in.close();
			} catch (IOException e) {

			}
			try {
				out.close();
			} catch (IOException e) {

			}
		}
	}

	/**
	 * 
	 * Write du lieu tu mot mang byte vao destFile.
	 * 
	 * @author: Nhantd
	 * @param write
	 * @param destFile
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean writeToFile(byte[] write, File destFile) {
		try {
			OutputStream out = new FileOutputStream(destFile);
			try {
				out.write(write);
			} finally {
				out.close();
			}
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	/**
	 * 
	 * Write du lieu tu mot source stream vao destFile.
	 * 
	 * @author: Nhantd
	 * @param inputStream
	 * @param destFile
	 * @return
	 * @return: boolean - true neu thanh cong. - false neu that bai.
	 * @throws:
	 */
	public static boolean writeToFile(InputStream inputStream, File destFile) {
		try {
			OutputStream out = new FileOutputStream(destFile);
			try {
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) >= 0) {
					out.write(buffer, 0, bytesRead);
				}
			} finally {
				out.close();
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 
	 * Write noi dung cua URL vao file
	 * 
	 * @author: Nhantd
	 * @param url
	 *            - url muon write noi dung
	 * @param file
	 *            - file muon luu noi dung cua url
	 * @return
	 * @return: boolean - true : neu thanh cong. - false : neu that bai.
	 * @throws:
	 */
	public static boolean writeToFile(URL url, File file) {
		BufferedInputStream urlin = null;
		BufferedOutputStream fout = null;
		try {
			int buffsize = 8 * 1024;
			urlin = new BufferedInputStream(url.openConnection()
					.getInputStream(), buffsize);
			fout = new BufferedOutputStream(new FileOutputStream(file),
					buffsize);
			copyPipe(urlin, fout, buffsize);
		} catch (IOException e) {
			return false;
		} catch (SecurityException sx) {
			return false;
		} finally {
			if (urlin != null) {
				try {
					urlin.close();
				} catch (IOException e) {
				}
				try {
					fout.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * Write noi dung cua srcFile vao mot mang byte array.
	 * 
	 * @author: Nhantd
	 * @param srcFile
	 * @return
	 * @throws IOException
	 * @return: byte[]
	 * @throws:
	 */
	public static byte[] writeToByteArray(File srcFile) throws IOException {
		Assert.notNull(srcFile, "No input File specified");
		return writeToByteArray(new BufferedInputStream(new FileInputStream(
				srcFile)));
	}

	/**
	 * 
	 * write noi dung cu inputstream vao mot mang byte array.
	 * 
	 * @author: Nhantd
	 * @param in
	 * @return
	 * @throws IOException
	 * @return: byte[]
	 * @throws:
	 */
	public static byte[] writeToByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		write(in, out);
		return out.toByteArray();
	}

	/**
	 * 
	 * Copy du lieu tu srcFile den destFile
	 * 
	 * @author: Nhantd
	 * @param srcFile
	 * @param destFile
	 * @return
	 * @return: boolean - true neu thanh cong. - false neu that bai.
	 * @throws:
	 */
	public static boolean copyFile(File srcFile, File destFile) {
		boolean result = false;
		if (destFile.isDirectory()) {
			destFile = new File(destFile, srcFile.getName());
		}
		try {
			InputStream in = new FileInputStream(srcFile);
			try {
				result = writeToFile(in, destFile);
			} finally {
				in.close();
			}
		} catch (IOException e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * copy du lieu tu inputstream va viet vao outputstream, cho den khi ket
	 * thuc inputstream
	 * 
	 * @author: Nhantd
	 * @param in
	 *            - inputstream
	 * @param out
	 *            - outputstream
	 * @param bufferSizeHint
	 *            - Kich thuoc cua mang byte muon copy. Default la 1024 byte
	 * @return
	 * @return: long - So byte duoc write.
	 * @throws:
	 */
	public static long copyPipe(InputStream in, OutputStream out,
			int bufferSizeHint) throws IOException {
		Assert.notNull(in, "No InputStream specified");
		Assert.notNull(out, "No OutputStream specified");
		if (bufferSizeHint == 0) {
			bufferSizeHint = 1024;
		}
		try {
			long byteCount = 0;
			byte[] buffer = new byte[bufferSizeHint];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();

			return byteCount;
		} finally {
			try {
				in.close();
			} catch (IOException e) {

			}
			try {
				out.close();
			} catch (IOException e) {

			}
		}
	}

	/**
	 * 
	 * Kiem tra xem mot file co "safe" hay khong (khong chua sieu ky tu va khoan
	 * trang)
	 * 
	 * @author: Nhantd
	 * @param file
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isFilenameSafe(File file) {
		// Note, we check whether it matches what's know to be safe,
		// rather than what's known to be unsafe. Non-ASCII, control
		// characters, etc. are all unsafe by default.
		return SAFE_FILENAME_PATTERN.matcher(file.getPath()).matches();
	}

	/**
	 * 
	 * Kiem tra xem mot file da ton tai hay chua
	 * 
	 * @author: Nhantd
	 * @param file
	 * @return
	 * @return: boolean - false : neu file = null hoac khong ton tai. - true :
	 *          neu file co ton tai.
	 * @throws:
	 */
	public static boolean isFileExists(File file) {
		if (file == null) {
			return false;
		}
		return file.exists();
	}

	/**
	 * 
	 * Kiem tra xem mot file duoc xac dinh boi ten file co ton tai hay khong
	 * 
	 * @author: Nhantd
	 * @param fileName
	 * @return
	 * @return: boolean - false : neu filename = null hoac filename = "" hoac
	 *          file khong ton tai. - true : neu file duoc xac dinh boi filename
	 *          co ton tai.
	 * @throws:
	 */
	public static boolean isFileExists(String fileName) {
		if (fileName == null
				|| (fileName = StringUtils.trimWhitespace(fileName)).equals("")) {
			return false;
		}
		File file = new File(fileName);
		if (!isFileExists(file)) {
			return false;
		}
		return isFileExists(file);
	}

	/**
	 * 
	 * Doc mot text file vao mot chuoi string, tuy chon gioi han do dai.
	 * 
	 * @author: Nhantd
	 * @param file
	 *            - file doc vao
	 * @param max
	 *            - do dai max (positive for head, negative of tail, 0 for no
	 *            limit) - > 0 la doc tu dau, < 0 la doc tu cuoi, = 0 la doc
	 *            khong gioi han
	 * @param ellipsis
	 *            - to add of the file was truncated (can be null)
	 * @return
	 * @throws IOException
	 * @return: String
	 * @throws:
	 */
	public static String readTextFile(File file, int max, String ellipsis)
			throws IOException {
		InputStream input = new FileInputStream(file);
		try {
			if (max > 0) {
				// "head" mode : read the first N bytes
				byte[] data = new byte[max + 1];
				int length = input.read(data);
				if (length <= 0) {
					return "";
				}
				if (length <= max) {
					return new String(data, 0, length);
				}
				if (ellipsis == null) {
					return new String(data, 0, max);
				}
				return new String(data, 0, max) + ellipsis;
			} else if (max < 0) {
				// "tail" mode : read it all, keep the last N
				int len;
				boolean rolled = false;
				byte[] last = null, data = null;
				do {
					if (last != null) {
						rolled = true;
					}
					byte[] tmp = last;
					last = data;
					data = tmp;
					if (data == null) {
						data = new byte[-max];
					}
					len = input.read(data);
				} while (len == data.length);

				if (last == null && len <= 0) {
					return "";
				}
				if (last == null) {
					return new String(data, 0, len);
				}
				if (len > 0) {
					rolled = true;
					System.arraycopy(last, len, last, 0, last.length - len);
					System.arraycopy(data, 0, last, last.length - len, len);
				}
				if (ellipsis == null || !rolled) {
					return new String(last);
				}
				return ellipsis + new String(last);
			} else {
				// "cat" mode : read it all
				ByteArrayOutputStream contents = new ByteArrayOutputStream();
				int len;
				byte[] data = new byte[1024];
				do {
					len = input.read(data);
					if (len > 0) {
						contents.write(data, 0, len);
					}
				} while (len == data.length);
				return contents.toString();
			}
		} finally {
			input.close();
		}
	}

	/**
	 * 
	 * read mot file tu /raw/res va tra ve duoi dinh dang chuoi String
	 * 
	 * @author: Nhantd
	 * @param res
	 *            - Resources instance.
	 * @param resourceId
	 *            - ResourceId ID of resource (ex: R.raw.resource_name)
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String readRawString(Resources res, int resourceId) {
		StringBuilder sb = new StringBuilder();
		Scanner s = new Scanner(res.openRawResource(resourceId));

		while (s.hasNextLine()) {
			sb.append(s.nextLine() + "\n");
		}
		return sb.toString();
	}

	/**
	 * 
	 * read mot file tu /raw/res/ va tra ve mot mang byte
	 * 
	 * @author: Nhantd
	 * @param res
	 *            - Resources instance.
	 * @param resourceId
	 *            - ResourcesId ID of resource (Ex: R.raw.resource_name)
	 * @return
	 * @return: byte[] - byte[] neu thanh cong. - null neu that bai.
	 * @throws:
	 */
	public static byte[] readRawByteArray(Resources res, int resourceId) {
		InputStream in = null;
		byte[] raw = new byte[] {};
		try {
			in = res.openRawResource(resourceId);
			raw = new byte[in.available()];
			in.read(raw);
		} catch (IOException e) {
			raw = null;
		} finally {
			try {
				in.close();
			} catch (IOException ioe) {
			}
		}
		return raw;
	}

	/**
	 * 
	 * Xac dinh MIME type cua file xac dinh boi filename
	 * 
	 * @author: Nhantd
	 * @param filename
	 * @return
	 * @return: String - MIMI type cua filename nhap vao
	 * @throws:
	 */
	public static String getMIMEType(String filename) {
		int pos = filename.lastIndexOf('.');
		if (pos != -1) {
			String ext = filename.substring(filename.lastIndexOf('.') + 1,
					filename.length());

			if (ext.equalsIgnoreCase("mp3")) {
				return "audio/mpeg";
			}
			if (ext.equalsIgnoreCase("aac")) {
				return "audio/aac";
			}
			if (ext.equalsIgnoreCase("wav")) {
				return "audio/wav";
			}
			if (ext.equalsIgnoreCase("ogg")) {
				return "audio/ogg";
			}
			if (ext.equalsIgnoreCase("mid")) {
				return "audio/midi";
			}
			if (ext.equalsIgnoreCase("wma")) {
				return "audio/x-ms-wma";
			}

			if (ext.equalsIgnoreCase("mp4")) {
				return "video/mp4";
			}
			if (ext.equalsIgnoreCase("avi")) {
				return "video/x-msvideo";
			}
			if (ext.equalsIgnoreCase("wmv")) {
				return "video/x-ms-wmv";
			}

			if (ext.equalsIgnoreCase("png")) {
				return "image/png";
			}
			if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpe")
					|| ext.equalsIgnoreCase("jpeg")) {
				return "image/jpeg";
			}
			if (ext.equalsIgnoreCase("gif")) {
				return "image/gif";
			}

			if (ext.equalsIgnoreCase("xml")) {
				return "text/xml";
			}
			if (ext.equalsIgnoreCase("txt") || ext.equalsIgnoreCase("cfg")
					|| ext.equalsIgnoreCase("csv")
					|| ext.equalsIgnoreCase("conf")
					|| ext.equalsIgnoreCase("rc")) {
				return "text/plain";
			}
			if (ext.equalsIgnoreCase("htm") || ext.equalsIgnoreCase("html")) {
				return "text/html";
			}

			if (ext.equalsIgnoreCase("pdf")) {
				return "application/pdf";
			}
			if (ext.equalsIgnoreCase("apk")) {
				return "application/vnd.android.package-archive";
			}
		}
		return "*/*";
	}

	/**
	 * 
	 * Format dung luong tu dong Ex : 4096 B -> 4 KB
	 * 
	 * @author: Nhantd
	 * @param size
	 *            - Dung luong can format.
	 * @return
	 * @return: String
	 * @throws:
	 */
	public static String formatFileSizeAuto(long size) {
		if (size <= 0) {
			return "0";
		}
		final String[] units = new String[] { "B", "KB", "MB", "GB", "TB",
				"PB", "EB" };
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size
				/ Math.pow(1024, digitGroups))
				+ " " + units[digitGroups];
	}

	/**
	 * 
	 * Tinh toan checksum cua mot file su dung CRC32 checksum.
	 * 
	 * @author: Nhantd
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return: long - Gia tri checksum tra ve.
	 * @throws:
	 */
	public static long checksumCrc32(File file) throws FileNotFoundException,
			IOException {
		CRC32 checkSum = new CRC32();
		CheckedInputStream cis = null;

		try {
			cis = new CheckedInputStream(new FileInputStream(file), checkSum);
			byte[] buffer = new byte[128];
			while (cis.read(buffer) >= 0) {
				// Just read for checksum to get calculated.
			}
			return checkSum.getValue();
		} finally {
			if (cis != null) {
				try {
					cis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	// ---------------------------------------------------------------------------
	// Cac ham xu ly chung voi directory
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * Kiem tra xem mot file xac dinh boi filename co phai la mot directory hay
	 * khong.
	 * 
	 * @author: Nhantd
	 * @param fileName
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isDirectory(String fileName) {
		if (fileName == null
				|| (fileName = StringUtils.trimWhitespace(fileName)).equals("")) {
			return false;
		}
		File file = new File(fileName);
		if (!isFileExists(file)) {
			return false;
		}
		return file.isDirectory();
	}

	/**
	 * 
	 * Copy directory src vao directory dest voi bo loc filter
	 * 
	 * @author: Nhantd
	 * @param srcDir
	 *            - File src (Directory)
	 * @param destDir
	 *            - File dest (Directory)
	 * @param filter
	 *            - file filter
	 * @throws IOException
	 * @return: void
	 * @throws:
	 */
	public static void copyToDirectory(File srcDir, File destDir,
			FileFilter filter) throws IOException {
		File nextDirectory = new File(destDir, srcDir.getName());

		// Create the directory if necessary
		if (!isFileExists(nextDirectory) && !nextDirectory.mkdirs()) {
			String message = "DirCopyFailed";
			throw new IOException(message);
		}

		File[] files = srcDir.listFiles();

		// And then all the items below the directory
		for (int n = 0; n < files.length; n++) {
			if (filter == null || filter.accept(files[n])) {
				copyToDirectory(files[n], nextDirectory, filter);
			} else {
				copyFile(files[n], nextDirectory);
			}
		}
	}

	/**
	 * 
	 * Copy directory src vao directory dest
	 * 
	 * @author: Nhantd
	 * @param srcDir
	 * @param destDir
	 * @throws IOException
	 * @return: void
	 * @throws:
	 */
	public static void copyToDirectory(File srcDir, File destDir)
			throws IOException {
		copyToDirectory(srcDir, destDir, null);
	}

	/**
	 * 
	 * Xoa mot directory
	 * 
	 * @author: Nhantd
	 * @param dir
	 * @return
	 * @throws IOException
	 * @return: boolean - true : neu thanh cong. - false : neu that bai.
	 * @throws:
	 */
	public static boolean deleteDirectory(File dir) throws IOException {
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.isDirectory()) {
					try {
						if (file.getCanonicalFile().getParentFile()
								.equals(dir.getCanonicalFile())) {
							deleteDirectory(file);
							if (file.exists() && !file.delete()) {
								String message = "Can't delete" + file;
								throw new IOException(message);
							}
						} else {
							String message = "Warning : " + file
									+ " may be a symlink. Ignoring.";
							throw new IOException(message);
						}
					} catch (IOException e) {
					}
				} else {
					if (file.exists() && !file.delete()) {
						String message = "Can't delete" + file;
						throw new IOException(message);
					}
				}
			}
			return dir.delete();
		}
		return false;
	}

	/**
	 * 
	 * Xoa cac file trong mot directory, van de lai directory rong.
	 * 
	 * @author: Nhantd
	 * @param dir
	 * @return
	 * @return: boolean - true : neu thanh cong. - false : neu that bai.
	 * @throws:
	 */
	public static boolean emptyDirectory(File dir) {
		boolean success;
		if (dir == null) {
			throw new IllegalArgumentException("Dir is null");
		}
		try {
			if ((success = dir.exists() && deleteDirectory(dir))) {
				dir.mkdirs();
			}

			return (success && dir.exists());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * Get dung luong (kich thuoc) cua directory
	 * 
	 * @author: Nhantd
	 * @param dirPath
	 *            - directory can kiem tra dung luong
	 * @return
	 * @return: long
	 * @throws:
	 */
	public static long getDirectorySize(File dirPath) {
		long totalSize = 0;

		if (dirPath == null) {
			return 0;
		}

		File[] files = dirPath.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				totalSize += file.length();
			} else if (file.isDirectory()) {
				totalSize += file.length();
				totalSize += getDirectorySize(file);
			}
		}
		return totalSize;
	}

	public static void saveObject(Context context, Object object,
			String fileName) {
		try {
			FileOutputStream fos = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.close();
			fos.close();
		} catch (Throwable e) {
			LogUtils.logToFile("Profile", "Error save object: " + e.toString());
			e.printStackTrace();
		}
	}

	public static Object readObject(Context context, String fileName) {
		Object object = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			if (isFileExists(fileName)) {
				fis = context.openFileInput(fileName);
				if (fis != null) {// ton tai file
					ois = new ObjectInputStream(fis);
					object = ois.readObject();

				}
			}
		} catch (Throwable e) {
			object = null;
			LogUtils.logToFile("Profile", "Error read object: " + e.toString());
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (Exception e) {
			}
		}
		return object;
	}

}
