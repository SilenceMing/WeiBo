package com.xiaoming.cweibo.views.MVP_view;

import com.xiaoming.cweibo.entities.FavoritesEntity;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2216:17
 */

public interface CollectView extends BaseView{
    void onRefreshComplete(List<FavoritesEntity> list, boolean loadMore);
}
