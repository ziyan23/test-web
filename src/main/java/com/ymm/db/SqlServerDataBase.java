package com.ymm.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;


/**
 * 数据库操作
 * 
 * @author jared.gu
 *
 */
public class SqlServerDataBase {
	private String url;
	private String env;

	private String userName;
	private String password;

	private String driver;

	private Connection conn;
	private PreparedStatement statement;
	private ResultSet rs;
	private ResultSetMetaData data;

	public SqlServerDataBase() {
		this.setEnv();
		this.setConfig();
	}

	/**
	 * 处理返回结果单条的查询语句
	 * 
	 * @param sql
	 *            具体的sql语句
	 * @return 返回的是一个key-value的键值对。 map形式的。key为 列名，value为具体的列值
	 */
	public Map<String, String> query(String sql) {
		beforeHandleResult(sql);

		Map<String, String> result = new HashMap<String, String>();

		try {
			while (this.rs.next()) {
				data = rs.getMetaData();
				for (int j = 1; j <= data.getColumnCount(); j++) {
					result.put(data.getColumnName(j), rs.getString(j));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
//			System.out.println("获取数据表错误！");
		}

		this.closeConnection();
		return result;
	}

	/**
	 * 查询多条记录
	 * 
	 * @param sql
	 *            具体的sql语句
	 * @return 返回的是一个list表。 这个表里的数据排列是按照查询结果得到的。 Map包含每一列
	 */
	public List<Map<String, String>> queryList(String sql) {
		beforeHandleResult(sql);

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		try {
			while (rs.next()) {
				data = rs.getMetaData();
				Map<String, String> temp = new HashMap<String, String>();
				for (int j = 1; j <= data.getColumnCount(); j++) {
					temp.put(data.getColumnName(j), rs.getString(j));
				}
				result.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
//			System.out.println("获取数据表错误！");
		}

		this.closeConnection();

		return result;
	}

	/**
	 * 处理resultSet之前做的事
	 * 
	 * @param sql
	 */
	public void beforeHandleResult(String sql) {
		this.getconnection();
		// 记录数据库表信息
		try {
			statement = conn.prepareStatement(sql);
			this.rs = statement.executeQuery();
		} catch (SQLException e) {
//			System.out.println("生产statement发生错误!");
			e.printStackTrace();
		}
	}

	/**
	 * 根据方法读取当前case运行的测试环境
	 * 
	 * @return
	 */
	public void setEnv() {
		Properties properties = new Properties();
		InputStream in;
		try {
			in = this.getClass().getClassLoader().getResourceAsStream("testConfig.properties");
			properties.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		this.env = properties.getProperty("env", "beta");
	}

	public void setConfig() {
		Properties properties = new Properties();
		InputStream in;
		try {
			in = this.getClass().getClassLoader().getResourceAsStream("dataBase.properties");
			properties.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		this.userName = properties.getProperty("sqlserver-username", "aspnet_dianping");
		this.password = properties.getProperty("sqlserver-password", "dp!@OpQW34bn");
		this.driver = properties.getProperty("sqlserver-driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String urlKey = this.env + "-sqlserver-url";
		this.url = properties.getProperty(urlKey);
	}

	public void getconnection() {
		try {
			Class.forName(driver);
			this.conn = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void closeConnection() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}