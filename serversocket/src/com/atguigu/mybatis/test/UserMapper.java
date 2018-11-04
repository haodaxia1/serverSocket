package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.user;

public interface UserMapper {
	public void addUser(user u);
	public user getUserByName(String username);
}
