<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>

 <style>
 .back{

 margin-top:0px;
 margin-bottom:10px;
 }
 .back .p1{
 color:blue;
 display:inline-block;
 margin-left:3px;
 }
 .back .p2{
 color:green;
 float:right;
margin-right:5px;
 }

.center{
margin-top:10px;
list-style:none;

}
.input{
background-color:transparent;
border:none;
border-bottom:1px solid #e2e2e2;
outline:none;
padding-bottom:0px;

font-size:17px;



}
.center p{
display:inline-block;
width:30%;
margin-top:0px;
width:20%;
font-size:17px;
}
.center input{
float:right;

width:70%;
}
 </style>
</head>
<body>

<div class="back">

<p class="p1" onclick="window.location='my.do'"><&nbsp返回</p>
<p class="p2" onclick="check()">完成</p>

</div>

<div class="center">
<form method="post" id="forminput">
<li>
<p>原密码</p><input type="password" class="input" name="password0" placeholder="填写原密码">
</li>
<li>
<p>新密码</p><input type="password" class="input" name="password1" placeholder="输入新密码">
</li>
<li>
<p>确认密码</p><input type="password" class="input" name="password2" placeholder="确认新密码">

</li></form>
</div>
<p style="color:gray;font-size:10px">密码长度不能低于6位</p>



<script>
${message}
function check(){
	document.getElementById("forminput").submit();
}

</script>
</body>
</html>