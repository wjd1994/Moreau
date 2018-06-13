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
<div class="top">
<p class="user">${username }</p>
</div>
<div class="center">

<p class="content" id="content">
${content }
</p>
</div>

<div class="pinlun">



</div>

<div class="comment">
<form action="" method="post" id="comment">
<input type="text" placeholder="input..." name="comment" form="comment">
<button>提交</button>
</form>

</div>
${message }
<script>


function clickshow(id){
	var ele = document.getElementById("comment_id_"+id);
	var show=ele.style.display;
	if(show == 'block'){
		ele.style.display="none";
	}
	else{
		ele.style.display="block";
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