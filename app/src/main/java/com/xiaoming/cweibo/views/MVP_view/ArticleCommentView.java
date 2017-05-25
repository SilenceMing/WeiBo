package com.xiaoming.cweibo.views.MVP_view;

import com.xiaoming.cweibo.entities.CommentEntity;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2312:24
 */

public interface ArticleCommentView extends BaseView {
    void onRefreshComplete(List<CommentEntity> list, boolean isLoadMore);
}
