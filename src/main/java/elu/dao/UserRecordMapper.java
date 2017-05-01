package elu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import elu.model.DriverRecord;
import elu.model.UserRecord;

public interface UserRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRecord record);

    int insertSelective(UserRecord record);

    UserRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRecord record);

    int updateByPrimaryKey(UserRecord record);
    
    List<UserRecord> selectByValue(Map map);

    List<UserRecord> queryUserRecordqLike(Map map);

	List checkStartRuntime(DriverRecord record);

	List queryUserRecordMatchList(@Param("d_start") String d_start, @Param("d_end") String d_end, @Param("runtime") String runtime);

	List<UserRecord> queryUserRecordList();

	List checkUserStartRuntime(UserRecord record);
}