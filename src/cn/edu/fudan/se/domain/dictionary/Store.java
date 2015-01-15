package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Store implements DomainDictionary{
	
List<String> list = new ArrayList<String>();
	
	public Store()
	{
		list.add("store");list.add("save");list.add("preserve");list.add("keep");
		list.add("restore");list.add("backup");
		
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
		return Global.STORE;
	}


}
