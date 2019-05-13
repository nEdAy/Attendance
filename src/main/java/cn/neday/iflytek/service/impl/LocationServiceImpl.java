package cn.neday.iflytek.service.impl;

public interface LocationServiceImpl {

    /**
     * 插入新位置信息
     *
     * @param lngLat    位置信息
     * @param isSuccess 是否打卡成功
     */
    int insert(String lngLat, boolean isSuccess, String time);

}


