package elu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import elu.model.DriverRecord;

public interface DriverRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverRecord record);

    int insertSelective(DriverRecord record);

    DriverRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DriverRecord record);

    int updateByPrimaryKey(DriverRecord record);
    
    List<DriverRecord> selectByValue(Map map);

	List queryDriverRecordMatchList(@Param("d_start") String d_start, @Param("d_end") String d_end, @Param("runtime") String runtime);
}