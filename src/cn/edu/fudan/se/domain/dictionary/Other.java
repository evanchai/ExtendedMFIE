package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Other implements DomainDictionary{
	
List<String> list = new ArrayList<String>();
	
	public Other()
	{
		list.add("ignore");list.add("neglect");list.add("mean");list.add("count");list.add("copy");
		list.add("run");list.add("work");list.add("duplicate");list.add("lay");list.add("guide");
		list.add("have");list.add("contain");
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
		return Global.UPDATE;
	}

}
