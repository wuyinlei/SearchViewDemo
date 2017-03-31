package com.mingchu.searchviewdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mingchu.searchviewdemo.R;
import com.mingchu.searchviewdemo.bean.SearchBean.DataBean;
import com.mingchu.searchviewdemo.utils.StringFormatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyinlei on 2017/3/31.
 *
 * @function 搜索adapter
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {


    private List<DataBean> mDataBeen = new ArrayList<>();

    private StringFormatUtil mFormatUtil;

    private String mLightStr;

    /**
     * 添加数据
     *
     * @param dataBeen 集合
     * @param key      搜索key值
     */
    public void loadData(List<DataBean> dataBeen, String key) {
        mDataBeen = dataBeen;
        mLightStr = key;
        notifyDataSetChanged();
    }

    /**
     * 上拉加载更多数据
     *
     * @param dataBeen 集合
     * @param key      搜索key值
     */
    public void loadMoreData(List<DataBean> dataBeen, String key) {
        mDataBeen.addAll(dataBeen);
        mLightStr = key;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataBean dataBean = mDataBeen.get(position);

        mFormatUtil = new StringFormatUtil(holder.itemView.getContext(), dataBean.getTitle(), mLightStr, R.color.colorAccent).fillColor();

        holder.tv_title.setText(mFormatUtil.getResult());
        holder.tv_answer_num.setText(dataBean.getAnswer_num() + "人回答");
        holder.tv_attention_num.setText(dataBean.getAttention_num() + "人关注");

    }

    @Override
    public int getItemCount() {
        return mDataBeen == null ? 0 : mDataBeen.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title, tv_attention_num, tv_answer_num;

        ViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_attention_num = (TextView) itemView.findViewById(R.id.tv_attention_num);
            tv_answer_num = (TextView) itemView.findViewById(R.id.tv_answer_num);
        }
    }
}
