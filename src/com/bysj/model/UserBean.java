package com.bysj.model;

public class UserBean {
	private String userID;//用户名
	private String password;//密码
	private String regdata;//注册日期
	private int equipnum;//设备个数
	private int balance; //余额
	private int grade;//用户级别
	
	public String getRegdata() {
		return regdata;
	}
	public void setRegdata(String regdata) {
		this.regdata = regdata;
	}
	public void setUserID(String userID){
		this.userID=userID;	
	}
	public String getUserID(){
		return this.userID;	
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return this.password;
	}
	public void setGrade(int grade){
		this.grade=grade;
		
	}
	public int getGrade(){
		return this.grade;	
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getEquipnum() {
		return equipnum;
	}
	public void setEquipnum(int equipnum) {
		this.equipnum = equipnum;
	}

}
