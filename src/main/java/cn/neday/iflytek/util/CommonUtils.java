
package cn.neday.iflytek.util;

import java.util.Random;


/**
 * 常用工具
 *
 * @author nEdAy
 */

public final class CommonUtils {

    private final static String TAG = "CommonUtils";

    /**
     * 获取范围内随机数
     *
     * @param max 最大值
     * @param min 最小值
     * @return 随机数
     */
    public static int getRandom(int max, int min) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 打Log并发送邮件
     *
     * @param addressee 收件人
     * @param subject   主题
     * @param body      内容
     * @param isError   是否Error信息
     */

    public static void sendEmailAndLog(String addressee, String subject, String body, boolean isError) {
        // EmailUtils.sendEmail(addressee, subject, body);
        if (isError) {
            System.err.println(body);
        } else {
            System.out.println(body);
        }
    }


}