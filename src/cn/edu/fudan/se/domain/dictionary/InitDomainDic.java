package cn.edu.fudan.se.domain.dictionary;

import java.util.ArrayList;
import java.util.List;

public class InitDomainDic {
	
	private  List<DomainDictionary> ddList = new ArrayList<DomainDictionary>();
	
	private static InitDomainDic instance = new InitDomainDic();
	
	public static InitDomainDic getInstance()
	{
		return instance;
	}
	public InitDomainDic()
	{
		ddList.add(new Add());
		ddList.add(new Check());
		ddList.add(new Close());
		ddList.add(new Code());
		ddList.add(new Cognize());
		ddList.add(new Configure());
		ddList.add(new Connect());
		ddList.add(new Create());
		ddList.add(new Delete());
		ddList.add(new Export());
		ddList.add(new Import());
		ddList.add(new Get());
		ddList.add(new Optimize());
		ddList.add(new Show());
		ddList.add(new Store());
		ddList.add(new Update());
		ddList.add(new Use());
		ddList.add(new Query());
		ddList.add(new Regard());
	}
	
	public String getSynonyms(String verb)
	{
		String synonyms = "";
		for(DomainDictionary dd:ddList)
		{
			if(dd.Authority(verb))
				synonyms += dd.getType()+" ";
		}
		
		return synonyms;
	}
	
	public  List<DomainDictionary> getDomainDictionary()
	{
		return ddList;
	}

}
