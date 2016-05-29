<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="com.bysj.model.*"%>
<head>
<style>
.main{padding-left:10%;padding-top:10%;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
	String clientID=request.getParameter("clientID");
	if(clientID==null)
		return;
	ClientBean getclient=ClientBeanCtrl.findArrayById(clientID);
%>
<div class="main">
<p>电机1运行状态：<a  href="ClientServlet?flag=changeLedstate&clientID=<%=getclient.getID()%>&led=0"><%=getclient.getLedState(0)%></a></p>
</div>
<div class="main">
<p>电机2运行状态：<a href="ClientServlet?flag=changeLedstate&clientID=<%=getclient.getID()%>&led=1"><%=getclient.getLedState(1)%></a></p>
</div>
</body>
</html>