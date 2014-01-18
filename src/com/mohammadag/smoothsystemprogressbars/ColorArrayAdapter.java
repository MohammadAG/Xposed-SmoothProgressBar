package com.mohammadag.smoothsystemprogressbars;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ColorArrayAdapter extends ArrayAdapter<Integer> {

	private SettingsHelper mSettingsHelper;

	public ColorArrayAdapter(Context context, int resource, SettingsHelper helper) {
		super(context, resource);
		mSettingsHelper = helper;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(android.R.layout.simple_list_item_1, null);
		}

		int color = getItem(position);
		v.setBackgroundColor(color);

		return v;
	}
}
