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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendMailContent {
	private static Logger logger = Logger.getLogger(SendMailContent.class.getName());

	private String apiUser;
	private String apiKey;
	private String verificateUrl;
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
	public String getVerificateUrl() {
		return verificateUrl;
	}
	public void setVerificateUrl(String verificateUrl) {
		this.verificateUrl = verificateUrl;
	}

	public boolean sendCommon(String address) throws ClientProtocolException, IOException {
		logger.info("sendCommon()执行......");

		final String url = "http://api.sendcloud.net/apiv2/mail/send";
		String subject = "验证邮箱邮件";
		String html = "<html><head></head><body>"
				+ "<p><a href='"+this.verificateUrl+"?email="+address+"'>点击</a>链接验证邮箱</p>" + "</body></html> ";
		
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpClient = HttpClients.createDefault();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apiUser", apiUser));
		params.add(new BasicNameValuePair("apiKey", apiKey));
		params.add(new BasicNameValuePair("to", address));
		//发件人
		params.add(new BasicNameValuePair("from", "sendcloud@sendcloud.org"));
		params.add(new BasicNameValuePair("fromName", "测试task7"));
		//主题
		params.add(new BasicNameValuePair("subject", subject));
		//内容正文
		params.add(new BasicNameValuePair("html", html));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse response = httpClient.execute(httpPost);

		// 处理响应
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			logger.error( "普通邮件发送异常: "+address+"返回状态值错误" +
					"原因:" + EntityUtils.toString(response.getEntity()) +
					response.getStatusLine().getReasonPhrase() + response.getStatusLine().getStatusCode());
			return false;
		}
		httpPost.releaseConnection();
		return true;
	}
	
}
