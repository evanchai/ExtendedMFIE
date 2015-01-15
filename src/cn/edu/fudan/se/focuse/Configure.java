package cn.edu.fudan.se.focuse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.edu.fudan.se.NLP.Clause;
import cn.edu.fudan.se.NLP.Predicate;
import cn.edu.fudan.se.NLP.Sentence;
import cn.edu.fudan.se.NLP.Subject;
import cn.edu.fudan.se.NLP.Object;
import cn.edu.fudan.se.environment.Free;
import cn.edu.fudan.se.facet.Facet;
import cn.edu.fudan.se.facet.CalculateGrade;
import cn.edu.fudan.se.facet.Grade;
import cn.edu.fudan.se.util.Global;

import com.stackoverflow.bean.Post;

public class Configure extends Facet{
	
	private List<String> list = new ArrayList<String>();
	
	private HashMap<Post,Grade> postM = new HashMap<Post,Grade>();
	

    private CalculateGrade calculateGrade = new CalculateGrade();
    
    private Grade grade;
	
	public Configure()
	{
		list.add("configuration");
	}

	private void MatchPattern(Subject subE,Predicate preE,Object objE,Post post)
	{
		if((preE.getSynonyms().contains(Global.CONFIGURE)||isTrue(objE.getObject())))
    		postM.put(post, grade);
	}
	@Override
	public void init(List<Sentence> sentence,Post post,Clause query){
		// TODO Auto-generated method stub
		grade = new Grade();
		Float point;
		for(Sentence sen:sentence)
		   {
			   
			   List<Clause> clauseList = sen.getClauseList();
				for(Clause clause:clauseList)
				{
//					System.out.println("Clause:"+clause.getClause());
					Subject subject = clause.getSubject();
					Predicate predicate = clause.getPredicate();
					Object object = clause.getObject();
					point = calculateGrade.Grade(subject, predicate, object, clause, query);
					grade.addGrade(point);
					MatchPattern(subject,predicate,object,post);
				}
		   }
		
	}

	@Override
	public boolean isTrue(String text) {
		// TODO Auto-generated method stub
		for(String s:list)
    	{
    		if(text.toLowerCase().contains(s))
    			return true;
    	}
    	return false;

	}


	@Override
	public void postList(Post post) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showPost() {
		// TODO Auto-generated method stub
		show(postM);
	
		
	}


	@Override
	public String getEnvir() {
		// TODO Auto-generated method stub
		return "Configure";
	}
	


}
