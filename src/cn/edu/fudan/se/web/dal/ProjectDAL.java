package cn.edu.fudan.se.web.dal;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.se.data.util.DataWatcher;
import cn.edu.fudan.se.data.util.SearchSwicher;
import cn.edu.fudan.se.web.bean.Class;
import cn.edu.fudan.se.web.bean.CloneClass;
import cn.edu.fudan.se.web.bean.Feature;
import cn.edu.fudan.se.web.bean.Method;
import cn.edu.fudan.se.web.bean.MethodInvocation;
import cn.edu.fudan.se.web.bean.MethodInvocationLink;
import cn.edu.fudan.se.web.bean.NeiborMethod;
import cn.edu.fudan.se.web.bean.TokenInMethod;
import cn.edu.fudan.se.web.bean.Variable;
import cn.edu.fudan.se.web.service.GetDataService;
import cn.edu.fudan.se.web.util.CommUtil;

public class ProjectDAL {

	private List<String> projectList;
	private List<Method> methodList;
	private List<MethodInvocation> methodInvocationList;

	private List<Variable> variableList;
	private List<Class> classList;
	private List<TokenInMethod> tokenInMethodList;
	private List<MethodInvocationLink> methodInvocationLinkList;
	private List<CloneClass> cloneClassList;
	private List<NeiborMethod> neiborMethods;

	private String currentProjectName;
	private static ProjectDAL instance;

	public List<MethodInvocationLink> getMethodInvocationLinkList() {
		return methodInvocationLinkList;
	}

	public List<Method> getMethodList() {
		return methodList;
	}

	/**
	 * Use singleton to avoid load database several times.
	 * 
	 * @param projectName
	 * @return
	 */
	public synchronized static ProjectDAL getInstance() {
		if (instance == null)
			instance = new ProjectDAL();
		return instance;
	}

	public List<CloneClass> getCloneClassList() {
		return cloneClassList;
	}

	public List<MethodInvocation> getMethodInvocationList() {
		return methodInvocationList;
	}

	public List<Variable> getVariableList() {
		return variableList;
	}

	public ProjectDAL(int type){
		if((type&GetDataService.INIT_PROJECT_LIST)>0){
			DBHelper.openConn();
			initialProjectList();
			DBHelper.closeConn();
		}
	}
	
