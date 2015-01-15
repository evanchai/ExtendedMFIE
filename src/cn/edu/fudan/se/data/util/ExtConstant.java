package cn.edu.fudan.se.data.util;

public class ExtConstant {

	public static final int EXT_MFIE = 1;
	public static final int ORI_MFIE = 0;
	public int EXT_FLAG = EXT_MFIE;
	private static ExtConstant singleton = null;
	
	private ExtConstant(){}
	
	public synchronized static ExtConstant getInstance(){
		if(singleton==null){
			singleton = new ExtConstant();
		}
		return singleton;
	}
	
}
