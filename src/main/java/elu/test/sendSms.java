package elu.test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;

public class sendSms {

	public static void main(String[] args) {
		try {
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI3efZ2vYdO1dq", "KJ5stQDsiyiNK8KxSlCzT0USOxC1Yf");
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");
			IAcsClient client = new DefaultAcsClient(profile);
			SingleSendSmsRequest requestSms = new SingleSendSmsRequest();
			requestSms.setSignName("e鹿平台");//控制台创建的签名名称
			requestSms.setTemplateCode("SMS_57150094");//控制台创建的模板CODE
			requestSms.setParamString("{\"code\":\"123\"}");//短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。"
//			requestSms.setParamString("{}");
			requestSms.setRecNum("15652798270");//接收号码
			SingleSendSmsResponse httpResponse = client.getAcsResponse(requestSms);
			} catch (ClientException e) {
			  e.printStackTrace();
			}
	}
}
