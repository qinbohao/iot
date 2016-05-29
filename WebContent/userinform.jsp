<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%@page import="com.bysj.model.*"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style>
*{font-family:'微软雅黑'}
.biaotilan {padding-left:10px;width:99%;position:fixed;}
.biaoti {font-size:20px;}
.biaoge {font-size:20px;padding-top:10%;padding-left:35%;}
#bg{background-image:url(images/content/dotted.png);}
</style>
</head>
<body>
<% 
UserBeanCtrl usercl=(UserBeanCtrl)session.getAttribute("userbeanctrl");
UserBean user=usercl.getUserBean();
%>
<div class="biaotilan">

<h1 class="biaoti" align="left">您的位置:账户管理>个人信息</h1>
<hr/>
</div>
<div class="biaoge">
<font >用  户 名:  <%=user.getUserID()%></font><br/>
<br/>
<font >注册日期:<%=user.getRegdata()%></font><br/>
<br/>
<font >设备个数:<%=user.getEquipnum() %></font><br/>
<br/>
<font >用户余额:<%=user.getBalance()%></font><br/>
<br/>
</div>
</body>
</html>