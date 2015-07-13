package com.lich.jflow.test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.lich.jflow.bean.ChatMessage;
import com.lich.jflow.bean.ChatMessage.Type;
import com.lich.jflow.utils.DBManager;
import com.lich.jflow.utils.HttpUtils;

public class TestDBManager extends AndroidTestCase {
	public static final String TAG = "IFLOW";
	private DBManager dbManager;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dbManager = new DBManager(getContext());
	}
	public void testdeleteAll(){
		List<ChatMessage> Msgs = new ArrayList<ChatMessage>();
		dbManager.deleteAll();
		Msgs = dbManager.query();
		Log.e(TAG,Msgs.size()+"");
	}
	public void testaddOne(){
		Log.e(TAG, "go");
		List<ChatMessage> Msgs = new ArrayList<ChatMessage>();
		
		ChatMessage msg = new ChatMessage("你好，小花为你服务。", Type.INCOMING,
				HttpUtils.getTime(new Date()));
		ChatMessage msg1 = new ChatMessage("魔兽世界", Type.INCOMING,
				HttpUtils.getTime(new Date()));
		ChatMessage msg2 = new ChatMessage("瓦利拉", Type.OUTCOMING,
				HttpUtils.getTime(new Date()));
		ChatMessage msg3 = new ChatMessage("小德", Type.INCOMING,
				HttpUtils.getTime(new Date()));
		dbManager.addOne(msg);
		dbManager.addOne(msg1);
		dbManager.addOne(msg2);
		dbManager.addOne(msg3);
		Msgs = dbManager.query();
		Log.e(TAG,Msgs.size()+"");
		for(ChatMessage m : Msgs){
			Log.e(TAG,m.getMsg());
		}
	}
}
