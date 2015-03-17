package cn.edu.fudan.se.facet;

public class Model {
	private String[] domainDicS;
	private String[] propertyS;//�������
	private String[] domainDicO;
	private String[] propertyO; //�������
	private String[] domainDicP;
	private String[] propertyP;//ν�����
	private String[] domainVerb;
	private String[] code;	
	private String[] tag;
	private String effect;
	private String question;
	private String tense;
	private String state;
	public Model(String domainS,String domainP,String domainO,
			String propertyS,String propertyP
			,String propertyO,String domainVerb
			,String code,String tag,
			String effect,String question,String tense,String state)
	{
		this.domainDicS = domainS.split(",");
		this.domainDicP = domainP.split(",");
		this.domainDicO = domainO.split(",");
		this.propertyS = propertyS.split(",");
		this.propertyO = propertyO.split(",");
		this.propertyP = propertyP.split(",");
		this.domainVerb = domainVerb.split(",");
		this.code = code.split(",");
		this.tag = tag.split(",");
		this.effect = effect;
		this.question = question;
		this.tense = tense;
		this.state = state;
	}
	
	public String[] getDomainDicS()
	{
		return domainDicS;
	}
	public String[] getDomainDicP()
	{
		return domainDicP;
	}
	public String[] getDomainDicO()
	{
		return domainDicO;
	}
	public String[] getPropertyS()
	{
		return propertyS;
	}
	public String[] getPropertyO()
	{
		return propertyO;
	}
	public String[] getPropertyP()
	{
		return propertyP;
	}
	public String[] getDomainVerb()
	{
		return domainVerb;
	}
	public String[] getCodeDic()
	{
		return code;
	}
	public String[] getTagDic()
	{
		return tag;
	}
	public String getEffect()
	{
		return effect;
	}
	public String getQuestion()
	{
		return question;
	}
	public String getTense()
	{
		return tense;
	}
	public String getState()
	{
		return state;
	}

}
