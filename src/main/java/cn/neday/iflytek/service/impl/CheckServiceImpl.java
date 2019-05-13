package cn.neday.iflytek.service.impl;

import cn.neday.iflytek.domain.User;

public interface CheckServiceImpl {

    /**
     * 检查上班打卡任务是否执行成功
     *
     * @param user     用户
     * @param isSignIn 是否签到中
     */
    boolean checkIsGoToWorkTaskComplete(User user, boolean isSignIn);


    /**
     * 检查下班打卡任务是否执行成功
     *
     * @param user     用户
     * @param isSignIn 是否签到中
     */
    boolean checkIsGoOffWorkTaskComplete(User user, boolean isSignIn);

}
