package com.njp.android.coolweather.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 显示吐司的工具类
 */

public class ToastUtil {

    private static Toast sToast;

    public static void init(Context context) {
        sToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    public static void show(String content) {
        sToast.setText(content);
        sToast.show();
    }

}
