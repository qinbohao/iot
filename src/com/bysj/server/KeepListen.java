package com.bysj.server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bysj.model.ClientBean;
import com.bysj.model.ClientBeanCtrl;


public class KeepListen extends Thread{ 
	private DataInputStream input = null;  //输入流
	private String[] str=new String[50];   //50个缓存区
	private char[] st=new char[150];	//单次接收最大值
	//private int port;					//54405 命令端口  8088视频端口
	private int num=0;					//单次传输字节数
	private int flag=0;
	private OneServer process=null;
	private Socket socket=null;
	private boolean state=false;
	private ClientBean client=null; //2016.5.21 增加指向设备的引用
	
	
	public KeepListen(Socket socket,OneServer process,DataInputStream input){
		//this.port=port;
		this.socket=socket;
		this.process=process;
		this.input=input;
		start();
	}
	public synchronized String getListenstr(){  //从缓存中取出
		if(flag!=0){
			flag--;
			return str[flag];	
		}
		return "";	
	}
	public void close(){   //关闭进程
		state=false;
		try {
			if(process!=null)
				process.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void checkisLegal(){  //判断用户是否合法
		try {
			socket.setSoTimeout(3000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}//设置超时
		System.out.println("Checking the User!");
		int i=6,j=0; //接收6个字符，判断校验字符数是否大于3
		for(;i>0;i--){
			try{
				char inp=(char)input.readByte();
				//System.out.println("Checking" + (6-i) + "times");
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
			System.out.println("User is legal!");
			state=true;
			process.setPause(true);		
			while(flag==0&&times<50){
				receive();
				times++;
			} ////////////等待接受用户ID
			String Id=getListenstr();
			client=ClientBeanCtrl.addClient(socket.getInetAddress(), Id, process);
			process.setPause(false);
			try {
				socket.setSoTimeout(0);
			} catch (SocketException e1) {
				this.close();
				e1.printStackTrace();
			}//设置超时
			
			 
		}else{
			Server.ipblacklist.add(socket);
			this.close();
			System.out.println("User is ilegal!");
		}
	}
	public void receive(){    //接收消息
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
	public void run(){
		
		checkisLegal();
		while(state){
			try {
				Thread.sleep(0);//////////////////////10
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			receive();
			String str = this.getListenstr();
			if (str.equals("") == false) {
				System.out.println("Client Socket Message:" + str);
				if(str.contains("Part")){
					Date date=new Date();
					DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time=format.format(date);
					System.out.println(time);
					if(str.contains("OK")){
							client.updatePartBuf(time, 1);
					}else{
						client.updatePartBuf(time, 0);
					}
				}
			}
		} 
		System.out.println("Listen close!");
	}	
}
