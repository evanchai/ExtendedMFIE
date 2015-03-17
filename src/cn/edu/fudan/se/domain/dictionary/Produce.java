package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Produce implements DomainDictionary{
List<String> list = new ArrayList<String>();
	
	public Produce()
	{
		list.add("throw");list.add("produce");list.add("make");
		list.add("generate");list.add("create");list.add("develop");
		list.add("write");list.add("build");
		
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
		return Global.PRODUCE;
	}

}
