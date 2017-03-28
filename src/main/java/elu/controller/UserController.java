package elu.controller;

import java.util.Date;
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

import elu.model.Areas;
import elu.model.Car;
import elu.model.User;
import elu.model.UserLicence;
import elu.model.UserRecord;
import elu.model.VerifyCode;
import elu.service.AreasService;
import elu.service.UserService;
import elu.service.VerifyCodeService;
import elu.util.Base64ImgUtil;
import elu.util.RRUtil;
import elu.util.ToolUtil;
import elu.util.sendSmsUtil;

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
	
	@Autowired
	private AreasService areasService;
	
	@Autowired
	private VerifyCodeService verifyCodeService;
	
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
		if(map.containsKey("uid")){
			User user=userService.queryUserByUId((String) map.get("uid"));
			map.put("userId",user.getId());
		}
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
        resMap.put("car",car);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "queryUserInfo", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryUserInfo(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String uid=(String)request.getSession().getAttribute("uid");
		System.out.println("uid=="+uid);
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
		resMap.put("car",car);
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
	
	/**
	 * 修改用户行程状态
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateUserRecordStatus", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateUserRecordStatus(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		int id=Integer.valueOf(request.getParameter("id"));
		int status = Integer.valueOf(request.getParameter("status"));
		UserRecord userRecord  = new UserRecord();
		userRecord.setId(id);
		userRecord.setuStatus(status);
	    boolean flag = userService.updateUserRecordStatus(userRecord);
	    if(flag){
	    	resMap.put("retCode", "200");
			resMap.put("retMsg", "状态更新成功！");
	    }else{
	    	resMap.put("retCode", "400");
			resMap.put("retMsg", "状态更新失败！");
	    }
		return RRUtil.getJsonRes(request,resMap);
	}
	
	/**
	 * 查询乘客的匹配任务列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryUserRecordMatchList", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryDriverRecordMatchList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String d_start = request.getParameter("d_start");
		String d_end = request.getParameter("d_end");
		String runtime = request.getParameter("runtime");
		Areas a_area =  areasService.queryParentById(d_start);
		Areas d_area =  areasService.queryParentById(d_end);
		List list = userService.queryUserRecordMatchList(d_start,d_end,runtime);
		
		resMap.put("list", list);
		return RRUtil.getJsonRes(request,resMap);
		
	}
	
	/**
	 * 发送短信验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "sendSms", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String sendSms(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String telNum = request.getParameter("telNum");
		String uid=(String)request.getSession().getAttribute("uid");
		String operateType = "1";
		String code = ToolUtil.getVerifyCode();
		//发送验证码
		sendSmsUtil.sendSms(telNum, code);
		
		VerifyCode oldCode = verifyCodeService.queryVerifyCodeByUid(uid);
		boolean flag = false;
		if(oldCode != null){//修改
			oldCode.setVfCode(code);
			oldCode.setSendtimes(oldCode.getSendtimes()+1);
			oldCode.setUpdateTime((new Date()).getTime());
			flag = verifyCodeService.updateVfCodeById(oldCode);
		}else{//新增
			VerifyCode verifyCode  = new VerifyCode();
			verifyCode.setUid(uid);
			verifyCode.setVfCode(code);
			verifyCode.setSendtimes(0);
			verifyCode.setVfType(operateType);
			verifyCode.setCreateTime((new Date()).getTime());
			verifyCode.setUpdateTime((new Date()).getTime());
			flag = verifyCodeService.insertVfCode(verifyCode);
		}
		
		if(flag){
			resMap.put("retCode", "200");
			resMap.put("retMsg", "验证码发送成功！");
		}else{
			resMap.put("retCode", "400");
			resMap.put("retMsg", "验证码发送失败！");
		}
		
		return RRUtil.getJsonRes(request,resMap);
	}
	
	/**
	 * 验证短信验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "checkSms", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String checkSms(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String code = request.getParameter("verifyCode");
		String telNum =  request.getParameter("telNum");
		String uid=(String)request.getSession().getAttribute("uid");
		VerifyCode oldCode = verifyCodeService.queryVerifyCodeByUid(uid);
		User user=userService.queryUserByUId(uid);
		
		if(code != null && code.equals(oldCode.getVfCode())){
			User userInfo = new User();
			userInfo.setId(user.getId());
			userInfo.setPhone(telNum);
			int count = userService.updateUser(userInfo);
			if(count > 0){
				resMap.put("retCode", "200");
				resMap.put("retMsg", "验证成功！");
				System.out.print("验证成功！");
			}else{
				resMap.put("retCode", "400");
				resMap.put("retMsg", "用户信息不存在！");
				System.out.print("验证码正确，用户信息不存在！");
			}
		}else{
			resMap.put("retCode", "400");
			resMap.put("retMsg", "验证码不正确！");
			System.out.print("验证码："+code+"，验证码不正确！");
		}
		
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "checkCarLicence", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String checkCarLicence(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		
		Map<String,Object> map=RRUtil.para2LimitMap(request);		
		String uid=(String)request.getSession().getAttribute("uid");
	    String status = request.getParameter("status");
	    String remark = (String)map.get("remark");
	    
		User user=userService.queryUserByUId(uid);
        Integer id = user.getId();
        UserLicence userLicence = userService.queryUserLicenceByUId(id);
        Car car = userService.queryCarByUId(id);
        
        Car carStatus = new Car();
        carStatus.setStatus(Integer.parseInt(status));
        carStatus.setId(car.getId());
        carStatus.setRemark(remark);
        userService.updateCar(carStatus);
        
        UserLicence licence = new UserLicence();
        licence.setId(userLicence.getId());
        licence.setStatus(Integer.parseInt(status));
        licence.setRemark(remark);
        userService.updateUserLicence(licence);
        
    	resMap.put("retCode", "200");
		resMap.put("retMsg", "审核成功！");
		
		return RRUtil.getJsonRes(request,resMap);
	}
	
	
}
