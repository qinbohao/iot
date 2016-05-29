package com.bysj.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.bysj.model.ClientBeanCtrl;
//��˿�ͨ�ŵ�TX����
public class OneServer extends Thread {

	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private boolean state=true;   //true����ִ�� false ����ֹͣ
	private boolean pause=true;   //true ����ͣ��false ��ͣ
	private int port;
	public void setPause(boolean pause){
		this.pause=pause;
	}
	public OneServer(Socket s,int port) {
		socket = s;
		this.port=port;
		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			System.out.println("find a new serversocket!" + socket.getInetAddress());
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void close(){
		try {
			state=false;

			if(socket!=null)
				socket.close();
			if(input!=null)
				input.close();
			if(output!=null)
				output.close();
			System.out.println("Close"+socket.getInetAddress());
			if(port==8087){
				ClientBeanCtrl.delClient(this); //�����б���ɾ��
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void send(String text) throws IOException{
		output.writeBytes(" " + text + '\0');
		output.flush();

	}
	public void run() {
		if(port==8087){
			new KeepListen(socket,this,input);
		}else if(port==8088){
			new CameraListen(socket,this,input);
		}
		while (state) {
			while(pause){}
			try {
				Thread.sleep(1000);////////////////////////////

			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}

		}		
	}
}
