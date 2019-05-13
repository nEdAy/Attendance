package cn.neday.iflytek;

import cn.neday.iflytek.controller.AttendanceController;
import cn.neday.iflytek.controller.CheckController;
import cn.neday.iflytek.util.date.WeekdayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Seconds : 可出现", - * /"四个字符，有效范围为0-59的整数
 * Minutes : 可出现", - * /"四个字符，有效范围为0-59的整数
 * Hours : 可出现", - * /"四个字符，有效范围为0-23的整数
 * DayofMonth : 可出现", - * / ? L W C"八个字符，有效范围为0-31的整数
 * Month : 可出现", - * /"四个字符，有效范围为1-12的整数或JAN-DEc
 * DayofWeek : 可出现", - * / ? L C #"四个字符，有效范围为1-7的整数或SUN-SAT两个范围。1表示星期天，2表示星期一， 依次类推
 * Year : 可出现", - * /"四个字符，有效范围为1970-2099年
 * <p>
 * "0 0 12 * * ?"    每天中午十二点触发
 * "0 15 10 ? * *"    每天早上10：15触发
 * "0 15 10 * * ?"    每天早上10：15触发
 * "0 15 10 * * ? *"    每天早上10：15触发
 * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
 * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发
 * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发
 * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
 * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
 * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
 * "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
 */
@Component
public class ScheduledTasks {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AttendanceController attendanceController;

    @Autowired
    private CheckController checkController;

    /**
     * 间隔60秒执行任务
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void queryUserSignInfoList() {
        if (WeekdayUtil.getWeekdayUtil().checkTodayIsHoliday()) {
            logger.info("[节假日]进程存活");
        } else {
            logger.info("[工作日]进程存活");
        }
    }


    /**
     * 每天1点、13点整执行任务
     * 随机更新上下班签到计划时间
     */
    @Scheduled(cron = "0 0 1,13 * * ?")
    public void updateScheduleTime() {
        logger.warn("执行随机更新上下班签到计划时间任务!");
        if (WeekdayUtil.getWeekdayUtil().checkTodayIsHoliday()) {
            logger.warn("[节假日]执行随机更新上下班签到计划时间任务任务失败");
            return;
        }
        attendanceController.updateScheduleTime();
    }

    /**
     * 每天7点~9点，间隔1分钟执行任务
     * 检查是否需要执行上班打卡任务
     */
    @Scheduled(cron = "0 0/1 7-8 * * ?")
    public void checkGoToWorkTask() {
        logger.warn("执行检查是否需要执行上班打卡任务！");
        if (WeekdayUtil.getWeekdayUtil().checkTodayIsHoliday()) {
            logger.warn("[节假日]检查是否需要执行上班打卡任务失败");
            return;
        }
        attendanceController.checkGoToWorkTask();
    }


    /**
     * 每天17点~21点，间隔1分钟执行任务
     * 检查是否需要执行下班打卡任务
     */
    @Scheduled(cron = "0 0/1 17-20 * * ?")
    public void checkGoOffWorkTask() {
        logger.warn("执行检查是否需要执行下班打卡任务！");
        if (WeekdayUtil.getWeekdayUtil().checkTodayIsHoliday()) {
            logger.warn("[节假日]检查是否需要执行下班打卡任务失败");
            return;
        }
        attendanceController.checkGoOffWorkTask();
    }

    /**
     * 每天8：25整执行任务
     * 检查上班打卡任务是否执行成功
     */
    @Scheduled(cron = "0 25 8 * * ?")
    public void checkIsGoToWorkTaskComplete() {
        logger.warn("执行检查上班打卡任务是否执行成功任务！");
        if (WeekdayUtil.getWeekdayUtil().checkTodayIsHoliday()) {
            logger.warn("[节假日]执行检查上班打卡任务是否执行成功任务失败");
            return;
        }
        checkController.checkIsGoToWorkTaskComplete();
    }


    /**
     * 每天21：10整执行任务
     * 检查下班打卡任务是否执行成功
     */
    @Scheduled(cron = "0 10 21 * * ?")
    public void checkIsGoOffWorkTaskComplete() {
        logger.warn("执行检查下班打卡任务是否执行成功任务！");
        if (WeekdayUtil.getWeekdayUtil().checkTodayIsHoliday()) {
            logger.warn("[节假日]执行检查下班打卡任务是否执行成功任务失败");
            return;
        }
        checkController.checkIsGoOffWorkTaskComplete();
    }

    /**
     * 每月25日21：20整执行任务
     * 发送签到月报
     */
    @Scheduled(cron = "0 20 21 25 * ?")
    public void sendMonthReport() {
        logger.warn("执行发送签到月报任务！");
        // attendanceController.updateScheduleTime();
    }

}
