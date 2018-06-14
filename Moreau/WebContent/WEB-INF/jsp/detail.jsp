<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
 <link rel="stylesheet" href="CSS/detail.css" type="text/css" />
</head>
<body>
<div class="back" style="">
<a class="back" href="index.do" style="text-decoration:none;color:blue;"><&nbsp&nbsp主页</a>
</div>
<div class="top" id="top">
<a class="click" onclick="clickshow(0)">...</a>
<p class="user">${username }</p>

<p class="company">公司：${company }</p>
<p class="job">岗位：${job }</p>
<p class="result">面试结果：${result }</p>
<p class="time" style="color:#9ca39d;">${time }</p>


</div>
<div class="top1">
<p class="detail" style="color:#34784e">面试详情</p>

</div>
<div class="center">

<p class="content" id="content">
${content }
</p>
</div>
<p style="color:#05ad6f;">评论区</p>
<div class="pinlun">

</div>

<div class="add">
<img src="IMG/add.jpg" height="45px" width="45px" onclick="window.location='addcontent.do'">
</div>

<div class="comment">
<form action="" method="post" id="comment">
<input type="text" placeholder="回复他/她：" name="comment" form="comment">
<button>提交</button>
</form>

</div>
${message }
<script>


function clickshow(id){
	var ele = document.getElementById("comment_id_"+id);
	var show0=[];
	var n=0;
	//for(var j in comment_id){
	//	var ele0 = document.getElementById("comment_id_"+comment_id[j]);
		//alert(ele0);
		
	//	if(comment_id[j] != id){
	//		ele0.style.display='none';
	//	}
	//}
	var show=ele.style.display;
	if(show == 'block'){
		ele.style.display="none";
		//for(var j in comment_id){
			
		//	document.getElementById("comment_id_"+comment_id[j]).style.display='none';
		//}
	}
	else{
		ele.style.display="block";
		for(var j in comment_id){
			if(comment_id[j] != id){
				document.getElementById("comment_id_"+comment_id[j]).style.display='none';
			}
		}
	}
}

function post(url, params) {
	var temp = document.createElement("form");
	temp.action = url;
	temp.method = "post";
	temp.style.display = "none";
	for (var x in params) {
	var opt = document.createElement("textarea");
	opt.name = x;
	opt.value = params[x];
	temp.appendChild(opt);
	}
	document.body.appendChild(temp);
	temp.submit();
	return temp;
}

</script>
</body>
</html>