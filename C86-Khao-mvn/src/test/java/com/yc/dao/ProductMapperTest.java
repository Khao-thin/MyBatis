package com.yc.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.home.po.Product;

public class ProductMapperTest {
	private SqlSession session;
	private InputStream in;
	
	{
		try {
			String resource="mybatis.xml";
			in=Resources.getResourceAsStream(resource);
			SqlSessionFactory sql=new SqlSessionFactoryBuilder().build(in);
			session=sql.openSession();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	@Test
	public void test1() throws IOException {
		/*String resource="mybatis.xml";
		InputStream in=Resources.getResourceAsStream(resource);
		SqlSessionFactory sql=new SqlSessionFactoryBuilder().build(in);
		SqlSession session=sql.openSession();
		//xml接口的命名空间+查询的sql id
*/		List<Product>list=session.selectList("com.yc.dao.ProductMapper.selectAll");
		/**
		 * 使用断言进行结果的判断
		 * true表示期望值
		 * list.size()>0是实际值
		 */
		Assert.assertEquals(true,list.size()>0);
		/*
		 * for (Product product : list) { System.out.println(product); }
		 */
	}
	
	@After
	public void destroy() throws Exception {
		//提交事务
		session.commit();
		//释放资源
		session.close();
		in.close();
	}
	@Test
	public void test2() throws IOException {
		Product p=new Product();
		p.setPname("aaaaddd");
		p.setPrice(145);
		session.insert("com.yc.dao.ProductMapper.insert",p);
		//不commit，会话关闭会自动回滚
	}
	@Test
	public void test3() throws IOException {
		Product p=new Product();
		p.setPrice(520);
		p.setPname("阿达");
		p.setPid(269);
		session.update("com.yc.dao.ProductMapper.update",p);
		
	}
	@Test
	public void test4() throws IOException {
		Product p=new Product();
		p.setPid(266);
		session.delete("com.yc.dao.ProductMapper.delete",p);
		
	}
}
