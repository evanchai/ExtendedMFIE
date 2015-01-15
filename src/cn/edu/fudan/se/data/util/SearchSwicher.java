package cn.edu.fudan.se.data.util;

import java.util.List;
import java.util.Set;

import cn.edu.fudan.se.web.bean.Method;
import cn.edu.fudan.se.web.dal.DBHelper;
import cn.edu.fudan.se.web.dal.ProjectDAL;
import cn.edu.fudan.se.web.pojo.Element;
import cn.edu.fudan.se.web.service.GetDataService;
import cn.edu.fudan.se.web.service.ProjectService;
import cn.edu.fudan.se.web.util.CommUtil;

//import cn.edu.fudan.se.web.dal.ProjectDAL;

public class SearchSwicher implements DataWatcher {
	public static String initFilePath = "WebRoot/conf.ini";
	private static String projectName = "projectName";
	private static String methodName = "methodName";
	private static String methodContent = "methodContent";

	public static void main(String[] argv) {
		swiche();
		System.out.println("done");

		
		String[][] k = Indexer.search(Indexer.indexPath,"methodContent","menu",new String[]{"methodName"});
		for(int i=0;i<k.length;i++){
			for(int j=0;j<k[0].length;j++){
				System.out.print(k[i][j]+" ");
			}
			System.out.println();
		}
		for(String[] s:k){
			System.out.println(s[0]);
		}
		System.out.println("done:"+k.length);

		
		//
//		DBHelper.openConn(initFilePath);
//		ProjectService.count = true;
//		
//		long start = System.currentTimeMillis();
//		List<Element> list = (new ProjectService(GetDataService.INIT_PROJECT_LIST)).searchByKeywordsLucene("Highlight", "JEdit");
//		long end = System.currentTimeMillis();
//		
//		System.out.println("All:"+(end-start));
//		DBHelper.closeConn();
//		
//		for(Element e:list){
//			Set<String> l = e.getCallClassList();
//			System.out.print(e.getMethodname()+" % ");
//			
//			for(String s:l)
//				System.out.print(s+" ^ ");
//			System.out.println();
//			
//		}
//		System.out.println("all:"+list.size());
	}

	public static void swiche() {
		new ProjectDAL(1, initFilePath, new SearchSwicher());
		
	}

	private int watchCount = 0;
	private static final int watchTimesInWriting =10000;
	private byte[] storationTags = new byte[]{1,1,1};
	
	@Override
	public void watch(Method method) {
		if(watchCount==0)
			Indexer.prepareIndex(Indexer.indexPath);
		else if(watchCount%watchTimesInWriting==0){
			Indexer.closeIndex();
			Indexer.prepareIndex(Indexer.indexPath);
			System.out.println(watchCount);
		}
		
		/*
		 * 0 project name
		 * 1 method name
		 * 3 content
		 */
		String[][] input = new String[3][2];
		input[0][0] = projectName;
		input[0][1] = method.getProjectName();
		input[1][0] = methodName;
		input[1][1] = method.getName();
		input[2][0] = methodContent;
		input[2][1] = CommUtil.transferContent(method.getContent());
		Indexer.makeIndex(input, storationTags, 3);
		
		watchCount++;
		
	}

	@Override
	public void watched() {
		Indexer.closeIndex();
	}
}
