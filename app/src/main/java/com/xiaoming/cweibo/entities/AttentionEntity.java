package com.xiaoming.cweibo.entities;

import java.util.List;

/**
 * @author slience
 * @des
 * @time 2017/5/2420:23
 */

public class AttentionEntity {

    public int next_cursor;
    public int previous_cursor;
    public int total_number;
    public UserEntity mUserEntity;
    public List<UserEntity> users;


}
