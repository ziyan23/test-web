package com.ymm.core;

import com.ymm.http.HttpClient;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author jared.gu
 *
 * HTML解析类(by Jsoup)
 *
 */
public class HTMLDecoder {

	/**
	 * 
	 * 通过CSS选取式提取相应正文中的符合的一组表达式元素节点
	 * 
	 * @param httpResponse
	 * @param selector
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static Elements findElementsByCSS(CloseableHttpResponse httpResponse, String selector)
			throws ParseException, IOException {
		Document doc = Jsoup.parse(HttpClient.toString(httpResponse));
		return doc.select(selector);
	}
	
	/**
	 * 
	 * 通过CSS选取式提取相应正文中的符合的第一个表达式元素节点
	 * 
	 * @param httpResponse
	 * @param selector
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static Element findElementByCSS(CloseableHttpResponse httpResponse, String selector)
			throws ParseException, IOException {
		Document doc = Jsoup.parse(HttpClient.toString(httpResponse));
		return doc.select(selector).get(0);
	}
	
}
