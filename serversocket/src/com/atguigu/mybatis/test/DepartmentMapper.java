package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Department;

public interface DepartmentMapper {
	
	public Department getDeptById(Integer id);
	
	//查询全部员工
	public Department getDeptBtIdPlus(Integer id);
}
