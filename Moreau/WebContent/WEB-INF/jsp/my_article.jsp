<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
 <link rel="stylesheet" href="CSS/index.css" type="text/css" />
 <style>
 .back{
	display:block;
	position:fixed;
	width:100%;
	z-index:3;
	top:0px;
	line-height:45px;
	font-size:15px;
	border-top:1px solid #e2e2e2;
	overflow:hidden;
}
.back ul
{
list-style-type:none;
margin:0;
padding:0;

}

.back li a{
	display:block;
color:#0F0F0F;

background-color:#fff;
padding:0px;
text-decoration:none;
text-transform:uppercase;
}
.center{
margin-top:45px;
}
 </style>
</head>
<body>






<div class="center" id="center">


</div>
<div class="back">
<ul>
<li>
<a class="backa" href="my.do" style="text-decoration:none;color:blue;"><&nbsp返回</a>

</li>
</ul>
</div>
<div class="add">
<img src="IMG/add.jpg" height="45px" width="45px" onclick="window.location='addcontent.do'">
</div>
<script>

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