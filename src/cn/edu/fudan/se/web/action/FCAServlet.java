package cn.edu.fudan.se.web.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.fudan.se.web.service.FCAGetDataService;
import cn.edu.fudan.se.web.util.CommUtil;

public class FCAServlet extends javax.servlet.http.HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1316194666030155170L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
//		String path = this.getServletContext().getRealPath("/");
//		path = path;
		System.out.println("FCAServlet");
		CommUtil.setRootPath(this.getServletContext().getRealPath("/"));
		
		FCAGetDataService data = new  FCAGetDataService();
		data.setSession(request.getSession());
		String id = request.getParameter("id");
		String jsonString = data.getChildGraphByID(id);
		
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonString);
		System.out.println(jsonString);

	}
}
