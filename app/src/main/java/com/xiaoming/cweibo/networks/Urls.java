package com.xiaoming.cweibo.networks;

/**
 * @author Slience_Manager
 * @time 2017/5/19 9:53
 */

public class Urls {

    public static final String PREFIX = "https://api.weibo.com/2/statuses/";
    /**
     * 最新的微博信息
     */
    public static final String PUBLIC_TIMELINE = PREFIX+"home_timeline.json";
    /**
     * 我的信息
     */
    public static final String USER_TIMELINE = PREFIX+"user_timeline.json";
    /**
     * 转发微博
     */
    public static final String REPOST = PREFIX+"repost.json";
    /**
     * 评论
     */
    public static final String COMMENTS = "https://api.weibo.com/2/comments/";
    /**
     * 评论微博
     */
    public static final String CREATE =COMMENTS + "create.json";
    /**
     * 微博的评论列表
     */
    public static final String COMMENTS_SHOW = COMMENTS + "show.json";
    /**
     * @ 我的微博
     */
    public static final String MENTIONS = PREFIX + "mentions.json";
     /* *
     * 我发出的微博
     */
    public static final String BY_ME = COMMENTS + "by_me.json";
    /**
     * 收藏
     */
    public static final String FAVORITES = "https://api.weibo.com/2/favorites.json";
    /**
     * 登录用户信息
     */
    public static final String USERSHOW = "https://api.weibo.com/2/users/show.json";
    /**
     * 关系
     */
    private static final String FRIENDS = "https://api.weibo.com/2/friendships/";
    /**
     * 用户关注列表
     */
    public static final String ATTENTIONS = FRIENDS+ "friends.json";
    /**
     * 粉丝列表
     */
    public static final String  FANS =FRIENDS + "followers.json";

}
