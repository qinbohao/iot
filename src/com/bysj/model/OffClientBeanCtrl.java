package com.bysj.model;

import java.sql.SQLException;
import java.util.ArrayList;

public class OffClientBeanCtrl {
	private String clientID;
	private ArrayList<ClientPartBean> clientpartlist;

	
	public OffClientBeanCtrl(String clientID){
		this.clientID=clientID;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ClientPartBean> getClientPartlist() throws SQLException{
		clientpartlist=(ArrayList<ClientPartBean>) Mysql.Query("client_part", clientID);
		return clientpartlist;
		
	}
}
