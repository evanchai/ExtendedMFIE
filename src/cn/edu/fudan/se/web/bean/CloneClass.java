package cn.edu.fudan.se.web.bean;

import cn.edu.fudan.se.web.pojo.Element;

public class CloneClass
{
	
	private String cloneClassName;
	private Element element;
	private String methodName;

	public String getMethodName()
	{
		return methodName;
	}
	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}
	public Element getElement()
	{
		return element;
	}
	public void setElement(Element element)
	{
		this.element = element;
	}

	public String getCloneClassName()
	{
		return cloneClassName;
	}
	public void setCloneClassName(String cloneClassName)
	{
		this.cloneClassName = cloneClassName;
	}
	
}
