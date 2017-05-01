package elu.dao;


import elu.model.ActUser;

import java.util.List;
import java.util.Map;

public interface ActUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActUser record);

    int insertSelective(ActUser record);

    ActUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActUser record);

    int updateByPrimaryKey(ActUser record);

    List<ActUser> selectByValue(Map map);
}