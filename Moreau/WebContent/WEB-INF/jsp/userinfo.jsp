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
border-bottom:1px solid gray;
outline:none;
padding-bottom:5px;

font-size:125%;
width:100%;


}
 </style>
</head>
<body>

<div class="back">

<p class="p1" onclick="window.location='my.do'"><&nbsp返回</p>
<p class="p2" onclick='check()'>保存</p>

</div>
<div class="center">
<form method="get" id="forminput">
<input type="text" class="input" name="username" placeholder="修改昵称(回车提交)" id="username">
</form>
</div>




<script>
${message}
function check(){
	document.getElementById("forminput").submit();
}

</script>
</body>
</html>