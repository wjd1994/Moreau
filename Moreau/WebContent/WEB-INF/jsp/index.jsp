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





<div class="image" id="image">
<img src="IMG/index1.jpg" height="100%" width="100%">
</div>
<div class="center" id="center">


</div>
<div class="add">
<img src="IMG/add.jpg" height="45px" width="45px" onclick="window.location='addcontent.do'">
</div>
<div class="search" id="search">
<form action="" method="get">
<input placeholder="search" name="search">
</form>
<a class="hot">HOT</a>
<p onclick="window.location='index.do?search=阿里'">阿里 </p><p>腾讯 </p><p>百度 </p><p>阿里 </p><p>腾讯 </p><p>百度 </p>
</div>

<div class="bottom">
<ul>
	<li ><a href="" class="active">首页</a></li>
	<li ><a href="offer.do">发现</a></li>
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

for(var i = 0; i < list.length; i++){
	var li = document.createElement('li');
	var url = 'detail.do?content_id='+list[i]['id'];
	li.onclick=function close(url){return function(){window.location=url;}}(url);
	var p = document.createElement('p');
	p.className = 'user';
	p.innerHTML = list[i]['user_name'];
	li.appendChild(p);
	var p = document.createElement('p');
	p.className = 'type';
	p.innerHTML = list[i]['type'];
	li.appendChild(p);
	var p = document.createElement('p');
	p.className = 'time';
	p.innerHTML = list[i]['contentDate'];
	li.appendChild(p);

	var p = document.createElement('p');
	p.className = 'result';
	p.innerHTML = '&nbsp&nbsp面试'+list[i]['result']+'&nbsp&nbsp';
	if(list[i]['result'] == '未过'){
		p.setAttribute('style','border-color:red;color:red;');
	}
	if(list[i]['result'] == '未知'){
		p.setAttribute('style','border-color:#868404;color:#868404;');
	}
	li.appendChild(p);
	var p = document.createElement('p');
	p.className = 'company';
	p.innerHTML = list[i]['company'];
	li.appendChild(p);
	var p = document.createElement('p');
	p.className = 'job';
	p.innerHTML = list[i]['job'];
	li.appendChild(p);
	var p = document.createElement('p');
	p.className = 'content';
	p.innerHTML = list[i]['content'];
	li.appendChild(p);
	ele.appendChild(li);
}
</script>
</body>
</html>