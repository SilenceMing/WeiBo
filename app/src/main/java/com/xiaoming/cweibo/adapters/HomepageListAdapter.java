package com.xiaoming.cweibo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoming.cweibo.Global.ParameterKey;
import com.xiaoming.cweibo.R;
import com.xiaoming.cweibo.activity.CommentsActivity;
import com.xiaoming.cweibo.activity.PhotoViewActivity;
import com.xiaoming.cweibo.activity.RepostActivity;
import com.xiaoming.cweibo.entities.PicUrlsEntity;
import com.xiaoming.cweibo.entities.StatusEntity;
import com.xiaoming.cweibo.utils.CircleTransform;
import com.xiaoming.cweibo.utils.RichTextUtils;
import com.xiaoming.cweibo.utils.TimeFormatUtils;
import com.xiaoming.cweibo.views.DrawCenterTextView;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2017:22
 */

public class HomepageListAdapter extends RecyclerView.Adapter<HomepageListAdapter.HomepageViewHolder> {
    private List<StatusEntity> mStatusEntities;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public HomepageListAdapter(Context context, List<StatusEntity> statusEntities) {
        mStatusEntities = statusEntities;
        mContext = context;
    }

    @Override
    public HomepageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weibo_content, parent, false);
        return new HomepageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomepageViewHolder holder, int position) {
        final StatusEntity entity = mStatusEntities.get(position);
        holder.mTvUserName.setText(entity.user.screen_name);
        holder.mTvTime.setText(TimeFormatUtils.parseToYYMMDD(entity.created_at));
        //获取运动方式
        holder.mTvContent.setMovementMethod(LinkMovementMethod.getInstance());
        holder.mTvContent.setText(RichTextUtils.getRichText(mContext,entity.text));

        holder.mTvSource.setText(Html.fromHtml(entity.source).toString());

        holder.mTvLike.setText(String.valueOf(entity.attitudes_count));
        holder.mTvComment.setText(String.valueOf(entity.comments_count));
        holder.mTvRetweet.setText(String.valueOf(entity.reposts_count));
        holder.mTvRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RepostActivity.class);
                intent.putExtra(ParameterKey.ID,entity.id);
                intent.putExtra(ParameterKey.STATUS,entity.text);
                intent.putExtra("HEAD_TITLE",entity.user.screen_name);
                if(entity.pic_urls!=null&&entity.pic_urls.size()>0) {
                    intent.putExtra("IMG_URL", entity.pic_urls.get(0).thumbnail_pic.replace("thumbnail", "large"));
                }
                mContext.startActivity(intent);
            }
        });
        holder.mTvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra(ParameterKey.ID,entity.id);
                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext).load(entity.user.profile_image_url)
                .placeholder(R.mipmap.ic_place_holder)
                .error(R.mipmap.ic_default_header)
                .transform(new CircleTransform(mContext))
                .into(holder.mIvHeader);
        List<PicUrlsEntity> mPicUrlsEntity = entity.pic_urls;
        if(mPicUrlsEntity!=null&&mPicUrlsEntity.size()>0){
            final PicUrlsEntity picUrlsEntity = mPicUrlsEntity.get(0);
            picUrlsEntity.original_pic = picUrlsEntity.thumbnail_pic.replace("thumbnail","large");
            picUrlsEntity.bmiddle_pic = picUrlsEntity.thumbnail_pic.replace("thumbnail","bmiddle");
            holder.mIvContent.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(picUrlsEntity.original_pic)
                    .placeholder(R.mipmap.ic_place_holder)
                    .into(holder.mIvContent);
            holder.mIvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,PhotoViewActivity.class);
                    intent.putExtra("IMG_URL",picUrlsEntity);
                    mContext.startActivity(intent);
                }
            });
        }else{
            holder.mIvContent.setVisibility(View.GONE);
        }
        StatusEntity reEntity = entity.retweeted_status;
        if (reEntity != null) {
            //由于listView滚动机制的问题，所引发的有时滚动时会消失布局
            holder.mLlRe.setVisibility(View.VISIBLE);
            holder.mTvReContent.setText(reEntity.text);
            List<PicUrlsEntity> mRePicUrlsEntity = reEntity.pic_urls;
            if(mRePicUrlsEntity!=null&&mRePicUrlsEntity.size()>0){
                PicUrlsEntity picUrlsEntity = mRePicUrlsEntity.get(0);
                picUrlsEntity.original_pic = picUrlsEntity.thumbnail_pic.replace("thumbnail","large");
                picUrlsEntity.bmiddle_pic = picUrlsEntity.thumbnail_pic.replace("thumbnail","bmiddle");
                holder.mIvReContent.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(picUrlsEntity.original_pic)
                        .placeholder(R.mipmap.ic_place_holder)
                        .error(R.mipmap.ic_default_header)
                        .into(holder.mIvReContent);
            }else{
                holder.mIvReContent.setVisibility(View.GONE);
            }
        } else {
            holder.mLlRe.setVisibility(View.GONE);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mStatusEntities.size();
    }


    class HomepageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvHeader;
        private TextView mTvUserName;
        private TextView mTvTime;
        private TextView mTvSource;
        private TextView mTvContent;
        private ImageView mIvContent;
        private LinearLayout mLlRe;
        private TextView mTvReContent;
        private ImageView mIvReContent;
        private DrawCenterTextView mTvLike;
        private DrawCenterTextView mTvRetweet;
        private DrawCenterTextView mTvComment;

        public HomepageViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.OnItemClick(v, getLayoutPosition());
                    }
                }
            });
        }

        private void initView(View v) {
            mIvHeader = (ImageView) v.findViewById(R.id.ivHeader);
            mTvUserName = (TextView) v.findViewById(R.id.tvUserName);
            mTvTime = (TextView) v.findViewById(R.id.tvTime);
            mTvSource = (TextView) v.findViewById(R.id.tvSource);
            mTvContent = (TextView) v.findViewById(R.id.tvContent);
            mIvContent = (ImageView) v.findViewById(R.id.ivContent);
            mLlRe = (LinearLayout) v.findViewById(R.id.llRe);
            mTvReContent = (TextView) v.findViewById(R.id.tvReContent);
            mIvReContent = (ImageView) v.findViewById(R.id.ivReContent);
            mTvLike = (DrawCenterTextView) v.findViewById(R.id.tvLike);
            mTvRetweet = (DrawCenterTextView) v.findViewById(R.id.tvRetweet);
            mTvComment = (DrawCenterTextView) v.findViewById(R.id.tvComment);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(View v, int position);
    }

}
