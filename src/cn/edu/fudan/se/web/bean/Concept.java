package cn.edu.fudan.se.web.bean;

import java.util.ArrayList;
import java.util.List;

public class Concept
{
	private String uid;
	private String extent;
	private String intent;
	private String projectName;
	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	List<Concept> children;
	public Concept()
	{
		uid = "";
		extent = "";
		intent = "";
		
		children = new ArrayList<Concept>();
				
	}
	
	public void addChild(Concept child)
	{
		if(children.contains(child) == false)
			children.add(child);
	}
	/**
	 * @return the children
	 */
	public List<Concept> getChildren()
	{
		return children;
	}
	/**
	 * @return the uid
	 */
	public String getUid()
	{
		return uid;
	}
	
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	/**
	 * @return the extent
	 */
	public String getExtent()
	{
		return extent;
	}
	/**
	 * @param extent the extent to set
	 */
	public void setExtent(String extent)
	{
		this.extent = extent;
	}
	/**
	 * @return the intent
	 */
	public String getIntent()
	{
		return intent;
	}
	/**
	 * @param intent the intent to set
	 */
	public void setIntent(String intent)
	{
		this.intent = intent;
	}

	public void print()
	{
		System.out.println("UID: " + uid );
		System.out.println("Has " + children.size() + " Children");
		for(Concept child: children)
		{
			System.out.println("child uid: " + child.getUid());
		}
		
		
	}
	
}