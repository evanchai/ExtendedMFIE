package cn.edu.fudan.se.facet;

import java.util.ArrayList;
import java.util.List;

public class Grade {
	
	List<Float> grade = new ArrayList<Float>();
	
	public Grade()
	{
		
	}
	
	public void addGrade(Float point)
	{
		grade.add(point);
	}
	
	public float getGrade()
	{
		float sum = 0;
		int size = grade.size();
		for(Float point:grade)
		{
			sum += point;
		}
		
		return sum/size;
	}

}
