package com.bysj.server; 

import java.io.IOException;
import java.net.ServerSocket;  
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;  

public class Server extends Thread {  
	public static ArrayList<Socket> ipblacklist=new ArrayList<Socket>();  //IP������
	private Timer timer;
	private int port=8087;
	public Server(int port){
		this.port=port;  //54405��������������ݶ˿�----8088----������Ƶ�˿�
	}
	public void run() {  
		timer=new Timer(true);
		timer.schedule(new TimerTask(){
			public void run(){
				for(Socket getip:ipblacklist){
					try {
						getip.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				ipblacklist.clear();
				}
			}, 0,1000*60 );  //ÿ��60sһ��ʱ�����������
		
		ServerSocket s = null;  
		Socket Aserver  = null; 
		boolean isblack=false;
		System.out.println("Server running!");
		try {  

			s = new ServerSocket(port);  
			//�ȴ������󡢷���һֱ����  
			while(true){  
				Aserver = s.accept(); 
				isblack=false;
				for(Socket getip:ipblacklist){
					if(Aserver.getInetAddress().equals(getip.getInetAddress())){
						ipblacklist.remove(getip);
						ipblacklist.add(Aserver);
						isblack=true;
						break;
					}
				}
				if(!isblack){
					System.out.println("socket:"+Aserver);  
					new OneServer(Aserver,port);  
				}
				
			}  
		} catch (Exception e) {  
			try { 
				System.out.println("Aserver close!"); 
				if(Aserver!=null){
					Aserver.close();  
				}
			} catch (IOException e1) {  
				e1.printStackTrace();  
			}  
		}finally{  
			try {  
				System.out.println("Server close!");  
				if(s!=null){
					s.close(); 
				}

			} catch (IOException e ) {  
				e.printStackTrace();  
			}  
		}  


	}  

} 
