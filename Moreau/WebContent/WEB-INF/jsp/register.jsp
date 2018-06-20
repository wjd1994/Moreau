<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="js/jquery-1.4.1.min.js" type="text/javascript"></script> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<style>

.back{
margin-bottom:10px;
}
.send{
display:none;
}

.user{
width:100%;
margin-bottom:10px;

height:34px;

background-color:transparent;
border:none;
outline:none;
font-size:125%;
border-bottom:1px solid #e2e2e2;
padding-bottom:0px;

}
.password{
margin-top:0px;
margin-bottom:10px;
width:100%;
font-size:17px;
height:34px;
background-color:transparent;
border:none;
outline:none;
font-size:125%;
border-bottom:1px solid #e2e2e2;
padding-bottom:0px;
}
.user_data{
width:50%;
margin-bottom:10px;
font-size:17px;
height:34px;
display:inline;
background-color:transparent;
border:none;
outline:none;
font-size:125%;
border-bottom:1px solid #e2e2e2;
padding-bottom:0px;
}
.btnSendCode{
margin-bottom:0px;

height:34px;
display:inline;
}
.submit{
	margin-top:10px;
	width:68px;
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
<a class="backa" href="javascript:history.back();" style="margin-top:10px;text-decoration:none;color:blue;"><&nbsp&nbsp返回</a>
</div>
<form action="" method="post" id="login" onkeydown="if(event.keyCode==13){return false;}">
<input type="text" placeholder="send" name="sendmail" form="login" class="send" id="send" value="0">
<input type="text" placeholder="邮箱" name="mail" form="login" class="user" id="mail" oninput="check()">
<input type="text" placeholder="验证码 " name="data" form="login" class="user_data" id="user_data">
<input class="btnSendCode" id="btnSendCode" type="submit" value="获取验证码" onclick="this.form.send.value='2'">
<input type="text" placeholder="昵称" name="user1" form="login" class="user">
<input type="password" placeholder="密码(长度应不低于6位)" name="password" form="login" class="password">
<input type="password" placeholder="确认密码" name="password1" form="login" class="password">
<input type="submit" value="注&nbsp册" class="submit" onclick="this.form.send.value='1'">


</form>
<p style="font-size:10px;color:gray;float:right;">温馨提示：qq邮件可能会被误存入垃圾箱！</p>
</body>
<script type="text/javascript"> 
var ele = document.getElementById("btnSendCode");

var h = ele.offsetLeft;

//document.getElementById("user_data").style.width=h+"px";
var InterValObj; //timer变量，控制时间  
var count = 3; //间隔函数，1秒执行  
var curCount;//当前剩余秒数  
document.getElementById("mail").value=getCookie("mail");
check();
${message}
function sendMessage() {  
  	curCount = count;  
	//设置button效果，开始计时  
     $("#btnSendCode").attr("disabled", "true");  
     $("#btnSendCode").val("重新发送(" + curCount +")");  
     InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次  
	 //向后台发送处理数据  
  
}  
//邮箱匹配
function check(){
	var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
	var obj = document.getElementById("mail"); //要验证的对象
	var ele = document.getElementById("btnSendCode");
	if(obj.value === ""){ //输入不能为空
		ele.disabled=true;
	}else if(!reg.test(obj.value)){ //正则验证不通过，格式不对
		ele.disabled=true;
	}else{
		ele.disabled=false;
	}
 }  
  
//timer处理函数  
function SetRemainTime() {  
            if (curCount == 0) {                  
                window.clearInterval(InterValObj);//停止计时器  
                $("#btnSendCode").removeAttr("disabled");//启用按钮  
                $("#btnSendCode").val("获取验证码");  
            }  
            else {  
                curCount--;  
                $("#btnSendCode").val("重新发送(" + curCount +")");  
            }  
        }  
        
function getCookie(name){
	var strcookie = document.cookie;//获取cookie字符串
	var s="";
	
	for(var i=0;i<strcookie.length;i++){
		if(strcookie[i]!='\"')
			s+=strcookie[i];
	}
	strcookie=s;
	var arrcookie = strcookie.split("; ");//分割
	//遍历匹配
	for ( var i = 0; i < arrcookie.length; i++) {
		var arr = arrcookie[i].split("=");
		if (arr[0] == name){
			return arr[1];
		}
	}
	return "";
}
</script>
</html>