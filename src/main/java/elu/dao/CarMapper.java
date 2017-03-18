package elu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	List<CarInfo> queryCarBrandList();

	List<CarInfo> queryCarTypeList(@Param("makeName") String makeName);
}