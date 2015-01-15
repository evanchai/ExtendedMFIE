package cn.edu.fudan.se.web.bean;

import cn.edu.fudan.se.web.pojo.Element;

public class SimilarElement extends Element implements SimilarAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5773243020223957504L;

	public static String SAME_NAME = "have the same method name; ";
	public static String SAME_CLASS = "in the same class; " ;
	public static String INVOCATION_LINK = "in the invocation link; ";
	public static String CLONE_CLASS = "in the same clone class; ";
	public static String RELATIVE_METHOD =  "in relative link; "; 
	public static String ACCESS_SAME_VARIABLE = "access the same variable; ";
	
	float degree = 0.0f;
	
	StringBuilder reason =  new StringBuilder();
	

	


	public SimilarElement(String type, Element element, String selectedID)
	{
		if(element == null || element.getMethodname() == null)
		{
			System.out.println("Null Error");
			System.out.println("Here");
		}
		setMethodname(element.getMethodname());
		this.addRelation(type, selectedID);
		this.setId( element.getId());
		this.setPackageposition(element.getPackageposition());
	}
	public float getDegree()
	{		
		return degree;		
	}
	
	public void setDegree(float value)
	{
		degree = value;
	}
	
	public void addRelation(String relation, String id)
	{	
		if(reason.indexOf(relation) < 0)
			reason.append(relation);
		if(relation.equals(SAME_NAME))
			degree += 0.01f;
		else if (relation.equals(CLONE_CLASS))
			degree += 0.01f;
		else if(relation.equals(RELATIVE_METHOD))
			degree += 0.01f;
		else
			degree += 0.01f;
		
	}
	
	
	
	
}
