package com.mohammadag.smoothsystemprogressbars;

import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class SmoothSystemPBar implements IXposedHookZygoteInit {
	private SettingsHelper mSettingsHelper;

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		mSettingsHelper = new SettingsHelper();

		XposedHelpers.findAndHookMethod(ProgressBar.class, "setIndeterminateDrawable", Drawable.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Drawable b = (Drawable) param.args[0];
				if (b == null)
					return;

				ProgressBar progressBar = (ProgressBar) param.thisObject;
				if (progressBar == null)
					return;

				if (progressBar.getIndeterminateDrawable() instanceof SmoothProgressDrawable)
					return;

				mSettingsHelper.reloadSettings();

				final float scale = progressBar.getResources().getDisplayMetrics().density;
				if ("android.graphics.drawable.AnimationDrawable".equals(b.getClass().getName())) {
					Drawable drawable = new SmoothProgressDrawable.Builder(progressBar.getContext())
					.interpolator(mSettingsHelper.getProgressBarInterpolator())
					.sectionsCount(mSettingsHelper.getSectionsCount())
					.separatorLength(mSettingsHelper.getProgressSeparatorLength())
					.strokeWidth(mSettingsHelper.getStrokeWidth(scale))
					.speed(mSettingsHelper.getSpeed())
					.mirrorMode(mSettingsHelper.getMirrored())
					.reversed(mSettingsHelper.getReversed())
					.colors(mSettingsHelper.getProgressBarColors())
					.build();
					progressBar.setIndeterminateDrawable(drawable);

					param.args[0] = drawable;
				}
			}
		});
	}

}
