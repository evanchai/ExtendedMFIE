package cn.edu.fudan.se.web.pojo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.ProcessingResult;

import com.stackoverflow.bean.Post;

import cn.edu.fudan.se.data.util.ExtConstant;
import cn.edu.fudan.se.facet.Facet;
import cn.edu.fudan.se.facet.Grade;
import cn.edu.fudan.se.web.bean.CallBean;
import cn.edu.fudan.se.web.bean.CallBy;
import cn.edu.fudan.se.web.bean.Class;
import cn.edu.fudan.se.web.bean.Method;
import cn.edu.fudan.se.web.bean.MiddleData;
import cn.edu.fudan.se.web.dal.DBHelper;
import cn.edu.fudan.se.web.util.AstUtil;
import cn.edu.fudan.se.web.util.CommUtil;

public class StackOverflowTree
{

	class LeafNode extends TreeNode
	{
		private List<String> relatedIDs = new ArrayList<String>();

		
		public LeafNode(String text)
		{
			super(text);
			
		}

	

		public void addRelatedID(String ID)
		{
			if (!relatedIDs.contains(ID))
				relatedIDs.add(ID.trim());
		}



		public List<String> getRelatedIDs()
		{
			return relatedIDs;
		}

		private String getRelatedIDsText()
		{
			StringBuilder sb = new StringBuilder();
			for (String str : relatedIDs)
			{
				sb.append(str + " ");
			}
			return sb.toString();
		}

		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			sb.append(getShortText() + " <span style=\"" + CSS_OF_ID + "\"> ");
			sb.append(getRelatedIDsText());
			sb.append("</span>");
			return sb.toString();
		}

	}
	class PackageNode extends TreeNode
	{
		
		private List<LeafNode> leafList = new ArrayList<LeafNode>();

		public PackageNode(String text)
		{
			super(text);
			
		}



		public void addLeaf(LeafNode leaf)
		{
			for (LeafNode currentNode : leafList)
			{
				if (currentNode.getText().equals(leaf.getText()))
				{
					for (String id : leaf.getRelatedIDs())
					{
						if (!currentNode.getRelatedIDs().contains(id))
							currentNode.addRelatedID(id);
					}
					return;
				}
			}

			this.leafList.add(leaf);
		}



		private int getCountOfRelatedID()
		{
			List<String> IDs = new ArrayList<String>();
			for (LeafNode node : leafList)
			{
				for (String id : node.getRelatedIDs())
				{
					if (!IDs.contains(id))
						IDs.add(id);
				}
			}

			return IDs.size();
		}

		public List<LeafNode> getLeaves()
		{
			return leafList;
		}

		public String getText()
		{
			if (!text.endsWith("results)"))
				text = getShortText() + " (" + this.getCountOfRelatedID() + " results)";
			return text;
		}

	}
	class TreeNode
	{
		protected String text;
		public TreeNode(String text)
		{
			this.text = text;
		}
		
		public String getShortText()
		{
			String result = new String(text);
			for (String str : Element.USELESS_PACKAGENAME)
			{
				if (result.startsWith(str))
					result = result.substring(str.length());

			}
			return result;
		}

		public String getText()
		{
			return text;
		}

		public void setText(String text)
		{
			this.text = text;
		}
	}

	class TypeNode extends TreeNode
	{
		public TypeNode(String text)
		{
			super(text);
			// TODO Auto-generated constructor stub
		}

		
		private List<TypeNode> subNodes = new ArrayList<StackOverflowTree.TypeNode>();;
		private TypeNode parentNode;



		public void addSubNode(TypeNode subNode)
		{
			for(TypeNode child : subNodes)
			{
				if(child.getText().equals(subNode.getText()))
					return;
			}
			subNodes.add(subNode);
		}

		public TypeNode findNodeByName(String strInterface)
		{

			if (text.equals(strInterface))
				return this;
			for (TypeNode subNode : subNodes)
			{
				TypeNode correctNode = subNode.findNodeByName(strInterface);
				if (correctNode != null)
					return correctNode;
			}
			return null;
		}

		public TypeNode getParentNode()
		{
			return parentNode;
		}

		public List<TypeNode> getSubNodes()
		{
			return subNodes;
		}



		public void setParentNode(TypeNode parentNode)
		{
			this.parentNode = parentNode;
			
		}


	}

	private List<TypeNode> typeNodeList;

	public static final String CSS_OF_ID = "color:white";

	private List<Class> totalClassList;
	private Map<String, Class> currentClassList;

	private static String quote = "'";

	private List<Post> postList;
	
	
	private List<Cluster> clusterByContentList;
	
	private List<Cluster> clusterByTagList;

	public StackOverflowTree()
	{
		typeNodeList = new ArrayList<StackOverflowTree.TypeNode>();

	}

	public void createTreeFromMethodList(List<Method> satifiedMethodList
			,String projectName)
	{

		currentClassList = new HashMap<String, Class>();

		for (Method method : satifiedMethodList)
		{
			String fullClassName = method.getClassName();
			
			Class oClass = null;
			
			ExtConstant extConstance = ExtConstant.getInstance();
			if(extConstance.EXT_FLAG == ExtConstant.ORI_MFIE)
				oClass = findClassByName(fullClassName, totalClassList);
			else if(extConstance.EXT_FLAG == ExtConstant.EXT_MFIE)
				oClass = findClassByNameInDB(fullClassName, projectName);
			
//			System.out.println(missCount+"/"+all);
			if (!currentClassList.containsKey(fullClassName) && oClass!=null)
			{
				currentClassList.put(fullClassName, oClass);
			}

		}
	}

	public static int all = 0;
	public static int missCount = 0;
	/**
	 * find item from classInfo database, and return according "Class" instance.
	 * Created by Luminosite.
	 * 
	 * @param fullClassName
	 * @return
	 */
	private Class findClassByNameInDB(String fullClassName, String projectName) {
		
		Class newClass = null;
		String sql = "SELECT isinterface, issuperclass,  superclass, interfaces, modifies FROM classinfo where projectName = '"
				+ projectName + "' and name='"+fullClassName+"' and isanonymous = 0  order by name";
		
		try {
			Connection conn = DBHelper.getConnection();
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			
			if (rs.next()) {
				newClass = new Class();
				newClass.setName(fullClassName);

				newClass.setInterface(rs.getBoolean(1));
				newClass.setSuperClass(rs.getBoolean(2));
				newClass.setSuperClass(rs.getString(3));
				newClass.setInterfaces(rs.getString(4));
				newClass.setModifier(rs.getString(5));
				newClass.setProjectName(projectName);

				rs.close();
				state.close();
			}else {
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
//		if(newClass==null){
//			if(sql.contains(".cpp"))
//				sql = 
//		}

//		all++;
//		if(newClass==null){
//			System.out.println("null class:"+sql);
//			missCount++;
//		}
		return newClass;
	}

	private Class findClassByName(String className, Collection<Class> classList2)
	{
		for (Class oClass : classList2)
		{
			if (oClass.getName().equals(className))
				return oClass;
		}
		return null;
	}



	private Element findElementByMethodName(List<Element> elementList, String methodName)
	{
		for (Element element : elementList)
			if (element.getMethodname().equals(methodName))
				return element;
		return null;
	}

	private PackageNode findPackageInTree(List<PackageNode> packageNodes, String packageName)
	{

		for (PackageNode packageNode : packageNodes)
		{

			if (packageNode.text.equals(packageName))
				return packageNode;
		}
		return null;
	}

	/**
	 * Search the position in specified Node by className
	 * 
	 * @param oClass
	 * @param typeNode
	 * @return
	 */
	private TypeNode findPosInNode(Class oClass, TypeNode typeNode)
	{
		if (typeNode.getText().equals(oClass.getName()))
			return typeNode;
		for (TypeNode childNode : typeNode.getSubNodes())
		{
			if (findPosInNode(oClass, childNode) != null)
				return childNode;
		}
		return null;
	}

	private TypeNode findPosInNodeList(Class oClass, List<TypeNode> nodeList)
	{
		TypeNode result = null;
		for (TypeNode node : nodeList)
		{
			result = findPosInNode(oClass, node);
			if (result != null)
				break;
		}
		return result;

	}

//	public String getAccessJSONString()
//	{
//		List<String> accessList = getAccessList();
//		List<PackageNode> accessPackageList = new ArrayList<StackOverflowTree.PackageNode>();
//		for (String accessField : accessList)
//		{
//			String packageName = AstUtil.getPackageName(accessField);
//			if (CommUtil.stringNullOrZero(packageName))
//				continue;
//			PackageNode packageNode = findPackageInTree(accessPackageList, packageName);
//			if (packageNode == null)
//			{
//				packageNode = new PackageNode(packageName);
//				accessPackageList.add(packageNode);
//			}
//			LeafNode leafNode = new LeafNode(AstUtil.getShortClassName(accessField));
//			for (Element element : elementList)
//			{
//				if (element.getAccessfields().contains(accessField))
//				{
//					leafNode.addRelatedID(element.getId());
//				}
//			}
//			packageNode.addLeaf(leafNode);
//		}
////		return getPackageJSONString(accessPackageList, true);
//	}

//	private List<String> getAccessList()
//	{
//		List<String> result = new ArrayList<String>();
//		for (Element element : elementList)
//		{
//			for (String accessField : element.getAccessfields())
//			{
//				if (result.contains(accessField) == false)
//					result.add(accessField);
//			}
//		}
//		return result;
//	}

	private CallBean getCallBeanInList(String callbyClassName, List<CallBean> callList)
	{
		for (CallBean callBean : callList)
			if (callBean.getCallClass().equals(callbyClassName))
				return callBean;
		return null;
	}

	private CallBy getCallbyInList(String callbyClassName, List<CallBy> callbyList)
	{
		for (CallBy callBy : callbyList)
			if (callBy.getCallByClass().equals(callbyClassName))
				return callBy;
		return null;
	}

	public String getCallByJSONString()
	{

		return "[]";
	}

	private List<CallBy> getCallByList()
	{
//		List<CallBy> result = new ArrayList<CallBy>();
//		for (Element element : elementList)
//		{
//			for (String callbyMethod : element.getCalledBymethods())
//			{
//				String callbyClassName = AstUtil.getClassName(callbyMethod);
//				CallBy callby = getCallbyInList(callbyMethod, result);
//				if (callby == null)
//				{
//					callby = new CallBy();
//					callby.setCallByClass(callbyClassName);
//					result.add(callby);
//				}
//				callby.setCount(callby.getCount() + 1);
//
//			}
//		}
//		return result;
		return null;
	}

	public String getCallJSONString()
	{
		return "[]";
	}

	private List<CallBean> getCallList()
	{
//		List<CallBean> result = new ArrayList<CallBean>();
//		for (Element element : elementList)
//		{
//			for (String callbyMethod : element.getCallmethods())
//			{
//				String callbyClassName = AstUtil.getClassName(callbyMethod);
//				CallBean callBean = getCallBeanInList(callbyMethod, result);
//				if (callBean == null)
//				{
//					callBean = new CallBean();
//					callBean.setCallClass(callbyClassName);
//					result.add(callBean);
//				}
//				callBean.setCount(callBean.getCount() + 1);
//
//			}
//		}
//		return result;
		return null;
	}

	private String getIDByMethodName(String methodName)
	{
//		for (Element element : elementList)
//		{
//			if (element.getMethodname_().equals(methodName))
//				return element.getId();
//		}
		return null;
	}


	
	
	private String getFocusJSONString(List<Post> postList)
	{
		StringBuilder sb = new StringBuilder();
		HashMap<String,List<Post>> facetPost = new HashMap<String,List<Post>>();
		for(Post post:postList)
		{
			String focus = post.getFocus();
			if(focus == null)
				continue;
			int index = focus.indexOf("-");

			if(index!=-1)
				focus = focus.substring(index+1,focus.length());
			if(facetPost.containsKey(focus))
				facetPost.get(focus).add(post);
			else
			{
				List<Post> list = new ArrayList<Post>();
				list.add(post);
				facetPost.put(focus, list);
			}
		}
	
		Iterator iter = facetPost.keySet().iterator();
		sb.append("[");
		int size = facetPost.size();
		int id = 0;
		String key = "";
		List<Post> list = null;
		while (iter.hasNext())
		{
			
			key = (String)iter.next();
			list = facetPost.get(key);
	
			sb.append("{");
			sb.append("text: " + quote);
			sb.append(key+"("+list.size()+")");
			sb.append(quote);
			sb.append(", children: [");
			
			for(Post post:list)
			{
				sb.append("{text: " + quote);
				sb.append(post.post_title.replace("{", "")
						.replace("}", "").replace("[", "").replace("]", "").replace("'", ""));
				sb.append("<span style=\"" + CSS_OF_ID + "\"> ");
				sb.append("&"+post.postId +"&");
				sb.append("</span>");
				sb.append(quote + "}");
				sb.append(", ");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append("]"); // end children
			sb.append("}"); // end of current node
			sb.append(", ");
		}
		if (sb.length() > 3)
			sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}
	
	private String getEnvironmentJSONString(List<Post> postList)
	{
		StringBuilder sb = new StringBuilder();
		HashMap<String,List<Post>> facetPost = new HashMap<String,List<Post>>();
		for(Post post:postList)
		{
			String focus = post.getEnvironment();
			if(focus == null)
				continue;
			int index = focus.lastIndexOf("-");
			if(index!=-1)
				focus = focus.substring(index+1,focus.length());
			if(facetPost.containsKey(focus))
				facetPost.get(focus).add(post);
			else
			{
				List<Post> list = new ArrayList<Post>();
				list.add(post);
				facetPost.put(focus, list);
			}
		}
	
		Iterator iter = facetPost.keySet().iterator();
		sb.append("[");
		int size = facetPost.size();
		int id = 0;
		String key = "";
		List<Post> list = null;
		while (iter.hasNext())
		{
			
		    key = (String)iter.next();
			list = facetPost.get(key);
	
			sb.append("{");
			sb.append("text: " + quote);
			
			sb.append(key+"("+list.size()+")");
			sb.append(quote);
			sb.append(", children: [");
			for(Post post:list)
			{
				sb.append("{text: " + quote);
				sb.append(post.post_title.replace("{", "")
						.replace("}", "").replace("[", "").replace("]", "").replace("'", ""));
				sb.append("<span style=\"" + CSS_OF_ID + "\"> ");
				sb.append("&"+post.postId +"&");
				sb.append("</span>");
				sb.append(quote + "}");
				sb.append(", ");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append("]"); // end children
			sb.append("}"); // end of current node
			sb.append(", ");
		}
		if (sb.length() > 3)
			sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}
	
	private String getContentJSONString(List<Cluster> clusterByContentList)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(Cluster cluster:clusterByContentList)
		{
				boolean is = false;
				sb.append("{");
				sb.append("text: " + quote);
				List<Document> documentList = cluster.getDocuments();
				sb.append(cluster.getLabel()+"("+documentList.size()+")");

				sb.append(quote);
				sb.append(", children: [");
			
			
				for (Document document:documentList)
				{
					Post post = document.getField("post");
					sb.append("{text: " + quote);
					sb.append(post.post_title.replace("{", "")
							.replace("}", "").replace("[", "").replace("]", "").replace("'", ""));
					sb.append("<span style=\"" + CSS_OF_ID + "\"> ");
					sb.append("&"+post.postId +"&");
					sb.append("</span>");
					sb.append(quote + "}");
					sb.append(", ");
					is = true;
			    }
				if(is)
					sb.delete(sb.length() - 2, sb.length());
				sb.append("]"); // end children
				sb.append("}"); // end of current node
				sb.append(", ");
		}
		if (sb.length() > 3)
			sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}
	private String getTagJSONString(List<Cluster> clusterByTagList)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(Cluster cluster:clusterByTagList)
		{
				boolean is = false;
				sb.append("{");
				sb.append("text: " + quote);
				List<Document> documentList = cluster.getDocuments();
				sb.append(cluster.getLabel()+"("+documentList.size()+")");
				sb.append(quote);
				sb.append(", children: [");
			
			
				for (Document document:documentList)
				{
					Post post = document.getField("post");
					sb.append("{text: " + quote);
					sb.append(post.post_title.replace("{", "")
							.replace("}", "").replace("[", "").replace("]", "").replace("'", ""));
					sb.append("<span style=\"" + CSS_OF_ID + "\"> ");
					sb.append("&"+post.postId +"&");
					sb.append("</span>");
					sb.append(quote + "}");
					sb.append(", ");
					is = true;
			    }
				if(is)
					sb.delete(sb.length() - 2, sb.length());
				sb.append("]"); // end children
				sb.append("}"); // end of current node
				sb.append(", ");
		}
		if (sb.length() > 3)
			sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}
	private List<TypeNode> sortTypeList(List<TypeNode> typeList)
	{
		List<TypeNode> result = new ArrayList<StackOverflowTree.TypeNode>();
		while(typeList.size() >0)
		{
			String minStr = typeList.get(0).getText();
			int minIndex = 0;
			for(int i=1; i<typeList.size(); i++)
			{
				if(minStr.compareToIgnoreCase(typeList.get(i).getText()) > 0)
				{
					minStr = typeList.get(i).getText();
					minIndex = i;
				}
			}			
			result.add(typeList.get(minIndex));
			typeList.remove(minIndex);
		}
		return result;
	}

	private List<PackageNode> sortTreeNode(List<PackageNode> packageList)
	{
		List<PackageNode> result = new ArrayList<StackOverflowTree.PackageNode>();
		while(packageList.size()>0)
		{
			String minStr = packageList.get(0).getText();
			int minIndex = 0;
			for(int i=1; i<packageList.size(); i++)
			{
				if(minStr.compareToIgnoreCase(packageList.get(i).getText()) > 0)
				{
					minStr = packageList.get(i).getText();
					minIndex = i;
				}
			}			
			result.add(packageList.get(minIndex));
			packageList.remove(minIndex);
		}
		
		return result;
		
	}

	public Object getTopicCalledByJSONString()
	{
		return getFocusJSONString(postList);
	}

	public String getEnvironmentJSONString()
	{
		return getEnvironmentJSONString(postList);
	}
	
	public String getFocusJSONString()
	{
		return getFocusJSONString(postList);
	}
	
	public String getTagJSONString()
	{
		return getTagJSONString(clusterByTagList);
	}
	
	public String getContentJSONString()
	{
		return getContentJSONString(clusterByContentList);
	}


	public Object getVHistoryJSONString() {
		
		return getFocusJSONString(postList);
	}
	
	public static MiddleData getHistoryData(Element e){
		MiddleData m = null;
		
		String sql = "SELECT tokens FROM historyInfo where projectName = '"
				+ e.getProjectName() + "' and methodName='"+e.getMethodname()+"'";
		
		try {
			Connection conn = DBHelper.getConnection();
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			
			if (rs.next()) {
				m = new MiddleData();
				m.className=e.getClassName();
				m.methodName=e.getMethodname_();
				m.historyData=rs.getString(1);

				rs.close();
				state.close();
			}else {
			}
		} catch (Exception exp) {
//			exp.printStackTrace();
		}
		
		return m;
	}

	public String getTypeJSONString(String projectName)
	{
		return getFocusJSONString(postList);
	}

	private String getTypeNodeJSONString(TypeNode typeNode)
	{
//		StringBuilder sb = new StringBuilder();
//		sb.append("{");
//		sb.append("text: " + quote);
//		LeafNode leafNode = new LeafNode(typeNode.getText());
//		for (Element element : elementList)
//		{
//			if (element.getClassName().equals(typeNode.getText()))
//			{
//				leafNode.addRelatedID(element.getId());
//			}
//		}	
//		
//		int totalRelatedIDs = getChildrenRelatedIDS(typeNode) + leafNode.getRelatedIDs().size();
//		
//		sb.append(leafNode.getShortText() + " (" + totalRelatedIDs + " results)" + " <span style=\"" + CSS_OF_ID + "\"> " + leafNode.getRelatedIDsText() + "</span>"); 
//		sb.append(quote);
//		if (typeNode.getSubNodes().size() > 0)
//		{
//			sb.append(", children: ["); // start children
//			for (TypeNode subNode : typeNode.getSubNodes())
//			{
//				sb.append(getTypeNodeJSONString(subNode));
//				sb.append(", ");
//			}
//			sb.delete(sb.length() - 2, sb.length());
//			sb.append("]");
//		}
//		sb.append("}");
//		return sb.toString();
		return null;
	}

	private int getChildrenRelatedIDS(TypeNode typeNode)
	{
		
//		LeafNode tmpNode = new LeafNode("Children");		
//		for(TypeNode child : typeNode.getSubNodes())
//		{
//			for(Element element : elementList)
//			{
//				if(element.getClassName().equals(child.getText()))
//					tmpNode.addRelatedID(element.getId());
//			}
//		}
//		
//		return tmpNode.getRelatedIDs().size();
		return 0;
	}



	private String performCluster(final String[][] data, int n)
	{
//		final ArrayList<Document> documents = new ArrayList<Document>();
//		for (int i=0;i<n;i++)
//		{
//			if(data[i][2].contains(";"))
//				data[i][2] = data[i][2].replaceAll(";", " ");
//			documents.add(new Document(data[i][1], data[i][2], data[i][0]));
//		}
//		final Controller controller = ControllerFactory.createSimple();
//		final ProcessingResult byTopicClusters = controller.process(documents, DBHelper.getCurrentProjectName(), LingoClusteringAlgorithm.class);
//		final List<Cluster> clusterByTopic = byTopicClusters.getClusters();
//
//		List<PackageNode> topicPackages = new ArrayList<StackOverflowTree.PackageNode>();
//		for (Cluster cluster : clusterByTopic)
//		{
//			String packageName = cluster.getLabel() + " " + ClusterDetailsFormatter.INSTANCE.formatClusterDetails(cluster);
//			
//			PackageNode packageNode = findPackageInTree(topicPackages, packageName);
//			if (packageNode == null)
//			{
//				packageNode = new PackageNode(packageName);
//				topicPackages.add(packageNode);
//			}
//			for (Document document : cluster.getDocuments())
//			{
//				String id = getIDByMethodName(document.getTitle());
//				LeafNode leafNode = new LeafNode(document.getTitle());
//				leafNode.addRelatedID(id);
//				packageNode.addLeaf(leafNode);
//			}
//
//		}
//		return getPackageJSONString(topicPackages, false);
		return null;
	}

	public void setClassList(List<Class> classList)
	{
		this.totalClassList = classList;

	}

	public void setPostList(List<Post> postList,List<Cluster> clusterByContentList,List<Cluster> clusterByTagList)
	{
		this.postList = postList;
		this.clusterByContentList = clusterByContentList;
		this.clusterByTagList = clusterByTagList;

	}

}
