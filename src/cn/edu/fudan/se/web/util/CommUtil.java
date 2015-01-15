package cn.edu.fudan.se.web.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;

import cn.edu.fudan.se.web.bean.MiddleData;
import cn.edu.fudan.se.web.pojo.Element;

public class CommUtil
{
	private static final String SPLITE_STRING = "[; ]";
	private static final int TOTAL_WORDS = 250;
	private static final String[] JAVA_KEYWORDS = new String[]
			{ "copyright", "import", "the", "abstract",  "break",  "case", "catch",  "class", "continue", "default", "do", "double", "else",
					"extends", "false", "final", "finally", "float","byte","boolean","char","for","int", "if", "implements", "instanceof",  "interface", "long", "native",
					"new", "null", "private", "protected", "public", "return", "short", "static", "super", "switch", "synchronized", "this", "throw",
					"throws", "transient", "true", "try", "void", "volatile", "while", "const", "goto", "String" };
	
	public static String getAroundWords(String text, int startPosition)
	{
		int index = startPosition;		
		while(index>0  && startPosition - index < 100)
		{
			if(text.charAt(index) == '\n')
				break;
			index--;
		}
		text = text.substring(index);
		if(text.length() > TOTAL_WORDS)
		{
			for(int lastIndex = TOTAL_WORDS - 10; lastIndex < text.length() && lastIndex < TOTAL_WORDS + 10; lastIndex++)
			{
				char c = text.charAt(lastIndex);
				if(c == '\n' || c== ' ' || c == ',' || c== '.')
				{
					text = text.substring(0, lastIndex);
					break;
				}
			}
			if(text.length()>TOTAL_WORDS)
				text = text.substring(0, TOTAL_WORDS);
			
		}
		return text;
	}
	
	public static String[] splitWithoutSpace(String text, String regex)
	{
		String[] array = text.trim().split(regex);
		List<String> list = new ArrayList<String>();
		for (String content : array)
		{
			if (content.trim().length() > 0)
				list.add(content.trim());
		}
		return list.toArray(new String[0]);
	}


	public static boolean stringNullOrZero(String str)
	{
		return str == null || str.trim().length() == 0;
	}

	public static String getCurrentProjectPath()
	{
		return rootPath;
	}

	public static int getMatchCount(Matcher matcher)
	{
		int result =0;
		while(matcher.find())
		{
			result ++;
		}
		return result;
	}
	
	public static void main(String[] argv){
		String keys = "a";
		List<String> ret = getCorrectRegularExpression(keys);
		for(String s:ret)
			System.out.println(s);
	}
	
	public static List<String> getCorrectRegularExpression(String originalString)
	{
		List<String> result = new ArrayList<String>();
		if (stringNullOrZero(originalString))
		{
			System.out.println("Error");
			return null;
		}
		String[] keywords = splitWithoutSpace(originalString, "[ *,;]");
		AllSort allSort = new AllSort(keywords);
		Object[][] permutation = allSort.getPermutation();
		for (int i = 0; i < permutation.length; i++)
		{
			String expression = "";
			for (int j = permutation[i].length - 1; j >= 0; j--)
			{
				expression = permutation[i][j] + "[\\s\\S]*" + expression;
			}
			result.add(expression);
		}

		return result;
	}

	public static String transferContent(String methodBody)
	{
		methodBody = methodBody.replace("<", "&lt;");
		methodBody = methodBody.replace(">", "&gt;");
		return methodBody;
	}

	public static String ListToString(List<String> list)
	{
		StringBuilder sb = new StringBuilder();
		for (String object : list)
		{
			sb.append(object + " ");
		}
		return sb.toString().trim();
	}

	public static List<String> stringToList(String strContent)
	{
		List<String> result = new ArrayList<String>();
		for (String str : strContent.split(SPLITE_STRING))
		{
			if (str.trim().length() > 0)
				result.add(str);
		}
		return result;
	}

	public static String getDateTime()
	{
		String result;
		GregorianCalendar calendar = new GregorianCalendar();
		result = calendar.get(Calendar.YEAR) + ""
				+ (calendar.get(Calendar.MONTH) + 1) + ""
				+ calendar.get(Calendar.DATE);
		return result;

	}
	
	public static int getCombinationLength(int totalCount, int selectCount)
	{
		int result = 1;
		if(totalCount< selectCount)
			return 0;
		for(int i=0; i<selectCount; i++)
		{
			result = result * (totalCount-i);
		}
		for(int i=0; i<selectCount ;i++)
		{
			result = result / (selectCount -i);
		}
		return result;
	}
	
	public static String[][] getCombination(String[] array, int length)
	{
		String[][] result = new String[getCombinationLength(array.length, length)][length];
		for(int i =0; i<result.length ;i ++)
		{
			
		}
		return result;
		
	}

	public static int CompareStr(String str1, String str2)
	{

		if (str1 == null)
			str1 = "";
		if (str2 == null)
			str2 = "";

		return str1.compareTo(str2);
	}

	public static String trimOnlySign(String fullMethodName)
	{
		String trimMethodName = fullMethodName;
		int indexP = fullMethodName.indexOf("(", 0);
		trimMethodName = fullMethodName.substring(0, indexP);

		// System.out.println(trimMethodName);

		return trimMethodName;

	}

	public static void printList(List<String> list)
	{
		for(String object : list)
			System.out.println(object.toString());
	}
	public static String trimMethodAndSign(String fullMethodName)
	{
		String trimMethodName = fullMethodName;
		int indexP = fullMethodName.indexOf("(", 0);
		trimMethodName = fullMethodName.substring(0, indexP);
		indexP = trimMethodName.lastIndexOf(".");
		trimMethodName = trimMethodName.substring(0, indexP);

		// System.out.println(trimMethodName);

		return trimMethodName;

	}

	private static String rootPath;

	public static void setRootPath(String realPath)
	{
		rootPath = realPath;

	}

	public static String removeKeywords(String body)
	{
		body = ";" + body;
		for(String keyword : JAVA_KEYWORDS)
		{
			body = body.replaceAll( ";" + keyword + ";", ";");
		}
		return body;
	}

	public static List<String> removeRepeatWords(List<String> words)
	{
		List<String> result = new ArrayList<String>();
		for(String word: words)
		{
			if(!result.contains(word))
				result.add(word);
		}
		return result;
	}
	
}
