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
.biaoge {padding-top:8%;}
.button{padding-left:38%;padding-top:3%;}
#bg{background-image:url(images/content/dotted.png);}
</style>
</head>
<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script>
function add(){
	document.getElementById("newclient").style.display="block";
	document.getElementById("editclient").style.display="none";
	document.getElementById("delclient").style.display="none";
}
function edit(){
	document.getElementById("newclient").style.display="none";
	document.getElementById("editclient").style.display="block";
	document.getElementById("delclient").style.display="none";
}
function del(){
	document.getElementById("newclient").style.display="none";
	document.getElementById("editclient").style.display="none";
	document.getElementById("delclient").style.display="block";
}
function yes(num){
	document.getElementById("newclient").style.display="none";
	document.getElementById("editclient").style.display="none";
	document.getElementById("delclient").style.display="none";
	var id=document.getElementById("1").value;
	var pass=document.getElementById("2").value;
	if(pass!="12345678"){
		alert("密码不正确");
		return;
	}
	if(num==0){
		$.get("ClientServlet?flag=add&clientID="+id,function(data){
			
		})
	}
}
function no(){
	document.getElementById("newclient").style.display="none";
	document.getElementById("editclient").style.display="none";
	document.getElementById("delclient").style.display="none";
}
</script>
<body>
<div class="biaotilan">

<h1 class="biaoti" align="left">您的位置:设备管理>设备信息</h1>
<hr/>
</div>
<div class="biaoge">
<table border="1" align="center" >
	<tr>
		<th>设备ID</th>
		<th>设备名称</th>
		<th>上次登录IP</th>
	</tr>
	<%
	UserBeanCtrl usercl=(UserBeanCtrl)request.getSession().getAttribute("userbeanctrl");
	//System.out.println(usercl);
		for (UserClientBean getclient : usercl.getClientlist()) {
	%>
	<tr>
		<td><%=getclient.getClientID()%></td>
		<td><%=getclient.getoffClient().getName()%></td>
		<td><%=getclient.getoffClient().getLastip()%></td>
	</tr>
	<%}%>
</table>
<div class="button">
<input type="button" onclick="javascript:add()" value="添加设备" >
<input type="button" onclick="javascript:edit()" value="编辑设备" >
<input type="button" onclick="javascript:del()" value="删除设备" >
</div>
</div>
<div id="newclient" style="display:none;padding-left:500px;;height:200px;width:200px;padding-top:50px;position:absolute;">
<p>&nbsp&nbsp设备ID:<input id="1" type="text" value=""></p>
<p>设备密码:<input id="2" type="text" value=""></p>
<div style="float:left;padding-left:10%;"><input type="button" onclick="javascript:yes(0)" value="确定" ></div>
<div style="float:left;padding-left:10%;"><input type="button" onclick="javascript:no()" value="取消" ></div>
</div>
<div id="editclient" style="display:none;padding-left:500px;;height:200px;width:200px;padding-top:50px;position:absolute;">
<p>&nbsp&nbsp设备ID:<input id="3" type="text"  value=""></p>
<p>设备昵称:<input id="4" type="text" value=""></p>
<div style="float:left;padding-left:10%;"><input type="button" onclick="javascript:yes(1)" value="确定" ></div>
<div style="float:left;padding-left:10%;"><input type="button" onclick="javascript:no()" value="取消" ></div>
</div>
<div id="delclient" style="display:none;padding-left:500px;;height:200px;width:200px;padding-top:50px;position:absolute;">
<p>&nbsp&nbsp设备ID:<input id="5" type="text" value=""></p>
<div style="float:left;padding-left:10%;"><input type="button" onclick="javascript:yes(2)" value="确定" ></div>
<div style="float:left;padding-left:10%;"><input type="button" onclick="javascript:no()" value="取消" ></div>
</div>
</body>
</html>