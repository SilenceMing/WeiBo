package com.xiaoming.cweibo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.adapters.ArticleCommentAdapter;
import com.xiaoming.cweibo.entities.CommentEntity;
import com.xiaoming.cweibo.presenter.ArticleCommentPresenter;
import com.xiaoming.cweibo.presenter.ArticleCommentPresenterImp;
import com.xiaoming.cweibo.views.MVP_view.ArticleCommentView;
import com.xiaoming.cweibo.views.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slience
 * @des 微博评论
 * @time 2017/5/2219:14
 */

public class ArticleCommentActivity extends BaseActivity implements ArticleCommentView {

    private List<CommentEntity> mCommentEntities = new ArrayList<>();
    private PullToRefreshRecyclerView mRlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArticleCommentAdapter mArticleCommentAdapter;
    private ArticleCommentPresenter mArticleCommentPresenter;
    private RecyclerView.ItemDecoration mItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle(R.string.title_weibo_detail);
        mArticleCommentPresenter = new ArticleCommentPresenterImp(this);
        mArticleCommentPresenter.loadData(true);
        init();
    }

    private void init() {
        mRlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        mLayoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(mLayoutManager);
        //自定义recyclerView的分割线
        mItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mRlv.addItemDecoration(mItemDecoration);
        mArticleCommentAdapter = new ArticleCommentAdapter(this,mArticleCommentPresenter.getStatusEntity(),mCommentEntities);
        mRlv.setAdapter(mArticleCommentAdapter);
        mRlv.setMode(PullToRefreshBase.Mode.BOTH);
        mRlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mArticleCommentPresenter.loadData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mArticleCommentPresenter.loadMore(false);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_articlecomment;
    }

    @Override
    public void onRefreshComplete(List<CommentEntity> list,boolean isLoadMore) {
        mRlv.onRefreshComplete();
        if(list!=null&&list.size()>0){
            if(!isLoadMore){
                mCommentEntities.clear();
            }
            mCommentEntities.addAll(list);
            mArticleCommentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {
        super.onError(error);
        mRlv.onRefreshComplete();
    }
}
