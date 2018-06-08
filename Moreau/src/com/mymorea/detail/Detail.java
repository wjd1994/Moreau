package com.mymorea.detail;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/")
public class Detail {
	int user_id;
	int comment_id;
	int content_id;
	int other_id;
	int reply_id;
	
	

	@RequestMapping("detail")
	public String printdeatil(Model model,HttpServletRequest request) {
		String format_str = "";
		String user_name = "";
		String other_name = "";
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		String url =  request.getServletPath()+"?"+request.getQueryString();
		System.out.println(url);
		
		content_id = Integer.parseInt( request.getParameter("content_id"));
		mysqlread mysql1 = null;
		mysqlread mysql2 = null;
		mysql1 = new mysqlread();
		mysql1.init();
		mysql2 = new mysqlread();
		mysql2.init();
		String js = "<script>var pinlun = document.getElementsByClassName('pinlun');\n";
		mysqlread mysql = new mysqlread();
		mysql.init();
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
				
				js += "var a=document.createElement('a');a.innerHTML='...';a.className='click';a.onclick=function(){clickshow("+comment_id+")};father.appendChild(a);";
				js += "var p=document.createElement('p');p.innerHTML='：';father.appendChild(p);";
				js += "var div=document.createElement('div');div.className='select';div.id='comment_id_"+comment_id+"';";
				format_str = String.format("id=%d&user_id=%d&other_id=%d&reply=%d&content_id=%d", comment_id,user_id,other_id,reply_id,content_id);
				js += "var p=document.createElement('p');p.innerHTML='回复';div.appendChild(p);p.onclick=function(){window.location='comment_reply.do?"+format_str+"'};";
				js += "var p=document.createElement('p');p.innerHTML='举报';div.appendChild(p);father.appendChild(div);";
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
					js += "var div=document.createElement('div');div.className='select_child';div.id='comment_id_"+comment_id+"';";
					format_str = String.format("id=%d&user_id=%d&other_id=%d&reply=%d&content_id=%d", comment_id,user_id,other_id,reply_id,content_id);
					js += "var p=document.createElement('p');p.innerHTML='回复';div.appendChild(p);p.onclick=function(){window.location='comment_reply.do?"+format_str+"'};";
					js += "var p=document.createElement('p');p.innerHTML='举报';div.appendChild(p);reply.appendChild(div);";
					js += "var li=document.createElement('li');li.innerHTML='：';reply.appendChild(li);";
					js += "var p=document.createElement('p');p.innerHTML='"+mysql2.getString("content")+"';reply.appendChild(p);father.appendChild(reply);";
					
				}
			}
		}
		js += "var ul=document.createElement('ul');ul.innerHTML='没有更多了';ul.style.color='#e7eaeb';pinlun[0].appendChild(ul);";
		System.out.println(js);
		
		HttpSession session = request.getSession();
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
				mysql.queryAll("select id from userinfo where name='"+session.getAttribute("user")+"';");
				mysql.next();
				user_id = mysql.getInt("id");
				mysql.update("insert into comment(user_id,other_id,reply,content,content_id) values("+user_id+",0,0,'"+comment+"',"+content_id+")");
				
				mysql.close();
				mysql1.close();
				mysql2.close();
				return "redirect:/detail.do?content_id="+content_id;
			}
		}
		System.out.println(session.getAttribute("user")+comment);
		js += "</script>";
		model.addAttribute("message",js);
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
	public String comment_reply(Model model, HttpServletRequest request){
		try{
			request.setCharacterEncoding("utf-8");
		}catch(Exception e){
			
		}
		String content = request.getParameter("reply0");
		String js = "";
		if(content == null){
			model.addAttribute("message",js);
			return "comment_reply";
		}
		if(content.equals("")){
			//String current_url =  request.getServletPath()+"?"+request.getQueryString();
			js += "<script>alert('kong');</script>";
			//return "redirect:/"+current_url;
		}
		else{
			comment_id = Integer.parseInt(request.getParameter("id"));
			other_id = Integer.parseInt(request.getParameter("user_id"));
			reply_id = Integer.parseInt(request.getParameter("reply"));
			content_id = Integer.parseInt(request.getParameter("content_id"));
	
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
			return "redirect:/detail.do?content_id="+content_id;
		}
		model.addAttribute("message",js);
		return "comment_reply";
	}
	
	
}
