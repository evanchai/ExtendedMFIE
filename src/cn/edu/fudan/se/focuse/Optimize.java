package cn.edu.fudan.se.focuse;

import java.util.HashMap;
import java.util.List;

import com.stackoverflow.bean.Post;

import cn.edu.fudan.se.NLP.Clause;
import cn.edu.fudan.se.NLP.Object;
import cn.edu.fudan.se.NLP.Predicate;
import cn.edu.fudan.se.NLP.Sentence;
import cn.edu.fudan.se.NLP.Subject;
import cn.edu.fudan.se.environment.Free;
import cn.edu.fudan.se.facet.Facet;

public class Optimize extends Facet{
	
	private HashMap<Post,Float> postM = new HashMap<Post,Float>();

	@Override
	public boolean isTrue(String text) {
		// TODO Auto-generated method stub
		return false;
	}
	private void MatchPattern(Subject subE,Predicate preE,Object objE,Post post)
	{
		
	}
	@Override
	public void init(List<Sentence> sentence,Post post,Clause query){
		// TODO Auto-generated method stub
	
		
	}

	@Override
	public void postList(Post post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showPost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEnvir() {
		// TODO Auto-generated method stub
		return null;
	}



}
