package com.xiaoming.cweibo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.entities.UserEntity;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2419:52
 */

public class FansAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private List<UserEntity> mUserEntities;

    public FansAdapter(List<UserEntity> userEntities) {
        mUserEntities = userEntities;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_fans,null);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        UserEntity entity = mUserEntities.get(position);
        holder.setText(R.id.tvUserName,entity.screen_name);
        holder.setText(R.id.tvDes,entity.description);
        holder.setImageByUrl(R.id.ivHeader,entity.profile_image_url);
    }

    @Override
    public int getItemCount() {
        return mUserEntities.size();
    }
}
