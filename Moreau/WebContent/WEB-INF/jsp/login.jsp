<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<style>
.user{

margin-top:10px;
width:100%;
margin-bottom:10px;
border-style:none;
border-bottom:1px solid #e2e2e2;
font-size:17px;
height:34px;
outline:none;
font-size:125%;
border-bottom:1px solid #e2e2e2;
padding-bottom:0px;
}
.password{
border-style:none;
margin-top:0px;
width:100%;
font-size:17px;
height:34px;
outline:none;
font-size:125%;
border-bottom:1px solid #e2e2e2;
padding-bottom:0px;
}
.submit{
	margin-top:10px;
	width:68px;
	height:34px;
	font-size:17px;
	width:100%;
	color:white;
	background-color:blue;
	text-decoration:none;
	
	border-radius:4px;
	border-style:none;
}
.bottom{
	margin-top:10px;
	position:relative;
}
.bottom .bottom1{
	position:absolute;
	color:blue;
	text-decoration:none;
}
.bottom .bottom2{
	right:0px;
	position:absolute;
	color:blue;
	text-decoration:none;
}
</style>
</head>
<body>
<div class="back">
<a class="backa" href="javascript:history.back();" style="margin-top:10px;text-decoration:none;color:blue;"><&nbsp&nbsp取消</a>
</div>
<form action="" method="post" id="login">

<input type="text" placeholder="账号" name="user" form="login" class="user">
<input type="password" placeholder="密码" name="password" form="login" class="password">
<input type="submit" value="登&nbsp录" class="submit">
<div class="bottom">
<a href="register.do?url1=${register }" class="bottom1">新用户注册</a>
<a href="" class="bottom2">无法登录？</a>
</div>
</form>
</body>
</html>