package com.njp.android.coolweather.utils;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 将assets下的数据库文件拷贝到databases目录下
 */

public class CopyFileUtil {

    public static void copy(Context context, String fileName) {

        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        String path = "/data/data/" + context.getPackageName() + "/databases/";
        File file = new File(path + fileName);
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        if (file.exists()) {
            return;
        }

        try {
            in = new BufferedInputStream(context.getAssets().open(fileName));
            out = new BufferedOutputStream(new FileOutputStream(file));
            int length = -1;
            byte[] bytes = new byte[1024];
            while ((length = in.read(bytes)) != -1) {
                out.write(bytes, 0, length);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
