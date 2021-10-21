package com.itheima.reggie_take_out.common;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;

public class SmsUtils {
    @Value("${reggin.aliyunSMSAccessKeyId}")
    private String accessKeyId;
    @Value("${reggin.accessSecret}")
    private String accessSecret;

    public static void sendSms(String phoneNumber, String signName, String templateCode, String templateParam) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "keyid", "secret");
        IAcsClient client = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setPhoneNumbers(phoneNumber);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam(templateParam);
        try {
            SendSmsResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


}