package cn.neday.iflytek.controller;

import cn.neday.iflytek.domain.User;
import cn.neday.iflytek.helper.RandomTimeHelper;
import cn.neday.iflytek.service.AttendanceService;
import cn.neday.iflytek.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/attendance")
public class AttendanceController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private AttendanceService attendanceService;

    @ApiOperation(value = "随机更新上下班签到计划时间", notes = "随机更新上下班签到计划时间")
    @RequestMapping(value = "/updateScheduleTime", method = RequestMethod.GET)
    public void updateScheduleTime() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            logger.error("随机更新上下班签到计划时间失败，Users is Empty!");
            return;
        }
        for (User user : users) {
            userService.updateGoToWorkScheduleTimeByUserAccount(RandomTimeHelper.getGoToWorkRandomTime(), user.getUserAccount());
            userService.updateGoOffWorkScheduleTimeByUserAccount(RandomTimeHelper.getGoOffWorkRandomTime(), user.getUserAccount());
        }
        logger.info("随机更新上下班签到计划时间成功!");
    }

    @ApiOperation(value = "更新上下班时间（最大范围内）", notes = "以输入时间为最大时间（单位：秒），更新上下班时间")
    @RequestMapping(value = "/updateScheduleTime/{maxTime}", method = RequestMethod.GET)
    public void updateScheduleTime(@PathVariable long maxTime) {
        List<User> users = userService.findAll();
        logger.warn("[手动]执行更新上下班时间（最大范围内）！");
        if (users.isEmpty()) {
            logger.error("[手动]更新上下班时间（最大范围内）失败，Users is Empty!");
            return;
        }
        for (User user : users) {
            userService.updateGoToWorkScheduleTimeByUserAccount(RandomTimeHelper.getTheRandomTimeInTheRange(maxTime), user.getUserAccount());
            userService.updateGoOffWorkScheduleTimeByUserAccount(RandomTimeHelper.getTheRandomTimeInTheRange(maxTime), user.getUserAccount());
        }
        logger.info("[手动]更新上下班时间（最大范围内）成功!");
    }

    @ApiOperation(value = "执行上班打卡检测任务", notes = "执行上班打卡检测任务一次")
    @RequestMapping(value = "/checkGoToWorkTask", method = RequestMethod.GET)
    public void checkGoToWorkTask() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            logger.error("执行上班打卡检测任务失败，Users is Empty!");
            return;
        }
        attendanceService.checkIsGoToWorkTimeComing(users);
    }

    @ApiOperation(value = "执行下班打卡检测任务", notes = "执行下班打卡检测任务一次")
    @RequestMapping(value = "/checkGoOffWorkTask", method = RequestMethod.GET)
    public void checkGoOffWorkTask() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            logger.error("执行下班打卡检测任务失败，Users is Empty!");
            return;
        }
        attendanceService.checkIsGoOffWorkTimeComing(users);
    }


}
