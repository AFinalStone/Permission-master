package com.shi.androidstudio.permission;


import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ToastUtil
 * 
 * @author SHI
 */
public class ToastUtil {

    private ToastUtil() {
        throw new AssertionError();
    }
    
	/**
	 * 不论是在主线程还是子线程，都能弹出Toast
	 * @param act  当前Activity
	 * @param msg  弹出来的信息
	 */
	public static void showToast(final Activity act, final String msg) {
		// 如果是主线程，直接弹出toast
		if ("main".equals(Thread.currentThread().getName())) {
			Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
		} else {
			// 如果不是，
			act.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
	
    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }
}
