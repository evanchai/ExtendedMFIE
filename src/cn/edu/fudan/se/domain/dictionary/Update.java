package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Update implements DomainDictionary{
	
	List<String> list = new ArrayList<String>();
	
	public Update()
	{
		list.add("swap");list.add("switch");list.add("swop");list.add("trade");
		list.add("change");list.add("alter");list.add("update");list.add("modify");list.add("edit");
		list.add("replace");list.add("name");list.add("rename");list.add("convert");
		list.add("exchange");list.add("move");list.add("revision");list.add("order");
		list.add("reorder");
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
