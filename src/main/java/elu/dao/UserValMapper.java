package elu.dao;

import elu.model.UserVal;

public interface UserValMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserVal record);

    int insertSelective(UserVal record);

    UserVal selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserVal record);

    int updateByPrimaryKey(UserVal record);
}