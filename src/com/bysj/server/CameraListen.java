package com.bysj.server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import com.bysj.model.ClientBean;
import com.bysj.model.ClientBeanCtrl;


public class CameraListen extends Thread{ 
	private DataInputStream input = null;  //������
	private String[] str=new String[5];   //50��������
	private char[] st=new char[30];	//���ν������ֵ
	private int num=0;					//���δ����ֽ���
	private int flag=0;
	private String clientID;
	private OneServer process=null;
	private Socket socket=null;
	private boolean state=false;
	/***************************************************************************************/	
	private  ArrayList<byte[]> imagelist=new ArrayList<byte[]>();//public static ArrayList<int[]> imagelist=new ArrayList<int[]>();
	private  ArrayList<Integer> imagelength=new ArrayList<Integer>();
	private  int imagenum=0;
	ClientBean client;


	public CameraListen(Socket socket,OneServer process,DataInputStream input){
		this.socket=socket;
		this.process=process;
		this.input=input;
		start();
	}

	public int getImagenum() {
		return imagenum;
	}
	public void setImagenum(int imagenum) {
		this.imagenum = imagenum;
	}
	public ArrayList<Integer> getImagelength() {
		return imagelength;
	}
	public void setImagelength(ArrayList<Integer> imagelength) {
		this.imagelength = imagelength;
	}
	public ArrayList<byte[]> getImagelist() {
		return imagelist;
	}
	public void setImagelist(ArrayList<byte[]> imagelist) {
		this.imagelist = imagelist;
	}
	
	//
	public synchronized String getListenstr(){  //�ӻ�����ȡ��
		if(flag!=0){
			flag--;
			return str[flag];	
		}
		return "";	
	}
	public void close(){   //�رս���
		state=false;
		try {
			if(process!=null)
				process.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void checkisLegal(){  //�ж��û��Ƿ�Ϸ�
		try {
			socket.setSoTimeout(3000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}//���ó�ʱ
		System.out.println("get the Camera!");
		int i=6,j=0; //����6���ַ����ж�У���ַ����Ƿ����3
		for(;i>0;i--){
			try{
				char inp=(char)input.readByte();
				if(inp=='\t')
					j++;
				else if(inp=='\n'){
					break;
				}
			}catch(Exception e){
				break;
			}
		}
		if(j>=3){
			int times=0;
			System.out.println("Camera is legal!");
			state=true;
			process.setPause(true);		
			while(flag==0&&times<50){
				receive();
				times++;
			} ////////////�ȴ������û�ID
			clientID=getListenstr();
			//System.out.println(clientID);
			if(clientID!=null){
				client=ClientBeanCtrl.findArrayById(clientID);
				if(client!=null){
					System.out.println("find client!");
					client.setCamera(this);  //��camera����client
				}else{
					this.close();
				}			
			}else{
				this.close();
			}	

			process.setPause(false);
			try {
				socket.setSoTimeout(0);
			} catch (SocketException e1) {
				this.close();
				e1.printStackTrace();
			}//���ó�ʱ


		}else{
			Server.ipblacklist.add(socket);
			this.close();
			System.out.println("User is ilegal!");
		}
	}
	public void receive(){    //������Ϣ
		try { 
			try{
				st[num]=(char)input.readByte();
			}catch(EOFException e){
				e.printStackTrace();  
				num=0;
			}finally{
				if(st[num]=='\n'||num>100){ 
					str[flag]=new String(st,0,num);				 
					flag++;
					num=0;
				} 
				else {
					num++; 
				}	
			}	
		} catch (Exception e) { 
			System.out.println("User is unstate!");
			this.close();   
		}  

	}
	public void getJpeg(){
		byte datanow = 0,datalast=0;
		byte image[];
		boolean isimageflag=false;
		while(state){
			int i=0;
			isimageflag=false;
			image=new byte[20480];
			if(imagenum>5){
				imagelist.remove(0);
				imagelength.remove(0);
			}
			try {
				Thread.sleep(0);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
				this.close();
			}
			while(true){
				//System.out.println("getting Jpeg!");
				try{
					//if(i==8192)
					//break;
					datalast=datanow;
					datanow= input.readByte();
					if((datanow==(byte)0xd8)&&(datalast==(byte)0xff)){
						image[i]=datalast;
						isimageflag=true;
						i++;
					}
					if(isimageflag){
						image[i]=datanow;
						i++;
					}
					if((datanow==(byte)0xd9)&&(datalast==(byte)0xff)){
						isimageflag=false;
						imagelist.add(image);
						imagelength.add(i);
						imagenum++;			
						break;
					}
				}catch (Exception e) {  
					e.printStackTrace();
					this.close();
				}
			}
		}	

	}
	public void run(){
		checkisLegal();
		getJpeg();
	}	
}
