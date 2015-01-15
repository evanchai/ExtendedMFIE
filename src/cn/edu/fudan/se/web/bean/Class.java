package cn.edu.fudan.se.web.bean;

import java.util.ArrayList;
import java.util.List;


public class Class extends ASTBasicDataType 
{

	private String interfaces = "";
	private boolean isInterface = false;
	private boolean isSuperClass = false;
	private List<String> methods;
	private String superClass = "";
	private String content = "";

	private String projectName;

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}




	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public Class()
	{
		methods = new ArrayList<String>();
		
	}

	/**
	 * @return the interfaces
	 */
	public String[] getInterface()
	{
		return interfaces.split("[,]");
	}

	/**
	 * @return the methods
	 */
	public List<String> getMethods()
	{
		return methods;
	}

	public String getPackageName()
	{
		return getName().substring(0, getName().lastIndexOf("."));
	}

	/**
	 * @return the superClass
	 */
	public String getSuperClass()
	{
		return superClass;
	}



	/**
	 * @return the isInterface
	 */
	public boolean isInterface()
	{
		return isInterface;
	}

	/**
	 * @return the isSuperClass
	 */
	public boolean isSuperClass()
	{
		return isSuperClass;
	}

	/**
	 * @param isInterface
	 *            the isInterface to set
	 */
	public void setInterface(boolean isInterface)
	{
		this.isInterface = isInterface;
	}

	/**
	 * @param interfaces
	 *            the interfaces to set
	 */
	public void setInterfaces(String interfaces)
	{
		this.interfaces = interfaces;
	}



	/**
	 * @param methods
	 *            the methods to set
	 */
	public void setMethods(List<String> methods)
	{
		this.methods = methods;
	}

	/**
	 * @param isSuperClass
	 *            the isSuperClass to set
	 */
	public void setSuperClass(boolean isSuperClass)
	{
		this.isSuperClass = isSuperClass;
	}

	/**
	 * @param superClass
	 *            the superClass to set
	 */
	public void setSuperClass(String superClass)
	{
		this.superClass = superClass;
	}
	
	

}
