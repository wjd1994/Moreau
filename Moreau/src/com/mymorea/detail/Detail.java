package com.mymorea.detail;

import org.apache.tomcat.jni.Time;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.portlet.ModelAndView;

import com.mymoreau.mysql.mysqlread;


import java.sql.*;
import java.util.*;
import java.util.List;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringEscapeUtils;

@RestController
@RequestMapping("/")
public class Detail {
	
	

	
	private String dealString(String xss){
		
		xss = StringEscapeUtils.escapeHtml4(xss);
		//xss = StringEscapeUtils.escapeEcmaScript(xss);
		xss = xss.replaceAll("\r\n", "<br>");
		xss = xss.replaceAll("\n", "<br>");
		return xss.replaceAll("'","’");
		
	}
	private String returnString(String xss){
		xss = StringEscapeUtils.unescapeHtml4(xss);
		xss = xss.replaceAll("<br>", "\r\n");
		return xss.replaceAll("’", "'");
	}
	private  MimeMessage createSimpleMail(Session session,String recive,String data) throws AddressException,MessagingException{
		//创建邮件对象
		MimeMessage mm=new MimeMessage(session);
		//设置发件人
		mm.setFrom(new InternetAddress("13002341389@163.com"));
		//设置收件人
		mm.setRecipient(Message.RecipientType.TO, new InternetAddress(recive));
		//设置抄送人
		mm.setRecipient(Message.RecipientType.CC, new InternetAddress("13002341389@163.com"));

		mm.setSubject("MoreauShow的注册验证码");
		mm.setContent(data, "text/html;charset=gbk");

		return mm;

		}
	@RequestMapping("index")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		String current_url =  request.getServletPath()+"?"+request.getQueryString();
		
		Cookie cookie0= new Cookie("url",current_url);
		cookie0.setMaxAge(360*24*60*60);
		cookie0.setPath("/");
		response.addCookie(cookie0);
		if(request.getSession().getAttribute("account") == null){
			Cookie []cookies = request.getCookies();
			for(Cookie cookie:cookies){
				if(cookie.getName().equals("login")){
					request.getSession().setAttribute("account",cookie.getValue());
					
				}
			}
		}
		String js = "";
		String search = request.getParameter("search");
		mysqlread mysql = new mysqlread();
		mysql.init();
		
