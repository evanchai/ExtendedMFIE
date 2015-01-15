package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Regard implements DomainDictionary{
List<String> list = new ArrayList<String>();
	
	public Regard()
	{
		list.add("regard");
		list.add("use");
		list.add("have");
		list.add("has");
		list.add("be");
		list.add("contain");
		list.add("come");
		
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
		return Global.REGARD;
	}


}
