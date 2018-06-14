package com.mymorea.detail;

import org.apache.tomcat.jni.Time;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.portlet.ModelAndView;

import com.mymoreau.mysql.mysqlread;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import java.sql.*;
import java.util.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringEscapeUtils;

@RestController
@RequestMapping("/")
public class Detail {
	int user_id;
	int comment_id;
	int content_id;
	int other_id;
	int reply_id;
	int login_id;
	String cookie = "";

	private String dealString(String xss){
		
		xss = StringEscapeUtils.escapeHtml4(xss);
		//xss = StringEscapeUtils.escapeEcmaScript(xss);

		return xss.replaceAll("'","’");
		
	}
	
	@RequestMapping("index")
	public String index(Model model, HttpServletRequest request){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
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
	
	@RequestMapping("detail")
	public String printdeatil(Model model,HttpServletRequest request) {
		String format_str = "";
		String user_name = "";
		String other_name = "";
		String user = "";
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		String url =  request.getServletPath()+"?"+request.getQueryString();
		System.out.println(url);
		HttpSession session = request.getSession();
		content_id = Integer.parseInt( request.getParameter("content_id"));
		mysqlread mysql = new mysqlread();
		mysql.init();
		mysql.queryAll("select * from content where id="+content_id);
		mysql.next();
		String content = mysql.getString("detail");
		System.out.println("content: "+content);
		content = content.replaceAll("\r\n","<br>");
		content = content.replaceAll("\n", "<br>");
		String company = mysql.getString("company");
		String job = mysql.getString("job");
		String result = mysql.getString("result");
		String time = mysql.getString("contentDate");
		int user_id = mysql.getInt("user_id");
		mysql.queryAll("select * from userinfo where id="+user_id);
		mysql.next();
		String main_user_name =mysql.getString("name");
		mysqlread mysql1 = null;
		mysqlread mysql2 = null;
		mysql1 = new mysqlread();
		mysql1.init();
		mysql2 = new mysqlread();
		mysql2.init();
		String js = "<script>var ele=document.getElementById('top');var select=document.createElement('div');select.className='select';select.id='comment_id_0';";
		if(main_user_name.equals(session.getAttribute("user"))){
			format_str = String.format("content_id:%d", content_id);
			js += "var p=document.createElement('p');p.innerHTML='修改';select.appendChild(p);p.onclick=function(){post('alter_content.do', {"+format_str+"})};var p=document.createElement('p');p.innerHTML='删除';select.appendChild(p);p.onclick=function(){post('delete_content.do', {"+format_str+"})};";
		}
		else{
			js += "var p=document.createElement('p');p.innerHTML='举报';select.appendChild(p);";
		}
		js +="ele.appendChild(select);var pinlun = document.getElementsByClassName('pinlun');var comment_id=[];var i=0;comment_id[i]=0;";
		
		mysql.queryAll("select id from userinfo where name='"+session.getAttribute("user")+"';");
		mysql.next();
		login_id = mysql.getInt("id");
		System.out.println(login_id);
		mysql.queryAll("select * from comment where content_id="+content_id); 
		while(mysql.next()){
			if(mysql.getInt("reply") == 0){
				comment_id = mysql.getInt("id");
				user_id = mysql.getInt("user_id");
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
					format_str = String.format("id:%d,user_id:%d,other_id:%d,reply:%d,content_id:%d", comment_id,user_id,other_id,reply_id,content_id);
					js += "var p=document.createElement('p');p.innerHTML='删除';div.appendChild(p);p.onclick=function(){post('delete.do', {"+format_str+"})};";
					js += "father.appendChild(div);";
				
				}
				else{
					format_str = String.format("id:%d,user_id:%d,other_id:%d,reply:%d,content_id:%d", comment_id,user_id,other_id,reply_id,content_id);
					js += "var p=document.createElement('p');p.innerHTML='回复';div.appendChild(p);p.onclick=function(){post('comment_reply.do', {"+format_str+"})};";
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
						format_str = String.format("id:%d,user_id:%d,other_id:%d,reply:%d,content_id:%d", comment_id,user_id,other_id,reply_id,content_id);
						js += "var p=document.createElement('p');p.innerHTML='删除';div.appendChild(p);p.onclick=function(){post('delete.do', {"+format_str+"})};";
					
						js += "reply.appendChild(div);";
					
					}
					else{
						format_str = String.format("id:%d,user_id:%d,other_id:%d,reply:%d,content_id:%d", comment_id,user_id,other_id,reply_id,content_id);
						js += "var p=document.createElement('p');p.innerHTML='回复';div.appendChild(p);p.onclick=function(){post('comment_reply.do', {"+format_str+"})};";
					
						js += "var p=document.createElement('p');p.innerHTML='举报';div.appendChild(p);reply.appendChild(div);";
					
					}
					js += "var li=document.createElement('li');li.innerHTML='：';reply.appendChild(li);";
					js += "var p=document.createElement('p');p.innerHTML='"+mysql2.getString("content")+"';reply.appendChild(p);father.appendChild(reply);";
					
				}
			}
		}
		js += "var ul=document.createElement('ul');ul.innerHTML='没有更多了';ul.style.color='#e7eaeb';pinlun[0].appendChild(ul);";

