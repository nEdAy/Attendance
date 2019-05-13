package cn.neday.iflytek.controller;

import cn.neday.iflytek.domain.User;
import cn.neday.iflytek.service.CheckService;
import cn.neday.iflytek.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/check")
public class CheckController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CheckService checkService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "检查上班打卡任务是否执行成功", notes = "检查上班打卡任务是否执行成功")
    @RequestMapping(value = "/checkIsGoToWorkTaskComplete", method = RequestMethod.GET)
    public void checkIsGoToWorkTaskComplete() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            logger.error("Users is Empty!");
            return;
        }
        for (User user : users) {
            checkService.checkIsGoToWorkTaskComplete(user, true);
        }
    }


    @ApiOperation(value = "检查单人上班打卡任务是否执行成功", notes = "检查单人上班打卡任务是否执行成功")
    @RequestMapping(value = "/checkIsGoToWorkTaskComplete/{UserAccount}", method = RequestMethod.GET)
    public void checkIsGoToWorkTaskComplete(String UserAccount) {
        User user = userService.findByUserAccount(UserAccount);
        checkService.checkIsGoToWorkTaskComplete(user, true);
    }


    @ApiOperation(value = "检查下班打卡任务是否执行成功", notes = "检查下班打卡任务是否执行成功")
    @RequestMapping(value = "/checkIsGoOffWorkTaskComplete", method = RequestMethod.GET)
    public void checkIsGoOffWorkTaskComplete() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            logger.error("Users is Empty!");
            return;
        }
        for (User user : users) {
            checkService.checkIsGoOffWorkTaskComplete(user, true);
        }
    }


    @ApiOperation(value = "检查单人下班打卡任务是否执行成功", notes = "检查单人下班打卡任务是否执行成功")
    @RequestMapping(value = "/checkIsGoOffWorkTaskComplete/{UserAccount}", method = RequestMethod.GET)
    public void checkIsGoOffWorkTaskComplete(String UserAccount) {
        User user = userService.findByUserAccount(UserAccount);
        checkService.checkIsGoOffWorkTaskComplete(user, true);
    }


    @ApiOperation(value = "检查签到失败记录，发送签到月报", notes = "检查签到失败记录，发送签到月报")
    @RequestMapping(value = "/sendMonthReport", method = RequestMethod.GET)
    public void sendMonthReport() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            logger.error("Users is Empty!");
            return;
        }
        //attendanceService.checkIsGoOffWorkTimeComing(users);
    }

}
