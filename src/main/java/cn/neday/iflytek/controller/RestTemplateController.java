package cn.neday.iflytek.controller;

import cn.neday.iflytek.domain.ResponseInfo;
import cn.neday.iflytek.domain.User;
import cn.neday.iflytek.service.EmailService;
import cn.neday.iflytek.service.HistoryService;
import cn.neday.iflytek.service.RestTemplateService;
import cn.neday.iflytek.service.UserService;
import cn.neday.iflytek.util.date.DateStyle;
import cn.neday.iflytek.util.date.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/restTemplate")
public class RestTemplateController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HistoryService historyService;

    @ApiOperation(value = "立即签到（所有用户）", notes = "对所有用户，执行立即签到操作")
    @PostMapping("/attendance")
    public ResponseInfo attendance() {
        return restTemplateService.attendance();
    }

    @ApiOperation(value = "立即签到（单用户）", notes = "对UserAccount对应用户，执行立即签到操作")
    @PostMapping("/attendance/{UserAccount}")
    public ResponseInfo attendanceByUserAccount(@PathVariable String UserAccount) {
        // 获取当前用户
        User user = userService.findByUserAccount(UserAccount);
        // 当前用户当前时间请求签到
        ResponseInfo responseInfo = restTemplateService.attendanceByUserAccount(user);
        ResponseInfo.ContentBean contentBean = responseInfo.getContent();
        if (contentBean == null) {
            logger.error("[手动]" + user.getUserName() + "请求立即签到失败，签到失败！");
            return responseInfo;
        }
        String message = contentBean.getMessage();
        if (responseInfo.getSuccess()) {
            // 签到成功
            String attendanceID = contentBean.getAttendanceID();
            String attendanceTime = contentBean.getAttendanceTime(); // yyyy-MM-dd HH:mm:ss
            String rankNum = contentBean.getRankNum();
            logger.info(user.getUserName() + "[手动]签到成功,attendanceID = " + attendanceID + ",attendanceTime = " + attendanceTime + ",rankNum = " + rankNum + ",message = " + message);
            historyService.insert(user.getUserAccount(), true, false, attendanceTime);
            emailService.sendTemplateMail(user.getEmail(), user.getUserName(), "手动成功",
                    "手动成功。");
        } else {
            // 签到失败
            logger.error("[手动]" + user.getUserName() + "请求立即签到成功，但签到失败！message = " + message);
            historyService.insert(user.getUserAccount(), false, false, DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
            emailService.sendTemplateMail(user.getEmail(), user.getUserName(), "手动失败",
                    "手动失败。");
        }
        return responseInfo;
    }

}
