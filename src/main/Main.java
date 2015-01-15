package main;

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
import cn.edu.fudan.se.filter.FilterPost;
import cn.edu.fudan.se.result.Result;
import cn.edu.fudan.se.util.Global;
import cn.edu.fudan.se.util.PostFacetType;


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
	    System.out.println(nlpQuery(query));
//		result.setQueryToken(qt.getToken());
		postList = result.getPosts();
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
		   for(Facet item:itemList)
		   {
			   item.setIsMatch(false);
			   
		   }
		   if(post.parentId != 0)
			   continue;
		   postNum++;
		   System.out.println("NUM:"+postNum);
		
//           if(postNum == 29||postNum == )
//        	   continue;
		   String text = post.getText((post.post_body_text));
		   text = post.post_title +". " + text;
		   if(text.length() > 1000)//�ַ�˵����1500��nlp��ջ���
			   text = text.substring(0,1000);
		   tcNLP.CoreNLP(text);
		   sentenceList = tcNLP.getSentence();
		   for(Facet item:itemList)
		   {
			   item.init(sentenceList, post, queryClause);
			   
		   }

	   }
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
		List<PostFacetType> pftList = Global.postFT;
		for(PostFacetType pft:pftList)
		{
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
							item.getEnvir().contains("Exception"))
					{
						if(post.postId == pft.postId)
						{
							pft.focus = item.getEnvir();
						}
					}else if(item.getEnvir().contains("Language")||
							item.getEnvir().contains("Database")||
							item.getEnvir().contains("System"))
					{
						if(post.postId == pft.postId)
						{
							pft.environment = item.getEnvir();
						}
					}
				}
			}
		}
		for(PostFacetType pft:pftList)
		{
			if(pft.focus == null)
				pft.focus = "Others";
			if(pft.environment == null)
				pft.environment = "Others";
		}
		PostDAOImpl pdi = new PostDAOImpl();
		pdi.Update(pftList);
	}

	
	public static void main(String args[])
	{
		Main main = new Main("How to connect to mysql database.");
//		main.matchFacetItem();
//		main.showEnvironment();
	}
	
	

}
