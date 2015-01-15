package cn.edu.fudan.se.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.stackoverflow.bean.Post;

import cn.edu.fudan.se.NLP.Clause;
import cn.edu.fudan.se.NLP.Predicate;
import cn.edu.fudan.se.NLP.Sentence;
import cn.edu.fudan.se.NLP.Subject;
import cn.edu.fudan.se.NLP.Object;
import cn.edu.fudan.se.NLP.WordProperty;
import cn.edu.fudan.se.facet.CalculateGrade;
import cn.edu.fudan.se.facet.Facet;
import cn.edu.fudan.se.facet.Grade;
import cn.edu.fudan.se.facet.Model;

public class Environment extends Facet{
    	//�����ν���д��ڽ��
	   public HashMap<Post,Grade> postM = new HashMap<Post,Grade>();
	    
	    private CalculateGrade calculateGrade = new CalculateGrade();
	    
	    private List<Model> modelList;
	    
	    private String envir;
	    
	    private String envirItem;
	    
	    private Grade grade;
	    
	    private boolean isMatch;
	    
	    
	    public Environment(String envir,String envirItem)
	    {
	    	this.envir = envir;
	    	this.envirItem = envirItem;
	    
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
		public boolean judge(List<WordProperty> list,String[] property,String[] domainDic)
		{
			int size = list.size();
			int index = 0;
			for(String p:property)
			{
				index = 0;
				boolean isOpen = false;
				for(WordProperty wp:list)
				{
					index ++;
					if(wp.getProperty().equalsIgnoreCase(p))
					{
						
						isOpen = true;
					}
					if(wp.getProperty().equalsIgnoreCase("TO"))
					{
						isOpen = false;
					}
					if(envirItem.equalsIgnoreCase("C++"))
					{
						
						if(isOpen&&wp.getWord().equalsIgnoreCase("C"))
						{
							if((index < size -1)&&list.get(index+1).getWord().equalsIgnoreCase("+"))
						    	return true;
						}
					}else if(envirItem.equalsIgnoreCase("C#"))
					{
						if(isOpen&&wp.getWord().equalsIgnoreCase("C"))
						{
							if((index < size -1)&&list.get(index+1).getWord().equalsIgnoreCase("#"))
						    	return true;
						}
					}else if(isOpen)
					{
						for(String dic:domainDic)
				    	{
				    		if(wp.getWord().equalsIgnoreCase(dic))
				    			return true;
				    	}
					}
				}
			}
			return false;
		}
		private void MatchPattern(Subject subE,Predicate preE,Object objE,Post post)
		{
			String[] domainDicS,domainDicP,domainDicO,codeDic
			,propertyS,propertyO,propertyP,domainVerb;
			for(Model model:modelList)
			{
				domainDicS = model.getDomainDicS();
				domainDicP = model.getDomainDicP();
				domainDicO = model.getDomainDicO();
				propertyS = model.getPropertyS();
				propertyO = model.getPropertyO();
				codeDic = model.getCodeDic();
				if(isTrue(propertyS[0]))
				{
					if(judge(subE.getSubjectList(),propertyS,domainDicS))
					{
						put(post);
					}
				}
				if(isTrue(propertyO[0]))
				{
					if(judge(objE.getobjectList(),propertyO,domainDicO))
					{
						put(post);
					}
				}
				if(isTrue(codeDic[0]))//�����г�������ʣ�php
				{
					if(judgeCode(post.post_body_code,codeDic))
						put(post);
				}
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
						MatchPattern(subject,predicate,object,post);
					}
			   }
			
		}
		
		@Override
		public void showPost() {
			// TODO Auto-generated method stub
			show(postM);
			

			
		}

		@Override
		public String getEnvir() {
			// TODO Auto-generated method stub
			return envir + " - " + envirItem;
		}
		@Override
		  public HashMap<Post,Grade> getPost()
		  {
			  return this.postM;
		  }
	
}
