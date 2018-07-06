<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
 <link rel="stylesheet" href="CSS/offer.css" type="text/css" />
 <style>

 </style>
</head>

<body>





<div class="image" id="image">
<img src="IMG/offer.jpg" height="100%" width="100%">
</div>
<div class="center" id="center">

</div>
<div class="add">
<img src="IMG/add.jpg" height="45px" width="45px" onclick="window.location='addoffer.do'">
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
${message};

for(var i = 0; i < list.length; i++){
	var li = document.createElement("li");
	var url = 'offer_detail.do?offer_id='+list[i]['id'];
	li.onclick=function close(url){return function(){window.location=url;}}(url);
	var p = document.createElement("p");
	p.className = "company";
	p.innerHTML = list[i]['company'];
	li.appendChild(p);
	var p = document.createElement("p");
	p.className = "time";
	p.innerHTML = list[i]['time'];
	li.appendChild(p);
	var p = document.createElement("p");
	p.className = "job";
	p.innerHTML = list[i]['job'];
	li.appendChild(p);
	var p = document.createElement("p");
	p.className = "salary";
	p.innerHTML = list[i]['salary'];
	li.appendChild(p);
	var p = document.createElement("p");
	p.className = "reliability";
	p.innerHTML = '可信度：'+list[i]['reliability'];
	li.appendChild(p);
	ele.appendChild(li);
}

</script>
</body>
</html>