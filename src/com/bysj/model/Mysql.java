package com.bysj.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Mysql {
	public static Connection connectDB(){
		String url = "jdbc:mysql://localhost:3306/bysj?"
				+ "user=root&password=88588164&useUnicode=true&characterEncoding=UTF8";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("连接数据库！");
			return DriverManager.getConnection(url);
		}catch(Exception e){
			e.printStackTrace();	
		}	
		return null;	
	}
	public static void  close(Connection conn,Statement stmt,ResultSet result){
		try {
			if(result!=null)
				result.close();
			if(stmt!=null)
				stmt.close();
			if(conn!=null)
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	public static Object Query(String table,String pk) throws SQLException{
		Connection conn=connectDB();
		if(conn!=null){
			Statement stmt = conn.createStatement();
			if(table.equals("user")){
				ResultSet result=stmt.executeQuery("select * from " + table + " where userID ='" + pk +"'");
					if(result.next()){
					UserBean user=new UserBean();
					user.setUserID(pk);
					user.setBalance(result.getInt(5));
					user.setRegdata(result.getString(4));
					user.setPassword(result.getString(3));
					user.setGrade(result.getInt(6));
					user.setEquipnum(result.getInt(7));
					Mysql.close(conn, stmt, result);
					return user;
					}
			}
			if(table.equals("client")){
				ResultSet result=stmt.executeQuery("select * from " + table + " where clientID ='" + pk +"'");
				if(result.next()){
					OffClientBean offclient=new OffClientBean();
					offclient.setName(result.getString(3));
					offclient.setLastip(result.getString(4));
					Mysql.close(conn, stmt, result);
					return offclient;
				}
			}
			if(table.equals("user_client")){
				ResultSet result=stmt.executeQuery("select * from " + table + " where userID ='" + pk +"'");
				ArrayList<UserClientBean> clientlist=new ArrayList<UserClientBean>();
				while(result.next()){
					UserClientBean clientpart=new UserClientBean();
					clientpart.setClient((OffClientBean) Mysql.Query("client", result.getString(3)));
					clientpart.setClientID(result.getString(3));
					clientlist.add(clientpart);
				}
				Mysql.close(conn, stmt, result);
				return clientlist;
			}
			if(table.equals("client_part")){
				ResultSet result=stmt.executeQuery("select * from " + table + " where clientID ='" + pk +"'");
				ArrayList<ClientPartBean> clientpartlist=new ArrayList<ClientPartBean>();
				while(result.next()){
					
					ClientPartBean clientpart=new ClientPartBean();
					clientpart.setPartbuf(result.getString(3).substring(0, result.getString(3).length()-2));
					clientpart.setPartstate(result.getInt(4));
					clientpartlist.add(clientpart);
				}
				Mysql.close(conn, stmt, result);
				return clientpartlist;
			}
		}
		return null;
	}
	//表名  列名  主键名 列值(要修改的值) 主键值
	public static boolean Updata(String table,String list,String pk,String value,String pkvalue) throws SQLException{
		//UPDATE `bysj`.`user` SET `password`='00000', `regdata`='2016-04-26' WHERE `userID`='qinbohao';
		boolean result=false;
		Connection conn=connectDB();
		if(conn!=null){
			Statement stmt = conn.createStatement();
			int res=-1;
			res=stmt.executeUpdate("update " + table +" set " + list + "='" + value +"' where " + pk +"='" + pkvalue +"'" );//("select * from " + table + " where userID ='" + pk +"'");
			if(res>=0)
				result=true;
		}
		return result;
		
	}
	// 表名 列名 值
	public static boolean Adddata(String table,String list,String values) throws SQLException{
		boolean result=false;
		Connection conn=connectDB();
		if(conn!=null){
			Statement stmt = conn.createStatement();
			int res=-1;
			//res=stmt.executeUpdate("update " + table +" set " + list + "='" + value +"' where " + pk +"='" + pkvalue +"'" );//("select * from " + table + " where userID ='" + pk +"'");
			//INSERT INTO `bysj`.`client` (`clientID`, `name`, `lastip`) VALUES ('17000000', '机械楼', '192.168.1.1');
			res=stmt.executeUpdate("insert into " + table +" (" + list +")" +" values (" + values +")");
			if(res>=0)
				result=true;
		}
		return result;
		
	}
}
