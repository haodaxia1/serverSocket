package com.atguigu.mybatis.bean;

public class space {
	private String fromname;
	private String message;
	private String time;
	private int num=0;
	public String getFromname() {
		return fromname;
	}
	public void setFromname(String fromname) {
		this.fromname = fromname;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "space [fromname=" + fromname + ", message=" + message + ", time=" + time + ", num=" + num + "]";
	}
	public space(String fromname, String message, String time) {
		this.fromname = fromname;
		this.message = message;
		this.time = time;
	}
	public space(){}
	
}
