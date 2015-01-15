package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Import implements DomainDictionary{
	
	List<String> list = new ArrayList<String>();
	public Import()
	{
		list.add("import");list.add("load");list.add("download");list.add("read");
		list.add("insert");
		
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
		return Global.IMPORT;
	}

}
