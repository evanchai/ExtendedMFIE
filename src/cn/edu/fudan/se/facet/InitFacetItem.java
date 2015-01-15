package cn.edu.fudan.se.facet;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import cn.edu.fudan.se.environment.Environment;

import cn.edu.fudan.se.focuse.Focus;


public class InitFacetItem {
	
private List<Facet> itemList = new ArrayList<Facet>();
	
//	public InitFacetItem()
//	{
//		itemList.add(new Linux());
//		itemList.add(new Android());
//		itemList.add(new OSX());
//		itemList.add(new Windows());
////		envirList.add(new IOS());
//
//		itemList.add(new Cc());
//		itemList.add(new Cx());
//		itemList.add(new Cy());
//		itemList.add(new Java());
//		itemList.add(new JSP());
//		itemList.add(new NET());
//		itemList.add(new PHP());
//		itemList.add(new Python());
//
//		itemList.add(new MySQL());
//		itemList.add(new SQL());
//		itemList.add(new SQLite());
//		itemList.add(new Oracle());
//		
//		itemList.add(new FreeLanguage());
//		itemList.add(new FreeSystem());
//		itemList.add(new FreeDatabase());
//		
//		itemList.add(new API());
//		itemList.add(new Connection());
//		itemList.add(new Exception());
//		itemList.add(new QueryFocus());
//		itemList.add(new KeyFocuse());
//		itemList.add(new ColumnFocuse());
//		itemList.add(new TableFocus());
//		itemList.add(new Configure());
//	
//	}
	
	
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
	            			 if(fac.getName().equalsIgnoreCase("Environment"))
	            			     facetInstance = new Environment("Environment-"+ele1.getName(),ele1.elementText("Item"));
	            			 else if(fac.getName().equalsIgnoreCase("Focus"))
	            				 facetInstance = new Focus("Focus",ele1.elementText("Item"));
	            			
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
	            							model.elementText("DomainVerb"),model.elementText("Code"),
	            							model.elementText("Effect")));
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

}
