package com.bysj.model;

public class UserBean {
	private String userID;//�û���
	private String password;//����
	private String regdata;//ע������
	private int equipnum;//�豸����
	private int balance; //���
	private int grade;//�û�����
	
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
