package cn.neday.iflytek.mapper;


import cn.neday.iflytek.domain.History;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HistoryMapper {

    @Select("SELECT * FROM history")
    List<History> findAll();

    @Select("SELECT * FROM history WHERE UserAccount = #{UserAccount}")
    List<History> findByUserAccount(@Param("UserAccount") String UserAccount);

    @Insert("INSERT INTO history(UserAccount, isSuccess, isAuto, time) VALUES(#{UserAccount}, #{isSuccess}, #{isAuto}, #{time})")
    int insert(@Param("UserAccount") String UserAccount, @Param("isSuccess") boolean isSuccess, @Param("isAuto") boolean isAuto, @Param("time") String time);

}
