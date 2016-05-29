package com.bysj.controller;
//在线设备控制和获取信息Servlet
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bysj.model.ClientBean;
import com.bysj.model.ClientBeanCtrl;
import com.bysj.model.Mysql;
import com.bysj.model.UserBeanCtrl;

@WebServlet("/ClientServlet")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String flag=request.getParameter("flag");
		if((flag!=null)&&(flag.equals("changeLedstate"))){
			int ledstate=0;
			String clientID=request.getParameter("clientID");
			String led=request.getParameter("led");
			if(led.equals("1")){
				ClientBean client=ClientBeanCtrl.findArrayById(clientID);
				if(client!=null){
					ledstate=client.getLedState();
					ledstate^=0x02;  //第二位取反;
					client.setLedState(ledstate);
				}
			}else if(led.equals("0"))	{
				ClientBean client=ClientBeanCtrl.findArrayById(clientID);
				if(client!=null){
					ledstate=client.getLedState();
					ledstate^=0x01;  //第1位取反;
					client.setLedState(ledstate);
				}
				
			}
		}
		if((flag!=null)&&(flag.equals("add"))){
			String clientID=request.getParameter("clientID");
			UserBeanCtrl usercl=(UserBeanCtrl)request.getSession().getAttribute("userbeanctrl");
		if(ClientBeanCtrl.findArrayById(clientID)!=null){
			try {
				Mysql.Adddata("user_client", "userID,clientID", "'" + usercl.getUserBean().getUserID() + "','" + clientID + "'");
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			request.getRequestDispatcher("equipinform.jsp").forward(request,response);
			return;
		}
		else return;	
		}
		request.getRequestDispatcher("equipctrl.jsp").forward(request,response);
	//	response.sendRedirect("editctrl.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
