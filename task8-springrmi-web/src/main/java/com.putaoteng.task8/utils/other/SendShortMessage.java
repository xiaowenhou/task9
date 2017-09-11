package com.putaoteng.task8.utils.other;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.log4j.Logger;

public class SendShortMessage {
	private static Logger logger = Logger.getLogger(SendShortMessage.class);

	private String accessKeyId;
	private String accessKeySecret;
	private String signName;
	private String templateParam;
	
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public String getTemplateParam() {
		return templateParam;
	}
	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
	}

	
	public boolean sendSMS(String phoneNumber, String code) {
		 logger.info("sendSMS()被执行...");
		 System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		 System.setProperty("sun.net.clinet.defaultReadTimeout", "10000");

		final String product = "Dysmsapi";
		final String domain = "dysmsapi.aliyuncs.com";

		//验证accessID和accessSecret
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", this.accessKeyId, this.accessKeySecret);

		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e1) {
			logger.error("发送短信异常: 客户端异常..........DefaultProfile.addEndpoint" + e1.getMessage());
			return false;
		}
		
		IAcsClient acsClient = new DefaultAcsClient(profile);
		SendSmsRequest request = new SendSmsRequest();
		request.setMethod(MethodType.POST);
		request.setPhoneNumbers(phoneNumber);	
		
		//拼接发送的验证码json字符串
		String prefixStr = "{\"number\":\"";
		String suffixStr = "\"}";
		String number = prefixStr + code + suffixStr;
		request.setTemplateParam(number);
		request.setSignName(this.signName);
		request.setTemplateCode(this.templateParam);
		// request.setSmsUpExtendCode("952713");
		//request.setOutId("00100520");

		SendSmsResponse sendSmsResponse;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
			if(sendSmsResponse.getCode() == null || !sendSmsResponse.getCode().equals("OK")) {
				logger.error("发送短信异常: "+phoneNumber+"服务端异常,错误代码:" +
						sendSmsResponse.getCode()+",错误消息:"+sendSmsResponse.getMessage());
				return false;
			}
		} catch (ServerException e) {
			logger.error("发送短信异常: "+phoneNumber+" 服务端异常............" + e.getMessage());
			return false;
		} catch (ClientException e) {
			logger.error("发送短信异常: "+phoneNumber+"客户端异常.........." + e.getMessage());
			return false;
		}

		return true;
	}
}
