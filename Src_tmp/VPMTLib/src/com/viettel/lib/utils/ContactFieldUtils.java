/**
 * Copyright 2014 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.lib.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;

/**
 * Mo ta muc dich cua lop (interface)
 * 
 * @author: Duchha
 * @version: 1.0
 * @since: 1.0
 */
public class ContactFieldUtils {

	// Cac bien chua key Contact
	public static final String ID_CONTACT = "id_contact";
	public static final String PHONE_NUMBER = "phone_number";
	public static final String DISPLAY_NAME = "display_name";
	public static final String PHOTO_URI = "photo_uri";
	public static final String PHOTO_THUMBNAIL_URI = "photo_thumbnail_uri";
	public static final String DISPLAY_NAME_PRIMARY = "display_name_primary";
	public static final String DISPLAY_NAME_ALTERNATIVE = "display_name_alternative";

	// ---------------------------------------------------------------------------
	// Cac ham xu ly voi Contact trong HelperUtils
	// ---------------------------------------------------------------------------

	/**
	 * 
	 * Get phone contacts.
	 * 
	 * @author: Duchha
	 * @param context
	 *            - Application context
	 * @return
	 * @return: ArrayList<Map<String,String>> - Map chua key va value cua cac
	 *          gia tri ID, NUMBER, DISPLAYNAME, PHOTO_URI, PHOTO_THUMBNAIL_URI,
	 *          DISPLAY_NAME_PRIMARY, DISPLAY_NAME_ALTERNATIVE
	 * @throws:
	 */
	public static ArrayList<Map<String, String>> getAllPhoneContacts(
			Context context) {
		ArrayList<Map<String, String>> arrContacts = new ArrayList<Map<String, String>>();
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		Cursor cursor = context
				.getContentResolver()
				.query(uri,
						new String[] {
								ContactsContract.CommonDataKinds.Phone.NUMBER,
								ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
								ContactsContract.CommonDataKinds.Phone._ID,
								ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
								ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI,
								ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
								ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE },
						null,
						null,
						ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
								+ " COLLATE LOCALIZED ASC");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(ID_CONTACT,
					String.valueOf(cursor.getInt(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))));
			map.put(DISPLAY_NAME,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)));
			map.put(PHONE_NUMBER,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
			map.put(PHOTO_THUMBNAIL_URI,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)));
			map.put(PHOTO_URI,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)));
			map.put(DISPLAY_NAME_ALTERNATIVE,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE)));
			map.put(DISPLAY_NAME_PRIMARY,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY)));

			if (map != null) {
				arrContacts.add(map);
			}
			cursor.moveToNext();
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return arrContacts;
	}

	/**
	 * 
	 * Get phone contacts boi contactName xac dinh truoc.
	 * 
	 * @author: Duchha
	 * @param context
	 * @param contactName
	 * @return
	 * @return: ArrayList<Map<String,String>> - List cac contact co DISPLAY_NAME
	 *          like '%contactName%'.
	 * @throws:
	 */
	public static ArrayList<Map<String, String>> findContactNameLIKE(
			Context context, String contactName) {
		ArrayList<Map<String, String>> arrContacts = new ArrayList<Map<String, String>>();
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
				+ " like '%" + contactName + "%'";
		Cursor cursor = context
				.getContentResolver()
				.query(uri,
						new String[] {
								ContactsContract.CommonDataKinds.Phone.NUMBER,
								ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
								ContactsContract.CommonDataKinds.Phone._ID,
								ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
								ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI,
								ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
								ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE },
						selection,
						null,
						ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
								+ " ASC");
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(ID_CONTACT,
					String.valueOf(cursor.getInt(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))));
			map.put(DISPLAY_NAME,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)));
			map.put(PHONE_NUMBER,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
			map.put(PHOTO_THUMBNAIL_URI,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)));
			map.put(PHOTO_URI,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)));
			map.put(DISPLAY_NAME_ALTERNATIVE,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE)));
			map.put(DISPLAY_NAME_PRIMARY,
					cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY)));

			if (map != null) {
				arrContacts.add(map);
			}
			cursor.moveToNext();
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return arrContacts;
	}

	/**
	 * 
	 * Tim kiem xem contactName xac dinh truoc da co trong contacts chua
	 * 
	 * @author: Duchha
	 * @param context
	 * @param contactName
	 * @return
	 * @return: int - Tra ve id contact xac dinh boi contactName neu co. - Tra
	 *          ve -1 neu khong tim ra hoac that bai.
	 * @throws:
	 */
	public static int findContactName(Context context, String contactName) {
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(uri, null, null, null,
					null);
		} catch (Exception e) {
			return -1;
		}
		try {
			if (cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					int id = cursor
							.getInt(cursor
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
					String name = cursor
							.getString(cursor
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
					if (name.equals(contactName)) {
						cursor.close();
						return id;
					}
				}
				cursor.close();
			}
			return -1;
		} catch (Exception ex) {
			return -1;
		}
	}

	/**
	 * 
	 * Them contact (ten va so dien thoai) vao contacts
	 * 
	 * @author: Duchha
	 * @param context
	 * @param name
	 * @param phoneNumber
	 * @return: void
	 * @throws:
	 */
	@SuppressWarnings("unused")
	public static void insertToContacts(Context context, String name,
			String phoneNumber) {
		if (!isPhoneNumberExistInContacts(context, phoneNumber)) {
			ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
			int rawContactInsertIndex = ops.size();

			ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
					.withValue(RawContacts.ACCOUNT_TYPE, null)
					.withValue(RawContacts.ACCOUNT_NAME, null).build());
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID,
							rawContactInsertIndex)
					.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
					.withValue(Phone.NUMBER, phoneNumber).build());
			ops.add(ContentProviderOperation
					.newInsert(Data.CONTENT_URI)
					.withValueBackReference(Data.RAW_CONTACT_ID,
							rawContactInsertIndex)
					.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
					.withValue(StructuredName.DISPLAY_NAME, name).build());

			try {
				ContentProviderResult[] res = context.getContentResolver()
						.applyBatch(ContactsContract.AUTHORITY, ops);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * Xoa contact trong contacts xac dinh boi name
	 * 
	 * @author: Duchha
	 * @param context
	 * @param name
	 * @return: void
	 * @throws:
	 */
	public static void deleteFromContacts(Context context, String name) {
		Uri contactUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(name));
		Cursor cur = context.getContentResolver().query(contactUri, null, null,
				null, null);
		try {
			if (cur.moveToFirst()) {
				do {
					String lookupKey = cur
							.getString(cur
									.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
					Uri uri = Uri.withAppendedPath(
							ContactsContract.Contacts.CONTENT_LOOKUP_URI,
							lookupKey);
					context.getContentResolver().delete(uri, null, null);
				} while (cur.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Kiem tra xem so dien thoai phoneNumber da co trong contact hay chua
	 * 
	 * @author: Duchha
	 * @param context
	 * @param phoneNumber
	 * @return
	 * @return: boolean
	 * @throws:
	 */
	public static boolean isPhoneNumberExistInContacts(Context context,
			String phoneNumber) {
		Cursor cur = null;
		ContentResolver cr = null;
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		try {
			cr = context.getContentResolver();
		} catch (Exception e) {
		}
		try {
			cur = cr.query(uri, null, null, null, null);
		} catch (Exception e) {
		}
		try {
			if (cur.getCount() > 0) {
				while (cur.moveToNext()) {
					String id = cur.getString(cur
							.getColumnIndex(ContactsContract.Contacts._ID));
					if (Integer
							.parseInt(cur.getString(cur
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER))) > 0) {
						Cursor phones = context
								.getContentResolver()
								.query(uri,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ " = " + id, null, null);
						while (phones.moveToNext()) {
							String phoneNumberX = phones
									.getString(phones
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							phoneNumberX = phoneNumberX.replace(" ", "");
							phoneNumberX = phoneNumberX.replace("(", "");
							phoneNumberX = phoneNumberX.replace(")", "");
							if (phoneNumberX.contains(phoneNumber)) {
								phones.close();
								cur.close();
								return true;
							}
						}
						phones.close();
					}
				}
			}
		} catch (Exception e) {

		}
		cur.close();
		return false;
	}
}
