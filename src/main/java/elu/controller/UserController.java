package elu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import elu.model.Car;
import elu.model.User;
import elu.model.UserLicence;
import elu.model.UserRecord;
import elu.service.UserService;
import elu.util.Base64ImgUtil;
import elu.util.RRUtil;

/** 
* @author liangt 
* @email tianliang211@sina.com
* @createTime：2017年1月2日 下午3:41:26 
* @desc 
*/

@Controller
@RequestMapping(value = "user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "publishRequire", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String publishRequire(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		UserRecord record=JSON.parseObject(text, UserRecord.class);
		userService.publishInfo(record);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	
	@RequestMapping(value = "queryRequire", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryRequire(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		Map<String,Object> map=RRUtil.para2LimitMap(request);		
		List<UserRecord> list=userService.queryUserRecord(map);
		resMap.put("result", list);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	
	@RequestMapping(value = "queryRequireDetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryDetail(HttpServletRequest request, HttpServletResponse response) {
		int userId=Integer.valueOf(request.getParameter("userId"));
		int recordId=Integer.valueOf(request.getParameter("recordId"));
		HashMap<String, Object> resMap=userService.queryRequireDetail(userId, recordId);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "addUser", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String addUser(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		User user=JSON.parseObject(text, User.class);
		userService.addUser(user);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "updateUser", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateUser(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		User user=JSON.parseObject(text, User.class);
		userService.updateUser(user);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "queryUser", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryUser(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String uid=request.getParameter("uid");
		User user=userService.queryUserByUId(uid);
        Integer id = user.getId();
        UserLicence userLicence = userService.queryUserLicenceByUId(id);
        Car car = userService.queryCarByUId(id);
        if(userLicence != null && userLicence.getStatus() == 3 && car.getStatus() == 3){
            resMap.put("status",1);
        }else{
            resMap.put("status",0);
        }
		resMap.put("user", user);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "queryUserInfo", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryUserInfo(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String uid=(String)request.getSession().getAttribute("uid");
		User user=userService.queryUserByUId(uid);
        Integer id = user.getId();
        UserLicence userLicence = userService.queryUserLicenceByUId(id);
        Car car = userService.queryCarByUId(id);
        if(userLicence != null && userLicence.getStatus() == 3 && car.getStatus() == 3){
            resMap.put("status",1);
        }else{
            resMap.put("status",0);
        }
		resMap.put("user", user);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "queryUserDetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryUserDetail(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String uid=request.getParameter("uid");
		User user=userService.queryUserByUId(uid);
		resMap.put("user", user);
		UserLicence userLicence=userService.queryUserLicenceByUId(user.getId());
		resMap.put("userLicence", userLicence);
		Car car=userService.queryCarByUId(user.getId());
		resMap.put("car", car);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "addCarAndLicence", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String addCarAndLicence(HttpServletRequest request, HttpServletResponse response) {
		String path="/alidata/server/tomcat-7.0.54/webapps/images/";
		response.setHeader("Access-Control-Allow-Origin", "*");
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		Car car=JSON.parseObject(text, Car.class);
		String carImgName=car.getUserId()+"_car.jpg";
		String liceneceImgName=car.getUserId()+"_licence.jpg";
		String carImgCode=request.getParameter("carImgCode");
		if(carImgCode!=null && "".equals(carImgCode)){
			Base64ImgUtil.GenerateImage(carImgCode, path + carImgName);
		}
		String liceneceImgCode=request.getParameter("liceneceImgCode");
		if(liceneceImgCode!=null && "".equals(liceneceImgCode)){
			Base64ImgUtil.GenerateImage(liceneceImgCode, path + liceneceImgName);
		}
		car.setCreateTime(System.currentTimeMillis());
		car.setIsStop(0);
		car.setStatus(1);
		car.setRegImg(carImgName);
		System.out.println(text);
		userService.addCar(car);
		UserLicence userLicence=JSON.parseObject(text, UserLicence.class);
		userLicence.setLicenceImg(liceneceImgName);
		userLicence.setCreateTime(System.currentTimeMillis());
		userLicence.setStatus(1);
		userLicence.setLicenceImg(liceneceImgName);
		userService.addUserLicence(userLicence);
		return RRUtil.getJsonRes(request,resMap);
	}
	@RequestMapping(value = "updateCarAndLicence", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateCarAndLicence(HttpServletRequest request, HttpServletResponse response) {
		String path="/alidata/server/tomcat-7.0.54/webapps/images/";
		response.setHeader("Access-Control-Allow-Origin", "*");
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		Car car=JSON.parseObject(text, Car.class);
		String carImgName=car.getUserId()+"_car.jpg";
		String liceneceImgName=car.getUserId()+"_licence.jpg";
		String carImgCode=request.getParameter("carImgCode");
		int carId=Integer.valueOf(request.getParameter("carId"));
		car.setId(carId);
		if(carImgCode!=null && "".equals(carImgCode)){
			Base64ImgUtil.GenerateImage(carImgCode, path + carImgName);
		}
		String liceneceImgCode=request.getParameter("liceneceImgCode");
		if(liceneceImgCode!=null && "".equals(liceneceImgCode)){
			Base64ImgUtil.GenerateImage(liceneceImgCode, path + liceneceImgName);
		}
		car.setCreateTime(System.currentTimeMillis());
//		car.setIsStop(0);
//		car.setStatus(0);
		car.setRegImg(carImgName);
		String carStatus=request.getParameter("carStatus");
		if(!"3".equals(carStatus)){
			car.setStatus(1);
			userService.updateCar(car);
		}
		UserLicence userLicence=JSON.parseObject(text, UserLicence.class);
		int userLicenceId=Integer.valueOf(request.getParameter("userLicenceId"));
		userLicence.setId(userLicenceId);
		userLicence.setLicenceImg(liceneceImgName);
		userLicence.setCreateTime(System.currentTimeMillis());
//		userLicence.setStatus(0);
		userLicence.setLicenceImg(liceneceImgName);
		String licenceStatus=request.getParameter("licenceStatus");
		if(!"3".equals(licenceStatus)){
			userLicence.setStatus(1);
			userService.updateUserLicence(userLicence);
		}
//		userService.addUserLicence(userLicence);
		return RRUtil.getJsonRes(request,resMap);
	}

	
	@RequestMapping(value = "addUserLicence", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String addUseLicence(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		UserLicence userLicence=JSON.parseObject(text, UserLicence.class);
		userService.addUserLicence(userLicence);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "updateUserLicence", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateUseLicence(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		UserLicence userLicence=JSON.parseObject(text, UserLicence.class);
		userService.updateUserLicence(userLicence);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	
	@RequestMapping(value = "addCar", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String addCar(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		Car car=JSON.parseObject(text, Car.class);
		userService.addCar(car);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "updateCar", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateCar(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		Car car=JSON.parseObject(text, Car.class);
		userService.updateCar(car);
		return RRUtil.getJsonRes(request,resMap);
	}

	@RequestMapping(value = "verifyStatus", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String verifyStatus(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		Map<String,Object> map=RRUtil.para2Map(request);
		Integer id= Integer.valueOf((String) map.get("id"));
		UserLicence userLicence = userService.queryUserLicenceByUId(id);
		Car car = userService.queryCarByUId(id);
		if(userLicence.getStatus() == 3 && car.getStatus() == 3){
			resMap.put("status",1);
		}else{
			resMap.put("status",0);
		}
		return RRUtil.getJsonRes(request,resMap);
	}
}
