# Android搜索结果显示高亮(有数据滑动底部自动刷新)

标签（空格分隔）： 开源项目

---
###首先的效果图
搜索到结果(这里我只是模拟数据,真正和服务器走得时候,返回来的数据都应该包含关键字的)<br>
![](http://ww1.sinaimg.cn/mw690/006jcGvzly1fe5zyz2w3mj30u01hcwi5.jpg)
<br>
<br>
模拟的没有搜索结果的界面<br>
![](http://ww1.sinaimg.cn/mw690/006jcGvzly1fe5zzd39gxj30u01hct9x.jpg)

###具体实现
>在这插一句哈,就是做一件事情,拆分成多个小结,不至于在开发的时候摸不着头脑而且还能把控开发的进度.

思路其实很简单,我们监听输入框的变化,然后在文字变化之后去请求服务器,然后取到我们需要的结果,进行数据展示即可.

* 第一步：搜索框的监听
```
 et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            /**
             * 当搜索框中的文字发生变化的时候回调此方法
             * @param charSequence  输入框的文字
             * @param start  开始
             * @param before  
             * @param count 字数
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
              //在这里进行逻辑请求
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
```
* 第二步：进行相关逻辑请求
```
 if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 3) {  //这里的3只是为了模拟请求
                    mKey = charSequence.toString();
                    initData(charSequence.toString());
                    changeStates(STATE);
                } else {
                    STATE = NO_TTHING;
                    changeStates(STATE);
                }
                
                
/**
     * 首次获取数据
     *
     * @param key 高亮值
     */
    private void initData(String key) {

        //这里是模拟网络请求的  实际就是走网络获取数据
        String result = JsonUtils.getJson(this, "search.json");
        Gson gson = new Gson();
        SearchBean searchBean = gson.fromJson(result, SearchBean.class);
        if (searchBean != null) {
            mDataBeen = searchBean.getData();
            if (mDataBeen != null && mDataBeen.size() > 0) {
                STATE = SHOW_DATA;
                mSearchAdapter.loadData(mDataBeen, key);
            } else {
                STATE = NO_TTHING;
            }
        } else {
            STATE = NO_TTHING;
        }
    }

 /**
     * 改变搜索状态
     *
     * @param state 搜索key值
     */
    private void changeStates(int state) {
        switch (state) {
            case NO_TTHING:
                mNoLayout.setVisibility(View.VISIBLE);
                recycler_view.setVisibility(View.INVISIBLE);
                break;

            case SHOW_DATA:
                mNoLayout.setVisibility(View.GONE);
                recycler_view.setVisibility(View.VISIBLE);
                break;
        }
    }

```
* 第三步：进行变色
```
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
            //开始循环查找里面是否包含关键字  使得一句话中出现多个关键词都会被高亮
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
    
   // 进行工具类使用,也就是在给title赋值的时候使用
    
    //这个是adapter里面的使用规则
        mFormatUtil = new StringFormatUtil(holder.itemView.getContext(), dataBean.getTitle(), mLightStr, R.color.colorAccent).fillColor();

        holder.tv_title.setText(mFormatUtil.getResult());
    
```
###Demo说明
这里的本地的json是我自己人为定义的,而且在搜索的时候加入了自己的逻辑,如果是实际工程中需要自己根据自己的需求来进行变更的.相关显示不需要在意,这里只是给大家一个实现搜索的并且关键词高亮的一个思路。

###Demo代码传送门

* https://github.com/wuyinlei/SearchViewDemo

###参考文章

* [android TextView 实现关键字高亮][1]


  [1]: http://www.blogjava.net/Green-nut/articles/348167.html
