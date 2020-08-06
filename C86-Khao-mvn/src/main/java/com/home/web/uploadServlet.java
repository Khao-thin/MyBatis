package com.home.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.home.po.Result;
@MultipartConfig
@WebServlet("/uploadServlet.do")
public class uploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		Part part=request.getPart("file");
		
		//获取工程根目录 tomcat服务/webapps/工程名
		String webpath="/";
		String diskpath=request.getServletContext().getRealPath(webpath);
		//将路径转为file对象
		File proDir=new File(diskpath);
		File webappsDir=proDir.getParentFile().getParentFile();//获取上级的上级目录 tomcat
		File uploadDir=new File(webappsDir,"/webapps/ROOT/upload");
		diskpath=uploadDir.getAbsolutePath();
		//part.getName();//文件上传的字段名file
		String filename=part.getSubmittedFileName();//获取上传的文件名
		//System.out.println(filename);
		//part.getSize();//文件大小
		//定义工程内部的上传文件夹
		//String webpath="/upload";
		//获取应用上下文对象
		//ServletContext sc=getServletContext();
		//返回web路径对应的磁盘路径
		//String diskpath=sc.getRealPath(webpath);
		diskpath=diskpath+"/"+filename;
		//System.out.println(diskpath);
		webpath+="/"+part.getSubmittedFileName();
		webpath=webpath.substring(1);
		//文件必须放在工程的里边
		part.write(diskpath);
		Result res=new Result(1,"文件上传成功","/upload/"+filename);
		Gson gson=new Gson();
		String json=gson.toJson(res);
		response.getWriter().append(json);
	}
}
