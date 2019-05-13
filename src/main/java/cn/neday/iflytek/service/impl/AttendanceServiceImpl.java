package cn.neday.iflytek.service.impl;

import cn.neday.iflytek.domain.User;

import java.util.List;

public interface AttendanceServiceImpl {

    /**
     * 遍历用户列表 检查上班签到预约时间是否临近
     *
     * @param users 用户列表
     */
    void checkIsGoToWorkTimeComing(List<User> users);


    /**
     * 遍历用户列表 检查下班签到预约时间是否临近
     *
     * @param users 用户列表
     */
    void checkIsGoOffWorkTimeComing(List<User> users);

}
