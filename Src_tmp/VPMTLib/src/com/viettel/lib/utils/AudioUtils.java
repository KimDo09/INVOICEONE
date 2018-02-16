/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Lop play audio
 * I want use class : Define Androidmanifesh <uses-permission android:name="android.permission.RECORD_AUDIO" />

 * 
 * @author: Nhantd
 * @version: 1.0
 * @since: 1.0
 */
public abstract class AudioUtils {

	private static MediaRecorder mMediaRecorder;

	/** 
	  * @author: QuyL
	  *  @since: 2.0
	  *  Description: start Recording audio
	  */
	public static void startRecordingAudio(Context context, File audioFileName) {
		if (mMediaRecorder != null) {
			// If recoding, release
			mMediaRecorder.release();
		}
		mMediaRecorder = new MediaRecorder();
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mMediaRecorder.setOutputFile(audioFileName.getAbsolutePath());

		try {
			mMediaRecorder.prepare();
			mMediaRecorder.start();
			Log.d("file audio", ""+audioFileName.getAbsolutePath());
		} catch (Exception e) {
			Log.e("giftlist",
					"io problems while preparing ["
							+ audioFileName.getAbsolutePath() + "]: "
							+ e.getMessage());
		}

	}
	
	/**
	 * 
	 *  Stop chuc nang ghi am (recording)
	 *  @author: Nhantd
	 *  @return: void
	 *  @throws:
	 */
	public static void stopRecordingAudio() {
		if (mMediaRecorder != null) {
			mMediaRecorder.stop();
			mMediaRecorder.release();
			mMediaRecorder = null;
		}
	}
	
	/**
	 * 
	 *  Play audio file
	 *  @author: Nhantd
	 *  @param audioFilePath
	 *  	-	Duong dan chua audio file.
	 *  @return: void
	 *  @throws:
	 */
	@SuppressWarnings("resource")
	public static void playAudio(String audioFilePath) {
		try {
			MediaPlayer mp = new MediaPlayer();
			mp.reset();
			FileInputStream in = new FileInputStream(new File(audioFilePath));
			mp.setDataSource(in.getFD());
			mp.prepare();
			mp.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	*  Play video
	*  @author: Quyl
	*  @param context of activity
	*  @param path of file
	*  @param file Name of file
	*  @param auto Play video
	*  @return: void
	*  @throws:
	 */
	public static void videoPlayer(Context context, String path, String fileName, boolean autoPlay){
		VideoView videoView = new VideoView(context);
		videoView.setMediaController(new MediaController(context));
		videoView.setVideoURI(Uri.parse(path +"/" +fileName));
		videoView.requestFocus();
		if(autoPlay){
			videoView.start();
		}
		
	}
}
