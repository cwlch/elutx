package elu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elu.dao.CarMapper;
import elu.dao.DriverRecordMapper;
import elu.dao.UserLicenceMapper;
import elu.dao.UserMapper;
import elu.dao.UserRecordMapper;
import elu.dao.UserValMapper;
import elu.model.Car;
import elu.model.CarInfo;
import elu.model.DriverRecord;
import elu.model.User;
import elu.model.UserLicence;
import elu.model.UserRecord;
import elu.model.UserVal;
import elu.util.RRUtil;

/**
 * 
 * @author zhixinli5
 *
 */
@Service("carService")
public class CarService {

 
	@Autowired
	private CarMapper carDao;

	public List<CarInfo> queryCarBrandList() {
		 List list = carDao.queryCarBrandList();
		return list;
	}

	
	public List<CarInfo> queryCarTypeList(String makeName) {
		 List list = carDao.queryCarTypeList(makeName);
		return list;
	}

	

}
