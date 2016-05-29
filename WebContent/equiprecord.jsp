<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="com.bysj.model.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style>
*{font-family:'仿宋'}
.biaotilan {padding-left:10px;width:99%;position:fixed;}
.biaoti {font-size:20px;}
.biaoge {padding-top:80px;}
.liebiao {padding-top: 5%;padding-left: 0;height:100px;}
.xiala {padding-left:0%;padding-top:16px;margin: 0;}
.selecttxt{float:left;padding-left:20%;padding-right:8%;}
#bg{background-image:url(images/content/dotted.png);}
.client{float:left;padding-left:25%;padding-top:2%;height:20px;width:150px;}
.clientstate{float:left;padding-top:2%;height:20px;width:150px;}
.part{padding-left:23%;padding-top:2%;height:200px;}
.page{padding-left:23%;padding-top:10%}
</style>
</head>
<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
var page=0;
function getid(){
	var list=document.getElementById("clientid");
	var id=list.options[list.selectedIndex].value;
	$.get("OffClientServlet?clientID=" + id + "&random="+Math.random() + "&page=" + page,function(data){
		$("#partpage").html("<br/>"+data);
})
}
function changepage(newpage){
	if(newpage==0)
		page=0;
	else if(newpage==1)
		page--;
	else if(newpage==2)
		page++;
	else if(newpage==3)
		page=65535000;
	if(page<0)
		page=0;
	getid();
}
</script>
<body>
<div class="biaotilan">

<h1 class="biaoti" align="left">您的位置:设备管理>历史数据</h1>
<hr/>
</div>
<div class="liebiao">
	<div class="selecttxt">
		<p >选择设备</p>
	</div>
	<div class="xiala">	
	<select id="clientid" style="width:200px;" onchange="javascript:getid()">
		<option value="0">请选择</option>
		<%
		UserBeanCtrl usercl=(UserBeanCtrl)request.getSession().getAttribute("userbeanctrl");
		//System.out.println(usercl);
			for (UserClientBean getclient : usercl.getClientlist()) {
				//session.setAttribute(getclient.getID(),getclient);
		%>
		<option value="<%=getclient.getClientID()%>"><%=getclient.getClientID()%></option>
		<%}%>
	</select>
	</div>
</div>
<div class="client">零件编号</div>
<div class="clientstate">零件状态</div>
<br/>
<div id="partpage"  class="part"><br/></div>
<div class="page">
<a id="home" href="javascript:changepage(0)" >首页</a>
<a id="last" href="javascript:changepage(1)">上一页</a>
<a id="next" href="javascript:changepage(2)">下一页</a>
<a id="back" href="javascript:changepage(3)">尾页</a>
</div>

</body>
</html>