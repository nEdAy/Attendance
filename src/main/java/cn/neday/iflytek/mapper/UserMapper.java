package cn.neday.iflytek.mapper;


import cn.neday.iflytek.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Select("SELECT * FROM user WHERE UserAccount = #{UserAccount}")
    User findByUserAccount(@Param("UserAccount") String UserAccount);

    @Update("UPDATE user SET Token=#{Token} WHERE UserAccount=#{UserAccount}")
    void updateTokenByUserAccount(@Param("Token") String Token, @Param("UserAccount") String UserAccount);

    @Update("UPDATE user SET goToWorkCheckTime=#{goToWorkCheckTime} WHERE UserAccount=#{UserAccount}")
    void updateGoToWorkCheckTimeByUserAccount(@Param("goToWorkCheckTime") String goToWorkCheckTime, @Param("UserAccount") String UserAccount);

    @Update("UPDATE user SET goOffWorkCheckTime=#{goOffWorkCheckTime} WHERE UserAccount=#{UserAccount}")
    void updateGoOffWorkCheckTimeByUserAccount(@Param("goOffWorkCheckTime") String goOffWorkCheckTime, @Param("UserAccount") String UserAccount);

    @Update("UPDATE user SET goToWorkScheduleTime=#{goToWorkScheduleTime} WHERE UserAccount=#{UserAccount}")
    void updateGoToWorkScheduleTimeByUserAccount(@Param("goToWorkScheduleTime") String goToWorkScheduleTime, @Param("UserAccount") String UserAccount);

    @Update("UPDATE user SET goOffWorkScheduleTime=#{goOffWorkScheduleTime} WHERE UserAccount=#{UserAccount}")
    void updateGoOffWorkScheduleTimeByUserAccount(@Param("goOffWorkScheduleTime") String goOffWorkScheduleTime, @Param("UserAccount") String UserAccount);

}
