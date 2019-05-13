package cn.neday.iflytek.service;

import cn.neday.iflytek.domain.ResponseInfo;
import cn.neday.iflytek.domain.User;
import cn.neday.iflytek.service.impl.AttendanceServiceImpl;
import cn.neday.iflytek.util.date.DateStyle;
import cn.neday.iflytek.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AttendanceService implements AttendanceServiceImpl {

    /**
     * 定时间隔1分钟,筛选6分钟内的
     */
    private static long AHEAD_OF_TIME = 4 * 60 * 1000;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplateService restTemplateService;

    @Autowired
    private UserService userService;

    @Autowired
    private CheckService checkService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HistoryService historyService;

    @Override
    public void checkIsGoToWorkTimeComing(List<User> users) {
        for (User user : users) {
            if (checkService.checkIsGoToWorkTaskComplete(user, false)) {
                continue;
            }
            // 当前时间戳
            long currentTime = new Date().getTime();
            // 获取上班签到约定时间
            Date goToWorkScheduleTime =
                    DateUtil.getDateByDateStyle(user.getGoToWorkScheduleTime(), DateStyle.YYYY_MM_DD_HH_MM_SS);
            // 判断上班签到约定时间合法性
            if (goToWorkScheduleTime == null) {
                logger.error(user.getUserName() + "上班签到约定时间不合法！");
                continue;
            }
            // 时间差（签到约定时间戳 - 当前时间戳）
            long timeDifference = goToWorkScheduleTime.getTime() - currentTime;
            // 是否上班签到时间来临
            if (!(timeDifference >= 0 && timeDifference <= AHEAD_OF_TIME)) {
                continue;
            }
            logger.warn(user.getUserName() + "上班签到约定时间来临，开始打卡！");
            // 立即打卡
            ResponseInfo responseInfo = restTemplateService.attendanceByUserAccount(user);

            ResponseInfo.ContentBean contentBean = responseInfo.getContent();
            if (contentBean == null) {
                logger.error(user.getUserName() + "上班签到请求失败，签到失败！");
            } else {
                String message = contentBean.getMessage();
                if (responseInfo.getSuccess()) {
                    // 签到成功
                    String attendanceID = contentBean.getAttendanceID();
                    String attendanceTime = contentBean.getAttendanceTime(); // yyyy-MM-dd HH:mm:ss
                    String rankNum = contentBean.getRankNum();
                    logger.info(user.getUserName() + "上班签到成功,attendanceID = " + attendanceID + ",attendanceTime = " + attendanceTime + ",rankNum = " + rankNum + ",message = " + message);
                    // 更新上班打卡时间
                    userService.updateGoToWorkCheckTimeByUserAccount(attendanceTime, user.getUserAccount());
                    historyService.insert(user.getUserAccount(), true, true, attendanceTime);
                    emailService.sendTemplateMail(user.getEmail(), user.getUserName(), "上午成功",
                            "上午成功。");
                } else {
                    // 签到失败
                    logger.error(user.getUserName() + "上班签到请求成功，但签到失败！message = " + message);
                    historyService.insert(user.getUserAccount(), false, true, DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
                  /*  emailService.sendTemplateMail(user.getEmail(), user.getUserName(), "上午失败",
                            "上午失败。");*/
                }
            }
        }

    }

    @Override
    public void checkIsGoOffWorkTimeComing(List<User> users) {
        for (User user : users) {
            if (checkService.checkIsGoOffWorkTaskComplete(user, false)) {
                continue;
            }
            // 当前时间戳
            long currentTime = new Date().getTime();
            // 获取下班签到约定时间
            Date goOffWorkScheduleTime =
                    DateUtil.getDateByDateStyle(user.getGoOffWorkScheduleTime(), DateStyle.YYYY_MM_DD_HH_MM_SS);
            // 判断下班签到约定时间合法性
            if (goOffWorkScheduleTime == null) {
                logger.error(user.getUserName() + "下班签到约定时间不合法！");
                continue;
            }
            // 时间差（签到约定时间戳 - 当前时间戳）
            long timeDifference = goOffWorkScheduleTime.getTime() - currentTime;
            // 是否下班签到时间来临
            if (!(timeDifference >= 0 && timeDifference <= AHEAD_OF_TIME)) {
                continue;
            }
            logger.warn(user.getUserName() + "下班签到约定时间来临，开始打卡！");
            // 立即打卡
            ResponseInfo responseInfo = restTemplateService.attendanceByUserAccount(user);

            ResponseInfo.ContentBean contentBean = responseInfo.getContent();
            if (contentBean == null) {
                logger.error(user.getUserName() + "下班签到请求失败，签到失败！");
            } else {
                String message = contentBean.getMessage();
                if (responseInfo.getSuccess()) {
                    // 签到成功
                    String attendanceID = contentBean.getAttendanceID();
                    String attendanceTime = contentBean.getAttendanceTime(); // yyyy-MM-dd HH:mm:ss
                    String rankNum = contentBean.getRankNum();
                    logger.info(user.getUserName() + "下班签到成功,attendanceID = " + attendanceID + ",attendanceTime = " + attendanceTime + ",rankNum = " + rankNum + ",message = " + message);
                    // 更新下班班打卡时间
                    userService.updateGoOffWorkCheckTimeByUserAccount(attendanceTime, user.getUserAccount());
                    historyService.insert(user.getUserAccount(), true, true, attendanceTime);
                    emailService.sendTemplateMail(user.getEmail(), user.getUserName(), "下午成功",
                            "下午成功。");
                } else {
                    // 签到失败
                    logger.error(user.getUserName() + "下班签到请求成功，但签到失败！message = " + message);
                    historyService.insert(user.getUserAccount(), false, true, DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
                   /* emailService.sendTemplateMail(user.getEmail(), user.getUserName(), "下午失败",
                            "下午失败。");*/
                }
            }
        }
    }

}
