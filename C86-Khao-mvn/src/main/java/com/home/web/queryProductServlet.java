package com.home.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.home.dao.productDao;

@WebServlet("/queryProductServlet.do")
public class queryProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	productDao pdao = new productDao();
	//数据库商品数据查询
	protected void queryProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int a=Integer.parseInt(request.getParameter("a"));
		int b=Integer.parseInt(request.getParameter("b"));
		List<Map<String, Object>> list = pdao.queryProduct(a,b);
		HashMap<String, Object> page = new HashMap<String, Object>();
		page.put("list", list);
		print(response, page);
	}
	//创意家居分页查询
	protected void queryPageidea(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("size"));
		//总页数
		int pages=pdao.countPages(size*9);
		List<Map<String, Object>> list = pdao.queryPageidea(page,size);
		HashMap<String, Object> page1 = new HashMap<String, Object>();
		page1.put("list", list);
		page1.put("pages", pages);
		print(response, page1);
	}
	//分页查询
	protected void queryPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("size"));
		int a=Integer.parseInt(request.getParameter("a"));
		int b=Integer.parseInt(request.getParameter("b"));
		//总页数
		int pages=pdao.countPages(size,a,b);
		System.out.println(pages);
		//list<map<String,Object>>是定义了一个List的集合变量，是map的一个集合；map是那个list的其中一个值
		List<Map<String, Object>> list = pdao.queryPage(a,b,page,size);
		HashMap<String, Object> page1 = new HashMap<String, Object>();
		page1.put("list", list);
		page1.put("pages", pages);
		print(response, page1);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
