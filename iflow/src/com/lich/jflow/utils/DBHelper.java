package com.lich.jflow.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ChatMsg.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		// 参数定义依次为：上下文环境、数据库名称、游标工厂(为null)、数据库版本
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 数据库第一次被创建时onCreate会被调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists Msgs"+
	"(_id integer primary key autoincrement,name varchar,msg varchar,type varchar,time varchar)");
	}

	// 如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("alter table Msgs add column other string");
	}
}
