package cn.edu.fudan.se.facet;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.edu.fudan.se.facet.Sentence;




public class InitFacetItem {
	
private List<Facet> itemList = new ArrayList<Facet>();
	
	
	public InitFacetItem()
	{
		 try {
	            File f = new File("newfacet.xml");
	            SAXReader reader = new SAXReader();
	            Document doc = reader.read(f);
	            Element root = doc.getRootElement();

	            Facet facetInstance = null;
	            List<Element> facet = root.elements();
	            for(Element fac:facet)
	            {
	            	String faceName = fac.attributeValue("name");
        			
	            	
	            		 List<Element> element = fac.elements();
	            		 for(Element ele1:element)
	            		 {
	            			String facetItemName = ele1.attributeValue("name");
	            			facetInstance = new ClassifyPost(faceName,facetItemName);
//	            			System.out.println(faceName+"|"+facetItemName);
	            			
	            			List<Element> modelElement= ele1.elements();
	            			List<Pattern> patternList = new ArrayList<Pattern>();
	            			Pattern pattern = null;
	            			List<Rule> ruleList = null;
	            			Sentence sentence;
	            			Code code;
	            			Tag tag;
	            			for(Element patterns:modelElement)
	            			{
	            				if(patterns.getName().equals("Pattern"))
	            				{
	            					String type = patterns.attributeValue("type");
//	            					System.out.println(patterns.attributeValue("type"));
	            					pattern = new Pattern(type);
	            					if(type.equalsIgnoreCase("sentence"))
	            					{
	            						Element sentenceElement = patterns.element("Sentence");
	            						String effect = sentenceElement.attributeValue("Effect");
	            						String question = sentenceElement.attributeValue("Question");
	            						String tense = sentenceElement.attributeValue("Tense");
//	            						System.out.println(sentenceElement.attributeValue("Effect"));
	            					    ruleList = new ArrayList<Rule>();
	            			           
	            					    List<Element> ruleElement = sentenceElement.elements();
	            					    for(Element rE:ruleElement )
	            					    {
	            						
//	            					    	System.out.println(rE.elementText("Dic"));
	            							ruleList.add(new Rule(rE.elementText("Type"),rE.elementText("Dic"),rE.elementText("BeforeAfter"),rE.elementText("Property")
	            										,rE.elementText("DomainVerb"),rE.elementText("Logic")));
	
	            					    }
	            					    sentence = new Sentence(effect,question,tense,ruleList);
	            					    pattern.setSentence(sentence);
	            					}else if(type.equalsIgnoreCase("code"))
	            					{
	            						String codeDic = patterns.elementText("Code");
	            						String answerHasCode = patterns.elementText("AnswerHasCode");
	            						code = new Code(codeDic,answerHasCode);
//	            						System.out.println(codeDic);
	            						pattern.setCode(code);
	            					}else if(type.equalsIgnoreCase("tag"))
	            					{
	            						String tagDic = patterns.elementText("Tag");
	            						tag = new Tag(tagDic);
//	            						System.out.println(tagDic);
	            						pattern.setTag(tag);
	            					}
	            				
		            				patternList.add(pattern);

	            				}	            				
	            			}
//	            			for(Pattern p:patternList)
//	            			{
//	            				System.out.println(p.getType());
//	            			}
	            			facetInstance.initModel(patternList);
	            			itemList.add(facetInstance);
	            			 
	            		 }
	            	}
	        } catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void init()
	{
		        
	}
	
	public List<Facet> getItem()
	{
		return itemList;
	}
	
	public static void main(String args[])
	{
		new InitFacetItem();
	}
	

}
