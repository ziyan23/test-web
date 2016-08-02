package com.ymm.http;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLInitializationException;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * httpclient操作类
 * 
 * @author jared.gu
 */
public class HttpClient {
	private static final Logger LOG = LogManager.getLogger(HttpClient.class);
	public static CloseableHttpClient httpClient = null;
	public static CloseableHttpClient httpClientWithoutRedirect = null;
	public static HttpClientContext context = null;
	public static CookieStore cookieStore = null;
	public static RequestConfig requestConfig = null;
	public static PoolingHttpClientConnectionManager phccm;

	static {
		init();
	}

	//初始化http请求
	@SuppressWarnings("deprecation")
	private static void init() {
		context = HttpClientContext.create();
		cookieStore = new BasicCookieStore();
		LayeredConnectionSocketFactory ssl = null;
		try {
			ssl = SSLConnectionSocketFactory.getSystemSocketFactory();
		} catch (final SSLInitializationException ex) {
			final SSLContext sslcontext;
			try {
				sslcontext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
				sslcontext.init(null, null, null);
				ssl = new SSLConnectionSocketFactory(sslcontext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// ssl registry
		final Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", ssl != null ? ssl : SSLConnectionSocketFactory.getSocketFactory()).build();

		// 配置HTTPmanager里面路由跳转和激活时间
		phccm = new PoolingHttpClientConnectionManager(sfr);
		phccm.setDefaultMaxPerRoute(100);
		phccm.setMaxTotal(200);
		phccm.setValidateAfterInactivity(10000);
		// 配置超时时间（连接服务端超时1秒，请求数据返回超时2秒）
		requestConfig = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(60000)
				.setConnectionRequestTimeout(60000).setStaleConnectionCheckEnabled(true).build();
		// 设置默认跳转以及存储cookie
		httpClient = HttpClientBuilder.create().setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setRedirectStrategy(new DefaultRedirectStrategy()).setConnectionManager(phccm)
				.setDefaultRequestConfig(requestConfig).setDefaultCookieStore(cookieStore).build();
		// 设置默认跳转以及存储cookie
		httpClientWithoutRedirect = HttpClientBuilder.create().setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setRedirectStrategy(new RedirectStrategy(){
					@Override
					public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
						return false;
					}
					@Override
					public HttpUriRequest getRedirect(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
						return null;
					}
				}).setConnectionManager(phccm)
				.setDefaultRequestConfig(requestConfig).setDefaultCookieStore(cookieStore).build();
	}

	/**
	 * http get
	 * 
	 * @param url
	 * @return response
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		return get(url, true);
	}

	public static CloseableHttpResponse get(String url, boolean withRedirect) throws ClientProtocolException, IOException {
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response;
		if (withRedirect) {
			response = httpClient.execute(httpget, context);
		} else {
			response = httpClientWithoutRedirect.execute(httpget, context);
		}
		try {
			cookieStore = context.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for (Cookie cookie : cookies) {
				LOG.debug("key:" + cookie.getName() + "  value:" + cookie.getValue());
			}
		} finally {
//			if (response != null) {
//				response.close();
//			}
		}
		return response;
	}

	/**
	 * http post
	 * 
	 * @param url
	 * @param parameters
	 *            form表单
	 * @return response
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static CloseableHttpResponse post(String url, String parameters)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = toNameValuePairList(parameters);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		CloseableHttpResponse response = httpClient.execute(httpPost, context);
		//兼容登录接口返回302重定向的逻辑
		if(response.getStatusLine().getStatusCode()==302){
			String Location=response.getFirstHeader("Location").getValue();
			return post(Location,parameters);
		}
		
		try {
			cookieStore = context.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for (Cookie cookie : cookies) {
				LOG.debug("key:" + cookie.getName() + "  value:" + cookie.getValue());
			}
		} finally {
//			if (response != null) {
//				response.close();
//			}
		}
		return response;
	}

	@SuppressWarnings("unused")
	private static List<NameValuePair> toNameValuePairList(String parameters) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		String[] paramList = parameters.split("&");
		for (String parm : paramList) {
			int index = -1;
			for (int i = 0; i < parm.length(); i++) {
				index = parm.indexOf("=");
				break;
			}
			String key = parm.substring(0, index);
			String value = parm.substring(++index, parm.length());
			nvps.add(new BasicNameValuePair(key, value));
		}
		// System.out.println(nvps.toString());
		return nvps;
	}

	/**
	 * 手动增加cookie
	 * 
	 * @param name
	 * @param value
	 * @param domain
	 * @param path
	 */
	public void addCookie(String name, String value, String domain, String path) {
		BasicClientCookie cookie = new BasicClientCookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookieStore.addCookie(cookie);
	}

	/**
	 * 把结果console出来
	 * 
	 * @param httpResponse
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void printResponse(HttpResponse httpResponse) throws ParseException, IOException {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状态
		System.out.println("status:" + httpResponse.getStatusLine());
		System.out.println("headers:");
		HeaderIterator iterator = httpResponse.headerIterator();
		while (iterator.hasNext()) {
			System.out.println("\t" + iterator.next());
		}
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			System.out.println("response length:" + responseString.length());
			System.out.println("response content:" + responseString.replace("\r\n", ""));
		}
		System.out.println(
				"------------------------------------------------------------------------------------------\r\n");
	}

	/**
	 * 把当前cookie从控制台输出出来
	 * 
	 */
	public static void printCookies() {
		System.out.println("headers:");
		cookieStore = context.getCookieStore();
		List<Cookie> cookies = cookieStore.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println("key:" + cookie.getName() + "  value:" + cookie.getValue());
		}
	}

	/**
	 * 检查cookie的键值是否包含传参
	 * 
	 * @param key
	 * @return
	 */
	public static boolean checkCookie(String key) {
		cookieStore = context.getCookieStore();
		List<Cookie> cookies = cookieStore.getCookies();
		boolean res = false;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(key)) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**
	 * 直接把Response内的Entity内容转换成String
	 * 
	 * @param httpResponse
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String toString(CloseableHttpResponse httpResponse) throws ParseException, IOException {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null)
			return EntityUtils.toString(entity);
		else
			return null;
	}


