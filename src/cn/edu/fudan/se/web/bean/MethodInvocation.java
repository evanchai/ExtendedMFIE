package cn.edu.fudan.se.web.bean;

public class MethodInvocation {


	
	
	//lihongwei 2012-3-14 add  for new ast.db test 
	//invocateVariant, callMethodName,calledMethodName
	private String invocateVariant="";
	private String callMethodName="";
	private String calledMethodName="";
	
	
	public MethodInvocation(String invocateVariant, String callMethodName, String calledMethodName) {
		this.invocateVariant=invocateVariant;
		this.callMethodName=callMethodName;
		this.calledMethodName = calledMethodName;
		
	}


	
	
	public String getInvocateVariant()
	{
		return invocateVariant;
	}

	public void setInvocateVariant(String invocateVariant)
	{
		this.invocateVariant = invocateVariant;
	}

	public String getCallMethodName()
	{
		return callMethodName;
	}

	public void setCallMethodName(String callMethodName)
	{
		this.callMethodName = callMethodName;
	}

	public String getCalledMethodName()
	{
		return calledMethodName;
	}

	public void setCalledMethodName(String calledMethodName)
	{
		this.calledMethodName = calledMethodName;
	}

	
//above  add by 2012-03-14  	
	


	/**
	 * 
	 */
	public MethodInvocation() {
		super();
	}


	
	public String toString() {
		String result = "";
		result += "\n[Method Invocation Start]";
		result+= "\t\nInvocateVariant: \t" +this.invocateVariant;
		result+= "\t\nCallMethodName: \t" +this.callMethodName;
		result+= "\t\ncalledMethodName: \t" +this.calledMethodName;
		result += "\t\n[Method Invocation End]\n\n";
		return result;
	}

}
