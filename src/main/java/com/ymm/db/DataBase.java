package com.ymm.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * 完成数据库的操作 ，完成基本操作
 * 
 * @author jared.gu
 *
 */
public class DataBase {
	private String url;
	private String env;

	private String userName;
	private String password;
	private String encode;

	private String driver;

	private Connection conn = null;
	private PreparedStatement statement = null;
	private ResultSet rs = null;
	private ResultSetMetaData data = null;

	/**
	 * 构造函数 初始化各种配置信息
	 */
	public DataBase(String dataBase, String dataBaseName) {
		this.setEnv();
		this.setConfig(dataBase);
		this.url = this.url + dataBaseName + "?" + this.encode;
	}

	/**
	 * 处理返回结果单条的查询语句
	 * 
	 * @param sql
	 *            具体的sql语句
	 * @return 返回的是一个key-value的键值对。 map形式的。key为 列名，value为具体的列值
	 * @throws SQLException
	 */
	public Map<String, Object> query(String sql) {
		beforeHandleResult(sql);

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			while (this.rs.next()) {
				data = rs.getMetaData();
				for (int j = 1; j <= data.getColumnCount(); j++) {
					result.put(data.getColumnLabel(j), rs.getObject(j));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	public List<Map<String, Object>> queryList(String sql) {
		beforeHandleResult(sql);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			while (rs.next()) {
				data = rs.getMetaData();
				Map<String, Object> temp = new HashMap<String, Object>();
				for (int j = 1; j <= data.getColumnCount(); j++) {
					temp.put(data.getColumnLabel(j), rs.getObject(j));
				}
				result.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.closeConnection();

		return result;
	}

	/**
	 * 处理 insert、update、delete an SQL Data Manipulation Language (DML)
	 * statement, such as <code>INSERT</code>, <code>UPDATE</code> or
	 * <code>DELETE</code>
	 * 
	 * @param sql
	 *            具体的sql语句 return 返回自增的ID 如果没有自增的ID 则 返回
	 */
	public int execute(String sql) {
		this.getconnection();
		// 记录数据库表信息
		int flag = 0;
		try {
			statement = conn.prepareStatement(sql);
			flag = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.closeConnection();

		return flag;
	}

	/**
	 * 处理 insert、update、delete an SQL Data Manipulation Language (DML)
	 * statement, such as <code>INSERT</code>, <code>UPDATE</code> or
	 * <code>DELETE</code>
	 * 
	 * @param sql
	 *            具体的sql语句 return 返回自增的ID
	 */
	public int executeAndGetGeneratedKey(String sql) {
		this.getconnection();
		int id = 0;
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet ids = statement.getGeneratedKeys();
			if (ids.next()) {
				id = ids.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.closeConnection();

		return id;
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
			this.rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据方法读取当前case运行的测试环境
	 */
	public void setEnv() {
		Properties properties = new Properties();
		InputStream in;
		try {
			//in = new BufferedInputStream(new FileInputStream("testConfig.properties"));
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			in = loader.getResourceAsStream("testConfig.properties");
			properties.load(in);
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		this.env = properties.getProperty("env", "beta");
	}

	public void setConfig(String dataName) {
		Properties properties = new Properties();
		InputStream in;
		try {
			//in = new BufferedInputStream(new FileInputStream("testConfig.properties"));
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			in = loader.getResourceAsStream("testConfig.properties");
			properties.load(in);
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		this.userName = properties.getProperty("username", "root");
		this.password = properties.getProperty("password", "zq");
		this.encode = properties.getProperty("encode", "characterEncoding=UTF8");
		this.driver = properties.getProperty("driver", "com.mysql.jdbc.Driver");
		String urlKey = this.env + "-" + dataName + "-url";
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

	public void setBaseURL(String url) {
		this.url = url;
	}

	/**
	 * 供外界获得env属性值
	 */
	public String getEnv() {
		return env;
	}
}