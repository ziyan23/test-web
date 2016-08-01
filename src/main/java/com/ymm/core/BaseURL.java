package com.ymm.core;

/**
 * @author jared.gu
 */
public class BaseURL {

	public String env;

	public BaseURL() {
		this.env = Common.setTestConfigFromPropertyFile();
	}

}
