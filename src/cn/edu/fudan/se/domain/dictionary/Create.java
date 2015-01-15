package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Create implements DomainDictionary{
	

	List<String> list = new ArrayList<String>();
	
	public Create()
	{
		list.add("create");list.add("produce");list.add("construct");
		list.add("build");list.add("progress");list.add("establish");
		list.add("setup");
		
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
		return Global.CREATE;
	}

}
