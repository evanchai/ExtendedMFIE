package cn.edu.fudan.se.facet;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;




public class InitFacetItem {
	
private List<Facet> itemList = new ArrayList<Facet>();
	
	
	public InitFacetItem()
	{
		 try {
	            File f = new File("C:\\Users\\jqt\\Desktop\\Others\\stackoverflow\\ExtendedMFIE\\ExtendedMFIE\\facet.xml");
	            SAXReader reader = new SAXReader();
	            Document doc = reader.read(f);
	            Element root = doc.getRootElement();
	            Element foo;

	            Facet facetInstance = null;
	            List<Element> facet = root.elements();
	            for(Element fac:facet)
	            {
//	            	if(fac.getName().equalsIgnoreCase("Environment"))
//	            	{
	            		 List<Element> element = fac.elements();
	            		 for(Element ele1:element)
	            		 {
//	            			 if(fac.getName().equalsIgnoreCase("Environment"))
//	            			     facetInstance = new ClassifyPost(ele1.getName(),ele1.elementText("Item"));
//	            			 else if(fac.getName().equalsIgnoreCase("Focus"))
//	            				 facetInstance = new ClassifyPost("Focus",ele1.elementText("Item"));
	            			 
//	            			 System.out.println(ele1.getName()+"|"+ele1.elementText("Item"));
	            			
	            			facetInstance = new ClassifyPost(ele1.getName(),ele1.elementText("Item"));
//	            			Element model = ele1.element("Model");
	            			
	            			List<Element> modelElement= ele1.elements();
	            			List<Model> modelList = new ArrayList<Model>();
	            			for(Element model:modelElement)
	            			{
	            				if(model.getName().equals("Model"))
	            				{
	            					modelList.add(new Model(model.elementText("DomainDicS"),model.elementText("DomainDicP"),model.elementText("DomainDicO")
	            							,model.elementText("PropertyS"),
	            							model.elementText("PropertyP"),model.elementText("PropertyO"),
	            							model.elementText("DomainVerb"),model.elementText("Code"),model.elementText("Tag"),
	            							model.elementText("Effect"),model.elementText("Question"),model.elementText("Tense"),
	            							model.elementText("State")));
	            				}
	            			}
	            			
	            			
	            			facetInstance.initModel(modelList);
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
