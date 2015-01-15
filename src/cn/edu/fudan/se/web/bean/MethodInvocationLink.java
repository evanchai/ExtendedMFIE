package cn.edu.fudan.se.web.bean;

public class MethodInvocationLink {
	String name;
	String invocationLink;
	int length;
	String projectName;
	public String getProjectName()
	{
		return projectName;
	}


	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}


	public MethodInvocationLink(int length, String name)
	{
		this.name = name;
		this.length = length;
	}
	
	
	public void setInvocationLink(String invocationLink) {
		this.invocationLink = invocationLink;
	}


	public String getInvocationLink()
	{
		return invocationLink;
	}

	public String getName() {
		return name;
	}

	public int getLength() {
		return length;
	}
}
