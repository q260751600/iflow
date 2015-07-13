package com.lich.jflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

import com.lich.jflow.bean.ChatMessage;
import com.lich.jflow.bean.ChatMessage.Type;
import com.lich.jflow.utils.DBManager;
import com.lich.jflow.utils.DialogUtils;
import com.lich.jflow.utils.HttpUtils;

public class MainActivity extends Activity implements OnClickListener {

	private ListView mMsgs;
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mDatas;

	private EditText mInputMsg;
	private Button mSendMsg;
	private ImageButton more;
	private PopupWindow pw;
	private RelativeLayout relayout;
	/** 数据库相关 */
	private DBManager dbManager;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			ChatMessage getMsg = (ChatMessage) msg.obj;
			mDatas.add(getMsg);
			dbManager.addOne(getMsg);
			refresh();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		initVies();
		initDatas();
		initLists();
	}

	private void initLists() {
		mInputMsg.setOnClickListener(this);
		more.setOnClickListener(this);
		mSendMsg.setOnClickListener(this);
		mMsgs.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return false;
			}
		});
	}

	// 刷新数据
	private void refresh() {
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
			mMsgs.setSelection(mAdapter.getCount());
		}
	}

	private void initDatas() {
		// 初始化DBManager
		dbManager = new DBManager(this);
		mDatas = new ArrayList<ChatMessage>();
		mDatas = dbManager.query();
		System.out.println("mDatas-->" + mDatas.size());
		if (mDatas.size() <= 0) {
			ChatMessage msg = new ChatMessage("hello，iflow智能为你服务。",
					Type.INCOMING, HttpUtils.getTime(new Date()));
			mDatas.add(msg);
			dbManager.addOne(msg);
			refresh();
		}
		mAdapter = new ChatMessageAdapter(this, mDatas);
		mMsgs.setAdapter(mAdapter);
		mMsgs.setSelection(mAdapter.getCount());
	}

	private void initVies() {
		mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
		mInputMsg = (EditText) findViewById(R.id.id_edt_msg);
		mSendMsg = (Button) findViewById(R.id.id_send_msg);
		more = (ImageButton) findViewById(R.id.right_btn);
		relayout = (RelativeLayout) findViewById(R.id.id_ly_top);
	}

	@Override
	public void onClick(View v) {
		if (v == mInputMsg) {
			refresh();
		}
		if (v == more) {
			if (pw == null) {
				List<String> menus = new ArrayList<String>();
				menus.add("清空聊天");
				ListView listView = new ListView(MainActivity.this);
				listView.setCacheColorHint(0x00000000);
				RightmenuAdapter adapter = new RightmenuAdapter(
						MainActivity.this, menus);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						pw.dismiss();
						final AlertDialog dialog = DialogUtils.getMyDialog(
								MainActivity.this, "提示", "\n是否清空聊天?");
						dialog.show();
						Button okb = (Button) dialog.findViewById(R.id.yes_btn);
						okb.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								dbManager.deleteAll();
								mDatas.clear();
								mAdapter.notifyDataSetChanged();
							}
						});
					}
				});
				pw = new PopupWindow(listView, relayout.getWidth() / 4,
						LayoutParams.WRAP_CONTENT, true);
				pw.setBackgroundDrawable(new ColorDrawable(0x00000000));
			}
			pw.update();
			pw.showAsDropDown(relayout,
					(relayout.getWidth() - relayout.getWidth() / 4) - 10, 2);
		}
		//发送
		if (v == mSendMsg) {
			final String sendMsg = mInputMsg.getText().toString();
			if (TextUtils.isEmpty(sendMsg)) {
				return;
			}
			ChatMessage msg = new ChatMessage(sendMsg, Type.OUTCOMING,
					HttpUtils.getTime(new Date()));
			mDatas.add(msg);
			mInputMsg.setText("");
			dbManager.addOne(msg);
			refresh();
			new Thread() {
				@Override
				public void run() {
					try {
						ChatMessage fromMsg = HttpUtils.sendMessage(sendMsg);
						Message m = Message.obtain();
						m.obj = fromMsg;
						handler.sendMessage(m);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbManager.closeDB();
	}
}
