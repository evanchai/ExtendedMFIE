package cn.edu.fudan.se.web.bean;

public class ExperimentResult
{
	String featureID;
	String experimentID;
	String type;
	float Top10, Top20, Top30, Top40;
	public String getFeatureID()
	{
		return featureID;
	}
	public void setFeatureID(String featureID)
	{
		this.featureID = featureID;
	}
	public String getExperimentID()
	{
		return experimentID;
	}
	public void setExperimentID(String experimentID)
	{
		this.experimentID = experimentID;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public float getTop10()
	{
		return Top10;
	}
	private void setTop10(float top10)
	{
		Top10 = top10;
	}
	public float getTop20()
	{
		return Top20;
	}
	private void setTop20(float top20)
	{
		Top20 = top20;
	}
	public float getTop30()
	{
		return Top30;
	}
	private void setTop30(float top30)
	{
		Top30 = top30;
	}
	public float getTop40()
	{
		return Top40;
	}
	private void setTop40(float top40)
	{
		Top40 = top40;
	}
	public void setResult(int[] hintCounts, int length)
	{
		setTop10( (float) (hintCounts[0] / (1.0 * length)));
		setTop20( (float) (hintCounts[1] / (1.0 * length)));
		setTop30( (float) (hintCounts[2] / (1.0 * length)));
		setTop40( (float) (hintCounts[3] / (1.0 * length)));
		
	}
	
}
