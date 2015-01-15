package cn.edu.fudan.se.web.bean;

import cn.edu.fudan.se.web.util.CommUtil;

public class HistoryNode
{
	public static final String TYPE_SEARCH = "Search";
	public static final String TYPE_SEARCH_IN = "Keyword Refine";
	public static final String TYPE_TOPIC = "Intent";
	public static final String TYPE_TOPIC_CALL = "Use";
	public static final String TYPE_TOPIC_CALLEDBY = "UsedBy";
	public static final String PACKAGE = "Package Structure";
	public static final String TYPE = "Inheritance Hierarchy";
	public static final String CALL = "Caller Explorer";
	public static final String CALLEDBY = "Callee Explorer";
	public static final String ACCESS = "Access Explorer";

	private String projectName = "";
	private static int generatedID = 0;
	private int id;
	private int numberOfElement;
	public String getParamater()
	{
		return paramater;
	}

	public void setParamater(String paramater)
	{
		this.paramater = paramater;
	}

	private String paramater;
	private String type;
	private Object data;
	
	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public HistoryNode()
	{
		id = generatedID++;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getNumberOfElement()
	{
		return numberOfElement;
	}

	public void setNumberOfElement(int numberOfElement)
	{
		this.numberOfElement = numberOfElement;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	private String keywords = "";

	private int parentID;

	public String getKeywords()
	{
		return keywords;
	}

	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
	}

	public int getId()
	{
		return id;
	}

	public int getParentID()
	{
		return parentID;
	}

	public void setParentID(int parentID)
	{
		this.parentID = parentID;
	}

	public static String getTypeByParameter(FLParameter flParameter)
	{
		String result = TYPE_SEARCH;
		if(!CommUtil.stringNullOrZero(flParameter.getAccess()))
			result = ACCESS;
		else if(!CommUtil.stringNullOrZero(flParameter.getCall()))
			result = CALL;
		else if(!CommUtil.stringNullOrZero(flParameter.getCallby()))
			result= CALLEDBY;
		else if(!CommUtil.stringNullOrZero(flParameter.getPackageTree()))
			result = PACKAGE;
		else if(!CommUtil.stringNullOrZero(flParameter.getTypeTree()))
			result = TYPE;
		else if(!CommUtil.stringNullOrZero(flParameter.getTopic()))
			result = TYPE_TOPIC;
		else if(!CommUtil.stringNullOrZero(flParameter.getTopicCall()))
			result = TYPE_TOPIC_CALL;
		else if(!CommUtil.stringNullOrZero(flParameter.getTopicCalledBy()))
			result = CALLEDBY;
			 
		return result;
	}

}