	//post方法无表单
	public static CloseableHttpResponse post0Parameter(String url)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
//		List<NameValuePair> nvps = toNameValuePairList(parameters);
//		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		CloseableHttpResponse response = httpClient.execute(httpPost, context);
		try {
			cookieStore = context.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for (Cookie cookie : cookies) {
				LOG.debug("key:" + cookie.getName() + "  value:" + cookie.getValue());
			}
		} finally {
//			if (response != null) {
//				response.close();
//			}
		}
		return response;
	}

	//post json参数的
	public static CloseableHttpResponse postJson(String url, String parameters)
			throws ClientProtocolException, IOException {

		HttpPost httpPost = new HttpPost(url);
//		String encoderJson = URLEncoder.encode(parameters, "UTF-8");
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		//大后台验证
		httpPost.addHeader("authorization", "Basic YV81Mzo4ZDk2OWVlZjZlY2FkM2MyOWEzYTYyOTI4MGU2ODZjZjBjM2Y1ZDVhODZhZmYzY2ExMjAyMGM5MjNhZGM2Yzky");
		StringEntity se = new StringEntity(parameters);
		se.setContentType("application/json");
		httpPost.setEntity(se);
		CloseableHttpResponse response = httpClient.execute(httpPost, context);

		try {
			cookieStore = context.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for (Cookie cookie : cookies) {
				LOG.debug("key:" + cookie.getName() + "  value:" + cookie.getValue());
			}
		} finally {

		}
		return response;
	}

	//post json auth参数的
	public static CloseableHttpResponse postAuthJson(String url, String parameters,String Auth)
			throws ClientProtocolException, IOException {

		HttpPost httpPost = new HttpPost(url);
//		String encoderJson = URLEncoder.encode(parameters, "UTF-8");
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		httpPost.addHeader("Authorization",Auth);
		StringEntity se = new StringEntity(parameters);
		se.setContentType("application/json");
		httpPost.setEntity(se);
		CloseableHttpResponse response = httpClient.execute(httpPost, context);

		try {
			cookieStore = context.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for (Cookie cookie : cookies) {
				LOG.debug("key:" + cookie.getName() + "  value:" + cookie.getValue());
			}
		} finally {

		}
		return response;
	}

}
