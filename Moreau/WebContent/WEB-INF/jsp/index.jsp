<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
 <link rel="stylesheet" href="CSS/index.css" type="text/css" />
</head>
<body>

<div class="search" id="search">
<form action="" method="get">
<input placeholder="search" >
</form>
<a class="hot">HOT</a>
<p>阿里 </p>
<p>腾讯 </p>
<p>百度 </p>
<p>阿里 </p>
<p>腾讯 </p>
<p>百度 </p>
<p>阿里 </p>
<p>腾讯 </p>
<p>百度 </p>

</div>



<div class="image" id="image">
<img src="IMG/index.jpg" height="100%" width="100%">
</div>
<div class="center">
<li onclick="window.location='http://www.baidu.com'">1</li>
<li>
<p class="user">匿名用户</p>
<p class="result">通过</p>

</li>
<li>1</li>
<li>1</li>
<li>1</li>
<li>1</li>
<li>1</li>
<li>1</li>
<li>1</li>
<li>2</li>
</div>
<div class="add">
<img src="IMG/add.jpg" height="45px" width="45px" onclick="window.location='addcontent.do'">
</div>
<div class="bottom">
<ul>
	<li ><a href="" class="active">首页</a></li>
	<li ><a href="">发现</a></li>
	<li ><a href="">我的</a></li>
</ul>
</div>

<script>
var o = document.getElementById("search");
var h = o.offsetHeight; 
var s = 'margin-top:'+h+'px';
document.getElementById("image").setAttribute('style',s);   
</script>
</body>
</html>