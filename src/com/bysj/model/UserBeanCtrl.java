package com.bysj.model;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBeanCtrl {
	private UserBean user=null;
//	private String userID="";
	private ArrayList<UserClientBean> clientlist;
	
	
	public boolean checkUser(String userID,String password){
		boolean res=false;
		try {
			user=(UserBean) Mysql.Query("user", userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(user!=null){
			if(user.getPassword().equals(password)){
				
				res=true;
			} 
		}
		return res;
	}
	public UserBean getUserBean(){
		return user;
	}
	public boolean changePasswd(String newpassword) throws SQLException{
		boolean res=false;
		user.setPassword(newpassword);
		if(user!=null){
			res=Mysql.Updata("user", "password", "userID",newpassword, user.getUserID());
		}
		return res;
		
	}
	@SuppressWarnings("unchecked")
	public ArrayList<UserClientBean> getClientlist() throws SQLException{
		if(user!=null){
			clientlist=(ArrayList<UserClientBean>) Mysql.Query("user_client", user.getUserID());
		}
		
		return clientlist;
		
	}
	
}
