package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.util.Global;

public class Show implements DomainDictionary{
	
	List<String> showList = new ArrayList<String>();
	
	public Show()
	{
		showList.add("show");showList.add("demo");showList.add("exhibit");showList.add("present");
		showList.add("demonstrate");showList.add("prove");showList.add("shew");
		showList.add("testify");showList.add("witness");showList.add("evidence");showList.add("picture");
		showList.add("depict");showList.add("render");showList.add("express");showList.add("evince");
		showList.add("point");showList.add("reveal");showList.add("display");
		showList.add("usher");showList.add("list");showList.add("tell");showList.add("expose");
		showList.add("disclose");showList.add("unwrap");showList.add("view");
		showList.add("represent");showList.add("scan");
	}

	@Override
	public boolean Authority(String verb) {
		// TODO Auto-generated method stub
		if(showList.contains(verb))
			return true;
		return false;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Global.SHOW;
	}

}
