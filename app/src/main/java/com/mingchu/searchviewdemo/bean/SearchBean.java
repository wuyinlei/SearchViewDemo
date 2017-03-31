package com.mingchu.searchviewdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuyinlei on 2017/3/31.
 *
 * @function search bean
 */

public class SearchBean implements Serializable {


    /**
     * code : 1
     * message : success
     * data : [{"title":"春风十里,不如吃你","attention_num":"100","answer_num":"22"},{"title":"你可以知道我爱你啊","attention_num":"100","answer_num":"22"},{"title":"傻瓜,有我在你不用害怕","attention_num":"1032","answer_num":"342"},{"title":"春风十里,不如吃你","attention_num":"100","answer_num":"22"},{"title":"你可以知道我爱你啊","attention_num":"100","answer_num":"22"},{"title":"傻瓜,有我在你不用害怕","attention_num":"1032","answer_num":"342"},{"title":"宝宝，我爱你啊","attention_num":"100","answer_num":"22"},{"title":"你可以知道我爱你啊","attention_num":"100","answer_num":"22"},{"title":"傻瓜,有我在你不用害怕","attention_num":"1032","answer_num":"342"},{"title":"真的爱你，你可知道，哎哈哈哈哈哈","attention_num":"100","answer_num":"2233"},{"title":"你可以知道我爱你啊","attention_num":"100","answer_num":"222"},{"title":"傻瓜,有我在你不用害怕","attention_num":"1032","answer_num":"3462"},{"title":"春风十里,不如吃你","attention_num":"100","answer_num":"282"},{"title":"你可以知道我爱你啊","attention_num":"100","answer_num":"229"},{"title":"傻瓜,有我在你不用害怕","attention_num":"132032","answer_num":"342"}]
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 春风十里,不如吃你
         * attention_num : 100
         * answer_num : 22
         */

        private String title;
        private String attention_num;
        private String answer_num;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAttention_num() {
            return attention_num;
        }

        public void setAttention_num(String attention_num) {
            this.attention_num = attention_num;
        }

        public String getAnswer_num() {
            return answer_num;
        }

        public void setAnswer_num(String answer_num) {
            this.answer_num = answer_num;
        }
    }
}
