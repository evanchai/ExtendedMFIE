package cn.edu.fudan.se.NLP;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Sentence {
	
	private String sentence;
	
	private List<Clause> clauseList;
	
	public Sentence()
	{
		clauseList = new ArrayList<Clause>();
	}
	
	public void addClause(Clause clause)
	{
		clauseList.add(clause);
	}
	
	public List<Clause> getClauseList()
	{
		return clauseList;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	public void setClause(List<List<WordProperty>> splitList)
	{
		String question = Global.declaration,tense = Global.present;
		for(List<WordProperty> list:splitList)
		{
			Clause clause;
			String word = "";
			for(WordProperty wp:list)
			{
				word += wp.getWord() +" ";
				if(wp.getProperty().equals("VBD"))
					tense = Global.past;
			}
			if(sentence.contains("?"))
			{
				question = Global.question;
			}
			clause = new Clause(question,tense);
			clause.setClause(word);
			clauseList.add(clause);
		}
	}
	
	

}
