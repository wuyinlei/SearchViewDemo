package com.mingchu.searchviewdemo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mingchu.searchviewdemo.R;
import com.mingchu.searchviewdemo.adapter.SearchAdapter;
import com.mingchu.searchviewdemo.bean.SearchBean;
import com.mingchu.searchviewdemo.bean.SearchBean.DataBean;
import com.mingchu.searchviewdemo.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyinlei
 * @function 搜索界面
 */
public class SearchActivity extends AppCompatActivity {

    private EditText et_search;
    private RecyclerView recycler_view;
    private TextView tv_cancel;
    private FrameLayout mNoLayout;

    //是否显示搜索结果的状体标志
    private final static int NO_TTHING = 0;
    private final static int SHOW_DATA = 1;
    private static int STATE = 0;  //默认的是没有数据

    private List<DataBean> mDataBeen = new ArrayList<>();
    private SearchAdapter mSearchAdapter;
    private String mKey;  //key值
    private List<DataBean> mLoadMoreData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        mSearchAdapter = new SearchAdapter();

        initRecyclerView();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 3) {  //这里的3只是为了模拟请求

                    mKey = charSequence.toString();
                    initData(charSequence.toString());

                    changeStates(STATE);

                } else {
                    STATE = NO_TTHING;

                    changeStates(STATE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
                mDataBeen.clear();
                mLoadMoreData.clear();
                et_search.setHint("请输入你搜索的关键字");
                mNoLayout.setVisibility(View.INVISIBLE);
                recycler_view.setVisibility(View.INVISIBLE);
            }
        });

        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                    //加载更多
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadMoreData(mKey);
                            Toast.makeText(SearchActivity.this, mKey, Toast.LENGTH_SHORT).show();
                        }
                    }, 500);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    /**
     * 加载更多数据
     * @param key  key值  高亮值
     */
    private void loadMoreData(String key) {

        String result = JsonUtils.getJson(this, "search.json");
        Gson gson = new Gson();
        SearchBean searchBean = gson.fromJson(result, SearchBean.class);
        if (searchBean != null) {
            mLoadMoreData = searchBean.getData();
            if (mLoadMoreData != null && mLoadMoreData.size() > 0) {
                mSearchAdapter.loadMoreData(mDataBeen, key);
            }
        }
    }

    /**
     * 改变搜索装填
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

    /**
     * 首次获取数据
     * @param key  高亮值
     */
    private void initData(String key) {

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

    private void initRecyclerView() {

        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mSearchAdapter);

    }

    private void initView() {
        et_search = (EditText) findViewById(R.id.et_search);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        mNoLayout = (FrameLayout) findViewById(R.id.no_data);

    }
}
