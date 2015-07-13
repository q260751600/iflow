package com.lich.jflow.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ChatMsg.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		// ������������Ϊ�������Ļ��������ݿ����ơ��α깤��(Ϊnull)�����ݿ�汾
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// ���ݿ��һ�α�����ʱonCreate�ᱻ����
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists Msgs"+
	"(_id integer primary key autoincrement,name varchar,msg varchar,type varchar,time varchar)");
	}

	// ���DATABASE_VERSIONֵ����Ϊ2,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("alter table Msgs add column other string");
	}
}
