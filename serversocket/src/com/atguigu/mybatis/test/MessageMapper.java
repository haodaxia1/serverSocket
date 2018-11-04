package com.atguigu.mybatis.test;

import java.util.List;

import com.atguigu.mybatis.bean.message;

public interface MessageMapper {
	public void setMes(message m);
	public List<message> getMes(String toname,String fromname);
}
