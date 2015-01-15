
package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

public class test {
	
	List<DomainDictionary> ddList = new ArrayList<DomainDictionary>();
	
	public test()
	{
		ddList.add(new Show());
		ddList.add(new Negative());
	}
	public void Authority(String verb)
	{
		for(DomainDictionary dd:ddList)
		{
			if(dd.Authority(verb))
				System.out.println(dd.getType());
		}
	}
	
	public static void main(String args[])
	{
		test t = new test();
		t.Authority("exhibit");
	}


}
