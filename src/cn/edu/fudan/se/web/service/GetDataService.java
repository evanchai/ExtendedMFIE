package cn.edu.fudan.se.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import cn.edu.fudan.se.data.util.ExtConstant;
import cn.edu.fudan.se.web.bean.ExperimentType;
import cn.edu.fudan.se.web.bean.FLParameter;
import cn.edu.fudan.se.web.bean.HistoryTree;
import cn.edu.fudan.se.web.bean.Method;
import cn.edu.fudan.se.web.pojo.Element;
import cn.edu.fudan.se.web.pojo.Tree;
import cn.edu.fudan.se.web.util.CommUtil;

public class GetDataService
{

	public static final int TEST = 0x0;
	public static final int INIT_PROJECT_LIST = 0x1;

	HttpSession session;
	ProjectService projectService;
	public GetDataService(int type){
		
		projectService = new ProjectService(type);
	}
	
	public GetDataService()
	{
		projectService = ProjectService.getInstance();

	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> searchInResult(String keywords, String project)
	{
		Map<String, Object> map = (Map<String, Object>) (((HistoryTree)session
				.getAttribute("HistoryTree")).getCurrentNode().getData());
		
		List<Element> elementList = (List<Element>) map.get("data");
		elementList = projectService.searchByKeywords(elementList, keywords, project);
		List<Method> satifiedMethodList = projectService.getSatifiedMethodList();

		System.out.println("size of elements " + elementList.size());
		elementList = sortElementListByKeywords(elementList, keywords);
		satifiedMethodList = sortMethodListByKeyWords(satifiedMethodList, keywords);
		return createDataToDisplay(elementList, satifiedMethodList, project);
	}

	/**
	 * 根据关键字获取页面要展示的内容，map
	 * 
	 * @param keywords
	 * @return
	 */
	public Map<String, Object> getData(String keywords, String project)
	{
		
		ExtConstant ec = ExtConstant.getInstance();
		
		List<Element> elementList = null;
		
		if(ec.EXT_FLAG == ExtConstant.EXT_MFIE)
			elementList = projectService.searchByKeywordsLucene(keywords, project);
		else if(ec.EXT_FLAG == ExtConstant.ORI_MFIE)
			elementList = projectService.searchByKeywords(keywords, project);
		
		List<Method> satifiedMethodList = projectService.getSatifiedMethodList();
		System.out.println("size of elements " + elementList.size());
		elementList = sortElementListByKeywords(elementList, keywords);
		satifiedMethodList = sortMethodListByKeyWords(satifiedMethodList, keywords);
		return createDataToDisplay(elementList, satifiedMethodList, project);
	}

	class SortElement
	{
		Object element;
		int hitCount;

		public SortElement()
		{
			hitCount = 0;
		}
	}

	private void insertSortElement(SortElement sortElement, List<SortElement> sortElements)
	{
		int index = 0;
		while (index < sortElements.size())
		{
			if (sortElement.hitCount > sortElements.get(index).hitCount)
			{
				sortElements.add(index, sortElement);
				return;
			}
			index++;

		}
		sortElements.add(index, sortElement);

	}

	private List<Method> sortMethodListByKeyWords(List<Method> satifiedMethodList, String keywords)
	{
		List<Method> result = new ArrayList<Method>();
		List<SortElement> sortElements = new ArrayList<GetDataService.SortElement>();
		String[] keywordItems = CommUtil.splitWithoutSpace(keywords, "[ *;]");

		for (Method method : satifiedMethodList)
		{
			SortElement sortElement = new SortElement();
			sortElement.element = method;
			for (String keyword : keywordItems)
			{
				Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
				Matcher matcher;
				matcher = pattern.matcher(method.getClassName());
				int count = CommUtil.getMatchCount(matcher) * 100;
				matcher = pattern.matcher(method.getName());
				count = count + CommUtil.getMatchCount(matcher) * 10;
				matcher = pattern.matcher(method.getContent());
				count = count + CommUtil.getMatchCount(matcher);
				
				sortElement.hitCount += count;
			}
			insertSortElement(sortElement, sortElements);
		}
		for (SortElement sortElement : sortElements)
		{
			result.add((Method) sortElement.element);
		}
		return result;
	}

	private List<Element> sortElementListByKeywords(List<Element> elementList, String keywords)
	{
		List<Element> result = new ArrayList<Element>();
		List<SortElement> sortElements = new ArrayList<GetDataService.SortElement>();
		String[] keywordItems = CommUtil.splitWithoutSpace(keywords, "[ ;*]");

		for (Element element : elementList)
		{
			SortElement sortElement = new SortElement();
			sortElement.element = element;
			for (String keyword : keywordItems)
			{
				Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
				Matcher matcher;
				matcher = pattern.matcher(element.getClassName());
				int count = CommUtil.getMatchCount(matcher) * 100;
				matcher = pattern.matcher(element.getMethodname());
				count = count + CommUtil.getMatchCount(matcher) * 10;
				matcher = pattern.matcher(element.getMethodbody());
				count = count + CommUtil.getMatchCount(matcher);				
				sortElement.hitCount += count;
			}
			insertSortElement(sortElement, sortElements);
		}
		for (SortElement sortElement : sortElements)
		{
			result.add((Element) sortElement.element);
		}
		return result;

	}

	private Map<String, Object> createDataToDisplay(List<Element> elementList, List<Method> satifiedMethodList
			, String projectName)
	{
		Tree tree = new Tree();
		tree.setElementList(elementList);
		tree.setClassList(projectService.getTotalClassList());
		tree.createTreeFromMethodList(satifiedMethodList, projectName);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", elementList);

		JSONArray topicTree = JSONArray.fromObject(tree.getTopicJSONString());
		map.put("topicTree", topicTree);
		JSONArray topicCallTree = JSONArray.fromObject(tree.getTopicCallJSONString());
		map.put("topicCallTree", topicCallTree);
		JSONArray topicCalledByTree = JSONArray.fromObject(tree.getTopicCalledByJSONString());
		map.put("topicCalledByTree", topicCalledByTree);
		JSONArray packageTree = JSONArray.fromObject(tree.getPackageJSONString());
		map.put("packageTree", packageTree);
		JSONArray typeTree = JSONArray.fromObject(tree.getTypeJSONString(projectName));
		map.put("typeTree", typeTree);
		JSONArray calledByTree = JSONArray.fromObject(tree.getCallByJSONString());
		map.put("calledByTree", calledByTree);
		JSONArray callTree = JSONArray.fromObject(tree.getCallJSONString());
		map.put("callTree", callTree);
		
		//Added feature.
		JSONArray vHistoryTree = JSONArray.fromObject(tree.getVHistoryJSONString());
		map.put("vHistoryTree", vHistoryTree);
		
//		JSONArray accessTree = JSONArray.fromObject(tree.getAccessJSONString());
//		map.put("accessTree", accessTree);
		map.put("elementCount", elementList.size());
		map.put("projectName", projectName);
		session.setAttribute("map", map);
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getIncrementalData(FLParameter flParameter)
	{
		Map<String, Object> map = (Map<String, Object>) session.getAttribute("map");
		if (flParameter.isEmpty())
			return map;
		List<Element> elementList = (List<Element>) map.get("data");
		System.out.println("At first: Length of element list is " + elementList.size());
		elementList = processFilter(flParameter, elementList);
		
		List<Method> methodList = null;
		
		ExtConstant ec = ExtConstant.getInstance();
		if(ec.EXT_FLAG==ExtConstant.ORI_MFIE){
			methodList = projectService.getMethodListByElementList(elementList);
		}
		else if(ec.EXT_FLAG==ExtConstant.EXT_MFIE){
			methodList = projectService.getMethodListByElementListLucene(elementList);
		}
		
		return createDataToDisplay(elementList, methodList, elementList.get(0).getProjectName());
	}
	
	private List<Element> processFilter(FLParameter flParameter, List<Element> elementList)
	{
		List<Element> newElements = new ArrayList<Element>();
		List<String> selectedElementIDs = flParameter.getIDs();
		for(Element element : elementList)
		{
			if(selectedElementIDs.contains(element.getId()))
			{
				newElements.add(element);
			}
		}
		return newElements;
	}




	public List<String> getProjectList()
	{
		return projectService.getProjectList();
	}





	public void setSession(HttpSession session)
	{
		this.session = session;

	}

	public Map<String, Object> getRecommend(String ids)
	{
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("data", projectService.getRecommend(ids.split("[;]")));
		return map;
	
	}

	public void performExperiment(ExperimentType fulllink)
	{
		projectService.performExperiment(fulllink);
		System.out.println("Perform Experiment");
		
		
	}

}
