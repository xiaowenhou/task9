package com.putaoteng.task8.utils.other;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SendMailTemplate {
	private static Logger logger = Logger.getLogger(SendMailTemplate.class.getName());

	private String apiUser;
	private String apiKey;
	private String template;
	
	public String getApiUser() {
		return apiUser;
	}
	public void setApiUser(String apiUser) {
		this.apiUser = apiUser;
	}

	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}

	public static String convert(List<String> dataList) {

		JSONObject ret = new JSONObject();
		JSONArray to = new JSONArray();
		JSONArray names = new JSONArray();

		to.put(dataList.get(0));
		names.put(dataList.get(1));

		JSONObject sub = new JSONObject();
		sub.put("%name%", names);

		ret.put("to", to);
		ret.put("sub", sub);

		return ret.toString();
	}

	public boolean sendTemplate(String address, String user){
		logger.info("sendTemplate()执行......");
		final String url = "http://api.sendcloud.net/apiv2/mail/sendtemplate";

		/*String subject = "注册成功";*/

		List<String> dataList = new ArrayList<String>();
		dataList.add(address);
		dataList.add(user);

		//将list转换为xsmtpapi字符串
		final String xsmtpapi = convert(dataList);
		System.out.println("xsmtpapi:   " + xsmtpapi);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//验证用户
		params.add(new BasicNameValuePair("apiUser", this.apiUser));
		//验证密码
		params.add(new BasicNameValuePair("apiKey", this.apiKey));
		//xsmtpapi字符串
		params.add(new BasicNameValuePair("xsmtpapi", xsmtpapi));
		//调用的模版
		params.add(new BasicNameValuePair("templateInvokeName", this.template));
		//发件人
		params.add(new BasicNameValuePair("from", "sendcloud@sendcloud.org"));
		//发件人名,這兩個參數相對比較固定,所以不抽取到spring配置文件中
		params.add(new BasicNameValuePair("fromName", "测试task7"));
		/*//邮件标题,模版中已定义
		params.add(new BasicNameValuePair("subject", subject));*/

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("发送模版邮件异常: "+address+"不支持的编码: "+e.getMessage());
			return false;
		}

		HttpResponse response;
		try {
			//发送请求
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				logger.error("发送模版邮件异常: "+address+"返回状态值错误" +
						"原因:" + EntityUtils.toString(response.getEntity()) +
						response.getStatusLine().getReasonPhrase() + response.getStatusLine().getStatusCode());
				return false;
			}
		} catch (ClientProtocolException e) {
			logger.error("发送模版邮件异常: "+address+"客户端协议异常: " + e.getMessage());
			return false;
		} catch (IOException e) {
			logger.error("发送模版邮件异常: "+address+"输入输出异常: " + e.getMessage());
			return false;
		}
		httpPost.releaseConnection();
		return true;
	}

}
