package com.home.web;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.home.dao.managerDao;
import com.home.po.Result;
import com.home.po.Samepdt;

@WebServlet("/managerServlet.do")
public class managerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private managerDao mdao=new managerDao();
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		/*
		 * if(mdao.login(name, pwd)) { response.getWriter().append("login success");
		 * }else print(response,"login failure");
		 */
		Map<String,Object>Manager=mdao.login(name, pwd);
		if(Manager!=null) {
			request.getSession().setAttribute("Manager",Manager);
			response.getWriter().append("login success");
		}else print(response,"login failure");
	}
	protected void getManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object>Manager=(Map<String, Object>) request.getSession().getAttribute("Manager");
		print(response,Manager);
		/*
		 * if(Manager!=null) { Manager.clear(); }
		 */
	}
	//退出登录
	protected void quitManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object>Manager=(Map<String, Object>) request.getSession().getAttribute("Manager");
		Manager.clear(); 
		print(response,"退出成功");
	}
	
	protected void queryProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		int page=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("rows"));
		/**
		 * bean/op 要装载的实体对象
		 * properties 存放属性值的map集合
		 */
		Samepdt sp=new Samepdt();
		//装载方法
		BeanUtils.populate(sp, request.getParameterMap());
		int total=mdao.lastPage(sp);
		List<Map<String,Object>>list=mdao.queryProduct(sp,page,size);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("rows", list);
		data.put("total", total);
		print(response,data);
	}
	//分类
	protected void cateGory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<?>list=mdao.query();
		print(response,list);
	}
	//保存商品
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		Samepdt sp=new Samepdt();
		//装载方法
		BeanUtils.populate(sp, request.getParameterMap());
		if(sp.getSname()==null||sp.getSname().trim().isEmpty()) {
			print(response,new Result(0,"商品名不能为空", sp));
			return;
		}
		if(sp.getPrice()==null||sp.getPrice()<=0) {
			print(response,new Result(0,"商品价格必须大于0", sp));
			return; 
		}
		System.out.println("sid"+sp.getSid());
		if(sp.getSid()==null||sp.getSid()==0) {
			mdao.insert(sp);
			print(response,new Result(1,"商品添加成功", sp));
		}else {
			mdao.update(sp);
			print(response,new Result(1,"商品修改成功", sp));
		}
	}
	//删除
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sid=Integer.parseInt(request.getParameter("sid"));
		mdao.delete(sid);
		response.getWriter().append("删除成功");
	}
}
//List       [{"sid":1,"sname":"非洲菊仿真花干花","image":"img/temp/proDet.jpg","cid":5,"isnew":"0","price":1.2},{"sid":2,"sname":"【最家】多色兰仿真花干花","image":"img/temp/pro01.jpg","cid":5,"isnew":"0","price":17.9},{"sid":3,"sname":"【最家】小束麦色仿真花干花","image":"img/temp/pro02.jpg","cid":5,"isnew":"0","price":17.9},{"sid":4,"sname":"【最家】三色仿真花干花","image":"img/temp/pro03.jpg","cid":5,"isnew":"0","price":17.9},{"sid":5,"sname":"【最家】现代黄花白瓷花瓶摆件","image":"img/temp/vase01.jpg","cid":6,"isnew":"0","price":17.9},{"sid":6,"sname":"【最家】黑白花瓶摆件","image":"img/temp/vase02.jpg","cid":6,"isnew":"0","price":17.9},{"sid":7,"sname":"【最家】北欧现代陶瓷花瓶摆件","image":"img/temp/vase03.jpg","cid":6,"isnew":"0","price":17.9},{"sid":8,"sname":"【最家】透明客厅茶几花瓶摆件","image":"img/temp/vase04.jpg","cid":6,"isnew":"0","price":17.9},{"sid":9,"sname":"【最家】狗尾巴草花瓶","image":"img/temp/vase05.jpg","cid":6,"isnew":"0","price":17.9},{"sid":10,"sname":"【最家】仿木雅致台面花瓶","image":"img/temp/vase06.jpg","cid":6,"isnew":"0","price":17.9},{"sid":11,"sname":"【最家】木盒仿真花干花","image":"img/temp/pro04.jpg","cid":5,"isnew":"0","price":17.9},{"sid":12,"sname":"【最家】五朵仿真花干花","image":"img/temp/pro05.jpg","cid":5,"isnew":"0","price":17.9},{"sid":13,"sname":"【最家】狗一把仿真花干花","image":"img/temp/pro06.jpg","cid":5,"isnew":"0","price":17.9},{"sid":14,"sname":"【最家】一束仿真花干花","image":"img/temp/pro07.jpg","cid":5,"isnew":"0","price":17.9},{"sid":15,"sname":"【最家】蛇纹仿真花干花","image":"img/temp/pro08.jpg","cid":5,"isnew":"0","price":17.9},{"sid":16,"sname":"【最家】法式乡村复古抱枕","image":"img/temp/bz07.jpg","cid":8,"isnew":"0","price":47.9},{"sid":17,"sname":"【最家】时尚图案抱枕","image":"img/temp/bz06.jpg","cid":8,"isnew":"0","price":20.9},{"sid":18,"sname":"【最家】北欧时尚现代动物抱枕","image":"img/temp/bz05.jpg","cid":8,"isnew":"0","price":37.9},{"sid":19,"sname":"【最家】简约格子抱枕","image":"img/temp/bz04.jpg","cid":8,"isnew":"0","price":17.9},{"sid":20,"sname":"【最家】纯色棉麻抱枕","image":"img/temp/bz03.jpg","cid":8,"isnew":"0","price":17.9},{"sid":21,"sname":"【最家】时尚现代叮当猫抱枕","image":"img/temp/bz02.jpg","cid":8,"isnew":"0","price":17.9},{"sid":22,"sname":"【最家】欧式现代棉麻抱枕","image":"img/temp/bz01.jpg","cid":8,"isnew":"0","price":17.9},{"sid":23,"sname":"【最家】清新乡村田园抱枕","image":"img/temp/bz08.jpg","cid":8,"isnew":"0","price":17.9},{"sid":24,"sname":"【最家】清新乡村田园桌布","image":"img/temp/zb08.jpg","cid":7,"isnew":"0","price":17.9},{"sid":25,"sname":"【最家】法式乡村复古桌布","image":"img/temp/zb07.jpg","cid":7,"isnew":"0","price":17.9},{"sid":26,"sname":"【最家】清新简约防水桌布","image":"img/temp/zb06.jpg","cid":7,"isnew":"0","price":47.9},{"sid":27,"sname":"【最家】北欧圣诞节防水桌布格子","image":"img/temp/zb05.jpg","cid":7,"isnew":"0","price":59.9},{"sid":28,"sname":"【最家】田园桌布防水防油布","image":"img/temp/zb04.jpg","cid":7,"isnew":"0","price":20.9},{"sid":29,"sname":"【最家】欧式家用小圆桌台布","image":"img/temp/zb03.jpg","cid":7,"isnew":"0","price":47.9},{"sid":30,"sname":"【最家】地中海圆形桌布","image":"img/temp/zb02.jpg","cid":7,"isnew":"0","price":37.9},{"sid":31,"sname":"【最家】欧式田园棉麻桌布","image":"img/temp/zb01.jpg","cid":7,"isnew":"0","price":39.9},{"sid":32,"sname":"仙人掌墙壁挂画","image":"img/temp/sec101.jpg","cid":9,"isnew":"0","price":299.9},{"sid":33,"sname":"抽象派墙壁挂画","image":"img/temp/sec102.jpg","cid":9,"isnew":"0","price":299.9},{"sid":34,"sname":"沙滩墙壁挂画","image":"img/temp/sec103.jpg","cid":9,"isnew":"0","price":299.9},{"sid":35,"sname":"花瓶装饰墙壁挂画","image":"img/temp/sec104.jpg","cid":9,"isnew":"0","price":299.9},{"sid":36,"sname":"羚羊装饰画墙壁挂画","image":"img/temp/sec105.jpg","cid":9,"isnew":"0","price":299.9},{"sid":37,"sname":"大海装饰画墙壁挂画","image":"img/temp/sec106.jpg","cid":9,"isnew":"0","price":299.9},{"sid":38,"sname":"花丛装饰墙壁画挂画","image":"img/temp/sec201.jpg","cid":10,"isnew":"0","price":299.9},{"sid":39,"sname":"田园装饰画墙壁挂画","image":"img/temp/sec202.jpg","cid":10,"isnew":"0","price":299.9},{"sid":40,"sname":"远景装饰画墙壁挂画","image":"img/temp/sec203.jpg","cid":10,"isnew":"0","price":299.9},{"sid":41,"sname":"女人墙壁画挂画","image":"img/temp/sec204.jpg","cid":10,"isnew":"0","price":299.9},{"sid":42,"sname":"花簇墙壁画挂画","image":"img/temp/sec205.jpg","cid":10,"isnew":"0","price":299.9},{"sid":43,"sname":"航海墙壁画挂画","image":"img/temp/sec206.jpg","cid":10,"isnew":"0","price":299.9},{"sid":44,"sname":"新中式客厅山水装饰画墙壁挂画 ","image":"img/temp/sec302.jpg","cid":17,"isnew":"0","price":299.9},{"sid":45,"sname":"莲花客厅装饰画墙壁挂画","image":"img/temp/sec303.jpg","cid":17,"isnew":"0","price":299.9},{"sid":46,"sname":"白瓶香薰","image":"img/temp/xx01.jpg","cid":12,"isnew":"0","price":299.9},{"sid":47,"sname":"橙瓶香薰","image":"img/temp/xx02.jpg","cid":12,"isnew":"0","price":299.9},{"sid":48,"sname":"褶皱瓶香薰","image":"img/temp/xx103.jpg","cid":12,"isnew":"0","price":299.9},{"sid":49,"sname":"三小只香薰","image":"img/temp/xx104.jpg","cid":12,"isnew":"0","price":299.9},{"sid":50,"sname":"两透明香薰","image":"img/temp/xx105.jpg","cid":12,"isnew":"0","price":299.9},{"sid":51,"sname":"白花香薰","image":"img/temp/xx106.jpg","cid":12,"isnew":"0","price":299.9},{"sid":52,"sname":"坑洼白色香薰","image":"img/temp/xx201.jpg","cid":13,"isnew":"0","price":299.9},{"sid":53,"sname":"莲台香薰","image":"img/temp/xx202.jpg","cid":13,"isnew":"0","price":299.9},{"sid":54,"sname":"倒立莲蓬香薰","image":"img/temp/xx203.jpg","cid":13,"isnew":"0","price":299.9},{"sid":55,"sname":"简约奶玉香薰","image":"img/temp/xx204.jpg","cid":13,"isnew":"0","price":299.9},{"sid":56,"sname":"金线陶瓷香薰","image":"img/temp/xx205.jpg","cid":13,"isnew":"0","price":299.9},{"sid":57,"sname":"墨白香薰","image":"img/temp/xx206.jpg","cid":13,"isnew":"0","price":299.9},{"sid":58,"sname":"【最家】创意家居饰品工艺品装饰三口之家","image":"img/idea2.jpg","cid":14,"isnew":"0","price":199.0,"isidea":1},{"sid":59,"sname":"【最家】创意家居饰品工艺品装饰三口鹿摆设","image":"img/idea3.jpg","cid":14,"isnew":"0","price":78.0,"isidea":1},{"sid":60,"sname":"【最家】简约欧式装饰五彩缤纷花朵","image":"img/idea4.jpg","cid":5,"isnew":"0","price":52.0,"isidea":1},{"sid":61,"sname":"【最家】客厅餐桌创意插花桌面摆件","image":"img/idea5.jpg","cid":5,"isnew":"0","price":22.0,"isidea":1},{"sid":62,"sname":"【最家】简约可爱创意玻璃水杯","image":"img/idea7.jpg","cid":14,"isnew":"0","price":22.0,"isidea":1},{"sid":63,"sname":"【最家】简约可爱创意玻璃拼盘","image":"img/idea8.jpg","cid":14,"isnew":"0","price":33.0,"isidea":1},{"sid":64,"sname":"【最家】创意亲嘴鱼陶瓷装饰摆件","image":"img/idea9.jpg","cid":14,"isnew":"0","price":99.0,"isidea":1},{"sid":65,"sname":"【最家】创意多彩种子收纳摆件","image":"img/idea10.jpg","cid":14,"isnew":"0","price":49.0,"isidea":1},{"sid":66,"sname":"【最家】透明球简约创意装饰品","image":"img/id1.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":67,"sname":"【最家】网格简约创意装饰品","image":"img/id2.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":68,"sname":"【最家】两瓶一花简约创意装饰品","image":"img/id3.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":69,"sname":"【最家】两鹤简约创意装饰品","image":"img/id4.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":70,"sname":"【最家】父子简约创意装饰品","image":"img/id5.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":71,"sname":"【最家】鹿人简约创意装饰品","image":"img/id6.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":72,"sname":"【最家】穿越书籍的黑狗装饰品","image":"img/id7.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":73,"sname":"【最家】黑白苹果简约创意装饰品","image":"img/id8.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":74,"sname":"【最家】创意摆件自行车复古","image":"img/id9.jpg","cid":14,"isnew":"0","price":288.0,"isidea":1},{"sid":75,"sname":"【最家】创意女孩秋千公主荡秋千","image":"img/id10.jpg","cid":14,"isnew":"0","price":200.0,"isidea":1},{"sid":76,"sname":"【最家】创意墙式挂饰水瓶","image":"img/id11.jpg","cid":14,"isnew":"0","price":50.0,"isidea":1},{"sid":77,"sname":"【最家】创意两只鹿","image":"img/id12.jpg","cid":14,"isnew":"0","price":99.0,"isidea":1},{"sid":78,"sname":"【最家】创意两只小象","image":"img/id13.jpg","cid":14,"isnew":"0","price":170.0,"isidea":1},{"sid":79,"sname":"【最家】简约客厅电视现代中式家居摆件","image":"img/id14.jpg","cid":14,"isnew":"0","price":179.0,"isidea":1},{"sid":80,"sname":"【最家】创意样板房树脂工艺品","image":"img/id15.jpg","cid":14,"isnew":"0","price":188.0,"isidea":1},{"sid":81,"sname":"【最家】创意笔筒小象","image":"img/id16.jpg","cid":14,"isnew":"0","price":30.0,"isidea":1}]
//HashMap   {"list":[{"sid":1,"sname":"非洲菊仿真花干花","image":"img/temp/proDet.jpg","cid":5,"isnew":"0","price":1.2},{"sid":2,"sname":"【最家】多色兰仿真花干花","image":"img/temp/pro01.jpg","cid":5,"isnew":"0","price":17.9},{"sid":3,"sname":"【最家】小束麦色仿真花干花","image":"img/temp/pro02.jpg","cid":5,"isnew":"0","price":17.9},{"sid":4,"sname":"【最家】三色仿真花干花","image":"img/temp/pro03.jpg","cid":5,"isnew":"0","price":17.9},{"sid":5,"sname":"【最家】现代黄花白瓷花瓶摆件","image":"img/temp/vase01.jpg","cid":6,"isnew":"0","price":17.9},{"sid":6,"sname":"【最家】黑白花瓶摆件","image":"img/temp/vase02.jpg","cid":6,"isnew":"0","price":17.9},{"sid":7,"sname":"【最家】北欧现代陶瓷花瓶摆件","image":"img/temp/vase03.jpg","cid":6,"isnew":"0","price":17.9},{"sid":8,"sname":"【最家】透明客厅茶几花瓶摆件","image":"img/temp/vase04.jpg","cid":6,"isnew":"0","price":17.9},{"sid":9,"sname":"【最家】狗尾巴草花瓶","image":"img/temp/vase05.jpg","cid":6,"isnew":"0","price":17.9},{"sid":10,"sname":"【最家】仿木雅致台面花瓶","image":"img/temp/vase06.jpg","cid":6,"isnew":"0","price":17.9},{"sid":11,"sname":"【最家】木盒仿真花干花","image":"img/temp/pro04.jpg","cid":5,"isnew":"0","price":17.9},{"sid":12,"sname":"【最家】五朵仿真花干花","image":"img/temp/pro05.jpg","cid":5,"isnew":"0","price":17.9},{"sid":13,"sname":"【最家】狗一把仿真花干花","image":"img/temp/pro06.jpg","cid":5,"isnew":"0","price":17.9},{"sid":14,"sname":"【最家】一束仿真花干花","image":"img/temp/pro07.jpg","cid":5,"isnew":"0","price":17.9},{"sid":15,"sname":"【最家】蛇纹仿真花干花","image":"img/temp/pro08.jpg","cid":5,"isnew":"0","price":17.9},{"sid":16,"sname":"【最家】法式乡村复古抱枕","image":"img/temp/bz07.jpg","cid":8,"isnew":"0","price":47.9},{"sid":17,"sname":"【最家】时尚图案抱枕","image":"img/temp/bz06.jpg","cid":8,"isnew":"0","price":20.9},{"sid":18,"sname":"【最家】北欧时尚现代动物抱枕","image":"img/temp/bz05.jpg","cid":8,"isnew":"0","price":37.9},{"sid":19,"sname":"【最家】简约格子抱枕","image":"img/temp/bz04.jpg","cid":8,"isnew":"0","price":17.9},{"sid":20,"sname":"【最家】纯色棉麻抱枕","image":"img/temp/bz03.jpg","cid":8,"isnew":"0","price":17.9},{"sid":21,"sname":"【最家】时尚现代叮当猫抱枕","image":"img/temp/bz02.jpg","cid":8,"isnew":"0","price":17.9},{"sid":22,"sname":"【最家】欧式现代棉麻抱枕","image":"img/temp/bz01.jpg","cid":8,"isnew":"0","price":17.9},{"sid":23,"sname":"【最家】清新乡村田园抱枕","image":"img/temp/bz08.jpg","cid":8,"isnew":"0","price":17.9},{"sid":24,"sname":"【最家】清新乡村田园桌布","image":"img/temp/zb08.jpg","cid":7,"isnew":"0","price":17.9},{"sid":25,"sname":"【最家】法式乡村复古桌布","image":"img/temp/zb07.jpg","cid":7,"isnew":"0","price":17.9},{"sid":26,"sname":"【最家】清新简约防水桌布","image":"img/temp/zb06.jpg","cid":7,"isnew":"0","price":47.9},{"sid":27,"sname":"【最家】北欧圣诞节防水桌布格子","image":"img/temp/zb05.jpg","cid":7,"isnew":"0","price":59.9},{"sid":28,"sname":"【最家】田园桌布防水防油布","image":"img/temp/zb04.jpg","cid":7,"isnew":"0","price":20.9},{"sid":29,"sname":"【最家】欧式家用小圆桌台布","image":"img/temp/zb03.jpg","cid":7,"isnew":"0","price":47.9},{"sid":30,"sname":"【最家】地中海圆形桌布","image":"img/temp/zb02.jpg","cid":7,"isnew":"0","price":37.9},{"sid":31,"sname":"【最家】欧式田园棉麻桌布","image":"img/temp/zb01.jpg","cid":7,"isnew":"0","price":39.9},{"sid":32,"sname":"仙人掌墙壁挂画","image":"img/temp/sec101.jpg","cid":9,"isnew":"0","price":299.9},{"sid":33,"sname":"抽象派墙壁挂画","image":"img/temp/sec102.jpg","cid":9,"isnew":"0","price":299.9},{"sid":34,"sname":"沙滩墙壁挂画","image":"img/temp/sec103.jpg","cid":9,"isnew":"0","price":299.9},{"sid":35,"sname":"花瓶装饰墙壁挂画","image":"img/temp/sec104.jpg","cid":9,"isnew":"0","price":299.9},{"sid":36,"sname":"羚羊装饰画墙壁挂画","image":"img/temp/sec105.jpg","cid":9,"isnew":"0","price":299.9},{"sid":37,"sname":"大海装饰画墙壁挂画","image":"img/temp/sec106.jpg","cid":9,"isnew":"0","price":299.9},{"sid":38,"sname":"花丛装饰墙壁画挂画","image":"img/temp/sec201.jpg","cid":10,"isnew":"0","price":299.9},{"sid":39,"sname":"田园装饰画墙壁挂画","image":"img/temp/sec202.jpg","cid":10,"isnew":"0","price":299.9},{"sid":40,"sname":"远景装饰画墙壁挂画","image":"img/temp/sec203.jpg","cid":10,"isnew":"0","price":299.9},{"sid":41,"sname":"女人墙壁画挂画","image":"img/temp/sec204.jpg","cid":10,"isnew":"0","price":299.9},{"sid":42,"sname":"花簇墙壁画挂画","image":"img/temp/sec205.jpg","cid":10,"isnew":"0","price":299.9},{"sid":43,"sname":"航海墙壁画挂画","image":"img/temp/sec206.jpg","cid":10,"isnew":"0","price":299.9},{"sid":44,"sname":"新中式客厅山水装饰画墙壁挂画 ","image":"img/temp/sec302.jpg","cid":17,"isnew":"0","price":299.9},{"sid":45,"sname":"莲花客厅装饰画墙壁挂画","image":"img/temp/sec303.jpg","cid":17,"isnew":"0","price":299.9},{"sid":46,"sname":"白瓶香薰","image":"img/temp/xx01.jpg","cid":12,"isnew":"0","price":299.9},{"sid":47,"sname":"橙瓶香薰","image":"img/temp/xx02.jpg","cid":12,"isnew":"0","price":299.9},{"sid":48,"sname":"褶皱瓶香薰","image":"img/temp/xx103.jpg","cid":12,"isnew":"0","price":299.9},{"sid":49,"sname":"三小只香薰","image":"img/temp/xx104.jpg","cid":12,"isnew":"0","price":299.9},{"sid":50,"sname":"两透明香薰","image":"img/temp/xx105.jpg","cid":12,"isnew":"0","price":299.9},{"sid":51,"sname":"白花香薰","image":"img/temp/xx106.jpg","cid":12,"isnew":"0","price":299.9},{"sid":52,"sname":"坑洼白色香薰","image":"img/temp/xx201.jpg","cid":13,"isnew":"0","price":299.9},{"sid":53,"sname":"莲台香薰","image":"img/temp/xx202.jpg","cid":13,"isnew":"0","price":299.9},{"sid":54,"sname":"倒立莲蓬香薰","image":"img/temp/xx203.jpg","cid":13,"isnew":"0","price":299.9},{"sid":55,"sname":"简约奶玉香薰","image":"img/temp/xx204.jpg","cid":13,"isnew":"0","price":299.9},{"sid":56,"sname":"金线陶瓷香薰","image":"img/temp/xx205.jpg","cid":13,"isnew":"0","price":299.9},{"sid":57,"sname":"墨白香薰","image":"img/temp/xx206.jpg","cid":13,"isnew":"0","price":299.9},{"sid":58,"sname":"【最家】创意家居饰品工艺品装饰三口之家","image":"img/idea2.jpg","cid":14,"isnew":"0","price":199.0,"isidea":1},{"sid":59,"sname":"【最家】创意家居饰品工艺品装饰三口鹿摆设","image":"img/idea3.jpg","cid":14,"isnew":"0","price":78.0,"isidea":1},{"sid":60,"sname":"【最家】简约欧式装饰五彩缤纷花朵","image":"img/idea4.jpg","cid":5,"isnew":"0","price":52.0,"isidea":1},{"sid":61,"sname":"【最家】客厅餐桌创意插花桌面摆件","image":"img/idea5.jpg","cid":5,"isnew":"0","price":22.0,"isidea":1},{"sid":62,"sname":"【最家】简约可爱创意玻璃水杯","image":"img/idea7.jpg","cid":14,"isnew":"0","price":22.0,"isidea":1},{"sid":63,"sname":"【最家】简约可爱创意玻璃拼盘","image":"img/idea8.jpg","cid":14,"isnew":"0","price":33.0,"isidea":1},{"sid":64,"sname":"【最家】创意亲嘴鱼陶瓷装饰摆件","image":"img/idea9.jpg","cid":14,"isnew":"0","price":99.0,"isidea":1},{"sid":65,"sname":"【最家】创意多彩种子收纳摆件","image":"img/idea10.jpg","cid":14,"isnew":"0","price":49.0,"isidea":1},{"sid":66,"sname":"【最家】透明球简约创意装饰品","image":"img/id1.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":67,"sname":"【最家】网格简约创意装饰品","image":"img/id2.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":68,"sname":"【最家】两瓶一花简约创意装饰品","image":"img/id3.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":69,"sname":"【最家】两鹤简约创意装饰品","image":"img/id4.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":70,"sname":"【最家】父子简约创意装饰品","image":"img/id5.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":71,"sname":"【最家】鹿人简约创意装饰品","image":"img/id6.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":72,"sname":"【最家】穿越书籍的黑狗装饰品","image":"img/id7.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":73,"sname":"【最家】黑白苹果简约创意装饰品","image":"img/id8.jpg","cid":14,"isnew":"0","price":98.0,"isidea":1},{"sid":74,"sname":"【最家】创意摆件自行车复古","image":"img/id9.jpg","cid":14,"isnew":"0","price":288.0,"isidea":1},{"sid":75,"sname":"【最家】创意女孩秋千公主荡秋千","image":"img/id10.jpg","cid":14,"isnew":"0","price":200.0,"isidea":1},{"sid":76,"sname":"【最家】创意墙式挂饰水瓶","image":"img/id11.jpg","cid":14,"isnew":"0","price":50.0,"isidea":1},{"sid":77,"sname":"【最家】创意两只鹿","image":"img/id12.jpg","cid":14,"isnew":"0","price":99.0,"isidea":1},{"sid":78,"sname":"【最家】创意两只小象","image":"img/id13.jpg","cid":14,"isnew":"0","price":170.0,"isidea":1},{"sid":79,"sname":"【最家】简约客厅电视现代中式家居摆件","image":"img/id14.jpg","cid":14,"isnew":"0","price":179.0,"isidea":1},{"sid":80,"sname":"【最家】创意样板房树脂工艺品","image":"img/id15.jpg","cid":14,"isnew":"0","price":188.0,"isidea":1},{"sid":81,"sname":"【最家】创意笔筒小象","image":"img/id16.jpg","cid":14,"isnew":"0","price":30.0,"isidea":1}]}