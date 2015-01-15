package cn.edu.fudan.se.web.bean;

import java.util.HashMap;
import java.util.Map;

import cn.edu.fudan.se.web.pojo.Element;

public class SingleLinkSimilarElement extends Element implements SimilarAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1882554319650166713L;
	StringBuilder reason =  new StringBuilder();
	Map<String, Float> degreeMap;
	Map<String, StringBuilder> relationMap;
	float max = 0.0f;
	
	public SingleLinkSimilarElement(String type, Element element, String selectedElementID )
	{
		degreeMap = new HashMap<String, Float>();
		relationMap = new HashMap<String, StringBuilder>();
		
		setMethodname(element.getMethodname());
		this.addRelation(type, selectedElementID);
		this.setId( element.getId());
		this.setPackageposition(element.getPackageposition());
	}
	
	public float getDegree()
	{		
		return max;
	}
	
	public void setDegree(float value)
	{
		max = value;
	}
	

	
	public void addRelation(String relation, String selectedElementID)
	{
		StringBuilder sb = relationMap.get(selectedElementID);
		if(sb == null)
			sb = new StringBuilder();
		Float degree = degreeMap.get(selectedElementID);
		if(degree == null)
			degree = 0.0f;
		if(sb.indexOf(relation)<0)
			sb.append(relation);
		degree += 0.01f;
		if(degree > max) max = degree;
		relationMap.put(selectedElementID, sb);
		degreeMap.put(selectedElementID, degree);
		
		
	}

	
}
