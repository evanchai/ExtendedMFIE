package cn.edu.fudan.se.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;






import org.carrot2.core.Cluster;

import com.stackoverflow.bean.Post;
import com.stackoverflow.utils.PostDAOImpl;

import net.sf.json.JSONArray;
import cn.edu.fudan.se.cluster.MultiFacets;
import cn.edu.fudan.se.data.util.ExtConstant;
import cn.edu.fudan.se.facet.Facet;
import cn.edu.fudan.se.filter.FilterStopWord;
import cn.edu.fudan.se.web.bean.ExperimentType;
import cn.edu.fudan.se.web.bean.FLParameter;
import cn.edu.fudan.se.web.bean.HistoryTree;
import cn.edu.fudan.se.web.bean.Method;
import cn.edu.fudan.se.web.pojo.Element;
import cn.edu.fudan.se.web.pojo.StackOverflowTree;
import cn.edu.fudan.se.web.pojo.Tree;
import cn.edu.fudan.se.web.util.CommUtil;

public class GetStackOverflowDataService
{

	public static final int TEST = 0x0;
	public static final int INIT_PROJECT_LIST = 0x1;
	List<Cluster> clusterByContentList = null;
	List<Cluster> clusterByTagList = null;

	HttpSession session;
	ProjectService projectService;
	List<Post> postList = null;
	MultiFacets multif = new MultiFacets();
	public GetStackOverflowDataService(int type){
		
//		projectService = new ProjectService(type);
	}
	
	public GetStackOverflowDataService()
	{
//		projectService = ProjectService.getInstance();

	}
	
