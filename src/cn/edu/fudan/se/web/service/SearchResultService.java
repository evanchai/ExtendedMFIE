package cn.edu.fudan.se.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.carrot2.core.Cluster;

import main.Main;
import cn.edu.fudan.se.NLP.Clause;
import cn.edu.fudan.se.NLP.Sentence;
import cn.edu.fudan.se.NLP.TestCoreNLP;
import cn.edu.fudan.se.cluster.MultiFacets;
import cn.edu.fudan.se.facet.Facet;
import cn.edu.fudan.se.facet.Grade;
import cn.edu.fudan.se.facet.InitFacetItem;
import cn.edu.fudan.se.filter.FilterPost;
import cn.edu.fudan.se.result.Result;

import com.stackoverflow.bean.Post;

public class SearchResultService {
	
	private TestCoreNLP tcNLP;
	
	private Result result;
	
	private InitFacetItem ift;
	
	private Clause queryClause;
	
	private List<Sentence> sentenceList;
	
	List<Post> postList = new ArrayList<Post>();
	
	List<Facet> itemList;
	
	
	
	public SearchResultService()
	{
		
	}
	
	public void searchStackOverflow(String query)
	{
//		FilterPost.setFilterTag("database");
// 		tcNLP = new TestCoreNLP();
// 	    postList.clear();
// 	    String title = "I am getting the following error in java";
// 	    postList.add(new Post(0,title,"java database","java database",0,0,0,0));
	    result = new Result(nlpQuery(query));
	    System.out.println(nlpQuery(query));
//		result.setQueryToken(qt.getToken());
		postList = result.getPosts();
		System.out.println("Size:"+postList.size());
		ift = new InitFacetItem();
		itemList = ift.getItem();
		matchFacetItem();
		
		try {
		
			//clusteringByProgarmmingLanuguage(postList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showEnvironment();
	}
	
	public List<Facet> getFacetItemList()
	{
		return this.itemList;
	}
	
	public List<Post> getPostList()
	{
		return this.postList;
	}
	
	
	
	public List<Post> transformPost()
	{
		List<Post> postList = new ArrayList<Post>();
		for(Facet facet:itemList)
		{
			HashMap<Post,Grade> postM = facet.getPost();
			Iterator iter = postM.keySet().iterator();
			while (iter.hasNext())
			{
		    	postList.add((Post) iter.next());
		    }
		
		}
		return postList;
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
		   if(post.parentId != 0)
			   continue;
		   postNum++;
		   System.out.println("NUM:"+postNum);
		
		   for(Facet item:itemList)
		   {
			   item.setIsMatch(false);
			   
		   }
		   String text = post.getText((post.post_body_text));
		   text = post.post_title +". " + text;
//		   String text = "Does anybody know if there is a way to create a SQLite database based on an XSD DataSet? In the past I've just used a basic SQLite manager, but want to fuse things a bit more with my .NET development if possible.;";
		   if(text.length() > 1300)//�ַ�˵����1500��nlp��ջ���
			   text = text.substring(0,1300);
		   tcNLP.CoreNLP(text);
//		   System.out.println(post.post_title);
//		   System.out.println(post.post_body_code);
		   sentenceList = tcNLP.getSentence();
		   for(Facet item:itemList)
		   {
			   item.init(sentenceList, post, queryClause);
		   }

	   }
//	   tcNLP.showSentenceComponent();
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
	}

	
	public static void main(String args[])
	{
		Main main = new Main("How to connect to mysql database.");
		main.matchFacetItem();
		main.showEnvironment();
	}

}
