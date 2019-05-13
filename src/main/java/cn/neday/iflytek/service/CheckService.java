package cn.neday.iflytek.service;

import cn.neday.iflytek.domain.User;
import cn.neday.iflytek.service.impl.CheckServiceImpl;
import cn.neday.iflytek.util.date.DateStyle;
import cn.neday.iflytek.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CheckService implements CheckServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SmsService smsService;

    @Autowired
    private EmailService emailService;

    /**
     * 上班自动打卡失败 SMS_105935162
     * 用户 [${name} ]在${time}前上班自动打卡失败，请手动打卡并提前管理员维护。
     *
     * @param user                用户
     * @param isAutoCheckComplete 是否是自动定时检测系统查询 是否完成
     * @return 是否上班已打卡
     */
    @Override
    public boolean checkIsGoToWorkTaskComplete(User user, boolean isAutoCheckComplete) {
        // 获取上班签到记录时间
        Date goToWorkCheckTime =
                DateUtil.getDateByDateStyle(user.getGoToWorkCheckTime(), DateStyle.YYYY_MM_DD_HH_MM_SS);
        // 判断上班签到记录时间合法性
        if (goToWorkCheckTime == null) {
            logger.error(user.getUserName() + "上班签到记录时间不合法!");
            return true;
        }
        // 判断当日上班是否已签到过
        if (DateUtil.getDay(new Date()) == DateUtil.getDay(goToWorkCheckTime)) {
            logger.info(user.getUserName() + "上班已签到!");
            return true;
        }
        logger.info(user.getUserName() + "上班尚未签到!");
        // 签到失败 && 仅自动定时检测系统查询 任务完成结果  将执行结果发送短信、邮件操作。
        if (isAutoCheckComplete) {
            logger.error(user.getUserName() + "上班尚未签到(已超出自动签到范围),发送短信、邮件提醒！");
            smsService.sendSms
                    (user.getPhone(), "SMS_107010114", getSmsTemplateParam(user.getUserName()));
            emailService.sendTemplateMail(user.getEmail(), user.getUserName(), "上午失败",
                    "您今天的【上班】打卡失败，请手动打卡并提前管理员维护。");
            logger.warn(user.getUserName() + "上班尚未签到(已超出自动签到范围),发送短信、邮件提醒成功！");
        }
        return false;
    }

    /**
     * 下班自动打卡失败 SMS_105965157
     * 用户 [${name} ]在${time}前下班自动打卡失败，请手动打卡并提前管理员维护。
     *
     * @param user                用户
     * @param isAutoCheckComplete 是否是自动定时检测系统查询 是否完成
     * @return 是否下班已打卡
     */
    @Override
    public boolean checkIsGoOffWorkTaskComplete(User user, boolean isAutoCheckComplete) {
        // 获取下班签到记录时间
        Date goOffWorkCheckTime =
                DateUtil.getDateByDateStyle(user.getGoOffWorkCheckTime(), DateStyle.YYYY_MM_DD_HH_MM_SS);
        // 判断下班签到记录时间合法性
        if (goOffWorkCheckTime == null) {
            logger.error(user.getUserName() + "下班签到记录时间不合法!");
            return true;
        }
        // 判断当日下班是否已签到过
        if (DateUtil.getDay(new Date()) == DateUtil.getDay(goOffWorkCheckTime)) {
            logger.info(user.getUserName() + "下班已签到!");
            return true;
        }
        logger.info(user.getUserName() + "下班尚未签到!");
        // 签到失败 && 仅自动定时检测系统查询 任务完成结果 将执行结果发送短信、邮件操作。
        if (isAutoCheckComplete) {
            logger.error(user.getUserName() + "下班尚未签到(已超出自动签到范围),发送短信、邮件提醒！");
            smsService.sendSms
                    (user.getPhone(), "SMS_107035115", getSmsTemplateParam(user.getUserName()));
            emailService.sendTemplateMail(user.getEmail(), user.getUserName(), "下午失败",
                    "您今天的【下班】打卡失败，请手动打卡并提前管理员维护。");
            logger.warn(user.getUserName() + "下班尚未签到(已超出自动签到范围)，发送短信、邮件提醒成功！");
        }
        return false;
    }

    // 月报 SMS_105860165
    // 用户[${name}]在本月考勤期间，存在[${times}]次自动打卡失败记录，请尽快在OA系统中维护处理。

    /**
     * 获取短信模版 填充用户名 和 当前时间（MM月dd日 HH:mm）
     *
     * @param name 用户名
     * @return {"name":"%s", "time":"%s"}
     */
    private String getSmsTemplateParam(String name) {
        return String.format("{\"name\":\"%s\", \"time\":\"%s\"}", name,
                DateUtil.DateToString(new Date(), DateStyle.MM_DD_HH_MM_CN));
    }
}
