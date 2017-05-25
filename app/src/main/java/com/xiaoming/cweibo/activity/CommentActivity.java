package com.xiaoming.cweibo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.adapters.CommentAdapter;
import com.xiaoming.cweibo.entities.CommentEntity;
import com.xiaoming.cweibo.presenter.BasePresenter;
import com.xiaoming.cweibo.presenter.CommentPresenterImp;
import com.xiaoming.cweibo.utils.DividerItemDecoration;
import com.xiaoming.cweibo.views.MVP_view.CommentView;
import com.xiaoming.cweibo.views.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2411:37
 */

public class CommentActivity extends BaseActivity implements CommentView {

    private RecyclerView.LayoutManager mLayoutManager;
    private List<CommentEntity> mCommentEntityList;
    private RecyclerView.ItemDecoration mItemDecoration;
    private BasePresenter mBasePresenter;
    private CommentAdapter mCommentAdapter;
    private PullToRefreshRecyclerView mRefreshListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.lbl_comment);
        mCommentEntityList = new ArrayList<>();
        init();
    }

    private void init() {
        mRefreshListView = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRefreshListView.setLayoutManager(mLayoutManager);
        mItemDecoration = new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL);
        mRefreshListView.addItemDecoration(mItemDecoration);
        mCommentAdapter = new CommentAdapter(getActivity(),mCommentEntityList);
        mRefreshListView.setAdapter(mCommentAdapter);
        mRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadMore(false);
            }
        });
        mBasePresenter = new CommentPresenterImp(this);
        mBasePresenter.loadData(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    public void onRefreshComplete(List<CommentEntity> list, boolean loadMore) {
        mRefreshListView.onRefreshComplete();
        if(list!=null&&list.size()>0){
            if(!loadMore){
                mCommentEntityList.clear();
            }
            mCommentEntityList.addAll(list);
            mCommentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {
        super.onError(error);
        mRefreshListView.onRefreshComplete();
    }
}
