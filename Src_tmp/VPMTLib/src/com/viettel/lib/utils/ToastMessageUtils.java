/**
 * Copyright 2014 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.androidvtmtlib.R;
import com.viettel.lib.eventlistener.ActionEventListener;

/**
 * @author:duchha
 * @since:Jan 21, 2014 4:46:26 PM
 * @Description:
 */
public class ToastMessageUtils {
	/**
	 * Class dung show Toast 0r Dialog
	 */
	static Toast obj;
	static int itemchosed = -1;
	static ArrayList<Integer> multiItemChoice;
	static ActionEventListener dlistener;
	public static int stt = 0;
	static String title = "";

	public static final void showToastShort(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * showToastLong
	 * 
	 * @author: Duchha
	 * @param context
	 * @param message
	 * @return: void
	 * @throws:
	 */
	public static final void showToastLong(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 
	 * Mo ta chuc nang cua ham
	 * 
	 * @author: Duchha
	 * @param context
	 * @param icon
	 *            : nhap -1 koi nhu khong co
	 * @param title
	 * @param message
	 * @return
	 * @return: Dialog
	 * @throws:
	 */
	public static AlertDialog showDialogWarning(Context context, int icon,
			String title, String message, String textButton) {
		// Use the Builder class for convenient dialog construction
		try {
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			if (icon != -1) {
				dialog.setIcon(icon);
			}
			dialog.setTitle(title)
					.setMessage(message)
					.setPositiveButton(textButton,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});
			AlertDialog alertDialog = dialog.create();
			alertDialog.setCanceledOnTouchOutside(false);
			alertDialog.show();
			return alertDialog;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * Showdialog voi 1 button. Thuc hien ActionEventListener sau khi click
	 * 
	 * @author: Duchha
	 * @param mes
	 * @param btName
	 * @param listener
	 * @param eventType
	 * @param eventCode
	 * @param data
	 * @param cancleable
	 * @param CanceledOnTouchOutside
	 * @return
	 * @return: AlertDialog
	 * @throws:
	 */
	public static AlertDialog showDialog(int icon, String title,
			final String mes, final String btName, final int eventType,
			final int eventCode, final Object data, boolean cancleable,
			boolean CanceledOnTouchOutside, final ActionEventListener listener) {

		try {
			final AlertDialog.Builder dialog;
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
				dialog = new AlertDialog.Builder((Context) listener);
				dialog.setInverseBackgroundForced(true);
			} else {
				dialog = new AlertDialog.Builder((Context) listener,
						AlertDialog.THEME_HOLO_LIGHT);
			}
			if (icon != -1) {
				dialog.setIcon(icon);
			}
			if (!StringUtils.isNullOrEmpty(title)) {
				dialog.setTitle(title);
			}
			dialog.setMessage(mes);
			dialog.setCancelable(cancleable);
			dialog.setPositiveButton(btName, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int arg1) {
					dialogInterface.dismiss();
					listener.onActionEventListener(eventType, eventCode, data);
				}
			});
			AlertDialog alertDialog = dialog.create();
			alertDialog.setCanceledOnTouchOutside(CanceledOnTouchOutside);
			alertDialog.show();

			return alertDialog;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * Showdialog voi 1 button. Thuc hien ActionEventListener sau khi click
	 * 
	 * @author: Duchha
	 * @param mes
	 * @param btName
	 * @param listener
	 * @param eventType
	 * @param eventCode
	 * @param data
	 * @param cancleable
	 * @param CanceledOnTouchOutside
	 * @return
	 * @return: AlertDialog
	 * @throws:
	 */
	public static AlertDialog showDialog(int icon, String title,
			final String mes, final String btName, final int eventType,
			final int eventCode, final Object data, boolean cancleable,
			boolean CanceledOnTouchOutside, final ActionEventListener listener,
			Context context) {

		try {
			final AlertDialog.Builder dialog;
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
				dialog = new AlertDialog.Builder(context);
				dialog.setInverseBackgroundForced(true);
			} else {
				dialog = new AlertDialog.Builder(context,
						AlertDialog.THEME_HOLO_LIGHT);
			}
			if (icon != -1) {
				dialog.setIcon(icon);
			}
			if (!StringUtils.isNullOrEmpty(title)) {
				dialog.setTitle(title);
			}
			dialog.setMessage(mes);
			dialog.setCancelable(cancleable);
			dialog.setPositiveButton(btName, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int arg1) {
					dialogInterface.dismiss();
					listener.onActionEventListener(eventType, eventCode, data);
				}
			});
			AlertDialog alertDialog = dialog.create();
			alertDialog.setCanceledOnTouchOutside(CanceledOnTouchOutside);
			alertDialog.show();

			return alertDialog;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * DialogSingleChoice
	 * 
	 * @author: Duchha
	 * @param context
	 * @param actionEvent
	 * @param actionType
	 * @param title
	 * @param arrayChar
	 *            :Source of the data in the DIalog
	 * @param dosomeThing
	 * @return
	 * @return: Dialog
	 * @throws:
	 */
	public static Dialog showDialogSingleChoice(final int actionEvent,
			final int actionType, String title, String[] arrayChar,
			ActionEventListener listener, int itemchoseda) {
		try {
			itemchosed = itemchoseda;
			dlistener = listener;
			AlertDialog.Builder dialog = new AlertDialog.Builder(
					(Context) listener);
			final String[] array = arrayChar;

			dialog.setTitle(title)
					.setSingleChoiceItems(array, itemchosed,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									itemchosed = which;
								}
							})

					.setPositiveButton(
							((Context) listener).getResources().getString(
									R.string.ok),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
//									ObjectValuesLib data = new ObjectValuesLib();
//									data.setPos(itemchosed);
//									data.setValues(array[itemchosed]);
//									dlistener.onActionEventListener(
//											actionEvent, actionType, data);
								}
							})
					.setNegativeButton(
							((Context) listener).getResources().getString(
									R.string.cancel),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});
			AlertDialog alertDialog = dialog.create();
			alertDialog.setCanceledOnTouchOutside(true);
			alertDialog.setCancelable(true);
			alertDialog.show();
			return alertDialog;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * DialogSingleChoice chua test
	 * 
	 * @author: Duchha
	 * @param context
	 * @param actionEvent
	 * @param actionType
	 * @param title
	 * @param arrayChar
	 *            :Source of the data in the DIalog
	 * @param dosomeThing
	 * @return
	 * @return: Dialog
	 * @throws:
	 */
	public static Dialog showDialogMultiChoice(final int actionEvent,
			final int actionType, String title, String[] arrayChar,
			boolean[] arayChecked, ActionEventListener listener) {
		try {
			multiItemChoice = new ArrayList<Integer>();
			dlistener = listener;
			AlertDialog.Builder dialog = new AlertDialog.Builder(
					(Context) listener);
			final String[] array = arrayChar;
			final boolean[] arayCheckedItems = arayChecked;
			dialog.setTitle(title)
					.setMultiChoiceItems(array, arayCheckedItems,
							new DialogInterface.OnMultiChoiceClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which, boolean isChecked) {
									// TODO Auto-generated method stub
									if (isChecked) {
										multiItemChoice.add(which);
									} else {
										for (int i = 0; i < multiItemChoice
												.size(); i++) {
											if (multiItemChoice.get(i) == which) {
												multiItemChoice.remove(i);
											}
										}
									}
								}
							})
					.setPositiveButton(
							((Context) listener).getResources().getString(
									R.string.ok),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									// ObjectValuesLib data = new
									// ObjectValuesLib();
									// data.setPos(multiItemChoice);
									// ArrayList<String> arr = new
									// ArrayList<String>();
									// for (int i = 0; i <
									// multiItemChoice.size(); i++) {
									// arr.add(array[multiItemChoice.get(i)]);
									// }
									// data.setValues(arr);
									// dlistener.onActionEventListener(
									// actionEvent, actionType, data);
								}
							})
					.setNegativeButton(
							((Context) listener).getResources().getString(
									R.string.cancel),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});

			AlertDialog alertDialog = dialog.create();
			alertDialog.setCanceledOnTouchOutside(true);
			alertDialog.setCancelable(true);
			alertDialog.show();
			return alertDialog;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * showDialogConfirm
	 * 
	 * @author: Duchha
	 * @param view
	 * @param icon
	 * @param title
	 * @param notice
	 * @param ok
	 * @param actionOk
	 * @param cancel
	 * @param actionCancel
	 * @param data
	 * @param isCanBack
	 * @param isTouchOutSide
	 * @param listener
	 * @return: void
	 * @throws:
	 */
	@SuppressWarnings("deprecation")
	public static void showDialogConfirm(int icon, String title,
			CharSequence notice, String ok, final int actionOk, String cancel,
			final int actionCancel, final Object data, boolean isCanBack,
			boolean isTouchOutSide, final ActionEventListener listener) {
		AlertDialog.Builder builder;
		if ((Context) listener != null) {
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
				builder = new AlertDialog.Builder((Context) listener);
				builder.setInverseBackgroundForced(true);
			} else {
				builder = new AlertDialog.Builder((Context) listener,
						AlertDialog.THEME_HOLO_LIGHT);
			}
			builder.setInverseBackgroundForced(true);
			AlertDialog alertDialog = builder.create();
			alertDialog.setIcon(icon);
			if (!StringUtils.isNullOrEmpty(title)) {
				alertDialog.setTitle(title);
			}
			alertDialog.setCancelable(isCanBack);
			alertDialog.setCanceledOnTouchOutside(isTouchOutSide);
			alertDialog.setMessage(notice);
			if (!StringUtils.isNullOrEmpty(ok)) {
				alertDialog.setButton(ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								listener.onActionEventListener(actionOk, -1,
										data);
								dialog.dismiss();
							}
						});
			}
			if (!StringUtils.isNullOrEmpty(cancel)) {
				alertDialog.setButton2(cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								listener.onActionEventListener(actionCancel,
										-1, data);
								dialog.dismiss();
							}
						});
			}
			alertDialog.show();
		}
	}

	/**
	 * 
	 * showDialogConfirm
	 * 
	 * @author: Duchha
	 * @param view
	 * @param icon
	 * @param title
	 * @param notice
	 * @param ok
	 * @param actionOk
	 * @param cancel
	 * @param actionCancel
	 * @param data
	 * @param isCanBack
	 * @param isTouchOutSide
	 * @param listener
	 * @return: void
	 * @throws:
	 */
	@SuppressWarnings("deprecation")
	public static void showDialogConfirm(int icon, String title,
			CharSequence notice, String ok, final int actionOk, String cancel,
			final int actionCancel, final Object data, boolean isCanBack,
			boolean isTouchOutSide, final ActionEventListener listener,
			Context context) {
		if (listener != null) {
			final AlertDialog.Builder builder;
			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
				builder = new AlertDialog.Builder(context);
				builder.setInverseBackgroundForced(true);
			} else {
				builder = new AlertDialog.Builder(context,
						AlertDialog.THEME_HOLO_LIGHT);
			}
			builder.setInverseBackgroundForced(true);
			AlertDialog alertDialog = builder.create();
			alertDialog.setIcon(icon);
			if (!StringUtils.isNullOrEmpty(title)) {
				alertDialog.setTitle(title);
			}
			alertDialog.setCancelable(isCanBack);
			alertDialog.setCanceledOnTouchOutside(isTouchOutSide);
			alertDialog.setMessage(notice);
			if (!StringUtils.isNullOrEmpty(ok)) {
				alertDialog.setButton(ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								listener.onActionEventListener(actionOk, -1,
										data);
								dialog.dismiss();
							}
						});
			}
			if (!StringUtils.isNullOrEmpty(cancel)) {
				alertDialog.setButton2(cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								listener.onActionEventListener(actionCancel,
										-1, data);
								dialog.dismiss();
							}
						});
			}
			alertDialog.show();
		}
	}

	@SuppressWarnings({ "deprecation" })
	public static void generateNotification(Context context, Bundle bundle,
			int icon, Object activityNext) {
		long when = System.currentTimeMillis();
		stt++;
		title = StringUtils.getString(context, R.string.noti1) + " " + stt
				+ " " + StringUtils.getString(context, R.string.noti1);

		Intent intent = null;
		if (!StringUtils.isNullOrEmpty(activityNext)) {
			intent = new Intent(context, (Class<?>) activityNext);
		}
		// set intent so it does not start a new activity
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("key", bundle);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, stt,
				intent, PendingIntent.FLAG_ONE_SHOT);

		Notification notification = new Notification(icon, title, when);
		notification.setLatestEventInfo(context, title,
				bundle.getString("contentTitle"), pendingIntent);

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.number += 1;

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}

}
