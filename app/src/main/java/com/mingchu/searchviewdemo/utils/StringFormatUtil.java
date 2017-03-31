package com.mingchu.searchviewdemo.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuyinlei
 * @function 字符串变色工具类
 */
public class StringFormatUtil {


    private SpannableStringBuilder spBuilder;
    private String wholeStr, highlightStr;
    private Context mContext;
    private int color;

    /**
     * @param context      上下文
     * @param wholeStr     全部文字
     * @param highlightStr 改变颜色的文字
     * @param color        颜色
     */
    public StringFormatUtil(Context context, String wholeStr, String highlightStr, int color) {
        this.mContext = context;
        this.wholeStr = wholeStr;
        this.highlightStr = highlightStr;
        this.color = color;
    }

    /**
     * 填充颜色
     *
     * @return StringFormatUtil
     */
    public StringFormatUtil fillColor() {


        if (!TextUtils.isEmpty(wholeStr) && !TextUtils.isEmpty(highlightStr)) {

            spBuilder = new SpannableStringBuilder(wholeStr);

            //匹配规则
            Pattern p = Pattern.compile(highlightStr);
            //匹配字段
            Matcher m = p.matcher(spBuilder);
            //上色
            color = mContext.getResources().getColor(color);

            //开始循环查找里面是否包含关键字
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                spBuilder.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            return this;
        }
        return null;
    }


    /**
     * 获取到已经更改好的结果(这个时候已经实现了高亮,在获取这个result的时候不要toString()要不然会把色调去除的)
     *
     * @return result
     */
    public SpannableStringBuilder getResult() {
        if (spBuilder != null) {
            return spBuilder;
        }
        return null;
    }
}
