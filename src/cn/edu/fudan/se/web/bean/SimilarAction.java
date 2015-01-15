package cn.edu.fudan.se.web.bean;

public interface SimilarAction
{
	
	float getDegree();
	void setDegree(float value);
	String getId();
	String getMethodname();
	void addRelation(String relation, String selectedElementID);
}
