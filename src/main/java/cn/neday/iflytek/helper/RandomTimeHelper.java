package cn.neday.iflytek.helper;

import cn.neday.iflytek.util.date.DateStyle;
import cn.neday.iflytek.util.date.DateUtil;

import java.util.Date;

public class RandomTimeHelper {

    private static DateStyle dateStyle = DateStyle.YYYY_MM_DD_HH_MM_SS;

    /**
     * 获取上班时间
     * 0:00 7:36            8:12
     * 0    7.6 * 3600      8.2 * 3600
     * 0    27 360          29 520
     *
     * @return 上班时间
     */
    public static String getGoToWorkRandomTime() {
        long GO_TO_WORK_MAX = 29520;
        long GO_TO_WORK_MIN = 27360;
        long go_to_work_add = GO_TO_WORK_MIN + Math.round(Math.random() * (GO_TO_WORK_MAX - GO_TO_WORK_MIN));
        return DateUtil.DateToString(new Date(DateUtil.getDayBeginTimestampLong() + go_to_work_add * 1000), dateStyle);
    }


    /**
     * 获取当前时间到最大时间内随机时间
     *
     * @param maxTime 最大时间
     * @return 最大时间内随机时间
     */
    public static String getTheRandomTimeInTheRange(long maxTime) {
        long time_add = Math.round(Math.random() * maxTime);
        return DateUtil.DateToString(new Date(new Date().getTime() + time_add * 1000), dateStyle);
    }


    /**
     * 获取下班时间（加班）
     * 0:00 19:30           20:54
     * 0    19.5 * 3600     20.9 * 3600
     * 0    70 200          75 240
     *
     * @return 下班时间（加班）
     */
    private static String getGoOffWorkAddRandomTime() {
        long GO_OFF_WORK_MAX = 75240;
        long GO_OFF_WORK_MIN = 70200;
        long go_off_work_add = GO_OFF_WORK_MIN + Math.round(Math.random() * (GO_OFF_WORK_MAX - GO_OFF_WORK_MIN));
        return DateUtil.DateToString(new Date(DateUtil.getDayBeginTimestampLong() + go_off_work_add * 1000), dateStyle);
    }


    /**
     * 获取下班时间（不加班）
     * 0:00 17:36           18:48
     * 0    17.6 * 3600     18.8 * 3600
     * 0    63 360          67 680
     *
     * @return 下班时间（不加班）
     */
    private static String getGoOffWorkNotAddRandomTime() {
        long GO_OFF_WORK_NOT_ADD_MAX = 67680;
        long GO_OFF_WORK_NOT_ADD_MIN = 63360;
        long go_to_work_not_add = GO_OFF_WORK_NOT_ADD_MIN + Math.round(Math.random() * (GO_OFF_WORK_NOT_ADD_MAX - GO_OFF_WORK_NOT_ADD_MIN));
        return DateUtil.DateToString(new Date(DateUtil.getDayBeginTimestampLong() + go_to_work_not_add * 1000), dateStyle);
    }


    /**
     * 获取下班时间
     * <p>
     * > 0.0952 下班时间（加班）
     * 0 - 0.0952 下班时间（不加班）
     *
     * @return 下班时间
     */
    public static String getGoOffWorkRandomTime() {
        double go_off_work_real_rand = Math.random();
        if (go_off_work_real_rand > 0.0952) {
            return getGoOffWorkAddRandomTime();
        } else {
            return getGoOffWorkNotAddRandomTime();
        }

    }

}
