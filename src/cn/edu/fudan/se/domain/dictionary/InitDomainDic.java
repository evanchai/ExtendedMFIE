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

		ddList.add(new Configure());
		ddList.add(new Get());
		ddList.add(new Use());
		ddList.add(new Produce());
		ddList.add(new Search());
		ddList.add(new Support());
		ddList.add(new Explain());
		ddList.add(new Understand());
		ddList.add(new Be());
		ddList.add(new Does());
		ddList.add(new Install());
		ddList.add(new Convert());
		ddList.add(new Parse());
		ddList.add(new Send());
		ddList.add(new Query());
		ddList.add(new Redirect());
	}
	
	public String getSynonyms(String verb,String word)
	{
		String synonyms = "";
		for(DomainDictionary dd:ddList)
		{
			if(dd.getType().equals("DOES")&&dd.Authority(word))
			{
				synonyms += dd.getType()+" ";
			}else if(dd.Authority(verb))
				synonyms += dd.getType()+" ";
		}
		
		return synonyms;
	}
	
	public  List<DomainDictionary> getDomainDictionary()
	{
		return ddList;
	}

}
