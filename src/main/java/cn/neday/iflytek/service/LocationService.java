package cn.neday.iflytek.service;

import cn.neday.iflytek.mapper.LocationMapper;
import cn.neday.iflytek.service.impl.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements LocationServiceImpl {

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public int insert(String encryptLngLat, boolean isSuccess, String time) {
        return locationMapper.insert(encryptLngLat, isSuccess, time);
    }

}