		System.out.println(js);
		
		
		String comment = (String)request.getParameter("comment");
		if(comment != null){
			if(comment.equals("")){
				System.out.println("kong");
				js += "alert('kong');";
				
				

				//return "redirect:/detail.do?content_id="+comment_id;
			}
			else{
				if(session.getAttribute("user") == null){
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
		System.out.println(session.getAttribute("user")+comment);
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
		return "detail";
	}

	private Object String(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@RequestMapping("login")
	public String login(Model model,HttpServletRequest request){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		String password = (String)request.getParameter("password");
		String url = request.getParameter("url");
		String current_url =  request.getServletPath()+"?"+request.getQueryString();
		if(password != null){
			if(password.equals("123")){
				HttpSession session = request.getSession();
				session.setAttribute("user", request.getParameter("user"));
				System.out.println("ok");
				return "redirect:"+url;
			}
			else
				return "redirect:"+current_url;
		}
		return "login";
	}

	@RequestMapping("comment_reply")
	public String comment_reply(Model model, HttpServletRequest request,HttpServletResponse resposne){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		
		String content = request.getParameter("reply0");
		String js = "";
		
		if(content == null){
			HttpSession session = request.getSession();
			
			mysqlread mysql = new mysqlread();
			mysql.init();
			mysql.queryAll("select id from userinfo where name='"+session.getAttribute("user")+"';");
			mysql.next();
			login_id = mysql.getInt("id");
			mysql.close();
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
			if(session.getAttribute("user") == null){
				String url = "detail.do?content_id="+content_id;
				return "redirect:/login.do?url="+url;
			}
			model.addAttribute("message",js);
			return "comment_reply";
		}
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
			mysql.queryAll("select id from userinfo where name='"+session.getAttribute("user")+"';");
			mysql.next();
			user_id = mysql.getInt("id");
			String sql = "";
			if(reply_id == 0)
				reply_id = comment_id;
			sql = String.format("insert into comment(user_id,other_id,reply,content,content_id) values(%d,%d,%d,'%s',%d)", user_id,other_id,reply_id,content,content_id);
			mysql.update(sql);
			mysql.close();
			//try{
			//	Thread.sleep(1000);
			//}catch(Exception e){
			//	System.out.println("error");
			//}
			System.out.println("wait");
			return "redirect:/detail.do?content_id="+content_id;
		}
		
	}
	
	@RequestMapping("delete_content")
	public String delete_content(Model model, HttpServletRequest request){
		try{
			content_id = Integer.parseInt(request.getParameter("content_id"));
		}catch(Exception e){
			return "redirect:/index.do";
		}
		mysqlread mysql = new mysqlread();
		mysql.init();
		mysql.update("delete from content where id="+content_id);
		mysql.close();
		return "redirect:/index.do";
	}
	
	@RequestMapping("delete")
	public String delete(Model model, HttpServletRequest request){
		try{
		comment_id = Integer.parseInt(request.getParameter("id"));
		content_id = Integer.parseInt(request.getParameter("content_id"));
		}catch(Exception e){
			return "redirect:/index.do";
		}
		mysqlread mysql = new mysqlread();
		mysql.init();
		mysql.update("delete from comment where id="+comment_id);
		mysql.close();
		return "redirect:/detail.do?content_id="+content_id;
	}
	@RequestMapping("addcontent")
	public String addcontent(Model model,HttpServletRequest request,ServletResponse response){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		Calendar now = Calendar.getInstance();
		System.out.println(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH));
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null){
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
		System.out.println(contentDate);
		String education = request.getParameter("education");
		String industry = request.getParameter("industry");
		String detail = request.getParameter("detail");
		if(detail != null)
			detail = dealString(detail);
		System.out.println(detail);
		if(detail != ""){
			cookie = detail;
			//cookie = cookie.replaceAll("\n", "<br>");
		}
		if(company != null){
			if(cookie == null)
				cookie = "";
			if(company == ""){
				
				model.addAttribute("message","<script>alert('请输入面试公司')</script>");
				model.addAttribute("fun",cookie);
				//model.addAttribute("fun","<script>var ele=document.getElementById('detail');ele.innerHTML='"+cookie+"';</script>");
				return "addcontent";
			}
			if(job == ""){
				model.addAttribute("message","<script>alert('请输入应聘岗位')</script>");
				model.addAttribute("fun",cookie);
				//model.addAttribute("fun","<script>var ele=document.getElementById('detail');ele.innerHTML='"+cookie+"';</script>");
				return "addcontent";
			}
			if(contentDate == ""){
				model.addAttribute("message","<script>alert('请输入面试时间')</script>");
				model.addAttribute("fun",cookie);
				//model.addAttribute("fun","<script>var ele=document.getElementById('detail');ele.innerHTML='"+cookie+"';</script>");
				return "addcontent";
			}
			if(detail == ""){
				cookie = "";
				model.addAttribute("message","<script>alert('请输入面试详情');</script>");
				return "addcontent";
			}
			mysqlread mysql = new mysqlread();
			mysql.init();
			mysql.queryAll("select id from userinfo where name='"+session.getAttribute("user")+"'");
			System.out.println(session.getAttribute("user"));
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
		try{
			content_id = Integer.parseInt(request.getParameter("content_id"));
		}catch(Exception e){
			return "redirect:/index.do";
		}
		System.out.println(content_id);
		String company = request.getParameter("company");
		mysqlread mysql = new mysqlread();
		mysql.init();
		mysql.queryAll("select * from content where id="+content_id);
		mysql.next();
		login_id=mysql.getInt("user_id");
		if(company == null){
			
			model.addAttribute("fun",mysql.getString("detail"));
			Map m = new HashMap();
			
			m.put("type", "'"+mysql.getString("type")+"'");
			m.put("time", "'"+mysql.getString("contentDate")+"'");
			m.put("company", "'"+mysql.getString("company")+"'");
			m.put("result", "'"+mysql.getString("result")+"'");
			m.put("job", "'"+mysql.getString("job")+"'");
			m.put("industry", "'"+mysql.getString("industry")+"'");
			String s="\""+m.toString()+"\"";
			model.addAttribute("content_id",content_id);
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
		System.out.println(contentDate);
		String education = request.getParameter("education");
		String industry = request.getParameter("industry");
		String detail = request.getParameter("detail");
		if(detail != null)
			detail = dealString(detail);
		//System.out.println(detail);
		if(detail != ""){
			cookie = detail;
			//cookie = cookie.replaceAll("\n", "<br>");
		}
		if(company != null){
			if(cookie == null)
				cookie = "";
			if(company == ""){
				
				model.addAttribute("message","<script>alert('请输入面试公司')</script>");
				model.addAttribute("fun",cookie);
				//model.addAttribute("fun","<script>var ele=document.getElementById('detail');ele.innerHTML='"+cookie+"';</script>");
				model.addAttribute("content_id",content_id);
				return "alter_content?content_id=22";
			}
			if(job == ""){
				model.addAttribute("message","<script>alert('请输入应聘岗位')</script>");
				model.addAttribute("fun",cookie);
				//model.addAttribute("fun","<script>var ele=document.getElementById('detail');ele.innerHTML='"+cookie+"';</script>");
				model.addAttribute("content_id",content_id);
				return "alter_content?content_id=22";
			}
			if(contentDate == ""){
				model.addAttribute("message","<script>alert('请输入面试时间')</script>");
				model.addAttribute("fun",cookie);
				//model.addAttribute("fun","<script>var ele=document.getElementById('detail');ele.innerHTML='"+cookie+"';</script>");
				model.addAttribute("content_id",content_id);
				return "alter_content?content_id=22";
			}
			if(detail == ""){
				cookie = "";
				model.addAttribute("message","<script>alert('请输入面试详情');</script>");
				model.addAttribute("content_id",content_id);
				return "alter_content?content_id=22";
			}
			
			
			
			System.out.println(login_id);
			String sql = String.format("insert into content(company,type,result,contentDate,education,industry,detail,user_id,job) values('%s','%s' ,'%s','%s','%s','%s','%s',%d,'%s')", company,type_,result,contentDate,education,industry,detail,login_id,job);
			System.out.println("sql:"+sql);
			//mysql.update(sql);
			
			mysql.close();
			return "redirect:/detail.do?content_id="+content_id;
			
		}
		model.addAttribute("content_id",content_id);
		return "alter_content";
		
		
	}
	
}
