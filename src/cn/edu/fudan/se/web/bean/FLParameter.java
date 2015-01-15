package cn.edu.fudan.se.web.bean;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.web.pojo.Tree;

public class FLParameter
{
	private String vHistory, typeTree, packageTree,  call, callby, access ,topic, topicCall, topicCalledBy;
	
	public String getTopicCalledBy()
	{
		return topicCalledBy;
	}
	public void setTopicCalledBy(String topicCalledBy)
	{
		this.topicCalledBy = topicCalledBy;
	}
	public String getTopicCall()
	{
		return topicCall;
	}
	public void setTopicCall(String topicCall)
	{
		this.topicCall = topicCall;
	}
	public String getTopic()
	{
		return topic;
	}
	public void setTopic(String topic)
	{
		this.topic = topic;
	}
	public String getCall()
	{
		return call;
	}
	public void setCall(String call)
	{
		this.call = call;
	}
	public String getCallby()
	{
		return callby;
	}
	public void setCallby(String callby)
	{
		this.callby = callby;
	}
	public String getAccess()
	{
		return access;
	}
	public void setAccess(String access)
	{
		this.access = access;
	}
	
	public FLParameter()
	{
		typeTree = "";
		packageTree = "";
	
		callby ="";
		call ="";
		access = "";		
		topic = "";
		
		vHistory = "";
	}
	
	//typeTree, packageTree, syntaxCluster, semanticCluster, calleeCluster, calledByCluster, call, callby, access ,topic;
	public boolean isEmpty()
	{
		return (vHistory+typeTree + packageTree + callby + call + access +topic + topicCall + topicCalledBy).length() == 0;
	}
	public String getTypeTree()
	{
		return typeTree;
	}

	public void setTypeTree(String typeTree)
	{
		this.typeTree = typeTree;
	}

	public String getPackageTree()
	{
		return packageTree;
	}

	public void setPackageTree(String packageTree)
	{
		this.packageTree = packageTree;
	}




	
	//Topic parameter: Set Search Text (8 docs, score: 9.34);org.homeunix.thecave.buddi.view.TransactionFrame.init() <span style="color:red">  1409</span>;org.homeunix.thecave.buddi.model.prefs.PrefsModel.setSearchPaneVisible(boolean) <span style="color:red">  487</span>;org.homeunix.thecave.buddi.model.prefs.PrefsModel.setSearchText(java.lang.String) <span style="color:red">  488</span>;org.homeunix.thecave.buddi.model.prefs.PrefsModelBean.setSearchPaneVisible(boolean) <span style="color:red">  568</span>;
	public List<String> getIDs()
	{
		
		String paraStr = vHistory + ";" + topic + ";" + packageTree + ";" + typeTree + ";" + call + ";" + callby + ";" + access + ";" + topicCall + ";" + topicCalledBy;		
		return getNumbers(paraStr);
	}
	
	private List<String> getNumbers(String params)
	{
		List<String> result = new ArrayList<String>();
		String endString = "</span>";
		int index = params.indexOf(Tree.CSS_OF_ID);
		while(index!=-1)
		{
			int begin = index + Tree.CSS_OF_ID.length() + 2;
			int end = params.indexOf(endString, begin);
			String numbers = params.substring(begin, end);
			String[] array = numbers.trim().split("[ ]");
			for(String number : array)
			{
				number = number.trim();
				if(number.length() > 0 && !result.contains(number))
					result.add(number);					
			}
			index = params.indexOf(Tree.CSS_OF_ID, index + 10);
		}
		return result;
	}
	public String getParameterString()
	{
		// TODO Auto-generated method stub
		//return typeTree + packageTree + callby + call + access +topic + topicCall + topicCalledBy;
		return typeTree + packageTree + topic + topicCall + topicCalledBy;
	}
	public String getvHistory() {
		return vHistory;
	}
	public void setvHistory(String vHistory) {
		this.vHistory = vHistory;
	}
	
}
