package com.bysj.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bysj.model.ClientBean;
import com.bysj.model.ClientBeanCtrl;
import com.bysj.model.ClientPartBean;

/**
 * Servlet implementation class GetPartServlet
 */
@WebServlet("/GetPartServlet")
public class GetPartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clientID=request.getParameter("clientID");
		//System.out.println("GetPart");
		if(clientID!=null){
			ClientBean client=ClientBeanCtrl.findArrayById(clientID);
			if(client==null){
				//request.getRequestDispatcher("equipctrl.jsp").forward(request,response);
				return;
			}
				
			///ArrayList<String> partnum=client.getPartbuf();
			//ArrayList<Integer>partstate=client.getPartstate();
			ArrayList<ClientPartBean>onlinepart=client.getOnlinepart();
			response.setCharacterEncoding("UTF-8");
			PrintWriter output=response.getWriter();
			String data="";
			for(ClientPartBean part:onlinepart){
				if(part.getPartstate()==0){
					data+=part.getPartbuf()+"\t不合格<br/>\r\n";		
				}else{
					data+=part.getPartbuf()+"\t合格<br/>\r\n";
				}
			}
			output.println(data);
		}
		else //request.getRequestDispatcher("equipctrl.jsp").forward(request,response);
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
