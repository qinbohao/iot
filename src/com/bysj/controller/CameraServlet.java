package com.bysj.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import com.bysj.model.ClientBeanCtrl;
import com.bysj.server.CameraListen;

/**
 * Servlet implementation class CameraServlet
 */
@WebServlet("/CameraServlet")
public class CameraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CameraServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clientID=request.getParameter("clientID");
		CameraListen camera=ClientBeanCtrl.openCameraById(clientID);	
		//System.out.println(clientID);

		if(camera==null)
			return;
		if(camera.getImagenum()==0)
			return;
		
		response.setContentType("multipart/x-mixed-replace; boundary=--ThisRandomStringTTTTTTTTTTTT");
		//System.out.println("servlet"); 
		OutputStream out=response.getOutputStream();
		String boundary="--ThisRandomStringTTTTTTTTTTTT\r\n";
		String contentimage="Content-Type: image/jpeg\r\n";
		String contentlength="Content-Length: ";


		int imagelength;
		byte image[];
		ImageIcon judgeim;

		while(true){
			imagelength=camera.getImagelength().get(2);
			image=camera.getImagelist().get(2);
			judgeim=new ImageIcon(image);
			//System.out.println(judgeim.getImageLoadStatus());
			String all=contentimage+contentlength+"\r\n\r\n";//+Integer.toString(imagelength)
			if(judgeim.getImageLoadStatus()==8)  //≈–∂œÕº∆¨ «∑Ò ‹À
			{
				out.write(all.getBytes());
				for(int i=0;i<imagelength+1;i++)
					out.write(image[i]);
				out.write("\r\n\r\n".getBytes());  //∑¢ÀÕÕº∆¨ƒ⁄»›
				//out.flush();
				out.write(boundary.getBytes());  //∑¢ÀÕ√ø∂Œ±Í ∂∑˚
				out.flush();
				try {
					TimeUnit.MILLISECONDS.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
