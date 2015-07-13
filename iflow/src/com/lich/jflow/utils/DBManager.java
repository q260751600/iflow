package com.lich.jflow.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lich.jflow.bean.ChatMessage;
import com.lich.jflow.bean.ChatMessage.Type;

public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}

	/**
	 * ���
	 * 
	 * @param mMsgs
	 */
	public void add(List<ChatMessage> mMsgs) {
		db.beginTransaction();
		try {
			for (ChatMessage msg : mMsgs) {
				db.execSQL("insert into Msgs values(null,?,?,?,?)",
						new Object[] { msg.getName(), msg.getMsg(),
								msg.getType().toString(), msg.getTime() });
			}
			db.setTransactionSuccessful();// �����������
		} catch (Exception e) {
		} finally {
			db.endTransaction();// ��������
		}
	}

	/**
	 * ����һ������
	 */
	public void addOne(ChatMessage msg) {
		db.beginTransaction();

		db.execSQL("insert into Msgs values(null,?,?,?,?)",
				new Object[] { msg.getName(), msg.getMsg(),
						msg.getType().toString(), msg.getTime() });
		db.setTransactionSuccessful();// �����������
		db.endTransaction();
	}

	/**
	 * ��ѯ����
	 */
	public List<ChatMessage> query() {
		List<ChatMessage> Msgs = new ArrayList<ChatMessage>();
		Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			ChatMessage msg = new ChatMessage();
			msg.setName(c.getString(c.getColumnIndex("name")));
			msg.setMsg(c.getString(c.getColumnIndex("msg")));
			String typeStr = c.getString(c.getColumnIndex("type"));
			Type type = Type.valueOf(Type.class, typeStr);
			msg.setType(type);
			msg.setTime(c.getString(c.getColumnIndex("time")));
			Msgs.add(msg);
		}
		return Msgs;
	}
	/**
	 * �������
	 */
	public void deleteAll() {
		db.beginTransaction();
		db.delete("Msgs", null, null);
		db.setTransactionSuccessful();// �����������
		db.endTransaction();
	}

	/**
	 * ����α�
	 */
	public Cursor queryTheCursor() {
		Cursor c = db.rawQuery("SELECT * FROM Msgs", null);
		return c;
	}

	/**
	 * �ر����ݿ�
	 */
	public void closeDB() {
		db.close();
	}
}
