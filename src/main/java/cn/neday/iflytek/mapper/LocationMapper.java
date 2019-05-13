package cn.neday.iflytek.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LocationMapper {

    @Insert("INSERT INTO location(lngLat, isSuccess, time) VALUES(#{lngLat}, #{isSuccess}, #{time})")
    int insert(@Param("lngLat") String lngLat, @Param("isSuccess") boolean isSuccess, @Param("time") String time);

}
