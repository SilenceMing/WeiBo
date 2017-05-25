package com.xiaoming.cweibo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.entities.StatusEntity;
import com.xiaoming.cweibo.utils.CircleTransform;
import com.xiaoming.cweibo.utils.TimeFormatUtils;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2412:07
 */

public class MentionAdapter extends RecyclerView.Adapter<MentionAdapter.MentionViewHolder> {

    private Context mContext;
    private List<StatusEntity> mEntityList;
    private OnItemClickListener mOnItemClickListener;

    public MentionAdapter(Context context,List<StatusEntity> entityList) {
        mContext = context;
        mEntityList = entityList;
    }

    @Override
    public MentionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_weibo_mention,null);
        return new MentionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MentionViewHolder holder, int position) {
        StatusEntity entity = mEntityList.get(position);
        holder.mTvUserName.setText(entity.user.screen_name);
        holder.mTvTime.setText(TimeFormatUtils.parseToYYMMDD(entity.created_at));
        holder.mTvSource.setText(Html.fromHtml(entity.source).toString());
        holder.mTvContent.setText(entity.text);
        Glide.with(mContext).load(entity.user.profile_image_url)
                .placeholder(R.mipmap.ic_place_holder)
                .error(R.mipmap.ic_default_header)
                .transform(new CircleTransform(mContext))
                .into(holder.mIvHeader);
        StatusEntity reEntity = entity.retweeted_status;
        if (reEntity != null) {
            //由于listView滚动机制的问题，所引发的有时滚动时会消失布局
            holder.mLlStatus.setVisibility(View.VISIBLE);
            if(reEntity.deleted!=-1){
                Glide.with(mContext).load(reEntity.user.profile_image_url).into(holder.mIvMentioned);
                holder.mTvMentionedName.setText("@"+reEntity.user.screen_name);
                holder.mTvReContent.setText(reEntity.text);
            }else{
                holder.mTvMentionedName.setVisibility(View.GONE);
                holder.mIvHeader.setVisibility(View.GONE);
                holder.mTvContent.setText(R.string.delete_weibodata);
            }


        } else {
            holder.mLlStatus.setVisibility(View.GONE);
        }

    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }
    @Override
    public int getItemCount() {
        return mEntityList.size();
    }

    public class MentionViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvHeader;
        private TextView mTvUserName;
        private TextView mTvTime;
        private TextView mTvSource;
        private TextView mTvContent;
        private LinearLayout mLlStatus;
        private ImageView mIvMentioned;
        private TextView mTvMentionedName;
        private TextView mTvReContent;

        public MentionViewHolder(View itemView) {
            super(itemView);
            init(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener!=null){
                        mOnItemClickListener.OnItemClick(v,getLayoutPosition());
                    }
                }
            });
        }

        private void init(View itemView) {
            mIvHeader = (ImageView) itemView.findViewById(R.id.ivHeader);
            mTvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvSource = (TextView) itemView.findViewById(R.id.tvSource);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mLlStatus = (LinearLayout) itemView.findViewById(R.id.llStatus);
            mIvMentioned = (ImageView) itemView.findViewById(R.id.ivMentioned);
            mTvMentionedName = (TextView) itemView.findViewById(R.id.tvMentionedName);
            mTvReContent = (TextView) itemView.findViewById(R.id.tvReContent);
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(View v,int position);
    }
}
