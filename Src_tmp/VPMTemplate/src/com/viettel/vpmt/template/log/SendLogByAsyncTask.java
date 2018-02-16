/**
 * Copyright 2013 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.vpmt.template.log;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

import com.viettel.lib.utils.LogUtils;

/**
 * PostDataAsyncTask
 * 
 * @author: vuonghv2
 * @version: 1.0
 * @since: May 12, 2014
 */
public class SendLogByAsyncTask extends AsyncTask<String, String, String> {
	String filePathToSend;
	private SendLogListener listener;

	/**
	 * Constructor
	 * 
	 * @author: vuonghv2
	 * @Since: Jun 5, 2014
	 * @throws:
	 */
	public SendLogByAsyncTask(String filePath, SendLogListener myListener) {
		// TODO Auto-generated constructor stub
		this.filePathToSend = filePath;
		this.listener = myListener;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		// do stuff before posting data
	}

	@Override
	protected String doInBackground(String... strings) {
		try {
			postFile();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String lenghtOfFile) {
		// do stuff after posting data
		listener.finishSendLog(filePathToSend);
	}

	private void postFile() {
		try {

			// the URL where the file will be posted
			String postReceiverUrl = ConfigLog.SERVER_SEND_LOG;
			// Log.v("postURL", "postURL: " + postReceiverUrl);

			// new HttpClient
			HttpClient httpClient = new DefaultHttpClient();

			// post header
			HttpPost httpPost = new HttpPost(postReceiverUrl);

			File file = new File(filePathToSend);
			FileBody fileBody = new FileBody(file);

			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntity.addPart("file", fileBody);
			httpPost.setEntity(reqEntity);

			// execute HTTP post request
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {
				String responseStr = EntityUtils.toString(resEntity).trim();
				LogUtils.v("Response", "Response: " + responseStr);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
