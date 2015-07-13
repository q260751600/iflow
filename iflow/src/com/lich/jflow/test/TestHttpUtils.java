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
			result = HttpUtils.doGet("����Ц��");
			Log.e(TAG, result);
			result = HttpUtils.doGet("���������");
			Log.e(TAG, result);
			result = HttpUtils.doGet("���");
			Log.e(TAG, result);
			result = HttpUtils.doGet("Ư��");
			Log.e(TAG, result);
			result = HttpUtils.doGet("���ʲô���֣�");
			Log.e(TAG, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
