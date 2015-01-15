package cn.edu.fudan.se.web.bean;


public class Variable extends ASTBasicDataType {

	private String dataType = "";
	private String belongMethod = "";
	private int count;

	/**
	 * @return the count
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count)
	{
		this.count = count;
	}


	/**
	 * 
	 */
	public Variable() {
		super();
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the belongMethod
	 */
	public String getBelongMethod() {
		return belongMethod;
	}

	/**
	 * @param belongMethod
	 *            the belongMethod to set
	 */
	public void setBelongMethod(String belongMethod) {
		this.belongMethod = belongMethod;
	}




	public String toString() {
		String result = "";
		result += "\n[Variable Declaration Start]";
		result += "\t\nModifier: \t" + this.getModifier();
		result += "\t\nMethod Name: \t" + this.getBelongMethod();
		result += "\t\nVariable Name: \t" + this.getName();
		result += "\t\nData Type: \t" + this.getDataType();
		result += "\t\n[Variable Declaration End]\n\n";
		return result;
	}
}
