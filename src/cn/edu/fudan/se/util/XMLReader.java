package cn.edu.fudan.se.util;

import org.dom4j.*;
import org.dom4j.io.*;

import cn.edu.fudan.se.environment.Environment;
import cn.edu.fudan.se.facet.Model;

import java.io.*;
import java.util.*;


public class XMLReader {
    public static void main(String[] arge) {

   	 try {
         File f = new File("facet.xml");
         SAXReader reader = new SAXReader();
         Document doc = reader.read(f);
         Element root = doc.getRootElement();
         Element foo;

         List<Element> facet = root.elements();
         for(Element fac:facet)
         {
         	if(fac.getName().equalsIgnoreCase("Environment"))
         	{
         		 List<Element> element = fac.elements();
         		 for(Element ele1:element)
         		 {
         			Environment environment = new Environment(ele1.getName(),ele1.elementText("Item"));
         			
//         			Element model = ele1.element("Model");
         			
         			List<Element> modelElement= ele1.elements();
         			for(Element model:modelElement)
         			{
         				if(model.getName().equals("Model"))
         				{
         					System.out.println(model.elementText("DomainDic")+"|"+model.elementText("PropertyS")+"|"+
         							model.elementText("PropertyP")+"|"+model.elementText("PropertyO")+"|"+model.elementText("DomainVerb"));
         					String s = model.elementText("DomainVerb");
         					if(s.equals(""))
         						System.out.println("true");
         				}
         			}
         			
         			 
         		 }
         	}
         }

     } catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
}
