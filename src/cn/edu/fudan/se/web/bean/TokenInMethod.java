package cn.edu.fudan.se.web.bean;


public class TokenInMethod extends ASTBasicDataType
{
	private String methodName = "";
	private String projectName = "";
	private String tokens = "";
	private String stem = "";
	private String tags = "";
	
	/**
	 * @return the tags
	 */
	public String getTags()
	{
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags)
	{
		this.tags = tags;
	}
	public String getMethodName()
	{
		return methodName;
	}
	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}
	public String getProjectName()
	{
		return projectName;
	}
	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}
	public String getTokens()
	{
		return tokens;
	}
	public void setTokens(String tokens)
	{
		this.tokens = tokens;
	}
	public String getStem()
	{
		return stem;
	}
	public void setStem(String stem)
	{
		this.stem = stem;
	}
	

	public TokenInMethod(String methodName, String projectName, String tokens, String stem)
	{
		this.methodName=methodName;
		this.projectName=projectName;
		this.tokens=tokens;
		this.stem=stem;
		
	}
	
	
	public TokenInMethod()
	{
		super();
	}
	
	public String toString() {
		String result = "";
		result += "\n[tokenInMethod Declaration Start]";
		result += "\t\nMethod Name: \t" + this.getMethodName();
		result += "\t\nProject Name: \t" + this.getProjectName();
		result += "\t\ntokens: \t" + this.getTokens();
		result += "\t\nstem: \t" + this.getStem();
		result += "\t\n[tokenInMethod Declaration End]\n\n";
		return result;
	}

	public static void main(String args[]) {
		TokenInMethod tokenInMethod = new TokenInMethod();
		tokenInMethod.setMethodName("cn.edu.fuan.se.ASTUtils.gettest()");
		tokenInMethod.setProjectName("ASTUtils");
		tokenInMethod.setTokens("this is a get test method content");
		tokenInMethod.setStem("this is a get test method content");
		System.out.println(tokenInMethod);
	}

	
}
