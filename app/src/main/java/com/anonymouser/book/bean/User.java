package com.anonymouser.book.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * @author
 * @what 用户表
 * @date 2018/2/8
 */

public class User extends BmobUser implements Serializable {
    public String nickname;
    public String avatar;
}
