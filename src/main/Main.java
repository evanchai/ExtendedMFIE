package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.stackoverflow.bean.Post;
import com.stackoverflow.utils.PostDAOImpl;

import cn.edu.fudan.se.NLP.Clause;
import cn.edu.fudan.se.NLP.Sentence;
import cn.edu.fudan.se.NLP.TestCoreNLP;
import cn.edu.fudan.se.facet.Facet;
import cn.edu.fudan.se.facet.Grade;
import cn.edu.fudan.se.facet.InitFacetItem;
import cn.edu.fudan.se.facet.PostFacetType;

import cn.edu.fudan.se.result.Result;
import cn.edu.fudan.se.util.Global;


public class Main {
	
	private TestCoreNLP tcNLP;
	
	private Result result;
	
	private InitFacetItem ift;
	
	private Clause queryClause;
	
	private List<Sentence> sentenceList;
	
	List<Post> postList;
	
	List<Facet> itemList;
	
	
	
	public Main(String query)
	{
//		FilterPost.setFilterTag("database");
 		tcNLP = new TestCoreNLP();
	    result = new Result(nlpQuery(query));

		postList = result.getPosts();
 		
 		postList.clear();
 		String title = "i like to eat apple in visual studio.";
 		String body = "";
 		postList.add(new Post(0,title,body,"http",0,1,0,0));

		System.out.println("Size:"+postList.size());
		ift = new InitFacetItem();
		itemList = ift.getItem();
		matchFacetItem();
		showEnvironment();
		
	}
	
	public String nlpQuery(String query)
	{
		queryClause = tcNLP.QuerySentence(query);
		return queryClause.getKeyWord();
	}
	
	public void matchFacetItem()
	{
	   int postNum = 0;

	   for(Post post:postList)
	   {
		   postNum++;
		   System.out.println("NUM:"+postNum+"|"+post.postId);
//         if(post.postId!=42996)
//    	   continue;
		   
		   for(Facet item:itemList)
		   {
			   item.setIsMatch(false);
			   item.MatchPattern(post);
		   }
		   
		   String text = post.getText((post.post_body_text));
		   text = post.post_title +"." + text;
		   if(text.length() > 1000)//�ַ�˵����1500��nlp��ջ���
			   text = text.substring(0,1000);
		   tcNLP.CoreNLP(text);
		   sentenceList = tcNLP.getSentence();
		   for(Facet item:itemList)
		   {

//	
//			   if(item.getEnvir().contains("Explain"))
//			   {
//				   System.out.println("ok");
//				   item.init(sentenceList, post, queryClause);
//			   }
			   if(item.isMatch())
				   continue;
			   item.init(sentenceList, post, queryClause);
		   }

	   }
	}
	public boolean contain(List<PostFacetType> pftList,int Id)
	{
		for(PostFacetType pft:pftList)
		{
			if(pft.postId == Id)
				return true;
		}
		return false;
	}
	public void showEnvironment()
	{
		for(Facet item:itemList)
		{
			System.out.println(item.getEnvir());
			item.postList(null);
			item.showPost();
			
		    System.out.println("----------------");
		}
//		List<PostFacetType> pftList = new ArrayList<PostFacetType>();
		HashMap<Integer,PostFacetType> hashpft = new HashMap<Integer,PostFacetType>();
	
			for(Facet item:itemList)
			{
				HashMap<Post,Grade> postM = item.getPost();
				if(postM == null)
					continue;
				Iterator iter = postM.keySet().iterator();
			
				while (iter.hasNext())
				{
					Post post = (Post) iter.next();
					if(item.getEnvir().contains("Configuration")||
							item.getEnvir().contains("Exception")||
							item.getEnvir().contains("Code")||
							item.getEnvir().contains("Design"))
					{
						if(!hashpft.containsKey(post.postId))
						{
							PostFacetType pft = new PostFacetType();
							pft.focus += item.getEnvir()+",";
							pft.postId = post.postId;
							pft.postTypeId = 1;					   
						    hashpft.put(post.postId, pft);
						}else
						{
							PostFacetType pft = hashpft.get(post.postId);
							
							pft.focus += item.getEnvir() +",";
						}
						
						
					}
					if(item.getEnvir().contains("Language"))
					{
						if(!hashpft.containsKey(post.postId))
						{
							PostFacetType pft = new PostFacetType();
							pft.language += item.getEnvir()+",";
							pft.postId = post.postId;
							pft.postTypeId = 1;					   
						    hashpft.put(post.postId, pft);
						}else
						{
							PostFacetType pft = hashpft.get(post.postId);
							pft.language += item.getEnvir()+",";
						}
					}
					if(item.getEnvir().contains("System"))
					{
						if(!hashpft.containsKey(post.postId))
						{
							PostFacetType pft = new PostFacetType();
							pft.system += item.getEnvir()+",";
							pft.postId = post.postId;
							pft.postTypeId = 1;					   
						    hashpft.put(post.postId, pft);
						}else
						{
							PostFacetType pft = hashpft.get(post.postId);
							pft.system += item.getEnvir()+",";
						}
					}
				}
			}
			
			for(Integer id:Global.intList)
			{
				if(!hashpft.containsKey(id))
				{
					PostFacetType p = new PostFacetType();
					p.postId = id;
					p.postTypeId = 1;
					hashpft.put(id, p);
				}
			}
			Iterator iter = hashpft.keySet().iterator();
			
			while (iter.hasNext())
			{
				PostFacetType pft = (PostFacetType) hashpft.get(iter.next());
				if(pft.focus.equals(""))
					pft.focus = "Others";
				if(pft.system.equals(""))
					pft.system = "Others";
				if(pft.language.equals(""))
					pft.language = "Others";
			}
//		PostDAOImpl pdi = new PostDAOImpl();
//		pdi.Update(hashpft);
	}

	
	public static void main(String args[])
	{
		Main main = new Main("Spring");
//		main.matchFacetItem();
//		main.showEnvironment();
	}
	
	

}
