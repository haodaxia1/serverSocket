package com.atguigu.mybatis.test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

import com.atguigu.mybatis.bean.Employee;

public interface EmployeeMapper {
	
	@MapKey("id")//告诉Mybatis封装map的时候用的是主键
	public Map<Integer,Employee> getEmpByIdReurnEmp(String lastname);
	
	public Map<String,Object> getEmpByIdReurnMap(Integer id);
	public List<Employee> getEmpsByLastNameLike(String lastname);
	public Employee getEmpById(Integer id);
	public void addEmp(Employee employee);
	public void updateEmp(Employee employee);
	public void delete(Employee employee);
	public Employee getEmpByMap(Map<String,Object> map);
}
