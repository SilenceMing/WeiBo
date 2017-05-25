package com.xiaoming.cweibo.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.xiaoming.cweibo.Global.Constant;
import com.xiaoming.cweibo.Global.ParameterKey;
import com.xiaoming.cweibo.entities.CommentEntity;
import com.xiaoming.cweibo.entities.HttpResponse;
import com.xiaoming.cweibo.entities.StatusEntity;
import com.xiaoming.cweibo.networks.BaseNetWork;
import com.xiaoming.cweibo.networks.Urls;
import com.xiaoming.cweibo.views.MVP_view.ArticleCommentView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author slience
 * @des 微博评论实现类
 * @time 2017/5/2312:36
 */

public class ArticleCommentPresenterImp implements ArticleCommentPresenter {

    private int PAGE = 1;
    private ArticleCommentView mArticleCommentView;

    public ArticleCommentPresenterImp(ArticleCommentView articleCommentView) {
        mArticleCommentView = articleCommentView;
    }

    @Override
    public void loadData(boolean showLoading) {
        PAGE = 1;
        LoadCommentData(false,showLoading);
    }

    @Override
    public void loadMore(boolean showLoading) {
        PAGE ++;
        LoadCommentData(true,showLoading);
    }

    private void LoadCommentData(final boolean isLoadMore,final boolean showLoading) {
        new BaseNetWork(mArticleCommentView, Urls.COMMENTS_SHOW,showLoading) {
            @Override
            public WeiboParameters onPrepare() {
                WeiboParameters parameters = new WeiboParameters(Constant.APP_KEY);
                parameters.put(ParameterKey.ID,getStatusEntity().id);
                parameters.put(ParameterKey.ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(mArticleCommentView.getActivity()).getToken());
                parameters.put(ParameterKey.PAGE,PAGE);
                parameters.put(ParameterKey.COUNT,10);
                return parameters;
            }

            @Override
            public void onFinish(HttpResponse response) {
                Type type = new TypeToken<ArrayList<CommentEntity>>(){}.getType();
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(response.response);
                if(element.isJsonArray()){
                    List<CommentEntity> temp = new Gson().fromJson(element,type);
                    mArticleCommentView.onRefreshComplete(temp,isLoadMore);
                }
            }
        }.get();
    }

    @Override
    public StatusEntity getStatusEntity() {
        return (StatusEntity) mArticleCommentView.getActivity().getIntent().getSerializableExtra(StatusEntity.class.getSimpleName());
    }
}
