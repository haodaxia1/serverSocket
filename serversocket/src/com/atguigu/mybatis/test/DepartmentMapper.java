package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Department;

public interface DepartmentMapper {
	
	public Department getDeptById(Integer id);
	
	//��ѯȫ��Ա��
	public Department getDeptBtIdPlus(Integer id);
}
