package com.mohammadag.smoothsystemprogressbars;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import de.robv.android.xposed.XSharedPreferences;

public class SettingsHelper {
	private static final String KEY_SECTION_COUNT = "progress_bar_section_count";
	private static final String KEY_SEPARATOR_LENGTH = "progress_bar_separator_length";
	private static final String KEY_STROKE_WIDTH = "progress_bar_stroke_width";
	private static final String KEY_SPEED = "progress_bar_speed";
	private static final String KEY_COLOR = "progress_bar_color_hex";
	private static final String KEY_COLORS = "progress_bar_colors";
	private static final String KEY_INTERPOLATOR = "progress_bar_interpolator";
	private static final String KEY_MIRRORED = "progress_bar_mirrored";
	private static final String KEY_REVERSED = "progress_bar_reversed";

	public static final int INTERPOLATOR_ACCELERATE = 0;
	public static final int INTERPOLATOR_LINEAR = 1;
	public static final int INTERPOLATOR_ACCELERATEDECELERATE = 2;
	public static final int INTERPOLATOR_DECELERATE = 3;

	private XSharedPreferences mXPreferences = null;
	private SharedPreferences mPreferences = null;

	public SettingsHelper() {
		mXPreferences = new XSharedPreferences("com.mohammadag.smoothsystemprogressbars");
		mXPreferences.makeWorldReadable();

		reloadSettings();
	}

	public SettingsHelper(Context context) {
		mPreferences = getWritablePreferences(context);
	}

	public void reloadSettings() {
		mXPreferences.reload();
	}

	public int getSectionsCount() {
		return getInt(KEY_SECTION_COUNT, 4);
	}

	public SettingsHelper setSectionCount(int value) {
		mPreferences.edit().putInt(KEY_SECTION_COUNT, value).commit();
		return this;
	}

	public int getProgressSeparatorLength() {
		return getInt(KEY_SEPARATOR_LENGTH, 8);
	}

	public SettingsHelper setProgressSeparatorLength(int value) {
		mPreferences.edit().putInt(KEY_SEPARATOR_LENGTH, value).commit();
		return this;
	}

	public Interpolator getProgressBarInterpolator() {
		return getInterpolatorFromValue(getInt(KEY_INTERPOLATOR, INTERPOLATOR_ACCELERATE));
	}

	public SettingsHelper setProgressBarInterpolator(Interpolator interpolator) {
		mPreferences.edit().putInt(KEY_INTERPOLATOR, getValueForInterpolator(interpolator)).commit();
		return this;
	}

	public float getStrokeWidth() {
		return getFloat(KEY_STROKE_WIDTH, 4.0f);
	}

	public float getStrokeWidth(float scale) {
		return getStrokeWidth() * scale + 0.5f;
	}

	public SettingsHelper setStrokeWidth(float value) {
		mPreferences.edit().putFloat(KEY_STROKE_WIDTH, value).commit();
		return this;
	}

	public SettingsHelper setStrokeWidth(float value, float scale) {
		mPreferences.edit().putFloat(KEY_STROKE_WIDTH, (value - 0.5f) / scale).commit();
		return this;
	}

	public int getProgressBarColor() {
		return Color.parseColor("#" + getString(KEY_COLOR, "33b5e5"));
	}

	public SettingsHelper setProgressBarColor(int color) {
		mPreferences.edit().putString(KEY_COLOR, convertToARGB(color)).commit();
		return this;
	}

	public float getSpeed() {
		return getFloat(KEY_SPEED, 1.0f);
	}

	public SettingsHelper setSpeed(float value) {
		mPreferences.edit().putFloat(KEY_SPEED, value);
		return this;
	}

	public boolean getMirrored() {
		return getBoolean(KEY_MIRRORED, false);
	}

	public SettingsHelper setMirrored(boolean value) {
		mPreferences.edit().putBoolean(KEY_MIRRORED, value).commit();
		return this;
	}

	public boolean getReversed() {
		return getBoolean(KEY_REVERSED, false);
	}

	public SettingsHelper setReversed(boolean value) {
		mPreferences.edit().putBoolean(KEY_REVERSED, value).commit();
		return this;
	}

	@SuppressLint("WorldReadableFiles")
	@SuppressWarnings("deprecation")
	public static SharedPreferences getWritablePreferences(Context context) {
		return context.getSharedPreferences("com.mohammadag.smoothsystemprogressbars_preferences", Context.MODE_WORLD_READABLE);
	}

