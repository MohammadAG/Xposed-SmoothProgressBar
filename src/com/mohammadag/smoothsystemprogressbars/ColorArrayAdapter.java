package com.mohammadag.smoothsystemprogressbars;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ColorArrayAdapter extends ArrayAdapter<Integer> {

	private SettingsHelper mSettingsHelper;
	private LayoutInflater mInflater;
	public ColorArrayAdapter(Context context, int resource, SettingsHelper helper) {
		super(context, resource);
		mSettingsHelper = helper;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mSettingsHelper.getProgressBarColors().length + 1;
	}

	@Override
	public Integer getItem(int position) {
		try {
			return mSettingsHelper.getProgressBarColors()[position];
		} catch (ArrayIndexOutOfBoundsException e) {
			return Color.parseColor("#33b5e5");
		}
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		if (v == null) {
			v = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
		}

		TextView textView = (TextView) v.findViewById(android.R.id.text1);
		
		int color = getItem(position);
		v.setBackgroundColor(color);
		if (position+1 == getCount()) {
			textView.setText(R.string.tap_to_add);
		} else {
			textView.setText(R.string.hold_to_delete);
		}

		return v;
	}
}
