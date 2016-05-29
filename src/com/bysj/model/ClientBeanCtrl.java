package com.bysj.model;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.bysj.server.CameraListen;
import com.bysj.server.OneServer;
//在线client控制类
public class ClientBeanCtrl {
	public static ArrayList<ClientBean> array=new ArrayList<ClientBean>();
	public static ClientBean addClient(InetAddress IP,String ID,OneServer process){
		OffClientBean offclient;
		try{
			offclient=(OffClientBean) Mysql.Query("client", ID);
			if(offclient==null){
				Mysql.Adddata("client","clientID",ID);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		for(ClientBean getclient:array){
			if(ID.equals(getclient.getID())){
				array.remove(getclient);  //若有相同ID，删除之
				break;
			}
		}
		ClientBean client=new ClientBean(IP,ID,process);
		System.out.println("Add User "+ process + ID);
		array.add(client);
		return client;	
	}
	public static boolean delClient(OneServer process){
		if(process!=null){
			ClientBean  findclient=findArrayByProcess(process);
			if(findclient!=null){
				System.out.println("Del User "+ process + findclient.getID());
				return array.remove(findclient);	
			}		
		}
		System.out.println("Can't find the user!");
		return false; //未找到删除项
	}
	public static ClientBean findArrayByProcess(OneServer process){
		for(ClientBean getclient:array){
			if(process.equals(getclient.getProcess()))
				return getclient;
		}
		return null;	
	}
	public static ClientBean findArrayById(String ID){
		for(ClientBean getclient:array){
			if(getclient.getID().equals(ID))
				return getclient;
		}
		return null;	
	}
	public static CameraListen openCameraById(String ID){
		if(ID!=null){
			for(ClientBean getclient:array){
				if(getclient.getID().equals(ID)){
					getclient.sendcmd("DCMI");  //打开摄像头
					try {
						TimeUnit.MILLISECONDS.sleep(1000); //等待摄像头打开
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return getclient.getCamera();
				}		
			}	
		}
		return null;
	}
	public static void GetPartRecord(){
		
	}
}
