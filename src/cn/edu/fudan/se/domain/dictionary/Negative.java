package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

public class Negative implements DomainDictionary{

	List<String> negativeList = new ArrayList<String>();
	
	public Negative()
	{
		negativeList.add("no");negativeList.add("forbid");negativeList.add("prohibit");negativeList.add("interdict");
		negativeList.add("proscribe");negativeList.add("veto");negativeList.add("disallow");negativeList.add("no");
		negativeList.add("not");negativeList.add("fail");negativeList.add("break");negativeList.add("die");
		negativeList.add("n't");negativeList.add("prevent");negativeList.add("forestall");
		negativeList.add("foreclose");negativeList.add("preclude");
	
	}
	@Override
	public boolean Authority(String verb) {
		// TODO Auto-generated method stub
		if(negativeList.contains(verb))
			return true;
		return false;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "NEGATIVE";
	}

}
