package cn.edu.fudan.se.web.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Element implements Serializable{
	public static  String[] USELESS_PACKAGENAME = new String[]{"org.homeunix.thecave.", "org.gjt.sp."};
	/**
	 * 
	 */
	private static final long serialVersionUID = -5819012025958488827L;
	String id;
	String methodname;
	String packageposition;
	String tag;

	String methodsimplebody;
	String methodbody;
	String projectName;
	String tokens;
	String shortMethodName;
	String methodInvocationLink;
	String shortestMethodName;
	public String getShortestMethodName()
	{
		return shortestMethodName;
	}



	public void setShortestMethodName(String shortestMethodName)
	{
		this.shortestMethodName = shortestMethodName;
	}




	List<Element> neiborElements = new ArrayList<Element>();
	List<Element> accessSameVariableElements = new ArrayList<Element>();
	String accessSameVariableMethods;
	
	
	
	public String getAccessSameVariableMethods()
	{
		return accessSameVariableMethods;
	}



	public void setAccessSameVariableMethods(String accessSameVariableMethods)
	{
		this.accessSameVariableMethods = accessSameVariableMethods;
	}



	public void addAccessSameVariableElement(Element element)
	{
		accessSameVariableElements.add(element);
	}



	public List<Element> getAccessSameVariableElements()
	{
		return accessSameVariableElements;
	}



	public List<Element> getNeiborElements()
	{
		return neiborElements;
	}



	public void addNeiborElement(Element neiborElement)
	{
		neiborElements.add(neiborElement);
	}



	public String getMethodInvocationLink()
	{
		return methodInvocationLink;
	}



	public void setMethodInvocationLink(String methodInvocationLink)
	{
		this.methodInvocationLink = methodInvocationLink;
	}



	public String getShortMethodName()
	{
		return shortMethodName;
	}



	public String getTokens()
	{
		return tokens;
	}
	


	public void setTokens(String tokens)
	{
		this.tokens = tokens;
	}




	List<String> callmethods;
	List<String> calledBymethods;
	List<String> accessfields;
	Set<String> callClassList;
	Set<String> calledClassList;
	String className;

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public Element()
	{
		calledBymethods = new ArrayList<String>();
		callmethods = new ArrayList<String>();
		accessfields = new ArrayList<String>();
		callClassList = new HashSet<String>();
		calledClassList = new HashSet<String>();
		
	}
	
	public void addCallClass(String callClass)
	{
		callClassList.add(callClass);
	}
	
	public void addCalledClass(String calledClass)
	{
		calledClassList.add(calledClass);
	}
	
	public Set<String> getCallClassList()
	{
		return callClassList;
	}

	public Set<String> getCalledClassList()
	{
		return calledClassList;
	}



	
	public void addAccessField(String accessField)
	{
		accessfields.add(accessField);
	}
	public void addCalledByMethod(String calledbyMethod)
	{
		calledBymethods.add(calledbyMethod);
	}
	public void addCallMethod(String callMethod)
	{
		callmethods.add(callMethod);
	}	
	/**
	 * @return the accessfields
	 */
	public List<String> getAccessfields() {
		return accessfields;
	}
	

	
	public List<String> getCalledBymethods()
	{
		return calledBymethods;
	}
	
	public List<String> getCallmethods()
	{
		return callmethods;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the methodbody
	 */
	public String getMethodbody() {
		return methodbody;
	}
	/**
	 * @return the methodname
	 */
	public String getMethodname() {
		
		return methodname;
	}
	

	private String methodname_ = null;
	public String getMethodname_() {
		if(methodname_==null){
			if(methodname.contains("/")){
				methodname_=methodname.replace("/", ".");
			}else{
				methodname_=methodname;
			}
			if(methodname_.contains("{"))
				methodname_=methodname_.replace("{", ".");
		}
		return methodname_;
	}
	/**
	 * @return the methodsimplebody
	 */
	public String getMethodsimplebody() {
		return methodsimplebody;
	}
	/**
	 * @return the packageposition
	 */
	public String getPackageposition() {
		return packageposition;
	}
	public String getProjectName()
	{
		return projectName;
	}
	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}
	/**
	 * @param accessfields the accessfields to set
	 */
	public void setAccessfields(List<String> accessfields) {
		this.accessfields = accessfields;
	}

	public void setCalledBymethods(List<String> calledBymethods)
	{
		this.calledBymethods = calledBymethods;
	}
	public void setCallmethods(List<String> callmethods)
	{
		this.callmethods = callmethods;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param methodbody the methodbody to set
	 */
	public void setMethodbody(String methodbody) {
		this.methodbody = methodbody;
	}
	/**
	 * @param methodname the methodname to set
	 */
	public void setMethodname(String methodname) {
		this.methodname = methodname;
		shortMethodName = new String(methodname);
		for (String str : USELESS_PACKAGENAME)
		{
			shortMethodName = shortMethodName.replace(str, "");
		}
		
		
	}
	/**
	 * @param methodsimplebody the methodsimplebody to set
	 */
	public void setMethodsimplebody(String methodsimplebody) {
		this.methodsimplebody = methodsimplebody;
	}
	/**
	 * @param packageposition the packageposition to set
	 */
	public void setPackageposition(String packageposition) {
		this.packageposition = packageposition;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}


}
