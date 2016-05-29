<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.bysj.model.*"%>
<%@page import="com.bysj.controller.*"%>
<html>
<head>
<style>
* {font-family: '仿宋'}
.biaotilan {padding-left: 10px;width: 99%;position: fixed;}
.biaoti {font-size: 20px;}
.liebiao {padding-top: 5%;padding-left: 0;height:100px;}
.xiala {padding-left:0%;padding-top:16px;margin: 0;}
.selecttxt{float:left;padding-left:20%;padding-right:8%;}
.client{padding-left:10%;padding-top:16px;height:500px;width:1024px;}
.frame{height:300px;width:350px;margin:0;border:0px;}
.camera{float:left;width:350px;height:240px;}
.list{float:left;width:250px;height:240px;}
#bg {background-image: url(images/content/dotted.png);overflow:hidden;}
</style>
</head>
<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script>

function getid(){
	var list=document.getElementById("clientid");
	var id=list.options[list.selectedIndex].value;
	var cameraframe=document.getElementById("camerapage");
	var ctrlframe=document.getElementById("ctrlpage");
	var partframe=document.getElementById("partpage");
	if(id!=0){
		cameraframe.src="CameraServlet?clientID="+id+"&random="+Math.random();//修改IP
		//cameraframe.src="http://42.96.150.151:8080/test/CameraServlet?clientID="+id;
		ctrlframe.src="equipctrl.jsp?clientID="+id+"&random="+Math.random();
		//partframe.src="GetPartServlet?clientID="+id+"&random="+Math.random();
		clearInterval(timer);
		var timer=setInterval(  function updata()
		{
			$.get("GetPartServlet?clientID="+id+"&random="+Math.random(),function(data){
				$("#partpage").html(data);
			})
			//alert('aaaa');
			//var list=document.getElementById("clientid");
			//var id=list.options[list.selectedIndex].value;
			 //  partframe.src="GetPartServlet?clientID="+id+"&random="+Math.random();
		},1000);//1000为1秒钟
	}
}


  
</script>
<body id="bg">
	<div class="biaotilan">
		<h1 class="biaoti" align="left">您的位置:设备管理>在线设备</h1>
		<hr />
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
				if(ClientBeanCtrl.findArrayById(getclient.getClientID())!=null){
			%>
			<option value="<%=getclient.getClientID()%>"><%=getclient.getClientID()%></option>
			<%}}%>
		</select>
		</div>
	</div>
	<div class="client">
		<div class="camera">
		<iframe id="camerapage" class="frame" src="CameraServlet"></iframe>
		</div>
		<div class="list">
		<iframe id="ctrlpage" class="frame" src="equipctrl.jsp"></iframe>
		</div>
		<div id="partpage" class="list">
		<!--  <iframe id="partpage" class="frame" src="GetPartServlet"></iframe>-->
		
		</div>
	</div>
</body>
</html>