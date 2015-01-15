package cn.edu.fudan.se.wordnet;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.filter.Stemmer;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class Synonym {
	
	List<String> synonymList = new ArrayList<String>();
	IDictionary dict ;
	public Synonym()
	{
		String wnhome = "H:\\WordNet\\2.1\\";
		String path = wnhome + File.separator + "dict";
		URL url = null;
		try {
			url = new URL("file", null, path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ����һ��URL����ָ��WordNet��ditcĿ¼

		// construct the dictionary object and open it
		dict = new Dictionary(url);

		dict.open(); // �򿪴ʵ�

	}
	public void extractSynonym(String verb) {
		
		// look up first sense of the word "dog "
	
		IIndexWord idxWord = dict.getIndexWord(verb, POS.VERB); // ��ȡһ������ʣ���go,���ʣ�

		if(idxWord == null)
		{
			System.out.println("error");
			return;
		}
		List<IWordID> iWordIDList = idxWord.getWordIDs();// ��ȡ�����б�

		for (IWordID iw : iWordIDList) {
			IWord word = dict.getWord(iw);// ��ȡ����

//			System.out.println(word.getLemma()+"|"+word.getSynset().getGloss());
//			if(!word.getPOS().toString().equals("verb"))
//				continue;
			ISynset relatedSynonyms = word.getSynset();
			List<IWord> synonymsList = relatedSynonyms.getWords();
			for (IWord w : synonymsList) {
//				System.out.println(w.getLemma());
				getSynonym(w.getLemma());
			}

		}

	}
	
	public List<String> getSynonym(String synonym)
	{
		if(synonym.contains("_"))
			synonym = synonym.replace("_", " ");
		if(!synonymList.contains(synonym))
			synonymList.add(synonym);
		return synonymList;
	}
	
	public void show()
	{
		for(String synonym:synonymList)
		{
			System.out.println(synonym);
		}
	}

	public static void main(String args[]) {
		Synonym t = new Synonym();
		t.extractSynonym("query");
		t.show();

	}

}