	public List<Post> getListPost()
	{
		return this.postList;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> searchInResult(String keywords, String project)
	{
//		Map<String, Object> map = (Map<String, Object>) (((HistoryTree)session
//				.getAttribute("HistoryTree")).getCurrentNode().getData());
//		
//		List<Element> elementList = (List<Element>) map.get("data");
//		elementList = projectService.searchByKeywords(elementList, keywords, project);
//		List<Method> satifiedMethodList = projectService.getSatifiedMethodList();
//
//		System.out.println("size of elements " + elementList.size());
//		elementList = sortElementListByKeywords(elementList, keywords);
//		satifiedMethodList = sortMethodListByKeyWords(satifiedMethodList, keywords);
//		return createDataToDisplay(elementList, satifiedMethodList, project);
		return null;
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
		
//		List<Element> elementList = null;
//		SearchResultService  srs = new SearchResultService();
//		List<Facet> facetItemList = null;

//		if(ec.EXT_FLAG == ExtConstant.EXT_MFIE)
//		{
//			srs.searchStackOverflow(keywords);
//			postList = srs.getPostList();
//			facetItemList = srs.getFacetItemList();
//			clusterByContentList = srs.getClusterByContent();
//			clusterByTagList = srs.getClusterByTag();
//			System.out.println("jqtjqt:"+keywords);
//		}
//		else if(ec.EXT_FLAG == ExtConstant.ORI_MFIE)
//		{
//			srs.searchStackOverflow(keywords);
//			srs.searchStackOverflow(keywords);
//			postList = srs.getPostList();
//			facetItemList = srs.getFacetItemList();
//			clusterByContentList = srs.getClusterByContent();
//			clusterByTagList = srs.getClusterByTag();
//			System.out.println("jqtjqt:"+keywords);
//		}
		
		PostDAOImpl pdi = new PostDAOImpl();
		FilterStopWord fsw = new FilterStopWord();
	    postList = pdi.findPosts(fsw.getStringWithoutStopWord(keywords));
//		postList = pdi.findSqlitePosts(fsw.getStringWithoutStopWord(keywords));
		
		
//       postList = CreateData.getData();
//		multif.init();
		clusterByContentList = getClusterByContent(postList);
		clusterByTagList = getClusterByTag(postList);
		System.out.println("size of elements " + postList.size());

		return createDataToDisplay(postList,clusterByContentList,clusterByTagList);
	}
	
	public List<Cluster> getClusterByContent(List<Post> postList)
	{
		try {
			return 	multif.clusteringByContent(postList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public List<Cluster> getClusterByTag(List<Post> postList)
	{
		try {
			return multif.clusteringByTag(postList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
		List<SortElement> sortElements = new ArrayList<GetStackOverflowDataService.SortElement>();
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
		List<SortElement> sortElements = new ArrayList<GetStackOverflowDataService.SortElement>();
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

	private Map<String, Object> createDataToDisplay(List<Post> postList,
			List<Cluster> clusterByContentList,List<Cluster> clusterByTagList)
	{
		StackOverflowTree tree = new StackOverflowTree();
		tree.setPostList(postList,clusterByContentList,clusterByTagList);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", postList);

		JSONArray focusTree = JSONArray.fromObject(tree.getFocusJSONString());
		map.put("focusFacet", focusTree);
//		System.out.println(focusTree.toString());
		JSONArray systemFacet = JSONArray.fromObject(tree.getSystemJSONString());
		map.put("systemFacet", systemFacet);
		JSONArray languageFacet = JSONArray.fromObject(tree.getLanguageJSONString());
		map.put("languageFacet", languageFacet);
//		JSONArray topicCalledByTree = JSONArray.fromObject(tree.getTopicCalledByJSONString());
//		map.put("topicCalledByTree", topicCalledByTree);
    	JSONArray packageTree = JSONArray.fromObject(tree.getTagJSONString());
    	map.put("packageTree", packageTree);
    	JSONArray typeTree = JSONArray.fromObject(tree.getContentJSONString());
    	map.put("typeTree", typeTree);
//		JSONArray calledByTree = JSONArray.fromObject(tree.getCallByJSONString());
//		map.put("calledByTree", calledByTree);
//		JSONArray callTree = JSONArray.fromObject(tree.getCallJSONString());
//    	map.put("callTree", callTree);
		
		//Added feature.
//		JSONArray vHistoryTree = JSONArray.fromObject(tree.getVHistoryJSONString());
//		map.put("vHistoryTree", vHistoryTree);
		
//		JSONArray accessTree = JSONArray.fromObject(tree.getAccessJSONString());
//		map.put("accessTree", accessTree);
		map.put("elementCount", postList.size());
		map.put("projectName", "StackOverflow");
		session.setAttribute("map", map);
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getIncrementalData(FLParameter flParameter,int treeNum)
	{
		Map<String, Object> map = (Map<String, Object>) session.getAttribute("map");
		if (flParameter.isEmpty())
			return map;
		List<Post> postList = (List<Post>) map.get("data");
		List<String> selectId = filter(flParameter,treeNum);
//		System.out.println("At first: Length of element list is " + elementList.size());
		postList = processFilter(selectId, postList);
		
		
//		List<Method> methodList = null;
		
//		ExtConstant ec = ExtConstant.getInstance();
//		if(ec.EXT_FLAG==ExtConstant.ORI_MFIE){
//			methodList = projectService.getMethodListByElementList(elementList);
//		}
//		else if(ec.EXT_FLAG==ExtConstant.EXT_MFIE){
//			methodList = projectService.getMethodListByElementListLucene(elementList);
//		}
		clusterByContentList = getClusterByContent(postList);
		clusterByTagList = getClusterByTag(postList);
		return createDataToDisplay(postList, clusterByContentList, clusterByTagList);
	}
	
	public List<String> filter(FLParameter flParameter,int treeNum)
	{
		List<String> ids = new ArrayList<String>();


		List<String> facetString = new ArrayList<String>();
		facetString .add(flParameter.getTopic());
		facetString .add(flParameter.getTopicCall());
		facetString .add(flParameter.getTopicCalledBy());
		facetString .add(flParameter.getPackageTree());
		facetString .add(flParameter.getTypeTree());
		for(String facet:facetString)
		{
			List<String> facetIds = SelectedIds(facet);
			//make array deduplicated
			facetIds = deduplicated(facetIds);
			//concat all arrays
			ids.addAll(facetIds);
		}

		 
		//var ids= SelectedIds(allSelectedItems);
	    if(treeNum>1) 
	    	ids=getArray(ids,treeNum); 
	    return ids;
	}
	
	private List<Post> processFilter(List<String> selectId, List<Post> postList)
	{
		List<Post> newPostList = new ArrayList<Post>();
		for(Post post : postList)
		{
			if(selectId.contains(post.postId+""))
			{
				newPostList.add(post);
			}
		}
		return newPostList;
	}
	
	public List<String> SelectedIds(String titleString)
	{
		List<String> ids = new ArrayList<String>();
		int index1 = titleString.indexOf("&");
		int num = 0;
		
	    while(index1!=-1)
	    {
	    	num++;
	    	titleString = titleString.substring(index1+1, titleString.length());
	    	//alert(allSelectedItems);
	    	int index2 = titleString.indexOf("&");
	    	String id = titleString.substring(0, index2);
	    	ids.add(id);
	    	titleString = titleString.substring(index2+1, titleString.length());
	    	index1 = titleString.indexOf("&");
	    };
	    return ids;
	}
	
	public List<String> deduplicated(List<String> a) {		
	    HashMap<String,Boolean> hash = new HashMap<String,Boolean>();
    	int len = a.size();
    	List<String> result = new ArrayList<String>();

		 for (int i = 0; i < len; i++){
			 String key = a.get(i);
		     if (hash.get(key)==null){
		         hash.put(key, true);
		         result.add(key);
		     } 
		 }		
		 return result;
	}
	public List<String> getArray(List<String> a,int treeNum) {
	    HashMap<String,Integer> hash = new HashMap<String,Integer>();
	 	int len = a.size();
    	List<String> result = new ArrayList<String>();
		
		 for (int i = 0; i < len; i++){
			 String key = a.get(i);
		     if (hash.get(key)==null){
		    	 hash.put(key, 1);
		     }
		     else if(hash.get(key)>=1){
		    	 int value = hash.get(key);
		    	 value ++;
		     	hash.put(key, value);
		     	if(value>=treeNum) 
		     		result.add(key);

		     }
		 }
		 return result;
		}




	public List<String> getProjectList()
	{
		List<String> projectList = new ArrayList<String>();
		projectList.add("StackOverflow");
		return projectList;
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

