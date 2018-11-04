package com.atguigu.mybatis.bean;

import java.util.List;

public class Department {
	private Integer id;
	private String depatmentName;
	private List<Employee> emps;
	public List<Employee> getEmps() {
		return emps;
	}
	public void setEmps(List<Employee> emps) {
		this.emps = emps;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDepatmentName() {
		return depatmentName;
	}
	public void setDepatmentName(String depatmentName) {
		this.depatmentName = depatmentName;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", depatmentName=" + depatmentName + "]";
	}
	
}
