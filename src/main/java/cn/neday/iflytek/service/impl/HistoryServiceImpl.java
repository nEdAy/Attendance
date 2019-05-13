package cn.neday.iflytek.service.impl;

import cn.neday.iflytek.domain.History;

import java.util.List;

public interface HistoryServiceImpl {

    /**
     * @return 全部用户历史信息
     */
    List<History> findAll();

    /**
     * 通过用户名获取用户历史信息
     *
     * @param UserAccount 用户名
     * @return 用户信息
     */
    List<History> findByUserAccount(String UserAccount);


    /**
     * 插入新历史信息
     *
     * @param UserAccount 用户名
     * @param isSuccess   是否打卡成功
     * @param isAuto      是否是自动打卡
     * @param time        当前时间
     */
    int insert(String UserAccount, boolean isSuccess, boolean isAuto, String time);

}


