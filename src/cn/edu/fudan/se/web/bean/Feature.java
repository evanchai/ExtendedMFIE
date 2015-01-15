package cn.edu.fudan.se.web.bean;

public class Feature
{
	String ID;
	String description;
	String relevantElements;
	String projectName;
	int relevantElementCount;
	public String getID()
	{
		return ID;
	}
	public void setID(String iD)
	{
		ID = iD;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getRelevantElements()
	{
		return relevantElements;
	}
	public void setRelevantElements(String relevantElements)
	{
		this.relevantElements = relevantElements;
	}
	public String getProjectName()
	{
		return projectName;
	}
	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}
	public int getRelevantElementCount()
	{
		return relevantElementCount;
	}
	public void setRelevantElementCount(int relevantElementCount)
	{
		this.relevantElementCount = relevantElementCount;
	}
	
	
}
