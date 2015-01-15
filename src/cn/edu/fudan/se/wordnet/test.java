package cn.edu.fudan.se.wordnet;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISenseKey;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class test {
    public void testDitctionary(){
        // construct the URL to the Wordnet dictionary directory 
//        String wnhome = System.getenv("WNHOME"); //��ȡ��������WNHOME 

        String wnhome = "H:\\WordNet\\2.1\\";
        String path = wnhome + File.separator + "dict";
        URL url = null;
		try {
			url = new URL("file", null, path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //����һ��URL����ָ��WordNet��ditcĿ¼ 

        // construct the dictionary object and open it 
        IDictionary dict = new Dictionary(url);

        dict.open(); //�򿪴ʵ� 

        // look up first sense of the word "dog " 
        IIndexWord idxWord = dict.getIndexWord("rely on", POS.VERB); //��ȡһ������ʣ���go,���ʣ� 

        List<IWordID> iWordIDList = idxWord.getWordIDs();//��ȡ�����б�
        
        
        for(IWordID iw:iWordIDList)
        {
        	 IWord word = dict.getWord(iw);//��ȡ����
        	 
        	 
//        	 List<IWordID> relatedWordList = word.getRelatedWords();//��ȡ��ش���
//        
//        	 for(IWordID rwID:relatedWordList)
//        	 {
//        		 IWord rWord = dict.getWord(rwID);
//        		 if(!rWord.getPOS().toString().equals("verb"))
//        			 continue;
//        		 System.out.println("R:"+rWord.getLemma()+"|"+rWord.getPOS());;
        		 
        		 ISynset relatedSynonyms = word.getSynset();
               	 List<IWord> synonymsList = relatedSynonyms.getWords();
               	 for(IWord w:synonymsList)
               	 {
               		 System.out.println(w.getLemma());
               	 }
        		 
//        	 }
        	
        	
        }
//        IWordID wordID = idxWord.getWordIDs().get(0); //��ȡdog��һ������ID 
//
//        IWord word = dict.getWord(wordID); //��ȡ�ô� 
//
////        System.out.println("Id = " + wordID);
//
//        System.out.println(" ��Ԫ = " + word.getLemma());
//    
//        List<IWordID> list = word.getRelatedWords();
//        
//        for(IWordID w:list)
//        {
//        	System.out.println("related:"+dict.getWord(w).getLemma());
//        }
//        System.out.println(" ע�� = " + word.getSynset().getGloss());
    }
    
//    public  static void main(String args[])
//    {
//    	test t = new test();
//			t.testDitctionary();
//	
//    }
}
