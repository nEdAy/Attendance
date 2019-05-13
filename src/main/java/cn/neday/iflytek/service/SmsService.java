package cn.neday.iflytek.service;

import cn.neday.iflytek.config.StaticConfig;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/*
    错误码列表

    Code	描述
    OK	请求成功
    isp.RAM_PERMISSION_DENY	RAM权限DENY
    isv.OUT_OF_SERVICE	业务停机
    isv.PRODUCT_UN_SUBSCRIPT	未开通云通信产品的阿里云客户
    isv.PRODUCT_UNSUBSCRIBE	产品未开通
    isv.ACCOUNT_NOT_EXISTS	账户不存在
    isv.ACCOUNT_ABNORMAL	账户异常
    isv.SMS_TEMPLATE_ILLEGAL	短信模板不合法
    isv.SMS_SIGNATURE_ILLEGAL	短信签名不合法
    isv.INVALID_PARAMETERS	参数异常
    isp.SYSTEM_ERROR	系统错误
    isv.MOBILE_NUMBER_ILLEGAL	非法手机号
    isv.MOBILE_COUNT_OVER_LIMIT	手机号码数量超过限制
    isv.TEMPLATE_MISSING_PARAMETERS	模板缺少变量
    isv.BUSINESS_LIMIT_CONTROL	业务限流
    isv.INVALID_JSON_PARAM	JSON参数不合法，只接受字符串值
    isv.BLACK_KEY_CONTROL_LIMIT	黑名单管控
    isv.PARAM_LENGTH_LIMIT	参数超出长度限制
    isv.PARAM_NOT_SUPPORT_URL	不支持URL
    isv.AMOUNT_NOT_ENOUGH	账户余额不足
*/
@Service
public class SmsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static int TRY_TIMES_MAX = 2;
    private int tryTimes;

    /**
     * 发送短信
     *
     * @param phoneNumbers  待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
     * @param templateCode  短信模板-可在短信控制台中找到
     * @param templateParam 模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为"{\"name\":\"Tom\", \"code\":\"123\"}" 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
     */
    @Async
    public void sendSms(String phoneNumbers, String templateCode, String templateParam) {
        try {
            Thread.sleep(10 * 1000);
            //设置超时时间-可自行调整
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient需要的几个参数
            final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
            final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
            //替换成你的AK
            final String accessKeyId = StaticConfig.ALIYUN_SMS_ACCESS_KEY_ID;//你的accessKeyId,参考本文档步骤2
            final String accessKeySecret = StaticConfig.ALIYUN_SMS_ACCESS_KEY_SECRET;//你的accessKeySecret，参考本文档步骤2
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(phoneNumbers);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(StaticConfig.ALIYUN_SMS_SIGN_NAME);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为"{\"name\":\"Tom\", \"code\":\"123\"}"
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam(templateParam);
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //request.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            String sendSmsResponseCode = sendSmsResponse.getCode();
            if (sendSmsResponseCode != null && sendSmsResponseCode.equals("OK")) {
                //请求成功
                logger.warn("成功发送短信给" + phoneNumbers + ",templateCode = " + templateCode + ",templateParam = " + templateParam);
            } else {
                logger.error("发送给" + phoneNumbers + "的短信失败,templateCode = " + templateCode + ",templateParam = " + templateParam + "sendSmsResponse.code = " + sendSmsResponseCode);
            }
        } catch (ClientException e) {
            tryTimes++;
            if (tryTimes < TRY_TIMES_MAX) {
                sendSms(phoneNumbers, templateCode, templateParam);
            } else {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
