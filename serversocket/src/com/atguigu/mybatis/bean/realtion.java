package com.atguigu.mybatis.bean;

public class realtion {
	private String mname;
	private String fname;
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	@Override
	public String toString() {
		return "realtion [mname=" + mname + ", fname=" + fname + "]";
	}
	
}
