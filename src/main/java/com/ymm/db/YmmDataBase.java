package com.ymm.db;

/**
 * 处理主库的
 * 
 * @author jared.gu
 * 
 */
public class YmmDataBase extends DataBase {
	/**
	 * 构造函数，确保数据库连接正确
	 */
	public YmmDataBase(String dataBaseName) {
		super("ymm", dataBaseName);
	}
}