		if(search == null || search == ""){
			mysql.queryAll("select * from content order by contentDate desc limit 0,10");
			
			ArrayList<Map> list = new ArrayList<Map>();
			mysqlread mysql1 = new mysqlread();
			mysql1.init();
			while(mysql.next()){
				Map m = new HashMap();
				m.put("'id'",mysql.getInt("id"));
				m.put("'company'", "'"+mysql.getString("company")+"'");
				m.put("'type'", "'"+mysql.getString("type")+"'");
				m.put("'result'", "'"+mysql.getString("result")+"'");
				m.put("'contentDate'", "'"+mysql.getString("contentDate")+"'");
				String content = mysql.getString("detail");
				content = content.replaceAll("\r\n","<br>");
				content = content.replaceAll("\n","<br>");
				m.put("'content'", "'"+content+"'");
				m.put("'job'", "'"+mysql.getString("job")+"'");
				mysql1.queryAll("select name from userinfo where id="+mysql.getInt("user_id"));
				mysql1.next();
				m.put("'user_name'", "'"+mysql1.getString("name")+"'");
				
				list.add(m);

			}
			String list_str = list.toString();
			list_str = list_str.replaceAll("'=","':");
			js += "var list="+list_str+";";
			
			model.addAttribute("message",js);
			
			return "index";
		}
		else{
			mysql.queryAll("select * from content where company like '%"+search+"%' order by contentDate");
			ArrayList<Map> list = new ArrayList<Map>();
			mysqlread mysql1 = new mysqlread();
			mysql1.init();
			while(mysql.next()){
				Map m = new HashMap();
				m.put("'id'",mysql.getInt("id"));
				m.put("'company'", "'"+mysql.getString("company")+"'");
				m.put("'type'", "'"+mysql.getString("type")+"'");
				m.put("'result'", "'"+mysql.getString("result")+"'");
				m.put("'contentDate'", "'"+mysql.getString("contentDate")+"'");
				String content = mysql.getString("detail");
				content = content.replaceAll("\r\n","<br>");
				content = content.replaceAll("\n","<br>");
				m.put("'content'", "'"+content+"'");
				m.put("'job'", "'"+mysql.getString("job")+"'");
				mysql1.queryAll("select name from userinfo where id="+mysql.getInt("user_id"));
				mysql1.next();
				m.put("'user_name'", "'"+mysql1.getString("name")+"'");
				System.out.println(m);
				list.add(m);

			}
			String list_str = list.toString();
			list_str = list_str.replaceAll("'=","':");
			js += "var list="+list_str+";";
			model.addAttribute("message",js);
			return "index";	
		}
		
		
		
	}
	
	@RequestMapping("offer")
	public String offer(Model model, HttpServletRequest request){
		mysqlread mysql = new mysqlread();
		mysql.init();
		Object search = request.getParameter("search");
		if(search != null && search != ""){
			mysql.queryAll("select * from offer where company like'%"+search+"%'");
			
			List<Map> list = new ArrayList<>();
			while(mysql.next()){
				Map map = new HashMap();
				map.put("id", mysql.getInt("id"));
				map.put("company", "'"+mysql.getString("company")+"'");
				map.put("time", "'"+mysql.getString("offertime")+"'");
				map.put("job", "'"+mysql.getString("job")+"'");
				map.put("salary", "'"+mysql.getString("salary")+"'");
				map.put("reliability", mysql.getInt("reliability"));
				list.add(map);
			}
			String js = "var list="+list.toString().replaceAll("=",":");
			model.addAttribute("message",js);
			return "offer";
		}
		mysql.queryAll("select * from offer order by offertime desc limit 0,10");
		
		List<Map> list = new ArrayList<>();
		while(mysql.next()){
			Map map = new HashMap();
			map.put("id", mysql.getInt("id"));
			map.put("company", "'"+mysql.getString("company")+"'");
			map.put("time", "'"+mysql.getString("offertime")+"'");
			map.put("job", "'"+mysql.getString("job")+"'");
			map.put("salary", "'"+mysql.getString("salary")+"'");
			map.put("reliability", mysql.getInt("reliability"));
			list.add(map);
		}
		String js = "var list="+list.toString().replaceAll("=",":");
		model.addAttribute("message",js);
		return "offer";
	}
	
	@RequestMapping("offer_detail")
	public String offer_detail(Map<String, Object> map,HttpServletRequest request){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		Map<String,String> map1 = new HashMap<String,String>();
		String offer_id;
		try{
			offer_id = request.getParameter("offer_id");
		}catch(Exception e){
			return "redirect:/offer.do";
		}
		mysqlread mysql = new mysqlread();
		mysql.init();
		String reliability = request.getParameter("reliability");
		System.out.println(reliability);
		if(reliability != null && reliability != ""){
			if(reliability.equals("1")){
				mysql.update("update offer set reliability=reliability-1 where id="+offer_id);
				mysql.close();
				return "redirect:offer_detail.do?offer_id="+offer_id;
			}
			else{
				mysql.update("update offer set reliability=reliability+1 where id="+offer_id);
				mysql.close();
				return "redirect:offer_detail.do?offer_id="+offer_id;
			}
		}
		String comment_content = request.getParameter("comment");
		if(comment_content != null && comment_content != ""){
			comment_content = dealString(comment_content);
			mysql.update("insert into offer_comment(content,offer_id) values('"+comment_content+"',"+offer_id+")");
			mysql.close();
			return "redirect:/offer_detail.do?offer_id="+offer_id;
		}
		mysql.queryAll("select * from offer where id="+offer_id);
		mysql.next();
		map1.put("job", "'"+mysql.getString("job")+"'");
		map1.put("industry", "'"+mysql.getString("industry")+"'");
		map1.put("company", "'"+mysql.getString("company")+"'");
		map1.put("job_type", "'"+mysql.getString("job_type")+"'");
		map1.put("place","'"+mysql.getString("place")+"'");
		map1.put("education", "'"+mysql.getString("education")+"'");
		map1.put("moreau", "'"+mysql.getString("salary")+"'");
		map1.put("reliability", "'"+Integer.toString(mysql.getInt("reliability"))+"'");
		map1.put("time", "'"+mysql.getString("offertime")+"'");
		map1.put("detail", "'"+mysql.getString("detail")+"'");
		mysql.queryAll("select * from offer_comment where offer_id="+offer_id);
		ArrayList comment = new ArrayList();
		int i = 0;
		while(mysql.next()){
			comment.add("'"+mysql.getString("content")+"'");
		}
		
		mysql.close();
		map.put("data", map1);
		map.put("comment", comment);
		return "offer_detail";
	}
	
	@RequestMapping("addoffer")
	public String addoffer(Model model, HttpServletRequest request){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		Object company = request.getParameter("company");
		if(company == null){
			return "addoffer";
		}
		String company_ = dealString(company.toString());
		String job = dealString(request.getParameter("job").toString());
		String salary = dealString(request.getParameter("salary").toString());
		String place = dealString(request.getParameter("place").toString());
		String type_ = request.getParameter("type_");
		String education = request.getParameter("education");
		String industry = request.getParameter("industry");
		String detail = dealString(request.getParameter("detail").toString());
		String offertime = "";
		Calendar now = Calendar.getInstance();
		offertime = now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
		String sql = String.format("insert into offer(company,job,industry,job_type,place,education,salary,offertime,detail,reliability,view) values('%s','%s','%s','%s','%s','%s','%s','%s','%s',%d,%d)" ,company_,job,industry,type_,place,education,salary,offertime,detail,4,0);
		mysqlread mysql = new mysqlread();
		mysql.init();
		mysql.update(sql);
		mysql.close();
		return "redirect:/offer.do";
	}
	
	@RequestMapping("my")
	public String my(Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		Object account = session.getAttribute("account");
		if(account == null){
			return "redirect:/login.do?url=my.do";
		}
		mysqlread mysql = new mysqlread();
		mysql.init();
		mysql.queryAll("select name from userinfo where account='"+account+"'");
		mysql.next();
		model.addAttribute("username",mysql.getString("name"));
		model.addAttribute("useraccount",account);
		mysql.close();
		return "my";
	}
	@RequestMapping("userinfo")
	public String userinfo(Model model, HttpServletRequest request){
		String username = request.getParameter("username");
		Object account = request.getSession().getAttribute("account");
		
		if(account == null)
			return "redirect:index.do";
		mysqlread mysql = new mysqlread();
		mysql.init();
		
		if(username != null){
			username = dealString(username);
			if(username == ""){
				model.addAttribute("message","alert('输入的信息为空')");
				mysql.close();
				return "userinfo";
			}
			else if(username.length() > 10){
				model.addAttribute("message","alert('昵称长度不能多于10位')");
				mysql.close();
				return "userinfo";
			}
			else{
				mysql.update("update userinfo set name='"+username+"' where account='"+account+"'");
				mysql.close();
				return "redirect:/my.do";
			}
		}
		
		mysql.queryAll("select name from userinfo where account='"+account+"'");
		mysql.next();
		model.addAttribute("message","document.getElementById('username').value='"+returnString(mysql.getString("name"))+"';");
		mysql.close();
		return "userinfo";
	}
	
	@RequestMapping("alter_password")
	public String alter_password(Model model, HttpServletRequest request){
		if(request.getSession().getAttribute("account") == null){
			return "redirect:/index.do";
		}
		Object password0 = request.getParameter("password0");
		Object password1 = request.getParameter("password1");
		Object password2 = request.getParameter("password2");
		
		if(password0 != null){
			if(password0 != "" && password1 != "" && password2 != ""){
				mysqlread mysql = new mysqlread();
				mysql.init();
				mysql.queryAll("select password from userinfo where account='"+request.getSession().getAttribute("account")+"'");
				mysql.next();
				
				if(!dealString(password0.toString()).equals(mysql.getString("password"))){
					model.addAttribute("message","alert('原密码不正确！');");
					mysql.close();
					return "alter_password";
				}
				if(password1.toString().length() < 6){
					model.addAttribute("message","alert('新密码长度不能低于6位！');");
					mysql.close();
					return "alter_password";
				}
				if(!password1.equals(password2)){
					model.addAttribute("message","alert('前后密码不一致！');");
					mysql.close();
					return "alter_password";
				}
				mysql.update("update userinfo set password='"+dealString(password1.toString())+"' where account='"+request.getSession().getAttribute("account")+"'");
				mysql.close();
				model.addAttribute("message","alert('修改密码成功！');");
			}
			else{
				model.addAttribute("message","alert('请输入完整信息');");
			}
		}
		return "alter_password";
	}
	
	@RequestMapping("my_article")
	public String myarticle(Model model,HttpServletRequest request, HttpServletResponse response){
		
		String current_url =  request.getServletPath()+"?"+request.getQueryString();
		
		Cookie cookie0= new Cookie("url",current_url);
		cookie0.setMaxAge(360*24*60*60);
		cookie0.setPath("/");
		response.addCookie(cookie0);
		mysqlread mysql = new mysqlread();
		mysql.init();
		mysql.queryAll("select id from userinfo where account='"+request.getSession().getAttribute("account")+"'");
		mysql.next();
		int login_id = mysql.getInt("id");
		mysql.queryAll("select * from content where user_id="+login_id);
		ArrayList<Map> list = new ArrayList<Map>();
		mysqlread mysql1 = new mysqlread();
		mysql1.init();
		while(mysql.next()){
			Map m = new HashMap();
			m.put("'id'",mysql.getInt("id"));
			m.put("'company'", "'"+mysql.getString("company")+"'");
			m.put("'type'", "'"+mysql.getString("type")+"'");
			m.put("'result'", "'"+mysql.getString("result")+"'");
			m.put("'contentDate'", "'"+mysql.getString("contentDate")+"'");
			String content = mysql.getString("detail");
			content = content.replaceAll("\r\n","<br>");
			content = content.replaceAll("\n","<br>");
			m.put("'content'", "'"+content+"'");
			m.put("'job'", "'"+mysql.getString("job")+"'");
			mysql1.queryAll("select name from userinfo where id="+mysql.getInt("user_id"));
			mysql1.next();
			m.put("'user_name'", "'"+mysql1.getString("name")+"'");
			System.out.println(m);
			list.add(m);

		}
		String list_str = list.toString();
		list_str = list_str.replaceAll("'=","':");
		String js = "";
		js += "var list="+list_str+";";
		model.addAttribute("message",js);
		
		return "my_article";
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		Cookie cookie= new Cookie("login",null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		request.getSession().removeAttribute("account");
		return "redirect:/index.do";
	}
	@RequestMapping("detail")
	public String printdeatil(Model model,HttpServletRequest request) {
		String format_str = "";
		String user_name = "";
		String other_name = "";
		String user = "";
		String current_url = "";
		int user_id;
		int comment_id;
		int content_id;
		int other_id;
		int reply_id;
		int login_id;
		
		try{
			request.setCharacterEncoding("utf-8");
			
		}catch(Exception e){
			
		}
		
		String url =  request.getServletPath()+"?"+request.getQueryString();
		
		HttpSession session = request.getSession();
		content_id = Integer.parseInt( request.getParameter("content_id"));
		mysqlread mysql = new mysqlread();
		mysql.init();
		mysql.queryAll("select * from content where id="+content_id);
		mysql.next();
		String content = mysql.getString("detail");
		
		content = content.replaceAll("\r\n","<br>");
		content = content.replaceAll("\n", "<br>");
		String company = mysql.getString("company");
		String job = mysql.getString("job");
		String result = mysql.getString("result");
		String time = mysql.getString("contentDate");
		user_id = mysql.getInt("user_id");
		mysql.queryAll("select * from userinfo where id="+user_id);
		mysql.next();
		String main_user_name =mysql.getString("name");
		String account =mysql.getString("account");
		mysqlread mysql1 = null;
		mysqlread mysql2 = null;
		mysql1 = new mysqlread();
		mysql1.init();
		mysql2 = new mysqlread();
		mysql2.init();
		String js = "<script>var ele=document.getElementById('top');var select=document.createElement('div');select.className='select0';select.id='comment_id_0';";
		if(account.equals(session.getAttribute("account"))){
			format_str = String.format("content_id=%d", content_id);
			js += "var p=document.createElement('p');p.innerHTML='修改';select.appendChild(p);p.onclick=function(){window.location='alter_content.do?"+format_str+"'};var p=document.createElement('p');p.innerHTML='删除';select.appendChild(p);p.onclick=function(){window.location='delete_content.do?"+format_str+"'};";
		}
		else{
			js += "var p=document.createElement('p');p.innerHTML='举报';select.appendChild(p);";
		}
		js +="ele.appendChild(select);var pinlun = document.getElementsByClassName('pinlun');var comment_id=[];var i=0;comment_id[i]=0;";
		
		mysql.queryAll("select id from userinfo where account='"+session.getAttribute("account")+"';");
		mysql.next();
		login_id = mysql.getInt("id");
		System.out.println("login_id:"+login_id);
		mysql.queryAll("select * from comment where content_id="+content_id); 
		while(mysql.next()){
			if(mysql.getInt("reply") == 0){
				comment_id = mysql.getInt("id");
				user_id = mysql.getInt("user_id");
				//System.out.println(user_id);
				other_id = mysql.getInt("other_id");
				reply_id = mysql.getInt("reply");
				mysql1.queryAll("select * from userinfo where id="+user_id);
				mysql1.next();
				user_name = mysql1.getString("name");
				js += "var father = document.createElement('ul');father.className='father';";
				js += "var pinlun_user=document.createElement('li');pinlun_user.className='pinlun_user';";
				js += "pinlun_user.innerHTML='"+user_name+"';father.appendChild(pinlun_user);";
				
				js += "comment_id[++i]="+comment_id+";var a=document.createElement('a');a.innerHTML='...';a.className='click';a.onclick=function(){clickshow("+comment_id+")};father.appendChild(a);";
				js += "var p=document.createElement('p');p.innerHTML='：';father.appendChild(p);";
				js += "var div=document.createElement('div');div.className='select';div.id='comment_id_"+comment_id+"';";
				//format_str = String.format("id=%d&user_id=%d&other_id=%d&reply=%d&content_id=%d", comment_id,user_id,other_id,reply_id,content_id);
				//js += "var p=document.createElement('p');p.innerHTML='回复';div.appendChild(p);p.onclick=function(){window.location='comment_reply.do?"+format_str+"'};";
				if(login_id==user_id){
					format_str = String.format("id=%d&user_id=%d&other_id=%d&reply=%d&content_id=%d", comment_id,user_id,other_id,reply_id,content_id);
					js += "var p=document.createElement('p');p.innerHTML='删除';div.appendChild(p);p.onclick=function(){window.location='delete.do?" +format_str+"'};";
					js += "father.appendChild(div);";
				
				}
				else{
					format_str = String.format("id=%d&user_id=%d&other_id=%d&reply=%d&content_id=%d", comment_id,user_id,other_id,reply_id,content_id);
					js += "var p=document.createElement('p');p.innerHTML='回复';div.appendChild(p);p.onclick=function(){window.location='comment_reply.do?" +format_str+"'};";
					js += "var p=document.createElement('p');p.innerHTML='举报';div.appendChild(p);father.appendChild(div);";
				}
				js += "var pinlun_content=document.createElement('p');pinlun_content.className='pinlun_content';pinlun_content.innerHTML='"+mysql.getString("content")+"';";
				js += "father.appendChild(pinlun_content);pinlun[0].appendChild(father);";
				
				
				mysql2.queryAll("select * from comment where reply="+comment_id+" and content_id="+content_id);
				while(mysql2.next()){
					comment_id = mysql2.getInt("id");
					user_id = mysql2.getInt("user_id");
					other_id = mysql2.getInt("other_id");
					reply_id = mysql2.getInt("reply");
					mysql1.queryAll("select * from userinfo where id="+user_id);
					mysql1.next();
					user_name = mysql1.getString("name");
					mysql1.queryAll("select * from userinfo where id="+other_id);
					mysql1.next();
					other_name = mysql1.getString("name");
					
					js += "var reply=document.createElement('ul');reply.className='reply';reply.onclick=function(){clickshow("+comment_id+")};var li=document.createElement('li');li.innerHTML='"+user_name+"';reply.appendChild(li);";
					js += "var li=document.createElement('li');li.innerHTML='回复';li.style.color='black';reply.appendChild(li);var li=document.createElement('li');li.innerHTML='"+other_name+"';reply.appendChild(li);";
					js += "comment_id[++i]="+comment_id+";var div=document.createElement('div');div.className='select_child';div.id='comment_id_"+comment_id+"';";
					//format_str = String.format("id=%d&user_id=%d&other_id=%d&reply=%d&content_id=%d", comment_id,user_id,other_id,reply_id,content_id);
					//js += "var p=document.createElement('p');p.innerHTML='回复';div.appendChild(p);p.onclick=function(){window.location='comment_reply.do?"+format_str+"'};";
					if(login_id==user_id){
						format_str = String.format("id=%d&user_id=%d&other_id=%d&reply=%d&content_id=%d", comment_id,user_id,other_id,reply_id,content_id);
						js += "var p=document.createElement('p');p.innerHTML='删除';div.appendChild(p);p.onclick=function(){window.location='delete.do?"+format_str+"'};";
					
						js += "reply.appendChild(div);";
					
					}
					else{
						format_str = String.format("id=%d&user_id=%d&other_id=%d&reply=%d&content_id=%d", comment_id,user_id,other_id,reply_id,content_id);
						js += "var p=document.createElement('p');p.innerHTML='回复';div.appendChild(p);p.onclick=function(){window.location='comment_reply.do?" +format_str+"'};";
					
						js += "var p=document.createElement('p');p.innerHTML='举报';div.appendChild(p);reply.appendChild(div);";
					
					}
					js += "var li=document.createElement('li');li.innerHTML='：';reply.appendChild(li);";
					js += "var p=document.createElement('p');p.innerHTML='"+mysql2.getString("content")+"';reply.appendChild(p);father.appendChild(reply);";
					
				}
			}
		}
		js += "var ul=document.createElement('ul');ul.innerHTML='没有更多了';ul.style.color='#e7eaeb';pinlun[0].appendChild(ul);";

		
		
		
		String comment = (String)request.getParameter("comment");
		if(comment != null){
			comment = dealString(comment);
			if(comment.equals("")){
				System.out.println("kong");
				js += "alert('kong');";
				
				

				//return "redirect:/detail.do?content_id="+comment_id;
			}
			else{
				if(session.getAttribute("account") == null){
					//js += "alert('login please');";
					
					mysql.close();
					mysql1.close();
					mysql2.close();
					return "redirect:/login.do?url="+url;
				}
				
				mysql.update("insert into comment(user_id,other_id,reply,content,content_id) values("+login_id+",0,0,'"+comment+"',"+content_id+")");
				
				mysql.close();
				mysql1.close();
				mysql2.close();
				//return new ModelAndView("redirect:/toList");
				return "redirect:/detail.do?content_id="+content_id;
			}
		}
		
		js += "</script>";
		model.addAttribute("message",js);
		model.addAttribute("username",main_user_name);
		model.addAttribute("company",company);
		model.addAttribute("job",job);
		model.addAttribute("result",result);
		model.addAttribute("time",time);
		model.addAttribute("content",content);
		mysql.close();
		mysql1.close();
		mysql2.close();
		model.addAttribute("url","/index.do");
		Cookie []cookies = request.getCookies();
		for(Cookie cookie:cookies){
			if(cookie.getName().equals("url")){
				model.addAttribute("url",cookie.getValue());
			}
		}
		return "detail";
	}

	private Object String(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@RequestMapping("login")
	public String login(Model model,HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		
		String url;
		try{
			url = request.getParameter("url");
		}catch(Exception e){
			return "redirect:/index.do";
		}
		HttpSession session = request.getSession();
		//用cookie直接登录
		Cookie []cookies = request.getCookies();
		for(Cookie cookie:cookies){
			if(cookie.getName().equals("login")){
				session.setAttribute("account",cookie.getValue());
				return "redirect:/"+url;
			}
		}
		
		//
		String current_url =  request.getServletPath()+"?"+request.getQueryString();
		
		Object password = request.getParameter("password");
		mysqlread mysql = new mysqlread();
		mysql.init();
		
		mysql.queryAll("select password from userinfo where account = '"+request.getParameter("user")+"'");
		
		
		
		mysql.next();
		
		
		if(password != null){
			if(mysql.getString("password").equals("error")){
				model.addAttribute("message","alert('帐号不存在！');");
				model.addAttribute("url","/Moreau/"+url);
				model.addAttribute("register","/Moreau"+current_url);
				mysql.close();
				return "login";
			}
			if(dealString(password.toString()).equals(mysql.getString("password"))){
				
				session.setAttribute("account", request.getParameter("user"));
				System.out.println("ok");
				Cookie cookie = new Cookie("login", request.getParameter("user"));  
	            cookie.setMaxAge(30*24*60 * 60);// 设置为30min  
	            cookie.setPath("/");  
	            System.out.println("已添加===============");  
	            response.addCookie(cookie); 
	            mysql.close();
				return "redirect:"+url;
			}
			else{
				model.addAttribute("message","alert('密码不正确！');");
				model.addAttribute("url","/Moreau/"+url);
				model.addAttribute("register","/Moreau"+current_url);
				mysql.close();
				return "login";
			}
		}
		model.addAttribute("url","/Moreau/"+url);
		model.addAttribute("register","/Moreau"+current_url);
		mysql.close();
		return "login";
	}
	@RequestMapping("register")
	public String register(Model model,HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		HttpSession session1 = request.getSession();
		//String s=request.getParameter("sendmail");
		
		String mail = request.getParameter("mail");
		
		System.out.println("mail:"+mail);
		
		
		String send = request.getParameter("sendmail");
		if(mail != null){
			//mail = dealString(mail);
			if(mail == ""){
				System.out.println(send);
				model.addAttribute("message","alert('kong');");
			}
			else{
				if(send.equals("1")){
					
					Cookie cookie = new Cookie("mail", mail);  
		            cookie.setMaxAge(30 * 60);// 设置为30min  
		            cookie.setPath("/");  
		            System.out.println("已添加===============");  
		            response.addCookie(cookie); 
		            String input_data = request.getParameter("data");
		            String user1 = dealString(request.getParameter("user1"));
		            String password = dealString(request.getParameter("password"));
		            String password1 = dealString(request.getParameter("password1"));
		            if(!input_data.equals(session1.getAttribute("data"))){
		            	model.addAttribute("message","alert('验证码不正确！')");
		            	return "register";
		            }
		            if(user1 == ""){
		            	user1 = "匿名用户";
		            }
		            if(password.length()<6){
		            	model.addAttribute("message","alert('密码位数不能低于6位！')");
		            	return "register";
		            }
		            if(!password.equals(password1)){
		            	model.addAttribute("message","alert('前后密码不一致！')");
		            	return "register";
		            }
		            mysqlread mysql = new mysqlread();
		            mysql.init();
		            String sql = String.format("insert into userinfo(name,password,account) values('%s','%s','%s')", user1,password,session1.getAttribute("mail"));
		            mysql.update(sql);
		            mysql.close();
		            session1.removeAttribute("data");
		            session1.removeAttribute("mail");
		            
					model.addAttribute("message","alert('注册成功！')");
		            //if(data == "")
				}
				else if(send.equals("2")){
					String mail1 = "";
					for(int i=0;i<mail.length();i++){
						if(mail.charAt(i)=='\"')
							continue;
						else
							mail1 += mail.charAt(i);
					}
					System.out.println("mail1:"+mail1);
					
					//判断mail在数据库是否存在
					mysqlread mysql = new mysqlread();
					mysql.init();
					mysql.queryAll("select * from userinfo where account='"+mail+"'");
					mysql.next();
					if(!mysql.getString("account").equals("error")){
						model.addAttribute("message","alert('该邮箱已注册！')");
						mysql.close();
		            	return "register";
					}
					mysql.close();
					//不存在时执行下面
					//mail存放session中防止用户前后两次输入邮箱不对的漏洞
					session1.setAttribute("mail", mail);
					Cookie cookie = new Cookie("mail", mail);  
		            cookie.setMaxAge(30 * 60);// 设置为30min  
		            cookie.setPath("/");  
		            System.out.println("已添加===============");  
		            response.addCookie(cookie); 
		            
		            int data1 = (int)((Math.random()*9+1)*10000);
					String data = ""+data1+"";
					System.out.println("data:"+data);
					
					session1.setAttribute("data",data);
		           
					Properties prop=new Properties();
					prop.put("mail.host","smtp.163.com" );
					prop.put("mail.transport.protocol", "smtp");
					prop.put("mail.smtp.auth", true);
					//使用java发送邮件5步骤
					//1.创建sesssion
					Session session=Session.getInstance(prop);
					//开启session的调试模式，可以查看当前邮件发送状态
					session.setDebug(true);
	
					try{
					//2.通过session获取Transport对象（发送邮件的核心API）
						Transport ts=session.getTransport();
					//3.通过邮件用户名密码链接
						ts.connect("13002341389@163.com", "wjd1994121715");
	
	
					//4.创建邮件
					
					
						Message msg=createSimpleMail(session,mail,"您的注册验证码为："+data);
	
	
					//5.发送电子邮件
	
						ts.sendMessage(msg, msg.getAllRecipients());
					}catch(Exception e){
						
					}
					
					model.addAttribute("message","sendMessage();");
				}
		
			}
		}
		else{
			model.addAttribute("message","");
		}
		String password = (String)request.getParameter("password");
		String url;
		try{
			url = request.getParameter("url1");
		}catch(Exception e){
			return "redirect:/index.do";
		}
		String current_url =  request.getServletPath()+"?"+request.getQueryString();
		model.addAttribute("url",url);
		
		return "register";
	}
	@RequestMapping("sendmail")
	public String sendmail(Model model){
		System.out.println("sendmail");
		return "redirect:/register.do?sendmail=126";
	}
	@RequestMapping("comment_reply")
	public String comment_reply(Model model, HttpServletRequest request,HttpServletResponse resposne){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		int comment_id;
		int other_id;
		int reply_id;
		int content_id;
		int user_id;
		try{
			comment_id = Integer.parseInt(request.getParameter("id"));
			other_id = Integer.parseInt(request.getParameter("user_id"));
			reply_id = Integer.parseInt(request.getParameter("reply"));
			content_id = Integer.parseInt(request.getParameter("content_id"));
			model.addAttribute("content_id",content_id);
		}catch(Exception e){
			//js += "<script>alert('请不要恶意攻击本网站');</script>";
			//model.addAttribute("message",js);
			return "redirect:/index.do";
		}
		String content = request.getParameter("reply0");
		String js = "";
		
		if(content == null){
			HttpSession session = request.getSession();
			/*
			mysqlread mysql = new mysqlread();
			mysql.init();
			mysql.queryAll("select id from userinfo where account='"+session.getAttribute("account")+"';");
			mysql.next();
			login_id = mysql.getInt("id");
			mysql.close();
			*/
			if(session.getAttribute("account") == null){
				String url = "detail.do?content_id="+content_id;
				return "redirect:/login.do?url="+url;
			}
			model.addAttribute("message",js);
			return "comment_reply";
		}
		content = dealString(content);
		if(content.equals("")){
			//String current_url =  request.getServletPath()+"?"+request.getQueryString();
			js += "<script>alert('kong');</script>";
			model.addAttribute("content_id",content_id);
			model.addAttribute("message",js);
			return "comment_reply";
			//return "redirect:/"+current_url;
		}
		else{
			HttpSession session = request.getSession();
			mysqlread mysql = new mysqlread();
			mysql.init();
			mysql.queryAll("select id from userinfo where account='"+session.getAttribute("account")+"';");
			mysql.next();
			user_id = mysql.getInt("id");
			String sql = "";
			if(reply_id == 0)
				reply_id = comment_id;
			sql = String.format("insert into comment(user_id,other_id,reply,content,content_id) values(%d,%d,%d,'%s',%d)", user_id,other_id,reply_id,content,content_id);
			mysql.update(sql);
			mysql.close();
			
			return "redirect:/detail.do?content_id="+content_id;
		}
		
	}
	
	@RequestMapping("delete_content")
	public String delete_content(Model model, HttpServletRequest request){
		int content_id;
		try{
			content_id = Integer.parseInt(request.getParameter("content_id"));
		}catch(Exception e){
			return "redirect:/index.do";
		}
		mysqlread mysql = new mysqlread();
		mysql.init();
		mysql.queryAll("select id from userinfo where account='"+request.getSession().getAttribute("account")+"'");
		mysql.next();
		int login_id = mysql.getInt("id");
		mysql.queryAll("select user_id from content where id="+content_id);
		mysql.next();
		if(login_id != mysql.getInt("user_id")){
			return "redirect:/index.do";
		}
		mysql.update("delete from content where id="+content_id);
		mysql.close();
		return "redirect:/index.do";
	}
	
	@RequestMapping("delete")
	public String delete(Model model, HttpServletRequest request){
		int content_id;
		int comment_id;
		try{
		comment_id = Integer.parseInt(request.getParameter("id"));
		content_id = Integer.parseInt(request.getParameter("content_id"));
		}catch(Exception e){
			return "redirect:/index.do";
		}
		mysqlread mysql = new mysqlread();
		mysql.init();
		mysql.queryAll("select id from userinfo where account='"+request.getSession().getAttribute("account")+"'");
		mysql.next();
		int login_id = mysql.getInt("id");
		mysql.queryAll("select user_id from comment where id="+comment_id);
		mysql.next();
		if(login_id != mysql.getInt("user_id")){
			return "redirect:/index.do";
		}
		mysql.update("delete from comment where id="+comment_id);
		mysql.close();
		return "redirect:/detail.do?content_id="+content_id;
	}
	@RequestMapping("addcontent")
	public String addcontent(Model model,HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		//Calendar now = Calendar.getInstance();
		//System.out.println(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH));
		HttpSession session = request.getSession();
		if(session.getAttribute("account") == null){
			return "redirect:/login.do?url="+"addcontent.do";
		}
		String company = request.getParameter("company");
		String job = request.getParameter("job");
		//Cookie[] cookies = request.getCookies();
		//cookie = new Cookie("company",company);
		System.out.println(company);
		
		String type_ = request.getParameter("type_");
		String result = request.getParameter("result");
		String contentDate = request.getParameter("time");
		
		String education = request.getParameter("education");
		String industry = request.getParameter("industry");
		String detail = request.getParameter("detail");
		
		
		if(detail != null){
			detail = dealString(detail);
			company = dealString(company);
			job = dealString(job);
		}
		//System.out.println(detail);
		if(detail != ""){
			//cookie = detail;
			//cookie = cookie.replaceAll("\n", "<br>");
		}
		if(company != null){
			//if(cookie == null)
				//cookie = "";
			if(company == ""){
				
				model.addAttribute("message","<script>alert('请输入面试公司')</script>");
				//model.addAttribute("fun",cookie);
				//model.addAttribute("fun","<script>var ele=document.getElementById('detail');ele.innerHTML='"+cookie+"';</script>");
				return "addcontent";
			}
			if(job == ""){
				model.addAttribute("message","<script>alert('请输入应聘岗位')</script>");
				//model.addAttribute("fun",cookie);
				//model.addAttribute("fun","<script>var ele=document.getElementById('detail');ele.innerHTML='"+cookie+"';</script>");
				return "addcontent";
			}
			if(contentDate == ""){
				model.addAttribute("message","<script>alert('请输入面试时间')</script>");
				//model.addAttribute("fun",cookie);
				//model.addAttribute("fun","<script>var ele=document.getElementById('detail');ele.innerHTML='"+cookie+"';</script>");
				return "addcontent";
			}
			if(detail == ""){
				//cookie = "";
				model.addAttribute("message","<script>alert('请输入面试详情');</script>");
				return "addcontent";
			}
			mysqlread mysql = new mysqlread();
			mysql.init();
			mysql.queryAll("select id from userinfo where account='"+session.getAttribute("account")+"'");
			mysql.next();
			int login_id = mysql.getInt("id");
			System.out.println(login_id);
			String sql = String.format("insert into content(company,type,result,contentDate,education,industry,detail,user_id,job) values('%s','%s' ,'%s','%s','%s','%s','%s',%d,'%s')", company,type_,result,contentDate,education,industry,detail,login_id,job);
			System.out.println("sql:"+sql);
			mysql.update(sql);
			mysql.queryAll("select MAX(id) from content where user_id="+login_id);
			mysql.next();
			int content_id = mysql.getInt("MAX(id)");
			System.out.println("content_id="+content_id);
			mysql.close();
			return "redirect:/detail.do?content_id="+content_id;
			
		}
		return "addcontent";
	}
	@RequestMapping("alter_content")
	public String alter_content(Model model,HttpServletRequest request){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		HttpSession session = request.getSession();
		
		String company = request.getParameter("company");
		mysqlread mysql = new mysqlread();
		mysql.init();
		mysql.queryAll("select id from userinfo where account='"+session.getAttribute("account")+"'");
		mysql.next();
		int login_id = mysql.getInt("id");
		int content_id;
		String content_id_str;
		try{
			content_id_str = request.getParameter("content_id");
			int index = content_id_str.indexOf("wrong");
			if(index != -1){
				String str = "";
				for(int i=5;i<content_id_str.length();i++)
					str += content_id_str.charAt(i);
				content_id = Integer.parseInt(str);
				model.addAttribute("message","alert('kong');");
			}
			else{
				content_id = Integer.parseInt(content_id_str);
			}
			
		}catch(Exception e){
			
			return "redirect:/index.do";
		}
		
		if(company == null){
			
			mysql.queryAll("select * from content where id="+content_id);
			mysql.next();
			if(login_id != mysql.getInt("user_id")){
				return "redirect:/index.do";
			}
			model.addAttribute("fun",returnString(mysql.getString("detail")));
			Map m = new HashMap();
			
			m.put("type", "'"+mysql.getString("type")+"'");
			m.put("time", "'"+mysql.getString("contentDate")+"'");
			m.put("company", "'"+returnString(mysql.getString("company"))+"'");
			m.put("result", "'"+mysql.getString("result")+"'");
			m.put("job", "'"+returnString(mysql.getString("job"))+"'");
			m.put("industry", "'"+mysql.getString("industry")+"'");
			String s="\""+m.toString()+"\"";
			
			model.addAttribute("map",s);
			return "alter_content";
		}
		
		company = request.getParameter("company");
		String job = request.getParameter("job");
		//Cookie[] cookies = request.getCookies();
		//cookie = new Cookie("company",company);
		System.out.println(company);
		
		String type_ = request.getParameter("type_");
		String result = request.getParameter("result");
		String contentDate = request.getParameter("time");
		String education = request.getParameter("education");
		String industry = request.getParameter("industry");
		String detail = request.getParameter("detail");
		if(company == "" || job == "" || contentDate == "" || detail == ""){
			return "redirect:/alter_content.do?content_id=wrong"+content_id;
		}
		if(detail != null){
			detail = dealString(detail);
			company = dealString(company);
			job = dealString(job);
		}

		

		String sql = String.format("update content set company='%s',type='%s',result='%s',contentDate='%s',education='%s',industry='%s',detail='%s',user_id=%d,job='%s' where id=%d", company,type_,result,contentDate,education,industry,detail,login_id,job,content_id);
		System.out.println("sql:"+sql);
		mysql.update(sql);
		
		mysql.close();
		return "redirect:/detail.do?content_id="+content_id;
			
		
		
		
		
	}
	
}
