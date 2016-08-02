package com.ymm.auth;

/**
 * @author jared.gu
 */
public enum URLEnum {

	// yunma 对应不同环境的URL
	ALPHASHOPCENTER("http://192.168.199.199:81/"), BETASHOPCENTER(
			"http://192.168.199.120/"), PRODSHOPCENTER("http://www.yunma.com/");

	public final String url;

	URLEnum(String url) {
		this.url = url;
	}


}
