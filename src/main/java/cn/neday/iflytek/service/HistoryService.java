package cn.neday.iflytek.service;

import cn.neday.iflytek.domain.History;
import cn.neday.iflytek.mapper.HistoryMapper;
import cn.neday.iflytek.service.impl.HistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService implements HistoryServiceImpl {

    @Autowired
    private HistoryMapper historyMapper;

    public List<History> findAll() {
        return historyMapper.findAll();
    }

    public List<History> findByUserAccount(String userAccount) {
        return historyMapper.findByUserAccount(userAccount);
    }

    @Override
    public int insert(String UserAccount, boolean isSuccess, boolean isAuto, String time) {
        return historyMapper.insert(UserAccount, isSuccess, isAuto, time);
    }

}
