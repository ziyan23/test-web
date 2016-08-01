package com.ymm.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON 解析类
 * 
 * @author jared.gu
 */
public class JSONDecoder {

	private static final Logger LOG = LogManager.getLogger(JSONDecoder.class);
	private Map<String, Object> jsonObj;
	private List<Map<String, Object>> jsonArr;
	private String jsonString;
	private Gson gson;

	public JSONDecoder() {
		super();
		this.setJsonArr(new ArrayList<Map<String, Object>>());
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.create();
	}

	public Map<String, Object> getJsonObj() {
		return jsonObj;
	}

	public List<Map<String, Object>> getJsonArr() {
		return jsonArr;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public void setJsonArr(List<Map<String, Object>> jsonArr) {
		this.jsonArr = jsonArr;
	}

	public void setJsonObj(Map<String, Object> jsonObj) {
		this.jsonObj = jsonObj;
	}

	/**
	 * 直接把response转化成Map<String, Object>形式
	 * 
	 * @param httpResponse
	 * @return
	 * @return
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	public JSONDecoder toJsonObj(CloseableHttpResponse httpResponse)
			throws UnsupportedOperationException, IOException {
		HttpEntity entity = httpResponse.getEntity();
		String content = EntityUtils.toString(entity);
		try {
			Map<String, Object> obj = gson.fromJson(content, new TypeToken<Map<String, Object>>() {
			}.getType());
			this.setJsonObj(obj);
		} catch (Exception ex) {
			LOG.error("Failed to parse JSON due to: " + ex);
		}
		return this;
	}

	/**
	 * 直接把jsonStr转化成Map<String, Object>形式
	 * 
	 * @param jsonStr
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	public JSONDecoder toJsonObj(String jsonStr) throws UnsupportedOperationException, IOException {
		try {
			Map<String, Object> obj = gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
			}.getType());
			this.setJsonObj(obj);
		} catch (Exception ex) {
			LOG.error("Failed to parse JSON due to: " + ex);
		}
		return this;
	}

	/**
	 * 直接把String转化成Map<String, Object>形式
	 * 
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	public JSONDecoder toJsonObj() throws UnsupportedOperationException, IOException {
		try {
			Map<String, Object> obj = gson.fromJson(this.jsonString, new TypeToken<Map<String, Object>>() {
			}.getType());
			this.setJsonObj(obj);
		} catch (Exception ex) {
			LOG.error("Failed to parse JSON due to: " + ex);
		}
		return this;
	}

	/**
	 * 把String转化为List<Map<String, Object>>形式
	 * 
	 * @param jsonArr
	 * @return
	 */
	public JSONDecoder toJsonArr(String jsonArr) {
		try {
			List<Map<String, Object>> obj = gson.fromJson(jsonArr, new TypeToken<List<Map<String, Object>>>() {
			}.getType());
			this.setJsonArr(obj);
		} catch (Exception ex) {
			LOG.error("Failed to parse JSONArray due to: " + ex);
		}
		return this;
	}
	
	/**
	 * 把String转化为List<Map<String, Object>>形式
	 * 
	 * @param jsonArr
	 * @return
	 */
	public JSONDecoder toJsonArr() {
		try {
			List<Map<String, Object>> obj = gson.fromJson(this.getJsonString(), new TypeToken<List<Map<String, Object>>>() {
			}.getType());
			this.setJsonArr(obj);
		} catch (Exception ex) {
			LOG.error("Failed to parse JSONArray due to: " + ex);
		}
		return this;
	}

	/**
	 * 根据key获取节点
	 * @param key
	 * @return
	 */
	public JSONDecoder get(String key) {
		this.setJsonString(this.getJsonObj().get(key).toString());
		return this;
	}

	/**
	 * 根据index获取jsonArray节点
	 * 从0开始
	 * @param index
	 * @return
	 */
	public JSONDecoder get(int index) {
		this.setJsonObj(this.getJsonArr().get(index));
		return this;
	}
}
