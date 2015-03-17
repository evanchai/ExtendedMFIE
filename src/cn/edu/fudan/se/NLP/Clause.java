package cn.edu.fudan.se.NLP;

import java.util.List;

import cn.edu.fudan.se.domain.dictionary.Condition;
import cn.edu.fudan.se.domain.dictionary.Expression;
import cn.edu.fudan.se.domain.dictionary.Negative;
import cn.edu.fudan.se.domain.dictionary.Translation;
import cn.edu.fudan.se.util.Global;

public class Clause {
	
	private String clause;
	
	private Subject subject;
	
	private Predicate predicate;
	
	private Object object;
	
	private String translation = "N";
	
	private String condition = "N";
	
	private String authority = Global.accept;
	
	private String question = Global.declaration;
	
	private String tense = Global.present;
	
	public Clause(String question,String tense)
	{
		this.question = question;
		this.tense = tense;
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	public String getPresent()
	{
		return tense;
	}
	public String getTranslation()
	{
		return translation;
	}
	public String getCondition()
	{
		return condition;
	}
	public void init()
	{
		if(Expression.getInstance().Authority(clause))
		{
			authority = Global.deny;
		}
		List<WordProperty> wpList = predicate.getPredicateList();
		Negative n = new Negative();
		for(WordProperty wp:wpList)
		{
			if(wp.getProperty().contains("V"))
			{
				if(n.Authority(wp.getLemmaWord()))
				{
					authority = Global.deny;
				}
			}

		}
		
		Translation trans = new Translation();
		if(trans.Authority(clause))
		{
			translation = "Y";
		}
		
		Condition cond = new Condition();
		if(cond.Authority(clause))
		{
			condition = "Y";
		}

	}
	
	public String getKeyWord()
	{
		String noun = subject.getNoun()+predicate.getVerb()+object.getNoun();
		int index = noun.lastIndexOf(",");
		return noun.substring(0,index);
		
	}
	public String getAuthority()
	{
		return authority;
	}

	public String getClause() {
		return clause;
	}

	public void setClause(String clause) {
		this.clause = clause;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Predicate getPredicate() {
		return predicate;
	}

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	

}