	private ProjectDAL() {
		try {
			throw new Exception();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBHelper.openConn();
		initialProjectList();
		initialMethodList();
		initialMethodInvocationList();
		initialVariableList();
		initialMethodInvocationLinkList();
		initialClassList();
		initialTokenInMethodList();
		initialCloneClassList();
		initialNeiborMethodList();
		DBHelper.closeConn();
	}

	public ProjectDAL(int tags, String iniPath, DataWatcher d){
		DBHelper.openConn(iniPath);
		initialProjectList();
		_initialMethodList(d);
		DBHelper.closeConn();
	}
	
	private void initialNeiborMethodList() {
		neiborMethods = new ArrayList<NeiborMethod>();

		String sql = "SELECT name, associatedMethod FROM " + DBHelper.NEIBOR;
		ResultSet rs = DBHelper.executeQuery(sql);
		try {
			while (rs.next()) {
				NeiborMethod neiborMethod = new NeiborMethod();
				neiborMethod.setName(rs.getString(1));
				neiborMethod.setNeibors(rs.getString(2));
				neiborMethods.add(neiborMethod);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<NeiborMethod> getNeiborMethods() {
		return neiborMethods;
	}

	private void initialMethodInvocationLinkList() {
		methodInvocationLinkList = new ArrayList<MethodInvocationLink>();
		for (String projectName : projectList) {
			String sql = "SELECT name, invocationlink, linklength FROM "
					+ DBHelper.METHOD_INVOCATION_LINK
					+ " where projectName = '" + projectName + "' ";
			ResultSet rs = DBHelper.executeQuery(sql);
			try {
				while (rs.next()) {
					MethodInvocationLink methodInvocationLink = new MethodInvocationLink(
							rs.getInt(3), rs.getString(1));
					methodInvocationLink.setInvocationLink(rs.getString(2));
					methodInvocationLink.setProjectName(projectName);
					methodInvocationLinkList.add(methodInvocationLink);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void initialCloneClassList() {
		cloneClassList = new ArrayList<CloneClass>();

		String sql = "SELECT methodName, class FROM " + DBHelper.CLONE;
		ResultSet rs = DBHelper.executeQuery(sql);
		try {
			while (rs.next()) {
				CloneClass cloneClass = new CloneClass();
				cloneClass.setMethodName(rs.getString(1));
				cloneClass.setCloneClassName(rs.getString(2));
				cloneClassList.add(cloneClass);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}

	private void initialTokenInMethodList() {
		tokenInMethodList = new ArrayList<TokenInMethod>();
		for (String projectName : projectList) {
			String sql = "SELECT methodName, tokens, stem FROM "
					+ DBHelper.TOKEN_IN_METHOD + " where projectName = '"
					+ projectName + "' ";
			ResultSet rs = DBHelper.executeQuery(sql);
			try {
				while (rs.next()) {
					TokenInMethod tokenInMethod = new TokenInMethod();
					tokenInMethod.setMethodName(rs.getString(1));
					List<String> tokens = CommUtil
							.stringToList(rs.getString(2));
					tokens = CommUtil.removeRepeatWords(tokens);
					String str = CommUtil.ListToString(tokens);
					tokenInMethod.setTokens(str);
					tokenInMethod.setStem(rs.getString(3));
					tokenInMethod.setProjectName(projectName);
					tokenInMethodList.add(tokenInMethod);
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}

	}
	
	/**
	 * Get the token information from DB according to method and project name.
	 * @param methodName
	 * @param projectName
	 * @return
	 */
	public static TokenInMethod getTokenInMethod(String methodName, String projectName) {
		String sql = "SELECT tokens, stem FROM "
				+ DBHelper.TOKEN_IN_METHOD + " where projectName = '"
				+ projectName + "' and methodName='"+methodName+"'";
		ResultSet rs = DBHelper.executeQuery(sql);
		TokenInMethod tokenInMethod = null;
		try {
			while (rs.next()) {
				tokenInMethod = new TokenInMethod();
				tokenInMethod.setMethodName(methodName);
				List<String> tokens = CommUtil
						.stringToList(rs.getString(1));
				tokens = CommUtil.removeRepeatWords(tokens);
				String str = CommUtil.ListToString(tokens);
				tokenInMethod.setTokens(str);
				tokenInMethod.setStem(rs.getString(2));
				tokenInMethod.setProjectName(projectName);
			}
			rs.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
		return tokenInMethod;
	}

	public List<TokenInMethod> getTokenInMethodList() {
		return tokenInMethodList;
	}

	/*
	 * private void initialContextClusterList() { contextClusterList = new
	 * ArrayList<MethodCluster>(); for (String projectName : projectList) {
	 * String sql =
	 * "SELECT clustertype, methodList,  tagCloudSrc,name,initialClusterName  "
	 * + " FROM " + DBHelper.CONTEXT_CLUSTER_TABLE_NAME +
	 * " where projectName = '" + projectName + "'"; ResultSet rs =
	 * DBHelper.executeQuery(sql); try { while (rs.next()) { MethodCluster
	 * cluster = new MethodCluster(); cluster.setType(rs.getString(1));
	 * cluster.setMethodList(CommUtil.stringToList(rs.getString(2)));
	 * cluster.setTagCloudSrc(rs.getString(3));
	 * cluster.setName(rs.getString(4));
	 * cluster.setInitialClusterName(rs.getString(5));
	 * cluster.setProjectName(projectName); contextClusterList.add(cluster); } }
	 * catch (Exception exp) { exp.printStackTrace(); } }
	 * 
	 * }
	 */

	/*
	 * private void initialMethodClusterList() { methodClusterList = new
	 * ArrayList<MethodCluster>(); for (String projectName : projectList) {
	 * String sql = "SELECT clustertype, methodList,  tagCloudSrc,name  " +
	 * " FROM " + DBHelper.CLUSTER_TABLE_NAME + " where projectName = '" +
	 * projectName + "'"; ResultSet rs = DBHelper.executeQuery(sql); try { while
	 * (rs.next()) { MethodCluster cluster = new MethodCluster();
	 * cluster.setType(rs.getString(1));
	 * cluster.setMethodList(CommUtil.stringToList(rs.getString(2)));
	 * cluster.setTagCloudSrc(rs.getString(3));
	 * cluster.setName(rs.getString(4)); cluster.setProjectName(projectName);
	 * methodClusterList.add(cluster); } } catch (Exception exp) {
	 * exp.printStackTrace(); } } }
	 */

	private void initialVariableList() {
		variableList = new ArrayList<Variable>();
		for (String projectName : projectList) {
			String sql = "SELECT name, datatype, belongedMethod, icount  "
					+ " FROM " + DBHelper.VARIABLE_TABLE_NAME
					+ " where projectName = '" + projectName + "'";
			ResultSet rs = DBHelper.executeQuery(sql);
			try {
				while (rs.next()) {
					Variable variable = new Variable();
					variable.setName(rs.getString(1));
					variable.setDataType(rs.getString(2));
					variable.setBelongMethod(rs.getString(3));
					variable.setCount(rs.getInt(4));
					variableList.add(variable);
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}

	}

	private void initialMethodInvocationList() {
		methodInvocationList = new ArrayList<MethodInvocation>();
		for (String projectName : projectList) {
			String sql = "SELECT invocateVariant, callMethodName, calledMethodName "
					+ " FROM "
					+ DBHelper.METHODINVOCATION_TABLE_NAME
					+ " where projectName = '" + projectName + "'";
			ResultSet rs = DBHelper.executeQuery(sql);
			try {
				while (rs.next()) {
					MethodInvocation methodInvocation = new MethodInvocation();
					methodInvocation.setInvocateVariant(rs.getString(1));
					methodInvocation.setCallMethodName(rs.getString(2));
					methodInvocation.setCalledMethodName(rs.getString(3));
					methodInvocationList.add(methodInvocation);
				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}

	}

	public List<Class> getClassList() {
		return classList;
	}

	private void initialClassList() {
		classList = new ArrayList<Class>();
		for (String projectName : projectList) {
			String sql = "SELECT name, isinterface, issuperclass,  superclass, interfaces, modifies FROM classinfo where projectName = '"
					+ projectName + "' and isanonymous = 0  order by name";
			ResultSet rs = DBHelper.executeQuery(sql);

			try {
				while (rs.next()) {
					Class newClass = new Class();
					newClass.setName(rs.getString(1));

					newClass.setInterface(rs.getBoolean(2));
					newClass.setSuperClass(rs.getBoolean(3));
					newClass.setSuperClass(rs.getString(4));
					newClass.setInterfaces(rs.getString(5));
					newClass.setModifier(rs.getString(6));
					newClass.setProjectName(projectName);
					classList.add(newClass);

				}
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}

	public String getCurrentProjectName() {
		return currentProjectName;
	}

	private void initialProjectList() {
		projectList = new ArrayList<String>();
		String sql = "SELECT distinct(projectName)  FROM "
				+ DBHelper.CLASS_TABLE_NAME;
		ResultSet rs = DBHelper.executeQuery(sql);
		try {
			while (rs.next()) {
				projectList.add(rs.getString(1));
			}

		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}

	public List<Feature> getFeatures(int minElementCount) {
		DBHelper.openConn();
		List<Feature> result = new ArrayList<Feature>();
		String sql = "select id,relevantElements, elementCount from "
				+ DBHelper.FEATURE + " where elementCount >= "
				+ minElementCount;
		ResultSet rs = DBHelper.executeQuery(sql);
		try {
			while (rs.next()) {
				Feature feature = new Feature();
				feature.setID(rs.getString(1));
				feature.setRelevantElements(rs.getString(2));
				feature.setRelevantElementCount(rs.getInt(3));
				result.add(feature);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		DBHelper.closeConn();
		return result;
	}
	
	private void _initialMethodList(DataWatcher d) {
		for (String projectName : projectList) {
			String sql = "SELECT name,className,returnType,modifies,throwExceptions,isConstructor,content,PNGwordcloud,tags, accessSameVariableMethods  FROM "
					+ DBHelper.METHOD_TABLE_NAME
					+ "  where projectName ='"
					+ projectName + "'";
			ResultSet rs = DBHelper.executeQuery(sql);
			try {
				while (rs.next()) {
					Method newMethod = new Method();
					newMethod.setName(rs.getString(1));
					newMethod.setClassName(rs.getString(2));
					newMethod.setReturnType(rs.getString(3));
					newMethod.setModifier(rs.getString(4));
					newMethod.setExceptions(rs.getString(5));
					newMethod.setConstructor(rs.getBoolean(6));
					newMethod.setContent(rs.getString(7));
					newMethod.setTagCloud(rs.getBytes(8));
					newMethod.setTags(rs.getString(9));
					newMethod.setAccessSameVariableMethods(rs.getString(10));
					newMethod.setProjectName(projectName);
					d.watch(newMethod);
				}
				
				d.watched();

			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}

	}

	private void initialMethodList() {
		methodList = new ArrayList<Method>();
		for (String projectName : projectList) {
			String sql = "SELECT name,className,returnType,modifies,throwExceptions,isConstructor,content,PNGwordcloud,tags, accessSameVariableMethods  FROM "
					+ DBHelper.METHOD_TABLE_NAME
					+ "  where projectName ='"
					+ projectName + "'";
			ResultSet rs = DBHelper.executeQuery(sql);
			try {
				while (rs.next()) {
					Method newMethod = new Method();
					newMethod.setName(rs.getString(1));
					newMethod.setClassName(rs.getString(2));
					newMethod.setReturnType(rs.getString(3));
					newMethod.setModifier(rs.getString(4));
					newMethod.setExceptions(rs.getString(5));
					newMethod.setConstructor(rs.getBoolean(6));
					newMethod.setContent(rs.getString(7));
					newMethod.setTagCloud(rs.getBytes(8));
					newMethod.setTags(rs.getString(9));
					newMethod.setAccessSameVariableMethods(rs.getString(10));
					newMethod.setProjectName(projectName);
					methodList.add(newMethod);
				}

			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}

	}

	public List<String> getProjectList() {
		return projectList;
	}

	public void setCurrentProjectName(String currentProjectName) {
		this.currentProjectName = currentProjectName;
	}

	public Method getMethodByName(String methodname) {
		for (Method method : methodList) {
			if (method.getName().equals(methodname))
				return method;
		}
		return null;
	}

	public static List<String> getCallMehtod(String name, String projectName) {
		String sql = "SELECT calledMethodName "
				+ " FROM "
				+ DBHelper.METHODINVOCATION_TABLE_NAME
				+ " where projectName = '" + projectName + 
				"' and callMethodName='"+name+"'";
		ResultSet rs = DBHelper.executeQuery(sql);
		try {
			List<String> ret = new ArrayList<String>();
			while (rs.next()) {
				ret.add(rs.getString(1));
			}
			
			rs.close();
			return ret;
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return null;
	}
	
	public static List<String> getCalledMehtod(String name, String projectName) {
		String sql = "SELECT callMethodName "
				+ " FROM "
				+ DBHelper.METHODINVOCATION_TABLE_NAME
				+ " where projectName = '" + projectName + 
				"' and calledMethodName='"+name+"'";
		ResultSet rs = DBHelper.executeQuery(sql);
		try {
			List<String> ret = new ArrayList<String>();
			while (rs.next()) {
				ret.add(rs.getString(1));
			}
			rs.close();
			return ret;
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return null;
	}

}
