package cn.edu.fudan.se.facet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import cn.edu.fudan.se.util.Global;

import com.stackoverflow.bean.Post;

public class ClassifyPost extends Facet{
	
	    public HashMap<Post,Grade> postM = new HashMap<Post,Grade>();
	    
	    private CalculateGrade calculateGrade = new CalculateGrade();
	    
	    private List<Model> modelList;
	    
	    private String focus;
	    
	    private String focusItem;
	    
	    private Grade grade;
	    boolean isMatch;
	    
	    boolean isTag = true;
	    
	    boolean isCode = true;
	    
	    public ClassifyPost(String focus,String focusItem)
	    {
	    	this.focus = focus;
	    	this.focusItem = focusItem;
	    
	    }
	    
	    public void setIsMatch(boolean isMatch)
		{
			this.isMatch = isMatch;
		}
	    
	    public void setIsTag(boolean isTag)
	    {
	    	this.isTag = isTag;
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
		public boolean judgeReversePredicate(String[] domainVerb,Predicate preE)
		{
			for(String verb:domainVerb)
			{
				if(verb.contains("~"))
				{
					verb = verb.replace("~", "");
					if(preE.getSynonyms().contains(verb))
						return false;
				}

			}
			return true;
		}
		
		public boolean judgePredicate(String[] domainDic,List<WordProperty> list)
		{
		
			for(String dic:domainDic)
			{
				
				for(WordProperty wp:list)
				{
					if(wp.getWord().equalsIgnoreCase(dic))
						return true;
				}
			}
			return false;
		}
		public List<WordProperty> judgeSpecialWordGroup(List<WordProperty> list)
		{
	    	int lenght = list.size();
			for(int i = 0; i < lenght ; i++)
	    	{
	    		if(list.get(i).getWord().equalsIgnoreCase("c"))
	    		{
	    			if(i == lenght-1)
	    			{
	    				list.get(i).setWord("C");
	    			}else if(list.get(i+1).getWord().equalsIgnoreCase("+"))
	    			{
	    				list.get(i).setWord("C++");
	    			}else if(list.get(i+1).getWord().equalsIgnoreCase("#"))
	    			{
	    				list.get(i).setWord("C#");
	    			}
	    		}else if(list.get(i).getWord().equalsIgnoreCase("sql"))
	    		{
	    			if(i == lenght-1)
	    			{
	    				list.get(i).setWord("sql");
	    			}else if(list.get(i+1).getWord().equalsIgnoreCase("server"))
	    			{
	    				list.get(i).setWord("sql server");
	    			}
	    		}else if(list.get(i).getWord().equalsIgnoreCase("node"))
	    		{
	    			if(i == lenght-1)
	    			{
	    				list.get(i).setWord("node");
	    			}else if(list.get(i+1).getWord().equalsIgnoreCase("."))
	    			{
	    				list.get(i).setWord("node.js");
	    			}
	    		}else if(list.get(i).getWord().equalsIgnoreCase("visual"))
	    		{
	    			if(i == lenght-1)
	    			{
	    				list.get(i).setWord("visual");
	    			}else if(list.get(i+1).getWord().equalsIgnoreCase("studio"))
	    			{
	    				list.get(i).setWord("visual studio");
	    			}
	    		}else if(list.get(i).getWord().equalsIgnoreCase("java"))
	    		{
	    			if(i == lenght-1)
	    			{
	    				list.get(i).setWord("java");
	    			}else if(list.get(i+1).getWord().equalsIgnoreCase("ee"))
	    			{
	    				list.get(i).setWord("java ee");
	    			}
	    		}
	    	}
			return list;
		}
		public boolean judgeAfterPre(List<WordProperty> list,String[] property,String[] domainDic)
		{
			list = judgeSpecialWordGroup(list);
			for(String p:property)
			{
				boolean isOpen = false;
				for(WordProperty wp:list)
				{
					if(wp.getProperty().equalsIgnoreCase(p)&&!wp.getWord().equalsIgnoreCase("without"))
					{
						isOpen = true;
					}
                     if(isOpen)
					{
						for(String dic:domainDic)
				    	{
				    		if(wp.getWord().equalsIgnoreCase(dic))
				    		{
				    			return true;
				    		}
				    	}
					}
				}
			}
			return false;
		}
		public boolean judgeBeforePre(List<WordProperty> list,String[] property,String[] domainDic)
		{
			list = judgeSpecialWordGroup(list);
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
				    		if(wp.getWord().equalsIgnoreCase(dic))
				    			return true;
				    	}
					}
				}
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
		
		public boolean judgeTag(String tag,String[] tagDic)
		{
			String[] tags = tag.split(" ");
			for(String dic:tagDic)
			{
				for(String t:tags)
				{
					if((t.contains("-")||t.contains("."))&&!t.contains("sql")&&t.toLowerCase().contains(dic.toLowerCase()))
						return true;
					else if(dic.equalsIgnoreCase(t))
						return true;
				}
			}
			return false;
		}
		
		public boolean isTrueEffect(String effect,Clause clause)
		{
		
			String[] effS = effect.split(",");
			for(String s:effS)
			{
				if(clause.getAuthority().equals(s))
					return true;
			}
			return false;
		}
		
		public boolean isTrueQuestion(String question,Clause clause)
		{
			
			if(clause.getQuestion().equals(question))
				return true;
			else
				return false;
		}
		public boolean isTrueTense(String tense,Clause clause)
		{
			if(clause.getPresent().equals(tense))
				return true;
			else
				return false;
		}
		
		public boolean isTrueTranslation(Clause clause)
		{
			if(clause.getTranslation().equals("Y"))
				return true;
			return false;
		}
		
		public boolean isTrueCondition(Clause clause)
		{
			if(clause.getCondition().equals("Y"))
				return true;
			return false;
		}
		private void MatchPattern(Clause clause,Post post)
		{
			Subject subE = clause.getSubject();
			Predicate preE = clause.getPredicate();
			Object objE = clause.getObject();
			String[] domainDicS,domainDicP,domainDicO
			,propertyS,propertyO,propertyP,domainVerb,
			codeDic,tagDic;
			String effect,question,tense,state;
			boolean isEffect;
			for(Model model:modelList)
			{
				domainDicS = model.getDomainDicS();
				domainDicP = model.getDomainDicP();
				domainDicO = model.getDomainDicO();
				propertyS = model.getPropertyS();
				propertyO = model.getPropertyO();
				propertyP = model.getPropertyP();
				domainVerb = model.getDomainVerb();
				codeDic = model.getCodeDic();
				tagDic = model.getTagDic();
				effect = model.getEffect();
				question = model.getQuestion();
				tense = model.getTense();
				state = model.getState();
				isEffect = isTrueEffect(effect,clause);
				switch(Integer.parseInt(state))
				{
				   case 1:
				   {
					   if(judgeAfterPre(objE.getobjectList(),propertyO,domainDicO)||
							   judgeAfterPre(subE.getSubjectList(),propertyS,domainDicS))
					   {
						   put(post);
					   }
					   break;
				   }
				   case 2:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)
							   &&judgePredicate(domainVerb,preE)&&judgeBeforePre(objE.getobjectList(),propertyO,domainDicO) )
					   {
						   put(post);
					   }
					   break;
				   }
				   case 3:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&judgePredicate(domainVerb,preE))
					   {
						   put(post);
					   }
					   break;
				   }
				   case 4:
				   {
					   if(judgeCode(post.post_body_code,codeDic))
					   {
						   put(post);
					   }
					   break;
				   }
				   case 5:
				   {
					   if(!post.post_body_code.equals(""))
					   {
							put(post);
					   }
					   break;
				   }
				   case 6:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&judgePredicate(domainVerb,preE))
					   {
						   put(post);
					   }
					   break;
				   }
				   case 7:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
							   judgePredicate(domainVerb,preE)&&judgeBeforePre(objE.getobjectList(),propertyO,domainDicO))
					   {
						   put(post);
					   }
					   break;
				   }
				   case 8:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&judgeBeforePre(objE.getobjectList(),propertyO,domainDicO))
					   {
						   put(post);
					   }
					   break;
				   }
				   case 9:
				   {
					   if(isTag)
					   {
						   isTag = false;
						   if(judgeTag(post.getPost_tag(),tagDic))
						   {
							   put(post);
						   }
							
					   }
					   break;
				   }
				   case 10:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
							   judgeBeforePre(subE.getSubjectList(),propertyS,domainDicS)
							   )
					   {
						   put(post);
					   }
					   break;
				   }
				   case 11:
				   {
					 
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
					     judgeBeforePre(subE.getSubjectList(),propertyS,domainDicS)
						 &&judgePredicate(domainDicP,preE.getPredicateList()))
					   {
						   put(post);
					   }
					   break;
				   }
				   case 12:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
						 judgeBeforePre(subE.getSubjectList(),propertyS,domainDicS))
					   {
						  put(post);
					   }
					   break;
				   }
				   case 13:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
						 judgeBeforePre(subE.getSubjectList(),propertyS,domainDicS)&&
						 judgePredicate(domainVerb,preE))
					  {
								  put(post);
					  }
					   break;
				   }
				   case 14:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
						 judgePredicate(domainVerb,preE))
					  {
								  put(post);
					  }
					   break;
				   }
				   case 15:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
							   judgeBeforePre(objE.getobjectList(),propertyO,domainDicO))
						{
							 put(post);
						}
					   break;
				   }
				   case 16:
				   {
					   if(judgePredicate(domainDicP,preE.getPredicateList())&& judgePredicate(domainVerb,preE))
						{
							 put(post);
						}
					   break;
				   }
				   case 17:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
							   judgeBeforePre(subE.getSubjectList(),propertyS,domainDicS)&&
							   judgeReversePredicate(domainVerb,preE))
						{
							 put(post);
						}
					   break;
				   }
				   case 18:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
							   judgeBeforePre(preE.getPredicateList(),propertyP,domainDicP))
						{
							 put(post);
						}
					   break;  
				   }
				   case 19:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
							   judgeBeforePre(preE.getPredicateList(),propertyP,domainDicP))
						{
							 put(post);
						}
					   break; 
				   }
				   case 20:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
							   judgeBeforePre(subE.getSubjectList(),propertyS,domainDicS)
							   &&judgeBeforePre(objE.getobjectList(),propertyO,domainDicO))
					   {
						   put(post);
					   }
					   break;
				   }
				   case 21:
				   {
					   if(judgeAfterPre(preE.getPredicateList(),propertyP,domainDicP))
						{
							 put(post);
						}
					   break;  
				   }
				   case 22:
				   {
					   if(judgeBeforePre(objE.getobjectList(),propertyO,domainDicO)&& judgePredicate(domainVerb,preE))
					   {
						   put(post);
					   }
				   }
				   case 23:
				   {
					   if(isTrueEffect(effect,clause)&&isTrueQuestion(question,clause)&&
							   judgeBeforePre(preE.getPredicateList(),propertyP,domainDicP)&&
							   judgeBeforePre(objE.getobjectList(),propertyO,domainDicO))
						{
							 put(post);
						}
					   break; 
				   }
					   default:
					   {
						   
					   }
				}
			}
		}
			
		public void put(Post post)
		{
			setIsMatch(true);
			postM.put(post, grade);
		}
		
		@Override
		public void init(List<Sentence> sentence,Post post,Clause query){
			// TODO Auto-generated method stub
			grade = new Grade();
			Float point;
			Clause clause,nextClause;
			Sentence sen,nextSen;
			int senSize = sentence.size();
			for(int i = 0;i < senSize; i++)
			   {
				if(isMatch())
					break;
				   sen = sentence.get(i);
				   List<Clause> clauseList = sen.getClauseList();
				   int clauseSize = clauseList.size();
				   for(int j = 0;j < clauseSize;j++)
				   {
						if(isMatch())
							break;
				   

					   if(j+1<clauseSize)
					   {
						   nextClause = clauseList.get(j+1);
						 //I try to get data from MySQL in Java Project it works, but when I use Android Project it doesn't work. 语句间的转折
						   if(isTrueTranslation(nextClause)&&isTrueCondition(nextClause))
							   continue;
						   //There are many ways to connect through PHP scrip/ json/xml/ webservices. But i dont need that. 
						   if(isTrueTranslation(nextClause)&&isTrueEffect("DENY",nextClause))
							   continue;
					   }
					   if((j+1==clauseSize)&&(i+1<senSize))
					   {
						   nextSen = sentence.get(i+1);
						   
						   int num = nextSen.getClauseList().size();
						   if(num >=1)
						   {
							   nextClause = nextSen.getClauseList().get(0);
							   if(isTrueTranslation(nextClause)&&isTrueCondition(nextClause))
								   continue;
							   if(isTrueTranslation(nextClause)&&isTrueEffect("DENY",nextClause))
								   continue;
						   }
					   }
					
					   clause = clauseList.get(j);
//						System.out.println("Clause:"+clause.getClause());
//						System.out.println("Effect:"+clause.getAuthority());
//						System.out.println("Question:"+clause.getQuestion());
//						System.out.println("Tense:"+clause.getPresent());

//						point = calculateGrade.Grade(subject, predicate, object, clause, query);
//						grade.addGrade(point);
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
