package cn.edu.fudan.se.focuse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.fudan.se.NLP.Clause;
import cn.edu.fudan.se.NLP.Predicate;
import cn.edu.fudan.se.NLP.Sentence;
import cn.edu.fudan.se.NLP.Subject;
import cn.edu.fudan.se.NLP.Object;
import cn.edu.fudan.se.NLP.WordProperty;
import cn.edu.fudan.se.environment.Free;
import cn.edu.fudan.se.facet.CalculateGrade;
import cn.edu.fudan.se.facet.Facet;
import cn.edu.fudan.se.facet.Grade;
import cn.edu.fudan.se.facet.Model;
import cn.edu.fudan.se.util.Global;

import com.stackoverflow.bean.Post;

public class Focus extends Facet{
	
	    public HashMap<Post,Grade> postM = new HashMap<Post,Grade>();
	    
	    private CalculateGrade calculateGrade = new CalculateGrade();
	    
	    private List<Model> modelList;
	    
	    private String focus;
	    
	    private String focusItem;
	    
	    private Grade grade;
	    boolean isMatch;
	    
	    public Focus(String focus,String focusItem)
	    {
	    	this.focus = focus;
	    	this.focusItem = focusItem;
	    
	    }
	    
	    public void setIsMatch(boolean isMatch)
		{
			this.isMatch = isMatch;
		}
		public boolean isMatch()
		{
			return this.isMatch;
		}
	    
	    public void initModel(List<Model> modelList)
	    {
	    	this.modelList = modelList;
	    }
		@Override
		public boolean isTrue(String s) {
			// TODO Auto-generated method stub
			if(s.equals(""))
				return false;
			return true;
		}

		@Override
		public void postList(Post post) {
			// TODO Auto-generated method stub
			
		}
		 
		
		public boolean judgePredicate(String[] domainVerb,Predicate preE)
		{
			for(String verb:domainVerb)
			{
				if(preE.getSynonyms().contains(verb))
					return true;
			}
			return false;
		}
		public boolean judgePredicate(String[] domainVerb,Predicate preE,String question)
		{
			for(String verb:domainVerb)
			{
				if((preE.getSynonyms().contains(verb)))
					return true;
			}
			return false;
		}
		public boolean judgeObject(List<WordProperty> list,String[] property,String[] domainDic)
		{
			for(String p:property)
			{
				boolean isOpen = true;
				for(WordProperty wp:list)
				{
					if(wp.getProperty().equalsIgnoreCase(p))
					{
						
						isOpen = false;
					}
                     if(isOpen)
					{
						for(String dic:domainDic)
				    	{
				    		if(wp.getWord().toLowerCase().contains(dic.toLowerCase()))
				    			return true;
				    	}
					}
				}
			}
			return false;
		}
		
		public boolean judgeCode(String code,String[] codeDic)
		{
			for(String dic:codeDic)
			{
				if(code.toLowerCase().contains(dic.toLowerCase()))
				{
					return true;
				}
			}
			return false;
		}
		
		public boolean isTrueEffect(String effect,Clause clause)
		{
			if(isTrue(effect))
			{
				if(clause.getAuthority().equals(effect))
					return true;
				else
					return false;
			}else
				return false;
		}
		private void MatchPattern(Clause clause,Post post)
		{
			Subject subE = clause.getSubject();
			Predicate preE = clause.getPredicate();
			Object objE = clause.getObject();
			String[] domainDicS,domainDicP,domainDicO
			,propertyS,propertyO,propertyP,domainVerb,codeDic;
			String effect;
			boolean isEffect;
			for(Model model:modelList)
			{
				domainDicS = model.getDomainDicS();
				domainDicP = model.getDomainDicP();
				domainDicO = model.getDomainDicO();
				propertyS = model.getPropertyS();
				propertyO = model.getPropertyO();
				domainVerb = model.getDomainVerb();
				codeDic = model.getCodeDic();
				effect = model.getEffect();
				isEffect = isTrueEffect(effect,clause);
				if(isTrue(domainVerb[0])&&isTrue(domainDicO[0])&&isEffect)
				{
					if(judgePredicate(domainVerb,preE)&&
							judgeObject(objE.getobjectList(),propertyO,domainDicO))
					{
						put(post);
					}
				}
				if(isTrue(domainVerb[0])&&!isTrue(domainDicO[0])&&isEffect)//���ʾ�Ķ������г������򶯴�
				{
					if(judgePredicate(domainVerb,preE,clause.getQuestion()))
						put(post);
				}
				if(isTrue(codeDic[0])&&!codeDic[0].equals("code"))//�����г�������ʣ�error
				{
					if(judgeCode(post.post_body_code,codeDic))
						put(post);
				}
//				if(codeDic[0].equals("code"))
//				{
//					if(!post.post_body_code.equals(""))
//						put(post);
//				}
			}
		}
			
		public void put(Post post)
		{
			setIsMatch(true);
			postM.put(post, grade);
			if(!Free.languageList.contains(post))
				Free.languageList.add(post);
		}
		
		@Override
		public void init(List<Sentence> sentence,Post post,Clause query){
			// TODO Auto-generated method stub
			grade = new Grade();
			Float point;
			for(Sentence sen:sentence)
			   {
				if(isMatch())
					break;
				   
				   List<Clause> clauseList = sen.getClauseList();
					for(Clause clause:clauseList)
					{
						if(isMatch())
							break;
//						System.out.println("Clause:"+clause.getClause());
						Subject subject = clause.getSubject();
						Predicate predicate = clause.getPredicate();
						Object object = clause.getObject();
						point = calculateGrade.Grade(subject, predicate, object, clause, query);
						grade.addGrade(point);
						MatchPattern(clause,post);
					}
			   }
			
		}
		@Override
		public HashMap<Post,Grade> getPost()
		{
			return postM;
		}
		
		@Override
		public void showPost() {
			// TODO Auto-generated method stub
			show(postM);
			

			
		}

		@Override
		public String getEnvir() {
			// TODO Auto-generated method stub
			return focus + " - " + focusItem;
		}

	
}
