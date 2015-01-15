package cn.edu.fudan.se.web.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stackoverflow.bean.Post;
import com.stackoverflow.utils.PostDAOImpl;

import cn.edu.fudan.se.data.util.ExtConstant;
import cn.edu.fudan.se.web.bean.ExperimentType;
import cn.edu.fudan.se.web.bean.FLParameter;
import cn.edu.fudan.se.web.bean.HistoryNode;
import cn.edu.fudan.se.web.bean.HistoryTree;
import cn.edu.fudan.se.web.pojo.Element;
import cn.edu.fudan.se.web.service.GetDataService;
import cn.edu.fudan.se.web.service.GetStackOverflowDataService;
import cn.edu.fudan.se.web.util.CommUtil;

@SuppressWarnings("serial")
public class StackOverflowSearchServlet extends HttpServlet
{
	GetStackOverflowDataService data;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
		
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("SearchServlet");
		CommUtil.setRootPath(this.getServletContext().getRealPath("/"));
		String action = request.getParameter("action");
		System.out.println("Action:"+action);
		
		if("NULL".equals(action)){
			System.out.println("get Null in searchServlet");
			return;
		}
		
		//choose wether original data initial, or extended MFIE data initial.
		//by Luminosite.
		ExtConstant ec = ExtConstant.getInstance();
		if(ec.EXT_FLAG==ExtConstant.ORI_MFIE){

			data = new GetStackOverflowDataService();
		}
		else if(ec.EXT_FLAG==ExtConstant.EXT_MFIE){
			data = new GetStackOverflowDataService(GetDataService.INIT_PROJECT_LIST);
		}
		data.setSession(request.getSession());
		HistoryTree historyTree = (HistoryTree) request.getSession().getAttribute("HistoryTree");
		if(historyTree == null)
			historyTree = new HistoryTree();
		
		if (action == null )
		{
			String keywords = request.getParameter("q");
			System.out.println("kewords:"+keywords);
			if (keywords != null)
			{
				
				String str = new String(keywords.getBytes("ISO8859-1"), "UTF-8");
				String project = request.getParameter("project");
				
				System.out.println("Str:"+str);
				System.out.println("project:"+project);
				
				request.getSession().setAttribute("keywords", str);
				request.getSession().setAttribute("project", project);
				Map<String, Object> map = data.getData(str, project);							
				request.setAttribute("q", str);
				request.getSession().setAttribute("projects", data.getProjectList());
				request.setAttribute("map", map);
				map.put("projectName", request.getSession().getAttribute("project"));
				
				HistoryNode historyNode = new HistoryNode();
				historyNode.setKeywords(str);				
				historyNode.setParamater(str);
				historyNode.setNumberOfElement(Integer.parseInt(map.get("elementCount").toString()));
				historyNode.setData(map);
				historyNode.setProjectName(project);
				historyNode.setType(HistoryNode.TYPE_SEARCH);
				historyTree.setCurrentNode(null);
				System.out.println("ok1");
				historyTree.addNode(historyNode);
				request.getSession().setAttribute("HistoryTree", historyTree);
				System.out.println("ok2");
				map.put("HistoryTree", historyTree.toString());
		
				request.getRequestDispatcher("/search.jsp").forward(request, response);	
	
			}
			else		
			{
				request.getSession().setAttribute("projects", data.getProjectList());
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}

		}

		if("historyNavigate".equals(action))
		{
			System.out.println("in historyNavigate");
			String nodeID = request.getParameter("id");
			HistoryNode historyNode = historyTree.getNodeByID(nodeID);
			historyTree.setCurrentNode(historyNode);
			Map<String, Object> map = (Map<String, Object>) historyNode.getData();
			map.put("HistoryTree", historyTree.toString());
			request.setAttribute("map", map);
			//request.getSession().setAttribute("keywords", historyNode.getKeywords());
//It's not necessary to set the map in session, because it has been set in session via historyNode.
//			request.getSession().setAttribute("map", map);
			request.getRequestDispatcher("/search.jsp").forward(request, response);
		}
		if ("searchInResult".equals(action))
		{
			String keywords = request.getParameter("q");
			if (keywords != null)
			{
				String str = new String(keywords.getBytes("ISO8859-1"), "UTF-8");
				request.getSession().setAttribute("keywords", str);
				String project = request.getParameter("project");				
//				request.getSession().setAttribute("keywords", str);
				request.getSession().setAttribute("project", project);
				Map<String, Object> map = data.searchInResult(str,project);
				map.put("projectName", request.getSession().getAttribute("project"));
				request.getSession().setAttribute("project", project);
				request.setAttribute("q", str);
				request.setAttribute("map", map);
				
				HistoryNode historyNode = new HistoryNode();
				historyNode.setParamater(str);		
				historyNode.setNumberOfElement(Integer.parseInt(map.get("elementCount").toString()));
				historyNode.setData(map);
				historyNode.setProjectName(project);
				historyNode.setType(HistoryNode.TYPE_SEARCH_IN);
				historyTree.addNode(historyNode);
				request.getSession().setAttribute("HistoryTree", historyTree);
				map.put("HistoryTree", historyTree.toString());
				request.setAttribute("action","nothing");
				
				
			}
			request.getRequestDispatcher("/search.jsp").forward(request, response);
		}
		if ("index".equals(action))
		{
			request.setAttribute("q", request.getParameter("q"));
			request.getSession().setAttribute("projects", data.getProjectList());
			data.performExperiment(ExperimentType.fullLink); //for experiment
			request.getRequestDispatcher("/index.jsp").forward(request, response);

		}

