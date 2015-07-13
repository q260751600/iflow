package com.lich.jflow.test;

import java.io.IOException;

import com.lich.jflow.utils.HttpUtils;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestHttpUtils extends AndroidTestCase {
	public static final String TAG = "IFLOW";
	public void testSerndInfo(){
		String result = null;
		try {
			result = HttpUtils.doGet("讲个笑话");
			Log.e(TAG, result);
			result = HttpUtils.doGet("讲个鬼故事");
			Log.e(TAG, result);
			result = HttpUtils.doGet("你好");
			Log.e(TAG, result);
			result = HttpUtils.doGet("漂亮");
			Log.e(TAG, result);
			result = HttpUtils.doGet("你叫什么名字？");
			Log.e(TAG, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
