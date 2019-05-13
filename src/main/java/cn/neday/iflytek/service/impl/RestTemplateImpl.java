package cn.neday.iflytek.service.impl;

import cn.neday.iflytek.domain.ResponseInfo;
import cn.neday.iflytek.domain.User;

public interface RestTemplateImpl {

    /**
     * 对UserAccount对应用户，执行立即签到操作
     *
     * @param user 用户信息
     * @return 打卡请求返回信息
     */
    ResponseInfo attendanceByUserAccount(User user);


    /**
     * 对所有用户，执行立即签到操作
     */
    ResponseInfo attendance();

}
