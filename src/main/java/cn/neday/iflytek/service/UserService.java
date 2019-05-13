package cn.neday.iflytek.service;

import cn.neday.iflytek.domain.User;
import cn.neday.iflytek.mapper.UserMapper;
import cn.neday.iflytek.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findByUserAccount(String userAccount) {
        return userMapper.findByUserAccount(userAccount);
    }

    @Override
    public void updateTokenByUserAccount(String Token, String UserAccount) {
        userMapper.updateTokenByUserAccount(Token, UserAccount);
    }

    @Override
    public void updateGoToWorkCheckTimeByUserAccount(String goToWorkCheckTime, String UserAccount) {
        userMapper.updateGoToWorkCheckTimeByUserAccount(goToWorkCheckTime, UserAccount);
    }

    @Override
    public void updateGoOffWorkCheckTimeByUserAccount(String goOffWorkCheckTime, String UserAccount) {
        userMapper.updateGoOffWorkCheckTimeByUserAccount(goOffWorkCheckTime, UserAccount);
    }

    @Override
    public void updateGoToWorkScheduleTimeByUserAccount(String goToWorkScheduleTime, String UserAccount) {
        userMapper.updateGoToWorkScheduleTimeByUserAccount(goToWorkScheduleTime, UserAccount);
    }

    @Override
    public void updateGoOffWorkScheduleTimeByUserAccount(String goOffWorkScheduleTime, String UserAccount) {
        userMapper.updateGoOffWorkScheduleTimeByUserAccount(goOffWorkScheduleTime, UserAccount);
    }

}
