package com.xiaoming.cweibo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.adapters.FansAdapter;
import com.xiaoming.cweibo.entities.UserEntity;
import com.xiaoming.cweibo.presenter.FansPresenter;
import com.xiaoming.cweibo.presenter.FansPresenterImp;
import com.xiaoming.cweibo.utils.DividerItemDecoration;
import com.xiaoming.cweibo.views.MVP_view.FansView;
import com.xiaoming.cweibo.views.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/24 18:27
 */

public class FansActivity extends BaseActivity implements FansView{

    private PullToRefreshRecyclerView mRecyclerView;
    private RecyclerView.ItemDecoration  mItemDecoration;
    private LinearLayoutManager mLayoutManager;
    private FansPresenter mPresenter;
    private List<UserEntity> mUserEntityList;
    private FansAdapter mFansAdapter;
    private String action;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FansPresenterImp(this);
        action = getIntent().getAction();
        switch (action){
            case "tvAttentions":
                getToolbar().setTitle(R.string.lbl_my_attention);
                mPresenter.requestMyAttation();
                break;
            case "tvFans":
                getToolbar().setTitle(R.string.lbl_my_fans);
                mPresenter.requestMyFans();
                break;
        }
        mUserEntityList = new ArrayList<>();
        init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fans;
    }

    private void init() {
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        mItemDecoration = new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mFansAdapter = new FansAdapter(mUserEntityList);
        mRecyclerView.setAdapter(mFansAdapter);
        mRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mPresenter.loadData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mPresenter.loadMore(false);
            }
        });
        mPresenter.loadData(true);
    }

    @Override
    public void onRefreshComplete(List<UserEntity> list, boolean loadMore) {
        mRecyclerView.onRefreshComplete();
        if(list!=null&&list.size()>0){
            if(!loadMore){
                mUserEntityList.clear();
            }
            mUserEntityList.addAll(list);
            mFansAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {
        super.onError(error);
        mRecyclerView.onRefreshComplete();
    }
}
