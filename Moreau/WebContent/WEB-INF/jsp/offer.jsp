<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
 <link rel="stylesheet" href="CSS/offer.css" type="text/css" />
</head>
<body>





<div class="image" id="image">
<img src="IMG/index.jpg" height="100%" width="100%">
</div>
<div class="center" id="center">


<li onclick="window.location='offer_detail.do?offer_id=1'">
<p class="company">腾讯</p>
<p class="time">2018-06-25<br></p>
<p class="job">软件开发</p>
<p class="moreau" id="moreau">13k</p>
<p class="xueli">本科985</p>
<p class="content" id="content">感觉还不错，已签</p>
</li>
<li onclick="window.location='offer_detail.do'">
<p class="company">腾讯</p>
<p class="time">2018-06-25<br></p>
<p class="job">软件开发</p>
<p class="moreau" id="moreau">13k</p>
<p class="xueli">本科985</p>
<p class="content" id="content">感觉还不错，已签</p>
</li>
<li onclick="window.location='offer_detail.do'">
<p class="company">腾讯</p>
<p class="time">2018-06-25<br></p>
<p class="job">软件开发</p>
<p class="moreau" id="moreau">13k</p>
<p class="xueli">本科985</p>
<p class="content" id="content">感觉还不错，已签</p>
</li>

</div>
<div class="add">
<img src="IMG/add.jpg" height="45px" width="45px" onclick="window.location='addcontent.do'">
</div>
<div class="search" id="search">
<form action="" method="get">
<input placeholder="search" name="search">
</form>
<a class="hot">HOT</a>
<p onclick="window.location='offer.do?search=阿里'">阿里 </p><p>腾讯 </p><p>百度 </p><p>阿里 </p><p>腾讯 </p><p>百度 </p>
</div>

<div class="bottom">
<ul>
	<li ><a href="index.do">首页</a></li>
	<li ><a href="" class="active">发现</a></li>
	<li ><a href="my.do">我的</a></li>
</ul>
</div>

<script>
var o = document.getElementById("search");
var h = o.offsetHeight; 
var s = 'margin-top:'+h+'px';
document.getElementById("image").setAttribute('style',s); 
var ele = document.getElementById("center");
${message}


</script>
</body>
</html>