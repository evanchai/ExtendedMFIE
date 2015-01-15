package cn.edu.fudan.se.web.bean;

public class ResultItem {
	public String name;
	public String onClick;
	public static String RESULT_LIST = "selectResultList";
	
	public ResultItem(String n, String o){
		name = n;
		onClick = o;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOnClick() {
		return onClick;
	}
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
}