	private String getString(String key, String defaultValue) {
		String returnResult = defaultValue;
		if (mPreferences != null) {
			returnResult = mPreferences.getString(key, defaultValue);
		} else if (mXPreferences != null) {
			returnResult = mXPreferences.getString(key, defaultValue);
		}
		return returnResult;
	}

	public float getFloat(String key, float defaultValue) {
		float returnResult = defaultValue;
		if (mPreferences != null) {
			returnResult = mPreferences.getFloat(key, defaultValue);
		} else if (mXPreferences != null) {
			returnResult = mXPreferences.getFloat(key, defaultValue);
		}
		return returnResult;
	}

	public int getInt(String key, int defaultValue) {
		int returnResult = defaultValue;
		if (mPreferences != null) {
			returnResult = mPreferences.getInt(key, defaultValue);
		} else if (mXPreferences != null) {
			returnResult = mXPreferences.getInt(key, defaultValue);
		}
		return returnResult;
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		boolean returnResult = defaultValue;
		if (mPreferences != null) {
			returnResult = mPreferences.getBoolean(key, defaultValue);
		} else if (mXPreferences != null) {
			returnResult = mXPreferences.getBoolean(key, defaultValue);
		}
		return returnResult;
	}

	public Set<String> getStringSet(String key, Set<String> defaultValue) {
		Set<String> returnResult = defaultValue;
		if (mPreferences != null) {
			returnResult = mPreferences.getStringSet(key, defaultValue);
		} else if (mXPreferences != null) {
			returnResult = mXPreferences.getStringSet(key, defaultValue);
		}
		return returnResult;
	}

	public static Interpolator getInterpolatorFromValue(int iInterpolator) {
		Interpolator interpolator;
		switch (iInterpolator) {
		case INTERPOLATOR_ACCELERATEDECELERATE:
			interpolator = new AccelerateDecelerateInterpolator();
			break;
		case INTERPOLATOR_DECELERATE:
			interpolator = new DecelerateInterpolator();
			break;
		case INTERPOLATOR_LINEAR:
			interpolator = new LinearInterpolator();
			break;
		case INTERPOLATOR_ACCELERATE:
		default:
			interpolator = new AccelerateInterpolator();
		}

		return interpolator;
	}

	public static int getValueForInterpolator(Interpolator interpolator) {
		if (interpolator instanceof AccelerateDecelerateInterpolator)
			return INTERPOLATOR_ACCELERATEDECELERATE;
		else if (interpolator instanceof DecelerateInterpolator)
			return INTERPOLATOR_DECELERATE;
		else if (interpolator instanceof LinearInterpolator)
			return INTERPOLATOR_LINEAR;
		else if (interpolator instanceof AccelerateInterpolator)
			return INTERPOLATOR_ACCELERATE;

		return INTERPOLATOR_ACCELERATE;
	}

	public static String convertToARGB(int color) {
		String alpha = Integer.toHexString(Color.alpha(color));
		String red = Integer.toHexString(Color.red(color));
		String green = Integer.toHexString(Color.green(color));
		String blue = Integer.toHexString(Color.blue(color));

		if (alpha.length() == 1) {
			alpha = "0" + alpha;
		}

		if (red.length() == 1) {
			red = "0" + red;
		}

		if (green.length() == 1) {
			green = "0" + green;
		}

		if (blue.length() == 1) {
			blue = "0" + blue;
		}

		return alpha + red + green + blue;
	}

	public int[] getProgressBarColors() {
		Set<String> defaultColors = new HashSet<String>();
		defaultColors.add("33b5e5");
		Set<String> colorStrings = getStringSet(KEY_COLORS, defaultColors);

		ArrayList<Integer> colorsList = new ArrayList<Integer>();

		for (String colorString : colorStrings) {
			colorsList.add(Color.parseColor("#" + colorString));
		}

		if (colorsList.size() == 0) {
			colorsList.add(Color.parseColor("#33b5e5"));
		}

		return convertIntegers(colorsList);
	}

	public SettingsHelper setProgressBarColors(int[] colors) {
		HashSet<String> colorStrings = new HashSet<String>();

		for (int i : colors) {
			colorStrings.add(convertToARGB(i));
		}

		mPreferences.edit().putStringSet(KEY_COLORS, colorStrings).commit();
		return this;
	}

	public static int[] convertIntegers(List<Integer> integers) {
		int[] ret = new int[integers.size()];
		Iterator<Integer> iterator = integers.iterator();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = iterator.next().intValue();
		}
		return ret;
	}
}
