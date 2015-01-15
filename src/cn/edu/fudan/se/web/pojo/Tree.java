package cn.edu.fudan.se.web.pojo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.ProcessingResult;

import cn.edu.fudan.se.cluster.ConsoleFormatter.ClusterDetailsFormatter;
import cn.edu.fudan.se.data.util.ExtConstant;
import cn.edu.fudan.se.web.bean.CallBean;
import cn.edu.fudan.se.web.bean.CallBy;
import cn.edu.fudan.se.web.bean.Class;
import cn.edu.fudan.se.web.bean.Method;
import cn.edu.fudan.se.web.bean.MiddleData;
import cn.edu.fudan.se.web.dal.DBHelper;
import cn.edu.fudan.se.web.util.AstUtil;
import cn.edu.fudan.se.web.util.CommUtil;

public class Tree
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

		
		private List<TypeNode> subNodes = new ArrayList<Tree.TypeNode>();;
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

	private List<Element> elementList;

	public Tree()
	{
		typeNodeList = new ArrayList<Tree.TypeNode>();

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

	public String getAccessJSONString()
	{
		List<String> accessList = getAccessList();
		List<PackageNode> accessPackageList = new ArrayList<Tree.PackageNode>();
		for (String accessField : accessList)
		{
			String packageName = AstUtil.getPackageName(accessField);
			if (CommUtil.stringNullOrZero(packageName))
				continue;
			PackageNode packageNode = findPackageInTree(accessPackageList, packageName);
			if (packageNode == null)
			{
				packageNode = new PackageNode(packageName);
				accessPackageList.add(packageNode);
			}
			LeafNode leafNode = new LeafNode(AstUtil.getShortClassName(accessField));
			for (Element element : elementList)
			{
				if (element.getAccessfields().contains(accessField))
				{
					leafNode.addRelatedID(element.getId());
				}
			}
			packageNode.addLeaf(leafNode);
		}
		return getPackageJSONString(accessPackageList, true);
	}

	private List<String> getAccessList()
	{
		List<String> result = new ArrayList<String>();
		for (Element element : elementList)
		{
			for (String accessField : element.getAccessfields())
			{
				if (result.contains(accessField) == false)
					result.add(accessField);
			}
		}
		return result;
	}

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
		List<CallBy> callbyList = getCallByList();
		List<PackageNode> callByPackageList = new ArrayList<Tree.PackageNode>();
		for (CallBy callby : callbyList)
		{
			String packageName = AstUtil.getPackageName(callby.getCallByClass());
			PackageNode packageNode = findPackageInTree(callByPackageList, packageName);
			if (packageNode == null)
			{
				packageNode = new PackageNode(packageName);
				callByPackageList.add(packageNode);
			}
			LeafNode leafNode = new LeafNode(AstUtil.getShortClassName(callby.getCallByClass()));
			for (Element element : elementList)
			{
				for (String callbyMethod : element.getCalledBymethods())
				{
					String callbyClassName = AstUtil.getClassName(callbyMethod);
					if (callbyClassName.equals(callby.getCallByClass()))
					{
						leafNode.addRelatedID(element.getId());
						break;
					}
				}
			}
			packageNode.addLeaf(leafNode);

		}
		return getPackageJSONString(callByPackageList, true);
	}

	private List<CallBy> getCallByList()
	{
		List<CallBy> result = new ArrayList<CallBy>();
		for (Element element : elementList)
		{
			for (String callbyMethod : element.getCalledBymethods())
			{
				String callbyClassName = AstUtil.getClassName(callbyMethod);
				CallBy callby = getCallbyInList(callbyMethod, result);
				if (callby == null)
				{
					callby = new CallBy();
					callby.setCallByClass(callbyClassName);
					result.add(callby);
				}
				callby.setCount(callby.getCount() + 1);

			}
		}
		return result;
	}

	public String getCallJSONString()
	{
		List<CallBean> callList = getCallList();
		List<PackageNode> callPackageList = new ArrayList<Tree.PackageNode>();
		for (CallBean callBean : callList)
		{
			String packageName = AstUtil.getPackageName(callBean.getCallClass());
			PackageNode packageNode = findPackageInTree(callPackageList, packageName);
			if (packageNode == null)
			{
				packageNode = new PackageNode(packageName);
				callPackageList.add(packageNode);
			}
			LeafNode leafNode = new LeafNode(AstUtil.getShortClassName(callBean.getCallClass()));
			for (Element element : elementList)
			{
				for (String callbyMethod : element.getCallmethods())
				{
					String callbyClassName = AstUtil.getClassName(callbyMethod);
					if (callbyClassName.equals(callBean.getCallClass()))
					{
						leafNode.addRelatedID(element.getId());
						break;
					}
				}
			}
			packageNode.addLeaf(leafNode);
		}
		return getPackageJSONString(callPackageList, true);
	}

	private List<CallBean> getCallList()
	{
		List<CallBean> result = new ArrayList<CallBean>();
		for (Element element : elementList)
		{
			for (String callbyMethod : element.getCallmethods())
			{
				String callbyClassName = AstUtil.getClassName(callbyMethod);
				CallBean callBean = getCallBeanInList(callbyMethod, result);
				if (callBean == null)
				{
					callBean = new CallBean();
					callBean.setCallClass(callbyClassName);
					result.add(callBean);
				}
				callBean.setCount(callBean.getCount() + 1);

			}
		}
		return result;
	}

	private String getIDByMethodName(String methodName)
	{
		for (Element element : elementList)
		{
			if (element.getMethodname_().equals(methodName))
				return element.getId();
		}
		return null;
	}

	public String getPackageJSONString()
	{
		List<PackageNode> packageNodeList = new ArrayList<Tree.PackageNode>();
		for (Class oClass : currentClassList.values())
		{
			String fullClassName = oClass.getName();
			String packageName = "";
			String className = "";
			if (fullClassName.indexOf(".") > 0)
			{
				packageName = fullClassName.substring(0, fullClassName.lastIndexOf("."));
				className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
			}
			else
			{
				className = fullClassName;
			}
			PackageNode packageNode = findPackageInTree(packageNodeList, packageName);
			if (packageNode == null)
			{
				packageNode = new PackageNode(packageName);
				packageNodeList.add(packageNode);
			}
			LeafNode leafNode = new LeafNode(className);
			for (Element element : elementList)
			{
				if (element.getClassName().equals(fullClassName))
					leafNode.addRelatedID(element.getId());
			}
			packageNode.addLeaf(leafNode);

		}

		return getPackageJSONString(packageNodeList, true);
	}

	private String getPackageJSONString(List<PackageNode> packageList , boolean needSort)
	{
		if(needSort)
			packageList = sortTreeNode(packageList);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (PackageNode packageNode : packageList)
		{
			sb.append("{");
			sb.append("text: " + quote);
			sb.append(packageNode.getText());
			sb.append(quote);
			sb.append(", children: ["); // start children
			for (LeafNode leafNode : packageNode.getLeaves())
			{
				sb.append("{text: " + quote);
				sb.append(leafNode.toString());
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
		System.out.println("wwww:"+sb.toString());
		return sb.toString();
	}
	
	private List<TypeNode> sortTypeList(List<TypeNode> typeList)
	{
		List<TypeNode> result = new ArrayList<Tree.TypeNode>();
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
		List<PackageNode> result = new ArrayList<Tree.PackageNode>();
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
		String[][] data = new String[elementList.size()][3];
		for (int i = 0; i < elementList.size(); i++)
		{
			Element currentElement = elementList.get(i);
			data[i][0] = currentElement.getClassName();
			data[i][1] = currentElement.getMethodname_();
			StringBuilder sb = new StringBuilder();
			for (String calledMethod : currentElement.getCalledBymethods())
			{
				Element element = findElementByMethodName(elementList, calledMethod);
				if (element != null)
					sb.append(element.getTokens() + ";");
			}
			data[i][2] = sb.toString();
		}
		return performCluster(data, elementList.size());
	}

	public String getTopicCallJSONString()
	{
		String[][] data = new String[elementList.size()][3];
		for (int i = 0; i < elementList.size(); i++)
		{
			Element currentElement = elementList.get(i);
			data[i][0] = currentElement.getClassName();
			data[i][1] = currentElement.getMethodname_();
			StringBuilder sb = new StringBuilder();
			for (String callMethod : currentElement.getCallmethods())
			{
				Element element = findElementByMethodName(elementList, callMethod);
				if (element != null)
					sb.append(element.getTokens() + ";");
			}
			data[i][2] = sb.toString();
		}

		return performCluster(data, elementList.size());
	}

	public String getTopicJSONString()
	{
		String[][] data = new String[elementList.size()][3];
		for (int i = 0; i < elementList.size(); i++)
		{

			Element currentElement = elementList.get(i);
			data[i][0] = currentElement.getClassName();
			data[i][1] = currentElement.getMethodname_();
			String body = currentElement.getTokens();

			data[i][2] = body;
		}
		return performCluster(data, elementList.size());
	}

	public Object getVHistoryJSONString() {
		
		List<MiddleData> middleList = new ArrayList<MiddleData>();
		for(Element e:elementList){
			MiddleData m = getHistoryData(e);
			if(m!=null)
				middleList.add(m);
		}
//		System.out.println("history info:"+middleList.size());
		
		String[][] data = new String[middleList.size()][3];
		for (int i = 0; i < middleList.size(); i++)
		{
			MiddleData currentElement = middleList.get(i);
			data[i][0] = currentElement.className;
			data[i][1] = currentElement.methodName;
			String body = currentElement.historyData;//currentElement.getTokens();

			data[i][2] = body;
//			data[i][2] = currentElement.historyData;
		}
		return performCluster(data, middleList.size());
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
		typeNodeList.clear();
		for (Class oClass : currentClassList.values())
		{
			TypeNode currentNode = findPosInNodeList(oClass, typeNodeList);
			if (currentNode != null)
				continue; // exist in current typeNodeList

			currentNode = new TypeNode(oClass.getName());
			
			// try to find parent
			if (oClass.getSuperClass().length() > 0)
			{
//				Class superClass = findClassByName(oClass.getSuperClass(), totalClassList);
				Class superClass = findClassByNameInDB(oClass.getSuperClass(), projectName);
				if (superClass != null)
				{
					TypeNode parentNode = findPosInNodeList(superClass, typeNodeList);
					if (parentNode != null) // parent found
					{							
						parentNode.addSubNode(currentNode);
						continue;
					}
				}
			}
			typeNodeList.add(currentNode);
			//no anyone need it, maybe it can find some children :-)
			for(int i =0; i< typeNodeList.size(); i++)
			{
				TypeNode childNode = typeNodeList.get(i);
				Class childClass = findClassByName(childNode.getText(), currentClassList.values());
				if(childClass!= null && childClass.getSuperClass().equals(oClass.getName())) // Child found
				{
					typeNodeList.remove(i);
					currentNode.addSubNode(childNode);					
					break;
				}
			}
			

		}
		typeNodeList = sortTypeList(typeNodeList);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (TypeNode typeNode : typeNodeList)
		{
			sb.append(getTypeNodeJSONString(typeNode));
			sb.append(", ");
		}
		if (sb.length() >= 3)
			sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}

	private String getTypeNodeJSONString(TypeNode typeNode)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("text: " + quote);
		LeafNode leafNode = new LeafNode(typeNode.getText());
		for (Element element : elementList)
		{
			if (element.getClassName().equals(typeNode.getText()))
			{
				leafNode.addRelatedID(element.getId());
			}
		}	
		
		int totalRelatedIDs = getChildrenRelatedIDS(typeNode) + leafNode.getRelatedIDs().size();
		
		sb.append(leafNode.getShortText() + " (" + totalRelatedIDs + " results)" + " <span style=\"" + CSS_OF_ID + "\"> " + leafNode.getRelatedIDsText() + "</span>"); 
		sb.append(quote);
		if (typeNode.getSubNodes().size() > 0)
		{
			sb.append(", children: ["); // start children
			for (TypeNode subNode : typeNode.getSubNodes())
			{
				sb.append(getTypeNodeJSONString(subNode));
				sb.append(", ");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}

	private int getChildrenRelatedIDS(TypeNode typeNode)
	{
		
		LeafNode tmpNode = new LeafNode("Children");		
		for(TypeNode child : typeNode.getSubNodes())
		{
			for(Element element : elementList)
			{
				if(element.getClassName().equals(child.getText()))
					tmpNode.addRelatedID(element.getId());
			}
		}
		
		return tmpNode.getRelatedIDs().size();
	}



	private String performCluster(final String[][] data, int n)
	{
		final ArrayList<Document> documents = new ArrayList<Document>();
		for (int i=0;i<n;i++)
		{
			if(data[i][2].contains(";"))
				data[i][2] = data[i][2].replaceAll(";", " ");
			documents.add(new Document(data[i][1], data[i][2], data[i][0]));
		}
		final Controller controller = ControllerFactory.createSimple();
		final ProcessingResult byTopicClusters = controller.process(documents, DBHelper.getCurrentProjectName(), LingoClusteringAlgorithm.class);
		final List<Cluster> clusterByTopic = byTopicClusters.getClusters();

		List<PackageNode> topicPackages = new ArrayList<Tree.PackageNode>();
		for (Cluster cluster : clusterByTopic)
		{
			String packageName = cluster.getLabel() + " " + ClusterDetailsFormatter.INSTANCE.formatClusterDetails(cluster);
			
			PackageNode packageNode = findPackageInTree(topicPackages, packageName);
			if (packageNode == null)
			{
				packageNode = new PackageNode(packageName);
				topicPackages.add(packageNode);
			}
			for (Document document : cluster.getDocuments())
			{
				String id = getIDByMethodName(document.getTitle());
				LeafNode leafNode = new LeafNode(document.getTitle());
				leafNode.addRelatedID(id);
				packageNode.addLeaf(leafNode);
			}

		}
		return getPackageJSONString(topicPackages, false);
	}

	public void setClassList(List<Class> classList)
	{
		this.totalClassList = classList;

	}

	public void setElementList(List<Element> elementList)
	{
		this.elementList = elementList;

	}

}
