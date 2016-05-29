package com.bysj.model;
//�����豸��ģ�Ͳ�
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bysj.server.CameraListen;
import com.bysj.server.OneServer;

public class ClientBean {
	
	private InetAddress IP=null;   //���ɸı�
	private String ID=null;	//���ɸı�
	private OneServer process=null; //���ɸı�,������������
	private CameraListen camera=null;
	private int motorstate=0;    //���״̬
	private int ledstate=0;  //0 ȫ�� 1 LED0��  2 LED1�� 3 ȫ��
	
	//��¼�豸��ǰ������״̬
	private ArrayList<ClientPartBean> onlinepart=new ArrayList<ClientPartBean>();
	private int partnum=0;
	//�������ݱ�ʶλ
	//public String isget;
	public ClientBean(InetAddress IP,String ID,OneServer process){
		this.IP=IP;
		this.ID=ID;
		this.process=process;	
	}
	
	public void sendcmd(String cmd){
		if(process!=null)
			try {
				process.send(cmd);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	//
	public void setLedState(int ledstate){
		int state=ledstate^this.ledstate;
		if((state&0x01)>0){
			if(ledstate==1||ledstate==3)
				sendcmd("LED0 ON");
			else 
				sendcmd("LED0 OFF");
		}
		else{
			if(ledstate==2||ledstate==3)
				sendcmd("LED1 ON");
			else 
				sendcmd("LED1 OFF");
		}
		System.out.println(ledstate);
		this.ledstate=ledstate;	
	}
	public int getLedState(){
		return ledstate;
	}
	public String getLedState(int i){
		if(i==0){
			if((ledstate&0x01)!=0)return "��";	
			else return "��";
		}
		else if(i==1){
			if((ledstate&0x02)!=0)return "��";	
			else return "��";
		}
		else 
			return "��";
	}
	public InetAddress getIP(){
		return IP;
	}
	public String getID(){
		return ID;
	}
	public OneServer getProcess(){
		return process;
	}
	public void setMotorState(int motorstate){
		this.motorstate=motorstate;	
	}
	public int getMotorState(){
		return motorstate;
	}
	
	public CameraListen getCamera() {
		return camera;
	}
	public void setCamera(CameraListen camera) {
		this.camera = camera;
	}
	
	
	public int getPartNum(){
		return partnum;
	}
	public ArrayList<ClientPartBean>getOnlinepart(){
		return onlinepart;	
	}
	
	public void updatePartBuf(String partname,int partstate ){   //���������Ϣ
		ClientPartBean newpart=new ClientPartBean();
		try {
			Mysql.Adddata("client_part", "clientID,partnum,partstate" , "'" + ID + "','" + partname + "','" + partstate +"'");
			//Mysql.Adddata("client_part", "clientID,partnum,partstate", ID + "," + partname + ","  + partstate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		newpart.setPartbuf(partname);
		newpart.setPartstate(partstate);
		if(partnum<10){
			partnum++;	
		}
		else{
			this.onlinepart.remove(0);
		}
		this.onlinepart.add(newpart);
	}
}
