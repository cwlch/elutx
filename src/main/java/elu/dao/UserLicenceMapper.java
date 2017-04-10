package elu.dao;

import elu.model.UserLicence;

import java.util.List;
import java.util.Map;

public interface UserLicenceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLicence record);

    int insertSelective(UserLicence record);

    UserLicence selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLicence record);

    int updateByPrimaryKey(UserLicence record);
    
    UserLicence selectByUid(Integer userId);

    List<UserLicence> selectByValue(Map map);
}