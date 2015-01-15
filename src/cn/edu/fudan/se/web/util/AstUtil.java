package cn.edu.fudan.se.web.util;


public class AstUtil
{

	
	public static void main(String[] args)
	{
//		Map<Map<String, String>, String> a = new HashMap<Map<String,String>, String>();	
		String methodName = "java.util.List<org.homeunix.thecave.buddi.model.Account>.remove(java.lang.Object)";
		String methodName2 = "Map<Map<String, String>, Map<String, String>>.remove(util.List<java.lang.Object>, Map<?>)";
		System.out.println(getMethodNameWithoutParas(methodName2));
		System.out.println(getShortMethodName(methodName2));
		System.out.println(getShortestMethodName(methodName2));
		String className = getClassName(methodName);
		String className2 = getClassName(methodName2);
		System.out.println(getClassName(methodName).endsWith("java.util.List"));
		System.out.println(getClassName(methodName2).endsWith("Map"));
		
		System.out.println(getPackageName(className).equals("java.util"));
		System.out.println(getPackageName(className2).equals(""));
		
		System.out.println(getShortClassName(className).equals("List"));
		System.out.println(getShortClassName(className2).equals("Map"));
		
	}
	
	public static String getMethodNameWithoutParas(String methodName)
	{
		int indexOfBracket = methodName.indexOf("(");
		return methodName.substring(0, indexOfBracket);
		
	}
	
	public static String[] getMethodParas(String methodName)
	{
		int firstIndex = methodName.indexOf("(");
		int lastIndex  = methodName.indexOf(")");
		return methodName.substring(firstIndex + 1, lastIndex).split("[,<>]");
	}
	
	public static String getShortMethodName(String methodName)
	{
		int indexOfLastBacket = methodName.lastIndexOf("(");
		int indexOfLastPoint = methodName.lastIndexOf(".", indexOfLastBacket);
		
		return methodName.substring(indexOfLastPoint +1);
	}
	
	public static String getShortestMethodName(String methodName)
	{
		String result = getShortMethodName(methodName);
		int indexOfFirstBacket = result.indexOf("(");
		return result.substring(0, indexOfFirstBacket);
		
	}
	
	public static String getClassName(String methodName)
	{
		int indexOfLastBacket = methodName.lastIndexOf("(");
		int indexOfLastPoint = methodName.lastIndexOf(".", indexOfLastBacket);
		if(methodName.charAt(indexOfLastPoint-1) == '>')
		{
			int positive = 1;
			int position = indexOfLastPoint -2;
			while(positive !=0)
			{				
				if(methodName.charAt(position)=='>')
					positive ++;
				else if(methodName.charAt(position) == '<')
					positive --;
				position --;
			}
			return methodName.substring(0,position+1);
		}
		return methodName.substring(0, indexOfLastPoint);
	}
	
	public static String getPackageName(String className)
	{
		if(className.contains(".") ==false)
			return "";
		return className.substring(0,className.lastIndexOf("."));
	}

	public static String getShortClassName(String fullClassName)
	{
		if(fullClassName.contains(".") ==false)
		{
			if(fullClassName.contains("<"))
				return fullClassName.substring(0, fullClassName.indexOf("<"));
			else {
				return fullClassName;
			}
		}
		return fullClassName.substring(fullClassName.lastIndexOf(".") +1);
	}
}
