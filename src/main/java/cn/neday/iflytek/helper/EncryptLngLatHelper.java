package cn.neday.iflytek.helper;

import cn.neday.iflytek.util.CommonUtils;
import cn.neday.iflytek.util.crypto.AES;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class EncryptLngLatHelper {


    /**
     * 获取随机范围内位置信息
     *
     * @return 随机范围内位置信息
     */
    public static String getRandomLngLat() {
        String lng = "117.3937" +
                new DecimalFormat("00").format(CommonUtils.getRandom(99, 10));
        String lat = "39.1604" +
                new DecimalFormat("00").format(CommonUtils.getRandom(99, 10));
        return lng + "_" + lat;
    }


    /**
     * AES加密LngLat
     *
     * @param timeStamp 当前时间戳
     * @param lngLat    随机范围内位置信息 117.393712_39.160455
     * @return 加密后LngLat
     */
    public static String getEncryptLngLat(long timeStamp, String lngLat) {
        String mEncryptLngLat = "";
        try {
            mEncryptLngLat = new AES().encrypt(
                    lngLat.getBytes("UTF8"),
                    String.valueOf(timeStamp)
                            .replaceAll("\r\n", "")
                            .replaceAll("\r", "")
                            .replaceAll("\n", "")
                            .replace('+', '.')
                            .replace('/', '_'));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return mEncryptLngLat;
    }

}
