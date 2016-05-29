package com.bysj.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bysj.model.ClientPartBean;
import com.bysj.model.OffClientBeanCtrl;

/**
 * Servlet implementation class OffClientServlet
 */
@WebServlet("/OffClientServlet")
public class OffClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OffClientServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String clientID=request.getParameter("clientID");
		int page=Integer.valueOf(request.getParameter("page"));

		int partnum=0;
		if(clientID!=null){
			OffClientBeanCtrl clientpart=null;
			ArrayList<ClientPartBean> partlist = null;
			try {
				if(page==0){
					clientpart=new OffClientBeanCtrl(clientID);
					partlist=clientpart.getClientPartlist();
					for(ClientPartBean part:partlist){
						partnum++;
					}
					request.getSession().setAttribute("partlist", partlist);
					request.getSession().setAttribute("partnum", partnum);

				}else{
					partlist=(ArrayList<ClientPartBean>) request.getSession().getAttribute("partlist");
					partnum=(int) request.getSession().getAttribute("partnum");
					if(page>partnum/10)
						page=partnum/10;
				}
				if(partlist==null)
					return;
				response.setCharacterEncoding("UTF-8");

				PrintWriter output=response.getWriter();
				String data="";
				for(int i=0;i<10;i++){
					int num=i+page*10;    //每页显示20条
					//System.out.println(num);
					if(num>=partnum)
						break;
					ClientPartBean part=partlist.get(num);
						if(part.getPartstate()==0){
							
							data+=part.getPartbuf()+"\t不合格<br/>\r\n";		
						}else{
							data+=part.getPartbuf()+"\t合格<br/>\r\n";
						}
					//System.out.println(data);
				
				}
				output.println(data);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
			return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
