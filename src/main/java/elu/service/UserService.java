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
import elu.model.DriverRecord;
import elu.model.User;
import elu.model.UserLicence;
import elu.model.UserRecord;
import elu.model.UserVal;
import elu.util.RRUtil;

/**
 * @author liangt
 * @email tianliang211@sina.com
 * @createTime：2017年1月2日 下午3:42:52
 * @desc
 */
@Service("userService")
public class UserService {

	@Autowired
	private UserMapper userDao;

	@Autowired
	private UserValMapper userValDao;
	
	@Autowired
	private UserLicenceMapper userLicenceDao;

	@Autowired
	private UserRecordMapper userRecordDao;

	@Autowired
	private DriverRecordMapper driverRecordDao;

	@Autowired
	private CarMapper carDao;

	public int publishInfo(UserRecord record) {
		record.setCreateTime(System.currentTimeMillis());
		record.setuStatus(0);
		return userRecordDao.insert(record);
	}

	public List<UserRecord> queryUserRecord(Map<String, Object> map) {
		if (map.containsKey("uDate")) {
			long startTime = Long.valueOf((String) map.get("uDate"));
			long endTime = calcEndTime(startTime);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
		}
		return userRecordDao.selectByValue(map);
	}

	public List<DriverRecord> queryDriverRecord(Map<String, Object> map) {
		if (map.containsKey("dDate")) {
			long startTime = Long.valueOf((String) map.get("dDate"));
			long endTime = calcEndTime(startTime);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
		}
		return driverRecordDao.selectByValue(map);
	}

	public int publishInfo(DriverRecord record) {
		record.setCreateTime(System.currentTimeMillis());
		record.setdStatus(0);
		return driverRecordDao.insert(record);
	}

	public HashMap<String, Object> queryRequireDetail(int userId, int recordId) {
		HashMap<String, Object> map = RRUtil.getStandardMap();
		User user = userDao.selectByPrimaryKey(userId);
		UserVal userVal = userValDao.selectByPrimaryKey(userId);
		UserRecord record = userRecordDao.selectByPrimaryKey(recordId);
		map.put("user", user);
		map.put("userVal", userVal);
		map.put("record", record);
		return map;
	}

	public HashMap<String, Object> queryCarDetail(int userId, int recordId, int carId) {
		HashMap<String, Object> map = RRUtil.getStandardMap();
		User user = userDao.selectByPrimaryKey(userId);
		Car car = carDao.selectByPrimaryKey(carId);
		DriverRecord record = driverRecordDao.selectByPrimaryKey(recordId);
		map.put("user", user);
		map.put("car", car);
		map.put("record", record);
		return map;
	}

	public long calcEndTime(long startTime) {
		return startTime + 24 * 60 * 60 * 1000;
	}

	public int addUser(User user) {
		if (queryUserByUId(user.getUid()) == null) {
			user.setIsStop(0);
			user.setCreateTime(System.currentTimeMillis());
			return userDao.insert(user);
		}else{
			return 0;
		}
	}

	public int updateUser(User user) {
		return userDao.updateByPrimaryKeySelective(user);
	}

	public User queryUserByUId(String uid) {
		return userDao.selectByUid(uid);
	}
	
	public User queryUserById(int id){
		return userDao.selectByPrimaryKey(id);
	}
	
	public int addUserLicence(UserLicence userLicence){
		UserLicence tmpLicence=queryUserLicenceByUId(userLicence.getUserId());
		if(tmpLicence==null){
			return userLicenceDao.insert(userLicence);
		}else{
			userLicence.setId(tmpLicence.getId());
			return userLicenceDao.updateByPrimaryKeySelective(userLicence);
		}
		
	}
	
	public int updateUserLicence(UserLicence userLicence){
		return userLicenceDao.updateByPrimaryKeySelective(userLicence);
	}
	
	public UserLicence queryUserLicenceByUId(int userId) {
		return userLicenceDao.selectByUid(userId);
	}
	
	public int addCar(Car car){
		Car tmpCar=queryCarByUId(car.getUserId());
		if(tmpCar==null){
			return carDao.insert(car);
		}else{
			car.setId(tmpCar.getId());
			return carDao.updateByPrimaryKey(car);
		}
		
	}
	
	public int updateCar(Car car){
		return carDao.updateByPrimaryKeySelective(car);
	}
	
	public Car queryCarByUId(int userId) {
		return carDao.selectByUid(userId);
	}
	
	
	
	

}
