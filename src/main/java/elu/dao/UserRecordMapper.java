package elu.dao;

import java.util.List;
import java.util.Map;

import elu.model.UserRecord;

public interface UserRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRecord record);

    int insertSelective(UserRecord record);

    UserRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRecord record);

    int updateByPrimaryKey(UserRecord record);
    
    List<UserRecord> selectByValue(Map map);
}