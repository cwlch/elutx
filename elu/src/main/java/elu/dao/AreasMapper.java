package elu.dao;

import java.util.List;
import java.util.Map;

import elu.model.Areas;

public interface AreasMapper {
    int deleteByPrimaryKey(String id);

    int insert(Areas record);

    int insertSelective(Areas record);

    Areas selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Areas record);

    int updateByPrimaryKey(Areas record);
    
    List<Areas> selectByValue(Map map);
}