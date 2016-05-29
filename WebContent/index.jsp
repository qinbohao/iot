<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
 <%@page import="com.bysj.server.*"%>
 <%@page import="com.bysj.model.*"%>
<%!
public void jspInit(){
	Server server=new Server(8087);
	server.start();
	System.out.println("CreatServer!");	
	Server camera=new Server(8088);
	camera.start();
	System.out.println("CreatCamera!");	

}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	if(session.getAttribute("userbeanctrl")==null)
		response.sendRedirect("login.jsp");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理系统</title>


<style type="text/css">
body{margin:0;padding:0;}
html, body{height:100%;}
img{border:none;}
*{font-family:'仿宋';font-size:16px;color:#626262;}
dl,dt,dd{display:block;margin:0;}
a{text-decoration:none;}

#bg{background-image:url(images/index/index/content/dotted.png);}
.container{width:100%;height:100%;margin:auto;padding-top:80px;}

/*left*/
.leftsidebar_box{width:10%;height:auto !important;overflow:hidden; !important;position:fixed;height:100% !important;background-color:#3992d0;}
.line{height:1%;width:100%;background-image:url(images/index/index/left/line_bg.png);background-repeat:repeat-x;}
.leftsidebar_box dt{padding-left:30%;padding-right:0%;background-repeat:no-repeat;background-position:10% center;color:#f5f5f5;font-size:18px;position:relative;line-height:48px;cursor:pointer;}
.leftsidebar_box dd{background-color:#317eb4;padding-left:30%;}
.leftsidebar_box dd a{color:#f5f5f5;line-height:40px;}
.leftsidebar_box dt img{right:40%;top：0%;}
.system_log dt{background-image:url(images/index/left/system.png);padding-bottom:10%;padding-top:10%;}
.app dt{background-image:url(images/index/left/app.png);padding-bottom:10%;padding-top:10%;}
.source dt{background-image:url(images/index/left/source.png);padding-bottom:10%;padding-top:10%;}
.statistics dt{background-image:url(images/index/left/statistics.png);padding-bottom:10%;padding-top:10%;}
.leftsidebar_box dl dd:last-child{padding-bottom:10%;}
.frame {padding-left:10%;height:600px;width:80%;font-family:'仿宋';font-size:14px;color:#626262;margin:0;border:0px;}
.top{width:100%;height:80px;background-color:#3992d0;overflow:auto;position:fixed;}
.top img{width:64px;height:64px;padding-top:10px;padding-left:48px;}
.toptext {font-family: '楷体';color: #f5f5f5;font-size: 30px;float: left;padding-top: 30px;	padding-left: 45px;}
</style>
<script>
	//window.onload=function
	function showpage(pagename) {
		var page = document.getElementById('pageto');
		page.src = pagename+"?random="+Math.random();
	}
</script>
</head>

<body id="bg">

	<div class="top">
		<dl>
			<dt style="float: left">
				<img src="images/index/top/top.png"></img>
			</dt>
			<dt class="toptext" onclick="login.jsp">无损检测设备管理系统</dt>
		</dl>
	</div>
	<div class="container">

		<div class="leftsidebar_box">

			<div class="line"></div>
			<dl class="system_log">
				<dt>
					账户管理<img src="images/index/left/select_xl01.png"></img>
				</dt>
				<dd class="first_dd">
					<a href="javascript:showpage('userinform.jsp')">个人信息</a>
				</dd>
				<dd>
					<a href="javascript:showpage('resetpw.jsp')">修改密码</a>
				</dd>
				<dd>
					<a href="javascript:showpage('operecord.jsp')">操作记录</a>
				</dd>
			</dl>
			<dl class="app">
				<dt>
					设备管理<img src="images/index/left/select_xl01.png"></img>
				</dt>
				<dd class="first_dd">
					<a href="javascript:showpage('equipinform.jsp')">设备信息</a>
				</dd>
				<dd>
					<a href="javascript:showpage('equiponline.jsp')">在线设备</a>
				</dd>
				<dd>
					<a href="javascript:showpage('equiprecord.jsp')">历史数据</a>
				</dd>
			</dl>
			<dl class="source">
				<dt>
					固件信息<img src="images/index/left/select_xl01.png"></img>
				</dt>
				<dd class="first_dd">
					<a href="#">固件信息</a>
				</dd>
				<dd>
					<a href="#">升级固件</a>
				</dd>
			</dl>
			<dl class="statistics">
				<dt>
					统计分析<img src="images/index/left/select_xl01.png"></img>
				</dt>
				<dd class="first_dd">
					<a href="#">客户统计</a>
				</dd>
			</dl>

		</div>
		<div id="contbox">
			<iframe id="pageto"  class="frame" src="wel.jsp"></iframe>
		</div>

	</div>


	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript">
		$(".leftsidebar_box dt").css({
			"background-color" : "#3992d0"
		});
		$(".leftsidebar_box dt img").attr("src", "images/index/left/select_xl01.png");
		$(function() {
			$(".leftsidebar_box dd").hide();
			$(".leftsidebar_box dt").mouseover(
					function() {
						$(".leftsidebar_box dt").css({"background-color" : "#3992d0"})
						$(this).css({"background-color" : "#317eb4"});
						$(this).parent().find('dd').removeClass("menu_chioce");
						$(".leftsidebar_box dt img").attr("src","images/index/left/select_xl01.png");
						$(this).parent().find('img').attr("src","images/index/left/select_xl.png");
						$(".menu_chioce").slideUp();
						$(this).parent().find('dd').slideToggle();
						$(this).parent().find('dd').addClass("menu_chioce");
					});
		})
	</script>

	<!--  <div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">

</div>-->
</body>
</html>
