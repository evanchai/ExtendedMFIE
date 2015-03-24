package cn.edu.fudan.se.facet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.edu.fudan.se.NLP.Clause;
import cn.edu.fudan.se.NLP.Sentence;






import com.stackoverflow.bean.Post;

public abstract class Facet {
	
	public void setIsMatch(boolean isMatch)
	{
		
	}
	public boolean isMatch()
	{
		return false;
	}
	public void setIsTag(boolean isTag)
	{
		
	}
	public boolean isTrue(String text)
	{
		return false;
	}
	
	public void initModel(List<Pattern> modelList)
	{
		
	}
	
	public void postList(Post post)
	{
		
	}
	
	
	public String getEnvir()
	{
		return "";
	}

	public void init(List<Sentence> sentence,Post post,Clause query){
		
	}
	public void MatchPattern(Post post)
	{
		
	}
	public void showPost()
	{
		
	}
	  public void show(HashMap<Post, Grade> postM) 
	    {
//	        List<Map.Entry<Post, Grade>> list_Data = new ArrayList<Map.Entry<Post, Grade>>(postM.entrySet());
//	        Collections.sort(list_Data,
//	            new Comparator<Map.Entry<Post, Grade>>() {
//	                @Override
//	                public int compare(Entry<Post, Grade> o1, Entry<Post, Grade> o2) {
//	                    // TODO Auto-generated method stub
//	                    if (o2.getValue().getGrade()>o1.getValue().getGrade()) {
//	                        return 1;
//	                    } else {
//	                        return -1;
//	                    }
//	                }
//	            });
//	        
		  Set set = postM.entrySet() ;
		  java.util.Iterator it = postM.entrySet().iterator();
		  while(it.hasNext())
		  {
			  java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
		      Post post = (Post)entry.getKey();
		     // entry.getValue() 
			  System.out.println(post.postId+"|"+post.post_title);
		  }
//	      for(Map.Entry<Post, Grade> map:list_Data)
//	      {
////	    	  System.out.println(map.getValue().getGrade()+"|"+map.getKey().post_title);
//	    	  System.out.println(map.getKey().postId+"|"+map.getKey().post_title);
//	      }
	    }
	  
	  public HashMap<Post,Grade> getPost()
	  {
		  return null;
	  }
}
