package com.xiaoming.cweibo.views.MVP_view;

import com.xiaoming.cweibo.entities.UserEntity;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2418:26
 */

public interface FansView extends BaseView {
    void onRefreshComplete(List<UserEntity> list, boolean loadMore);
}
