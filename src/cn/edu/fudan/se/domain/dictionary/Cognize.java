package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Cognize implements DomainDictionary{
	
List<String> list = new ArrayList<String>();
	
	public Cognize()
	{
		list.add("know");list.add("cognize");list.add("acknowledge");list.add("recognize");
		list.add("recognise");list.add("consider");list.add("interpret");list.add("explain");
		list.add("compare");list.add("equate");
		
	}

	@Override
	public boolean Authority(String verb) {
		// TODO Auto-generated method stub
		if(list.contains(verb))
			return true;
		return false;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Global.COGNIZE;
	}


}
