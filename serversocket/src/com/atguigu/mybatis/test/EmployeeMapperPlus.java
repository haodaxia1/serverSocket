package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Employee;

public interface EmployeeMapperPlus {

	public Employee getEmpById(Integer id);
	public Employee getEmpAndDep(Integer id);
	public Employee getEmpByIdStep(Integer i);
}
