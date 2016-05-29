package com.bysj.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bysj.model.UserBeanCtrl;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userID=request.getParameter("userID");
		String password=request.getParameter("password");
		UserBeanCtrl usercl=new UserBeanCtrl();
		if(usercl.checkUser(userID, password)){	
			//request.getSession().setAttribute("myName",userID);
			//request.getSession().setAttribute("userbean",user.getUserBean());
			request.getSession().setAttribute("userbeanctrl",usercl);
			response.sendRedirect("index.jsp");
		}
		else{
			request.getRequestDispatcher("login.jsp").forward(request,response);
		}
		
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
