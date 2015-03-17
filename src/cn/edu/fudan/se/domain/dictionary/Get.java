package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Get implements DomainDictionary{
	
List<String> list = new ArrayList<String>();
	
	public Get()
	{
		list.add("get");list.add("receive");list.add("obtain");list.add("meet");
		list.add("have");list.add("read");

		
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
		return Global.GET;
	}

}
