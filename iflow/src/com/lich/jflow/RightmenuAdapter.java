package com.lich.jflow;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RightmenuAdapter extends BaseAdapter {

	private List<String> mlist;
	private LayoutInflater mInflater;

	public RightmenuAdapter(Context context, List<String> mlist) {
		this.mlist = mlist;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.rightmene_item, parent,
					false);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.id_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		System.out.println(mlist.get(position));
		holder.tv.setText(mlist.get(position));
		return convertView;
	}

	private final class ViewHolder {
		TextView tv;
	}
}
