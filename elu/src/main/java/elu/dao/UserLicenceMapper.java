package elu.dao;

import elu.model.UserLicence;

public interface UserLicenceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLicence record);

    int insertSelective(UserLicence record);

    UserLicence selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLicence record);

    int updateByPrimaryKey(UserLicence record);
    
    UserLicence selectByUid(Integer userId);
}