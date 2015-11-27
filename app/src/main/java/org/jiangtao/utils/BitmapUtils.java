package org.jiangtao.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by mr-jiang on 15-11-28.
 * 有关用户头像的一些操作。
 */
public class BitmapUtils {
    /**
     * 根据路径生成bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmap(String path) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        Bitmap bitmap;
        if (fis == null) {
            return null;
        } else {

        }
        bitmap = BitmapFactory.decodeStream(fis);
        return bitmap;
    }

    /**
     * 保存bitmap到固定位置
     *
     * @param bitmap
     * @return
     */
    public static String setBitmap(Bitmap bitmap) {
        String path = null;
        return path;
    }
}
