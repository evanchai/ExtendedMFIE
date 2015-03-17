package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

public class Expression {
	private static List<String> expression = new ArrayList<String>();
	
	private static Expression expre = new Expression();
	public Expression()
	{
		expression.add("n't ");
		expression.add("not ");
		expression.add("no ");
		expression.add("never ");
//		expression.add("without ");
		expression.add("dont ");
		expression.add("unable ");
		expression.add("nothing ");
		expression.add("doesnt ");
		expression.add("isnt ");
		expression.add("wasnt");
	}
	
	public static Expression  getInstance()
	{
		return expre;
	}
	
	public static boolean Authority(String environment)
	{
		for(String exss:expression)
		{
			
			if(environment.toLowerCase().contains(exss))
				return true;
		}
		return false;
	}


}
