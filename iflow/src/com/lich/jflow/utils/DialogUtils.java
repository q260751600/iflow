package com.lich.jflow.utils;

import com.lich.jflow.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DialogUtils {
	/**
	 * ��ȡAlertDialog
	 * @param context ������
	 * @param title	dialog����
	 * @param content ����
	 * @return AlertDialog
	 */
	public static AlertDialog getMyDialog(Context context,String title,String content){
		final AlertDialog dlg = new AlertDialog.Builder(context,R.style.DialogTheme).create();
		dlg.show();
		Window window = dlg.getWindow();
		// ����xml
		window.setContentView(R.layout.dialog_style);
		// ��ť
		Button ok_btn = (Button) window.findViewById(R.id.yes_btn);
		Button no_btn = (Button) window.findViewById(R.id.no_btn);
		TextView tv_title = (TextView) window.findViewById(R.id.title_tv);
		TextView tv_context = (TextView) window.findViewById(R.id.context_tv);
		tv_title.setText(title);
		tv_context.setText(content);
		ok_btn.setText("ȷ��");
		no_btn.setText("ȡ��");
		no_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		});
		return dlg;
	}
}
