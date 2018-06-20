<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<style>
.center{

}
.center textarea{
	margin-top:15px;
	width:100%;
	height:60px;
	font-size:17px;
	
}

.center input{
	
	
	height:34px;
	font-size:17px;
	width:100%;
	color:white;
	background-color:blue;
	text-decoration:none;
	
	border-radius:4px;
	border-style:none;
}
</style>
</head>
<body>

<div class="back">
<a class="backa" href="detail.do?content_id=${content_id }" style="margin-top:10px;text-decoration:none;color:blue;"><&nbsp&nbsp返回</a>
</div>
<div class="center">
<form action="" method="post" id="comment_reply1">
<textarea placeholder="回复他/她：" class="textinput" id="textinput" name="reply0" form="comment_reply1"></textarea>
<input type="submit" class="submit" value="提&nbsp交">
</form>
</div>
${message }
</body>
</html>