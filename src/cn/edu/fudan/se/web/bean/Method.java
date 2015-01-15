package cn.edu.fudan.se.web.bean;


public class Method extends ASTBasicDataType {

	private String className = "";
	
	private String exceptions = "";
	private boolean isConstructor = false;
	private boolean isDestructor = false;
	private String returnType = "";
	private String projectName="";
	private String tags = "";
	private String accessSameVariableMethods ;
	
	public String getAccessSameVariableMethods()
	{
		return accessSameVariableMethods;
	}

	public void setAccessSameVariableMethods(String accessSameVariableMethods)
	{
		this.accessSameVariableMethods = accessSameVariableMethods;
	}

	public String getTags()
	{
		return tags;
	}

	public void setTags(String tags)
	{
		this.tags = tags;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	private String content="";
	private byte[] tagCloud;

	private String id = null;

	/**
	 * @return the tagCloud
	 */
	public byte[] getTagCloud()
	{
		return tagCloud;
	}

	/**
	 * @param tagCloud the tagCloud to set
	 */
	public void setTagCloud(byte[] tagCloud)
	{
		this.tagCloud = tagCloud;
	}

	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	

	/**
	 * @param name
	 * @param modifier
	 */
	public Method(String name, String modifier) {
		super(name, modifier);
	}

	/**
	 * 
	 */
	public Method() {
		super();
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}



	/**
	 * @return the exceptions
	 */
	public String getExceptions() {
		return exceptions;
	}

	/**
	 * @param exceptions
	 *            the exceptions to set
	 */
	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}

	/**
	 * @return the isConstructor
	 */
	public boolean isConstructor() {
		return isConstructor;
	}

	/**
	 * @param isConstructor
	 *            the isConstructor to set
	 */
	public void setConstructor(boolean isConstructor) {
		this.isConstructor = isConstructor;
	}

	/**
	 * @return the isDestructor
	 */
	public boolean isDestructor() {
		return isDestructor;
	}

	/**
	 * @param isDestructor
	 *            the isDestructor to set
	 */
	public void setDestructor(boolean isDestructor) {
		this.isDestructor = isDestructor;
	}

	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType
	 *            the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	

	public String toString() {
		String result = "";
		result += "\n[Method Declaration Start]";
		result += "\t\nClass Name: \t" + this.getClassName();
		result += "\t\nMethod Name: \t" + this.getName();
		
		result += "\t\nModifiers: \t" + this.getModifier();
		result += "\t\nReturn Type: \t" + this.getReturnType();
		result += "\t\nIs Constructor?\t" + this.isConstructor();
		result += "\t\nIs Destructor?\t" + this.isDestructor();
		result += "\t\nExceptions: \t" + this.getExceptions();
		
		result += "\t\n[Method Declaration End]\n\n";
		return result;
	}

	public void setId(String string) {
		// TODO Auto-generated method stub
		id  = string;
	}

	public String getId(){
		return id;
	}

}
