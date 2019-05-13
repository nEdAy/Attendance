package cn.neday.iflytek.service.impl;

import cn.neday.iflytek.domain.User;

import java.util.List;

public interface UserServiceImpl {

    /**
     * @return 全部用户信息
     */
    List<User> findAll();

    /**
     * 通过用户名获取用户信息
     *
     * @param UserAccount 用户名
     * @return 用户信息
     */
    User findByUserAccount(String UserAccount);


    /**
     * 通过用户名更新用户Token
     *
     * @param Token       token
     * @param UserAccount 用户名
     */
    void updateTokenByUserAccount(String Token, String UserAccount);


    /**
     * 通过用户名更新用户上班签到记录时间
     *
     * @param goToWorkCheckTime 上班签到记录时间
     * @param UserAccount       用户名
     */
    void updateGoToWorkCheckTimeByUserAccount(String goToWorkCheckTime, String UserAccount);


    /**
     * 通过用户名更新用户下班签到记录时间
     *
     * @param goOffWorkCheckTime 下班签到记录时间
     * @param UserAccount        用户名
     */
    void updateGoOffWorkCheckTimeByUserAccount(String goOffWorkCheckTime, String UserAccount);


    /**
     * 通过用户名更新用户上班签到预约时间
     *
     * @param goToWorkScheduleTime 上班签到预约时间
     * @param UserAccount          用户名
     */
    void updateGoToWorkScheduleTimeByUserAccount(String goToWorkScheduleTime, String UserAccount);


    /**
     * 通过用户名更新用户下班签到预约时间
     *
     * @param goOffWorkScheduleTime 下班签到预约时间
     * @param UserAccount           用户名
     */
    void updateGoOffWorkScheduleTimeByUserAccount(String goOffWorkScheduleTime, String UserAccount);

}
