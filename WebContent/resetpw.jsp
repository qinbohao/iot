<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%@page import="com.bysj.model.*"%>
<html>
<script>
	function changepasswd() {
		var form = document.createElement("form");
		var oldpasswd = document.getElementById("oldpassword").value;
		var newpasswd = document.getElementById("newpassword").value;
		var confirmpasswd=document.getElementById("confirmpassword").value;
		if(oldpasswd==""){
			alert('请输入原密码！');
		}
		else if(newpasswd==confirmpasswd){
			form.id = "form";
			form.name = "form";
			document.body.appendChild(form);
			form.method = "POST";
			form.action = "UserServlet?oldpassword=" + oldpasswd +'&' + "newpassword=" + newpasswd;
			form.submit();
		}
		else{
			alert('两次输入密码不一致！');
		}
		document.body.removeChild(form);
	}
	window.onload=function(){	
	<%String ischeck=(String)request.getParameter("ischeck");	
		char is='z';
		if(ischeck!=null){
			if(ischeck.equals("yes")) is='a'; //修改成功
			else if(ischeck.equals("no")) is='b';//密码错误
			else is='c';   //连接超时
		}
		%>
		var is='<%=is%>';
		if(is=='a')alert('修改成功');
		else if(is=='b')alert('密码错误');
		else if(is=='c')alert('修改出错，请重试！');
		
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style >
*{font-family:'微软雅黑'}
.biaotilan {padding-left:10px;width:99%;position:fixed;}
.biaoti {font-size:20px;}
.biaoge {font-size:20px;padding-top:10%;padding-left:35%;}
#bg{background-image:url(images/content/dotted.png);}
</style>
</head>
<body>
<% 
UserBean user=(UserBean)session.getAttribute("userbean");
%>
<div class="biaotilan">

<h1 class="biaoti" align="left">您的位置:账户管理>修改密码</h1>
<hr/>
</div>
<div class="biaoge">
<font >输入旧密码:<input id="oldpassword" type="password" value=""/></font><br/>
<br/>
<font >输入新密码:<input id="newpassword" type="password" value=""/></font><br/>
<br/>
<font >确  认 密 码:<input id="confirmpassword" type="password" value=""/></font>
<p  style="padding-left:30%;padding-top:0%;"><input type="button" value="确定" onclick="javascript:changepasswd()" /></p>
</div>
</body>
</html>