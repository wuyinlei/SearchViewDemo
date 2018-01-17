package com.mingchu.searchviewdemo.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuyinlei on 2018/1/17.
 *
 * @function 第二种上色
 * <p>
 * 比如后台约定好<em>关键字</em>  这样的,关键字被<em></em>包裹起来了
 * 这个时候通过获取到关键字 然后对关键字进行上色
 * <p>
 * 用法和StringFormatUtil基本一直
 * mFormatUtil = new StringFormatUtil(holder.itemView.getContext(), dataBean.getTitle(), R.color.colorAccent).fillColor();
 * <p>
 * holder.tv_title.setText(mFormatUtil.getResult());
 */

public class StringColorUtil {

    private SpannableStringBuilder spBuilder;
    private String wholeStr;
    private Context mContext;
    private int color;

    /**
     * @param context  上下文
     * @param wholeStr 全部文字
     * @param color    颜色
     */
    public StringColorUtil(Context context, String wholeStr, int color) {
        this.mContext = context;
        this.wholeStr = wholeStr;
        this.color = color;
    }

    public static final class Builder {
        private SpannableStringBuilder spBuilder;
        private String wholeStr;
        private List<String> highlightStr;
        private Context mContext;
        private int color;


        public StringColorUtil build() {
            return new StringColorUtil(mContext, wholeStr, color);
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Builder context(Context context) {
            mContext = context;
            return this;
        }

        public Builder wholeStr(String wholeStr) {
            this.wholeStr = wholeStr;
            return this;
        }
    }


    /**
     * 填充颜色
     *
     * @return StringFormatUtil
     */
    public StringColorUtil fillColor() {

        String str = wholeStr;
        //匹配规则
        Pattern p = Pattern.compile("<em[^>]*?>.*?</em>");
        Matcher m = p.matcher(str);

        List<String> highStrLists = new ArrayList<>();

        //先找到<em></em>标签里面的字段
        while (m.find()) {
            String group = m.group();
            String highStr = group.substring(4, group.length() - 5);
            highStrLists.add(highStr);
        }


        //在这里要剔除<em>标签和</em>标签  目前想到的是这样  可能会有更好的方式处理
        //关于这个地方的优化点  也可以后台返回两个字段 一个用于包含<em></em>标签的搜索结果
        //一个则是不包含<em></em>的搜索结果    也就是客户端不需要做下面的两步进行替换了
        str = str.replaceAll("<em>", "");
        wholeStr = str.replaceAll("</em>", "");


        if (!TextUtils.isEmpty(wholeStr) && highStrLists.size() > 0) {

            //转换为小写  也就是忽略大小写
            String text = wholeStr.toLowerCase();

            spBuilder = new SpannableStringBuilder(wholeStr);
            //上色
            color = mContext.getResources().getColor(color);
            int length = highStrLists.size();
            for (int i = 0; i < length; i++) {
                //匹配规则
                Pattern p1 = Pattern.compile(highStrLists.get(i).toLowerCase());
                //匹配字段
                Matcher m1 = p1.matcher(text);
                //开始循环查找里面是否包含关键字
                while (m1.find()) {
                    int start = m1.start();
                    int end = m1.end();
                    spBuilder.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
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
