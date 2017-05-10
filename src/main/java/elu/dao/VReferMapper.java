package elu.dao;

import java.util.List;

import elu.model.VRefer;

public interface VReferMapper {
    int insert(VRefer record);

    int insertSelective(VRefer record);
    
    List<VRefer> selectLimit();
}