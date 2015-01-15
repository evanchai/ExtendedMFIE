package cn.edu.fudan.se.web.dal;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.edu.fudan.se.data.util.SearchSwicher;
import cn.edu.fudan.se.web.util.CommUtil;
import cn.edu.fudan.se.web.util.INIHelper;

public class DBHelper {
	private static Connection conn;
	private static Statement stmt;
	private static String projectName;

	public static Connection getConn() {
		return conn;
	}

	private static void initialDB() {

	}
	
	public static Connection openConn(String iniPath){
		
		INIHelper iniHelper = new INIHelper(iniPath);
		String DB_FILENAME = "WebRoot/"
			+ iniHelper.getValue("DB", "DB_FILENAME", "");

		return openConn(iniHelper, DB_FILENAME);
	}
	
	public static Connection openConn(){
		String path = CommUtil.getCurrentProjectPath()+ "/conf.ini";
		if(path.contains("null")){
			path = SearchSwicher.initFilePath;
		}
		INIHelper iniHelper = new INIHelper(path
				);
		String DB_FILENAME = CommUtil.getCurrentProjectPath() + "/"
			+ iniHelper.getValue("DB", "DB_FILENAME", "");
		return openConn(iniHelper, DB_FILENAME);
	}

	private static Connection openConn(INIHelper ini, String DB_FILENAME) {
		INIHelper iniHelper = ini;
		String JDBC_DRIVER = iniHelper.getValue("DB", "JDBC_DRIVER", "");
		String DB_URL = iniHelper.getValue("DB", "DB_URL", "");
		
		try {
			File file = new File(DB_FILENAME);
			if (file.exists() == false)
				file.createNewFile();
			@SuppressWarnings("unchecked")
			Class<Driver> jdbcDriver = (Class<Driver>) Class
					.forName(JDBC_DRIVER);
			DriverManager.registerDriver(jdbcDriver.newInstance());
			conn = DriverManager.getConnection(DB_URL + DB_FILENAME);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			initialDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	
	/**
	 * A integrated version of openConn.
	 * by Luminosite.
	 * 
	 * @return
	 */
	public static Connection getConnection(){
		INIHelper iniHelper = new INIHelper(CommUtil.getCurrentProjectPath()
				+ "/conf.ini");
		String DB_FILENAME = CommUtil.getCurrentProjectPath() + "/"
			+ iniHelper.getValue("DB", "DB_FILENAME", "");
		String JDBC_DRIVER = iniHelper.getValue("DB", "JDBC_DRIVER", "");
		String DB_URL = iniHelper.getValue("DB", "DB_URL", "");
		Connection connection = null;
		
		try {
			File file = new File(DB_FILENAME);
			if (file.exists() == false)
				file.createNewFile();
			@SuppressWarnings("unchecked")
			Class<Driver> jdbcDriver = (Class<Driver>) Class
					.forName(JDBC_DRIVER);
			DriverManager.registerDriver(jdbcDriver.newInstance());
			connection = DriverManager.getConnection(DB_URL + DB_FILENAME);
//			connection.setAutoCommit(true);
			initialDB();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return connection;
	}

	public static void closeConn() {
		try {
			stmt.close();
			conn.commit();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void executeUpdate(String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}

	/**
	 * Before the invocation of this method, there must be a invocation of DBHelper.openConn() 
	 * and after it, there must be a invocation of DBHelper.closeConn(). If not, this setting
	 * feature will be passed to the caller of this method.
	 * 
	 * @param sql
	 * @return
	 */
	public static ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {

			rs = stmt.executeQuery(sql);
		} catch (Exception exp) {
			exp.printStackTrace();
		}

		return rs;
	}

	public static String getCurrentProjectName() {
		return projectName;
	}

	public static void setCurrentProjectName(String _projectName) {
		projectName = _projectName;
	}

	public static final String CLASS_TABLE_NAME = "classinfo";
	public static final String METHOD_TABLE_NAME = "methodinfo";
	public static final String FIELD_TABLE_NAME = "fieldinfo";
	public static final String CLASSRELATION_TABLE_NAME = "classRelationinfo";
	public static final String METHODINVOCATION_TABLE_NAME = "methodinvocationinfo";
	public static final String VARIABLE_TABLE_NAME = "variableinfo";
	public static final String TOKEN_TABLE_NAME = "tokeninfo";
	public static final String PROJECT_LANGUAGE = "projectLanguage";
	public static final String DOCUMENT_TABLE_NAME = "document";
	public static final String WORD_TABLE_NAME = "word";
	public static final String TOPIC_DOCUMENT_TABLE_NAME = "topicToDocument";
	public static final String TOPIC_WORDS_TABLE_NAME = "topicToWord";
	public static final String STEM_TABLE_NAME = "stems";
	public static final String TOKEN_IN_METHOD = "tokenInMethod";
	public static final String CLUSTER_TABLE_NAME = "cluster";
	public static final String CONTEXT_CLUSTER_TABLE_NAME = "contextcluster";
	public static final String METHOD_INVOCATION_LINK = "methodInvocationLink";
	public static final String CLONE = "cloneInfo";
	public static final String NEIBOR = "associatedMethod";
	public static final String FEATURE = "feature";

}
