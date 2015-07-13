package com.lich.jflow.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.provider.ContactsContract.Contacts.Data;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lich.jflow.bean.ChatMessage;
import com.lich.jflow.bean.ChatMessage.Type;
import com.lich.jflow.bean.Result;

public class HttpUtils {

	private static final String URL = "http://www.tuling123.com/openapi/api";
	private static final String API_KEY = "c619b044006d27de7a855b8d40c63609";

	/**
	 * 发送一个消息，得到返回的json数据
	 * @param msg
	 * @return 
	 * @throws IOException
	 */
	public static ChatMessage sendMessage(String msg) throws IOException{
		ChatMessage chatMessage = new ChatMessage();
		String jsonRes = doGet(msg);
		Gson gson = new Gson();
		try{
			Result result = gson.fromJson(jsonRes, Result.class);
			chatMessage.setMsg(result.getText());
		}catch(JsonSyntaxException e){
			chatMessage.setMsg("服务器繁忙，请稍后再试");
		}
		chatMessage.setTime(getTime(new Date()));
		chatMessage.setType(Type.INCOMING);
		return chatMessage;
	}

	public static String doGet(String msg) throws IOException {
		String result = "";
		String url = setParams(msg);
		InputStream inputStream = null;
		URL getUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
		conn.setConnectTimeout(10 * 1000);
		conn.setReadTimeout(10 * 1000);
		conn.setRequestMethod("GET");
		inputStream = conn.getInputStream();
		int len = -1;
		byte[] buf = new byte[128];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		while ((len = inputStream.read(buf)) != -1) {
			baos.write(buf, 0, len);
		}
		baos.flush();
		result = new String(baos.toByteArray());
		if (inputStream != null)
			inputStream.close();
		if (baos != null)
			baos.close();
		return result;
	}
	public static String getTime(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(date);
		return time;
	}
	private static String setParams(String msg)
			throws UnsupportedEncodingException {
		// ?key=" + APIKEY + "&info=" + INFO;
		String url = URL + "?key=" + API_KEY + "&info="
				+ URLEncoder.encode(msg, "UTF-8");
		return url;
	}
}
