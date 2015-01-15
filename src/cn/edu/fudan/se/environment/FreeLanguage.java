package cn.edu.fudan.se.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.stackoverflow.bean.Post;

import cn.edu.fudan.se.NLP.Clause;
import cn.edu.fudan.se.NLP.Object;
import cn.edu.fudan.se.NLP.Predicate;
import cn.edu.fudan.se.NLP.Sentence;
import cn.edu.fudan.se.NLP.Subject;
import cn.edu.fudan.se.facet.Facet;
import cn.edu.fudan.se.facet.CalculateGrade;
import cn.edu.fudan.se.facet.Grade;

public class FreeLanguage extends Facet{
	
    public HashMap<Post,Grade> postM = new HashMap<Post,Grade>();
    
    private CalculateGrade calculateGrade = new CalculateGrade();
    
    private Grade grade;
	@Override
	public boolean isTrue(String envir) {
		// TODO Auto-generated method stub
		if(envir.contains("C++")||(envir.contains("C#")&&envir.contains("#"))
				||envir.contains("Java")||envir.contains("NET")||envir.contains("PHP")
				||envir.contains("Python")||envir.contains("net")||(envir.contains("c")&&envir.contains("#")))
			return false;
		return true;
	}

	@Override
	public void postList(Post post) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void init(List<Sentence> sentence,Post post,Clause query){
		// TODO Auto-generated method stub
		grade = new Grade();
		grade.addGrade((float)0);
		if(!Free.languageList.contains(post)&&!postM.containsKey(post))
			postM.put(post, grade);
		
	}
	
	@Override
	public void showPost() {
		// TODO Auto-generated method stub
		
		show(postM);
		
	}

	@Override
	public String getEnvir() {
		// TODO Auto-generated method stub
		return "Free Language";
	}

}

