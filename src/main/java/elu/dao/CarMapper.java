package elu.dao;

import java.util.List;

import elu.model.Car;
import elu.model.CarInfo;

public interface CarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Car record);

    int insertSelective(Car record);

    Car selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Car record);

    int updateByPrimaryKey(Car record);
    
    Car selectByUid(Integer userId);

	List<CarInfo> queryCarInfoList();
}