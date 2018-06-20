<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
 <link rel="stylesheet" href="CSS/offer_detail.css" type="text/css" />
</head>
<body>
<div class="center">
<li>
<ul class="ul1"><p class="p1">岗位</p></ul>
<ul class="ul2"><p class="p2" id="job"></p></ul>
</li>
<li>
<ul class="ul1"><p class="p1">行业</p></ul>
<ul class="ul2"><p class="p2" id="industry"></p></ul>
</li>

<li>
<ul class="ul1"><p class="p1">公司</p></ul>
<ul class="ul2"><p class="p2" id="company"></p></ul>
</li>
<li>
<ul class="ul1"><p class="p1">类型</p></ul>
<ul class="ul2"><p class="p2" id="job_type"></p></ul>
</li>
<li>
<ul class="ul1"><p class="p1">地点</p></ul>
<ul class="ul2"><p class="p2" id="place"></p></ul>
</li>
<li>
<ul class="ul1"><p class="p1">学历</p></ul>
<ul class="ul2"><p class="p2" id="education"></p></ul>
</li>

<li>
<ul class="ul1"><p class="p1">工资</p></ul>
<ul class="ul2"><p class="p2" id="moreau"></p></ul>
</li>
<li>
<ul class="ul1"><p class="p1">可信度</p></ul>
<ul class="ul2"><p class="p2" id="reliability"></p></ul>
</li>
<li>
<ul class="ul1"><p class="p1">发布时间</p></ul>
<ul class="ul2"><p class="p2" id="offertime"></p></ul>
</li>
<li>
<ul class="ul1"><p class="p1">备注</p></ul>
<ul class="ul2"><p class="p2" id="content"></p></ul>
</li>
</div>
<div class="bottom">
<ul><li><a href="javascript:check(1)">不可信</a></li></ul>
<ul><li><a href="javascript:check(2)"class="active">真实</a></li></ul>
<form>

</form>
</div>
<div class="pinlun" id="pinlun">
<li>
<p class="active">评论区</p>
</li>
<li>
<p>这么高？</p>
</li>
</div>
<div class="back">
<ul>
<li>
<a class="backa" href="javascript:history.back();" style="text-decoration:none;color:blue;"><&nbsp&nbsp返回</a>

</li>
</ul>
</div>
<div class="add">
<img src="IMG/add.jpg" height="45px" width="45px" onclick="window.location='addcontent.do'">
</div>

<div class="comment">
<form action="" method="post" id="comment">
<input type="text" style="display:none" name = "reliability" id="reliability_input">
<textarea placeholder="回复他/她：" name="comment" form="comment">
</textarea>
<button>提交</button>
</form>

</div>

<script>
function check(data){
	var form = document.getElementById("comment");
	document.getElementById("reliability_input").value=data;
	form.submit();
}
eval("${data}");
document.getElementById("job").innerHTML=job;
document.getElementById("industry").innerHTML=industry;
document.getElementById("company").innerHTML=company;
document.getElementById("job_type").innerHTML=job_type;
document.getElementById("place").innerHTML=place;
document.getElementById("education").innerHTML=education;
document.getElementById("moreau").innerHTML=moreau;
document.getElementById("reliability").innerHTML=reliability;
document.getElementById("offertime").innerHTML=time;
document.getElementById("content").innerHTML=detail;
var comment = ${comment};

var ele = document.getElementById("pinlun");
for(var i = 0; i < comment.length; i++){
	var li = document.createElement("li");
	var p = document.createElement("p");
	p.innerHTML = comment[i];
	li.appendChild(p);
	ele.appendChild(li);
}
</script>
</body>
</html>