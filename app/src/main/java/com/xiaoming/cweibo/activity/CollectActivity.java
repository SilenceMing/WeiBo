package com.xiaoming.cweibo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.adapters.CollectAdapter;
import com.xiaoming.cweibo.entities.FavoritesEntity;
import com.xiaoming.cweibo.presenter.BasePresenter;
import com.xiaoming.cweibo.presenter.CollectPresenter;
import com.xiaoming.cweibo.utils.DividerItemDecoration;
import com.xiaoming.cweibo.views.MVP_view.CollectView;
import com.xiaoming.cweibo.views.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2411:37
 */

public class CollectActivity extends BaseActivity implements CollectView{

    private List<FavoritesEntity> mFavoritesEntityList;
    private LinearLayoutManager mLayoutManager;
    private PullToRefreshRecyclerView mRecyclerView;
    private CollectAdapter mCollectAdapter;
    private RecyclerView.ItemDecoration mItemDecoration;
    private BasePresenter mBasePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.lbl_like);
        mFavoritesEntityList = new ArrayList<>();
        init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_liked;
    }

    private void init() {
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mItemDecoration = new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mCollectAdapter = new CollectAdapter(mFavoritesEntityList,getActivity());
        mRecyclerView.setAdapter(mCollectAdapter);
        mRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadMore(false);
            }
        });
        mBasePresenter = new CollectPresenter(this);
        mBasePresenter.loadData(true);
    }

    @Override
    public void onRefreshComplete(List<FavoritesEntity> list, boolean loadMore) {
        mRecyclerView.onRefreshComplete();
        if(list!=null&list.size()>0){
            if(!loadMore){
                mFavoritesEntityList.clear();
            }
            mFavoritesEntityList.addAll(list);
            mCollectAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {
        super.onError(error);
        mRecyclerView.onRefreshComplete();
    }
}
