package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Check implements DomainDictionary{
	
	List<String> list = new ArrayList<String>();
	public Check()
	{
		list.add("check");list.add("look");list.add("see");list.add("ensure");
		list.add("assure");
		
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
		return Global.CHECK;
	}

}