		if ("detail".equals(action))
		{
			String value = request.getParameter("id");
			System.out.println("id:"+value);
			String keywords = (String) request.getSession().getAttribute("keywords");
			String project = (String) request.getSession().getAttribute("project");
			System.out.println(keywords);
			System.out.println(project);

			Map<String, Object> map = null;
			
			ExtConstant econstant = ExtConstant.getInstance();
			
			if(econstant.EXT_FLAG == ExtConstant.EXT_MFIE){
				map = (Map<String, Object>) (((HistoryTree)request.getSession()
						.getAttribute("HistoryTree")).getCurrentNode().getData());
				
			}else if(econstant.EXT_FLAG == ExtConstant.ORI_MFIE)
				map = data.getData(keywords, project);
			

			List<Post> list = (List<Post>) map.get("data");
			Post post = null;
			if (list != null)
			{
				for (Post p : list)
				{
					if (String.valueOf(p.getPostId()).equals(value))
					{
						post = p;
//						PostDAOImpl pdi = new PostDAOImpl();
//						try {
//							post.setAnswerList(pdi.findRelatedAnswers(post.getPostId()));
//							post.setCommentList(pdi.findRelatedComments(post.getPostId()));
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					}
				}
			}else{
				
			}
			if (post != null)
			{
				request.setAttribute("post", post);
			}
			request.getRequestDispatcher("/detail.jsp").forward(request, response);
		}
		if("performExperiment".equals(action))
		{
			data.performExperiment(ExperimentType.fullLink);
		}
		
		if ("recommend".equals(action))
		{
			String ids = request.getParameter("id");			
			Map<String, Object> map = data.getRecommend(ids);			
			request.setAttribute("map", map);
			request.getRequestDispatcher("/recommend.jsp").forward(request, response);
		}

		if ("submit".equals(action) )
		{
			
			FLParameter flParameter = new FLParameter();
			String topics = request.getParameter("topicTree1");
			String topicCall = request.getParameter("topicCallTree1");
			String topicCalledBy = request.getParameter("topicCalledByTree1");
			String valueOfPackageTree = request.getParameter("valueOfPackageTree");
			String valueOfTypeTree = request.getParameter("valueOfTypeTree");
			String callBy = request.getParameter("valueOfCalledBy");
			String call = request.getParameter("valueOfCall");
			String access = request.getParameter("valueOfAccess");
			String vHistory = request.getParameter("valueOfVHistory");
			
			System.out.println("VH:"+vHistory);
			System.out.println("DD:"+topics);

			flParameter.setTopic(topics);
			flParameter.setTopicCall(topicCall);
			flParameter.setTopicCalledBy(topicCalledBy);
			
			flParameter.setPackageTree(valueOfPackageTree);
			flParameter.setTypeTree(valueOfTypeTree);
			flParameter.setCallby(callBy);
			flParameter.setCall(call);
			flParameter.setAccess(access);
			flParameter.setvHistory(vHistory);
			Map<String, Object> map = null;

			map = data.getIncrementalData(flParameter);

			
			map.put("projectName", request.getSession().getAttribute("project"));
			request.setAttribute("map", map);
			request.setAttribute("q", request.getSession().getAttribute("keywords"));
			
			HistoryNode historyNode = new HistoryNode();			
			historyNode.setNumberOfElement(Integer.parseInt(map.get("elementCount").toString()));	
			historyNode.setData(map);
			historyNode.setParamater(flParameter.getParameterString());
			historyNode.setType(HistoryNode.getTypeByParameter(flParameter));
			historyTree.addNode(historyNode);
			request.getSession().setAttribute("HistoryTree", historyTree);
			map.put("HistoryTree", historyTree.toString());
			
			request.getRequestDispatcher("/search.jsp").forward(request, response);
			
		}


	}

}
