package com.atguigu.mybatis.test;

import java.util.List;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.bean.space;

public interface SpaceMapper {
	public void insertSpace(space m);
	public List<space> getSpace(String fromname);
	public space getOneSpace(String message);
	public void updateSpace(space ss);

}
