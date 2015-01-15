package cn.edu.fudan.se.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.fudan.se.web.bean.ResultItem;

/**
 * Servlet implementation class ResultSetServlet
 */
public class ResultSetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String Q = "\"";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultSetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String id = request.getParameter("id").trim();
		String call = request.getParameter("callon");
		System.out.println("ResultSetServlet");
		String caller = (call.substring(call.indexOf("{")+1, call.indexOf("}"))).trim();
		
		@SuppressWarnings("unchecked")
		ArrayList<ResultItem> selectResultList = (ArrayList<ResultItem>) request.getSession()
				.getAttribute(ResultItem.RESULT_LIST);
		
		if(selectResultList==null){
			selectResultList = new ArrayList<ResultItem>();
		}
		if("NULL_add".equals(action)){
			boolean notAdded = true;
			for(ResultItem r:selectResultList){
				if(r.getName().equals(id))
					notAdded=false;
			}
			if(notAdded && id!=null && id.length()>0)
				selectResultList.add(new ResultItem(id, caller));
		}else if("NULL_remove".equals(action)){
			for(int i=0;i<selectResultList.size();i++){
				if(selectResultList.get(i).getName().equals(id)){
					selectResultList.remove(i);
					break;
				}
			}
		}
		request.getSession().setAttribute(ResultItem.RESULT_LIST, selectResultList);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("{\"list\":[");
		int n=selectResultList.size();
		for(int i=0;i<n;i++){
			ResultItem ri = selectResultList.get(i);
			sb.append("{\"name\":"+Q+ri.getName()+Q+",\"onC\":"+Q+ri.getOnClick()+Q+"}");
			if(i!=(n-1)){
				sb.append(",");
			}
		}
		
		sb.append("]}");
		
		PrintWriter pw = response.getWriter();
		pw.write(sb.toString());//返回数据
		pw.flush();
		pw.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
