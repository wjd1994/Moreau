<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>test</title>
<style>
.company{

}
.company p{
  font-size:15px;
  display:inline-block;

  
}
.company input{
  font-size:15px;
  display:inline-block;
  margin-left:60px;
}
.job{

}
.job p{
  font-size:15px;
  display:inline-block;

  
}
.job input{
  font-size:15px;
  display:inline-block;
  margin-left:60px;
}



.type_{

}
.type_ p{
  font-size:15px;
  display:inline-block;
}
.type_ input{
  font-size:15px;
  display:inline-block;
  margin-left:30px;
}
.result{

}
.result p{
  font-size:15px;
  display:inline-block;
}
.result input{
  font-size:15px;
  display:inline-block;
  margin-left:30px;
}
.time p{
   font-size:15px;
   display:inline-block;
   
}
.time input{
   font-size:15px;
   display:inline-block;
   margin-left:30px;
   
}
.education{
  display:none;
}
.education p{
  display:inline-block;
  font-size:15px;
}
.education select{
  display:inline-block;
  margin-left:60px;
  font-size:15px;
}
.industry{
  
}
.industry p{
  display:inline-block;
  font-size:15px;
}
.industry select{
  display:inline-block;
  margin-left:60px;
  font-size:15px;
}
.detail textarea{
  font-size:15px;
  width:100%;
  height:300px;
}
.bottom{
  list-style-type:none;
  display:block;

	width:100%;
	z-index:3;
	bottom:0px;
	line-height:55px;
	font-size:15px;
	border-top:1px solid #e2e2e2;
	overflow:hidden;
}
.bottom li{
  float:left;
  width:50%;
}
.bottom li p{
margin:0;
display:block;
color:#0F0F0F;
text-align:center;
background-color:#fff;
padding:0px;
text-decoration:none;
text-transform:uppercase;
}
.bottom li input{
margin:0;
width:100%;
height:100%
display:block;
color:gray;
text-align:center;
background-color:#e2e2e2;
background-color:transparent;
padding:0px;
text-decoration:none;
text-transform:uppercase;
}
</style>
</head>
<body>
<form action="" method="post" id="addcontent">
  <div class="company">
<p>公司    </p><input type="text" placeholder="公司名称" name ="company" id="company" style="border:none;" onkeydown="if(event.keyCode==13){return false;}" oninput="check()">
  </div>
<div class="job">
<p>岗位    </p><input type="text" placeholder="应聘岗位" name ="job" id="job" style="border:none;" onkeydown="if(event.keyCode==13){return false;}" oninput="check()">
  </div>
    <div class="type_">
    <p>
      应聘类型
    </p>
    <input name ="type_" type="radio" value="校招" checked>校招
    <input name ="type_" type="radio" value="社招">社招
    <input name ="type_" type="radio" value="实习">实习
  </div>
  
    <div class="result">
    <p>
      面试结果
    </p>

    <input name ="result" type="radio" value="通过" checked>通过
    <input name ="result" type="radio" value="未过">未过
    <input name ="result" type="radio" value="未知">未知

  </div>
    <div class="time">
  <p>面试时间</p>
  <input name="time" type="date" id="time" oninput="check()">
  </div>
  <div class="education">
    <p>
      学历
    </p>
    <select name="education">
      <option value="博士981/211">博士981/211</option>
      <option value="硕士985/211" selected>硕士985/211</option>
      <option value="本科985/211">本科985/211</option>
      <option value="博士其他">博士其他</option>
      <option value="硕士其他">硕士其他</option>
      <option value="本科其他">本科其他</option>
      <option value="专科">专科</option>
      <option value="其他">其他</option>
    </select>
  </div>
  <div class="industry">
    <p>
      行业
    </p>
    <select name="industry">
      <option value="IT|互联网|通信">IT|互联网|通信</option>
      <option value="金融">金融</option>
      <option value="房产|建筑|物业管理">房产|建筑|物业管理</option>
      <option value="电网">电网</option>
      <option value="物流|采购">物流|采购</option>
      <option value="汽车|机械|自动化|设计">汽车|机械|自动化|设计</option>
      <option value="物流|采购">物流|采购</option>
      <option value="教育|咨询|翻译|法律">教育|咨询|翻译|法律</option>
      <option value="人力资源|财务|行政">人力资源|财务|行政</option>
      <option value="市场|销售">市场|销售</option>
    </select>
  </div>

  <div class="detail">
    <textarea name="detail" placeholder="面试详情" id="detail" oninput="check()"></textarea>
  </div>
  <div class="bottom">
  <li>
    <p onclick="javascript:history.back();" style="background-color:blue">
      放弃
    </p>
    </li>
    <li >
    <input type="submit" value="提交" disabled=true style="border:0;" id="submit">
    
    </li>

  </div>
  </form>
  ${message }
 <script>

// var button = document.getElementById("submit");
 //button.disabled=false;
 function check() {
     //alert("ok");
     var company = document.getElementById("company").value;
     var job = document.getElementById("job").value;
     var time = document.getElementById("time").value;
     var detail = document.getElementById("detail").value;
     var button = document.getElementById("submit");
     if (company && company.length >= 1 && job && job.length >= 1 && time && detail && detail.length>=1){
         button.disabled = false;
         button.style.color = "black";
     }
     else{
         button.disabled = true;
         button.style.color = "gray";
     }
 }
 </script>
</body>
</html>