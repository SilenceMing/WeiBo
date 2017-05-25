package com.xiaoming.cweibo.views.MVP_view;

import com.xiaoming.cweibo.entities.StatusEntity;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2216:17
 */

public interface HomeView extends BaseView{
    void onRefreshComplete(List<StatusEntity> list,boolean loadMore);
}
