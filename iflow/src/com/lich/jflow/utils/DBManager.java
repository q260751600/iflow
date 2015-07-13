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
	 * 添加
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
			db.setTransactionSuccessful();// 设置事务完成
		} catch (Exception e) {
		} finally {
			db.endTransaction();// 结束事务
		}
	}

	/**
	 * 插入一条数据
	 */
	public void addOne(ChatMessage msg) {
		db.beginTransaction();

		db.execSQL("insert into Msgs values(null,?,?,?,?)",
				new Object[] { msg.getName(), msg.getMsg(),
						msg.getType().toString(), msg.getTime() });
		db.setTransactionSuccessful();// 设置事务完成
		db.endTransaction();
	}

	/**
	 * 查询所有
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
	 * 清空数据
	 */
	public void deleteAll() {
		db.beginTransaction();
		db.delete("Msgs", null, null);
		db.setTransactionSuccessful();// 设置事务完成
		db.endTransaction();
	}

	/**
	 * 获得游标
	 */
	public Cursor queryTheCursor() {
		Cursor c = db.rawQuery("SELECT * FROM Msgs", null);
		return c;
	}

	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		db.close();
	}
}
