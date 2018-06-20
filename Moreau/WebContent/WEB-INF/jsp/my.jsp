<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>

 <style>
 .bottom{
	display:block;
	position:fixed;
	width:100%;
	z-index:3;
	bottom:0px;
	line-height:45px;
	font-size:15px;
	border-top:1px solid #e2e2e2;
	overflow:hidden;
	}
.bottom ul
{
list-style-type:none;
margin:0;
padding:0;

}
.bottom li{
float:left;
width:33%;
}
.bottom li a{
display:block;
color:#0F0F0F;
text-align:center;
background-color:#fff;
padding:0px;
text-decoration:none;
text-transform:uppercase;
}
.bottom li a.active {
color: #fff;background-color:#09DFEC;
}
.top{
border-bottom:1px solid #e2e2e2;
}
.top img{
width:60px;
height:60px;
display:inline-block;
}
.top ul{
margin:5px;
padding:10px;
display:inline-block;
}
.center{
list-style:none;

}
.center li{

border-bottom:20px solid #e2e2e2;
}
 </style>
</head>
<body>

<div class="top" onclick="window.location='userinfo.do'">
<img src="IMG/add.jpg">
<ul>
<p>${username }</p>
<p>${useraccount }</p>
</ul>

</div>
<div class="center">
<li onclick="window.location='my_article.do'"><p>我的文章</p></li>
<li onclick="window.location='alter_password.do'"><p>修改密码</p></li>

<li onclick="window.location='logout.do'"><p>注销</p></li>
</div>


<div class="bottom">
<ul>
	<li ><a href="index.do">首页</a></li>
	<li ><a href="offer.do">发现</a></li>
	<li ><a href=""  class="active">我的</a></li>
</ul>
</div>

<script>



</script>
</body>
</html>