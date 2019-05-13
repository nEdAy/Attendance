package cn.neday.iflytek.service;

import cn.neday.iflytek.config.StaticConfig;
import cn.neday.iflytek.domain.RequestInfo;
import cn.neday.iflytek.domain.ResponseInfo;
import cn.neday.iflytek.domain.User;
import cn.neday.iflytek.helper.EncryptLngLatHelper;
import cn.neday.iflytek.service.impl.RestTemplateImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class RestTemplateService implements RestTemplateImpl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LocationService locationService;

    /**
     * @param user 用户信息
     * @return 打卡回调信息
     */
    public ResponseInfo attendanceByUserAccount(User user) {
        // 构造请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", user.getUserAgent());
        // 构造上班签到请求参数(用户信息)
        RequestInfo requestInfo = new RequestInfo(user);
        // 当前时间戳
        long currentTime = new Date().getTime();
        String encryptLngLat = "";
        while (encryptLngLat.equals("") || encryptLngLat.contains("+")
                || encryptLngLat.contains("/")) {
            // 添加上班签到请求参数(加密地理位置，当前时间戳)
            encryptLngLat = EncryptLngLatHelper.getEncryptLngLat(currentTime, EncryptLngLatHelper.getRandomLngLat());
        }
        if ("".equals(encryptLngLat)) {
            logger.error("加密位置字符串生成错误！");
            return new ResponseInfo();
        }
        requestInfo.setLngLat(encryptLngLat);
        requestInfo.setTimeStamp(String.valueOf(currentTime));
        // 构造对象转JSONObject
        JSONObject jsonObject = (JSONObject) JSON.toJSON(requestInfo);
        if (PhoneType.Android.name().equals(user.getPhoneType())) {
            jsonObject.put("VirtualEnable", "0");
        }
        // if (PhoneType.ios.name().equals(user.getPhoneType())) {
        //
        // }
        // 构造Http请求体
        HttpEntity<JSONObject> jsonObjectHttpEntity = new HttpEntity<>(jsonObject, headers);
        // 构造Http请求URL
        String ATTENDANCE_URL = StaticConfig.HTTP + StaticConfig.BASE_HOST_URL + StaticConfig.ATTENDANCE_BY_IFLY_URL;
        // Http请求
        String responseString = this.restTemplate.postForObject(ATTENDANCE_URL, jsonObjectHttpEntity, String.class);
   /*     // FIXME:临时代码 收集正常位置
        if (responseString.contains("Sucess")) {
            locationService.insert(encryptLngLat, true, String.valueOf(currentTime));
        } else {
            locationService.insert(encryptLngLat, false, String.valueOf(currentTime));
        }*/
        // 返回请求结果
        return JSON.parseObject(responseString,
                new TypeReference<ResponseInfo>() {
                });
    }

    public ResponseInfo attendance() {
        return new ResponseInfo();
    }

    private enum PhoneType {Android, ios}

}
