package com.bysj.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bysj.model.UserBean;
import com.bysj.model.UserBeanCtrl;
/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBeanCtrl usercl=(UserBeanCtrl)request.getSession().getAttribute("userbeanctrl");
		UserBean user=usercl.getUserBean();
		String old=request.getParameter("oldpassword");
		String newp=request.getParameter("newpassword");
		String res="err";
		if(user!=null&&old!=null){
			if(user.getPassword().equals(old)){
				try {
					if(usercl.changePasswd(newp))res="yes";
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			else{
				res="no";
			}		
		}	
		response.sendRedirect("resetpw.jsp?ischeck="+res);
}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
