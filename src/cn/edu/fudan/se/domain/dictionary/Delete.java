package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Delete implements DomainDictionary{
	
List<String> list = new ArrayList<String>();
	
	public Delete()
	{
		list.add("delete");list.add("cancel");list.add("remove");
		list.add("withdraw");list.add("take");list.add("cut");list.add("trim");
		list.add("thin");list.add("geld");list.add("foreshorten");list.add("shorten");
		list.add("drop");list.add("sink");list.add("omit");list.add("cast");
		list.add("shed");list.add("shrink");list.add("purge");list.add("empty");list.add("abandon");
		list.add("void");list.add("vacate");
		
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
		return Global.DELETE;
	}

}
