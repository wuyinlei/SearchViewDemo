package com.mingchu.searchviewdemo.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wuyinlei on 2017/3/31.
 *
 * @function 本地json解析辅助类
 */

public class JsonUtils {

    /**
     * 获取到json文件  本地的
     *
     * @param context  上下文
     * @param fileName 文件名字
     * @return 字符串
     */
    public static String getJson(Context context, String fileName) {
        StringBuilder sb = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String str = "";
            while (null != (str = br.readLine())) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }
}
