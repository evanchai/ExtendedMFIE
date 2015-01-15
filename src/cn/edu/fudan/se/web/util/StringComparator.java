package cn.edu.fudan.se.web.util;

import java.util.Arrays;
import java.util.Comparator;
import static java.lang.System.out;
public class StringComparator  implements Comparator<String>
{

	public int compare(String arg0, String arg1)
	{
		return arg0.compareToIgnoreCase(arg1);		
	}
	
	public static void main(String...strings )
	{
		String[] strs = new String[]{"abc", "efg","bdcc", "bcd"};
		Arrays.sort(strs, new StringComparator());
		for(String str : strs)
		{
			out.println(str);
		}
		
	}

}
