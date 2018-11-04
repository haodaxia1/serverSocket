package com.atguigu.mybatis.bean;

public class message {
	private String fromname;
	private String toname;
	private String time;
	private String message;
	private int mid;
	
	public message(String fromname, String toname, String time, String message) {
		this.fromname = fromname;
		this.toname = toname;
		this.time = time;
		this.message = message;
	}
	public message(){
	}
	public String getFromname() {
		return fromname;
	}
	@Override
	public String toString() {
		return "message [fromname=" + fromname + ", toname=" + toname + ", time=" + time + ", message=" + message
				+ ", mid=" + mid + "]";
	}
	public void setFromname(String fromname) {
		this.fromname = fromname;
	}
	public String getToname() {
		return toname;
	}
	public void setToname(String toname) {
		this.toname = toname;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
}
