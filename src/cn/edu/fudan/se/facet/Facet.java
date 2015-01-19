package cn.edu.fudan.se.facet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	public boolean isTrue(String text)
	{
		return false;
	}
	
	public void initModel(List<Model> modelList)
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
	public void showPost()
	{
		
	}
	  public void show(HashMap<Post, Grade> postM) 
	    {
	        List<Map.Entry<Post, Grade>> list_Data = new ArrayList<Map.Entry<Post, Grade>>(postM.entrySet());
	        Collections.sort(list_Data,
	            new Comparator<Map.Entry<Post, Grade>>() {
	                @Override
	                public int compare(Entry<Post, Grade> o1, Entry<Post, Grade> o2) {
	                    // TODO Auto-generated method stub
	                    if (o2.getValue().getGrade()>o1.getValue().getGrade()) {
	                        return 1;
	                    } else {
	                        return -1;
	                    }
	                }
	            });
	        
	      for(Map.Entry<Post, Grade> map:list_Data)
	      {
//	    	  System.out.println(map.getValue().getGrade()+"|"+map.getKey().post_title);
	    	  System.out.println(map.getKey().post_title);
	      }
	    }
	  
	  public HashMap<Post,Grade> getPost()
	  {
		  return null;
	  }
}
