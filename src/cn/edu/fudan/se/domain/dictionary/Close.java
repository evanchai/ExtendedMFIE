package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Close implements DomainDictionary{
	
List<String> list = new ArrayList<String>();
	
	public Close()
	{
		list.add("close");list.add("kill");list.add("down");list.add("shut");
		
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
		return Global.CLOSE;
	}

}
