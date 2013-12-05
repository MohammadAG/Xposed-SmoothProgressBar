package com.mohammadag.smoothsystemprogressbars;

import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateInterpolator;
import android.widget.ProgressBar;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class SmoothSystemPBar implements IXposedHookZygoteInit {

	private static final float PROGRESS_BAR_STROKE_WIDTH = 4.0f;

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		XposedHelpers.findAndHookMethod(ProgressBar.class, "setIndeterminateDrawable", Drawable.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Drawable b = (Drawable) param.args[0];
				ProgressBar progressBar = (ProgressBar) param.thisObject;
				final float scale = progressBar.getResources().getDisplayMetrics().density;

				if (b.getClass().getName().equals("android.graphics.drawable.AnimationDrawable")) {
					Drawable drawable = new SmoothProgressDrawable.Builder(progressBar.getContext())
					.interpolator(new AccelerateInterpolator())
					.sectionsCount(4)
					.separatorLength(8)
					.width((int) (PROGRESS_BAR_STROKE_WIDTH * scale + 0.5f))
					.speed(1.0f)
					.build();
					progressBar.setIndeterminateDrawable(drawable);

					param.args[0] = drawable;
				}
			}
		});
	}

}
