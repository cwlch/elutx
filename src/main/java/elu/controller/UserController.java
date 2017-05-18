package elu.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elu.common.TicktCache;
import elu.model.*;
import elu.service.ActUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import elu.service.AreasService;
import elu.service.UserService;
import elu.service.VReferService;
import elu.service.VerifyCodeService;
import elu.util.Base64ImgUtil;
import elu.util.RRUtil;
import elu.util.Sign;
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

	private static Logger logger = LoggerFactory.getLogger("eluLogger");


	@Autowired
	private UserService userService;
	
	@Autowired
	private AreasService areasService;
	
	@Autowired
	private VerifyCodeService verifyCodeService;

	@Autowired
	private ActUserService actUserService;
	
	@Autowired
	private VReferService vReferService;
	
	@RequestMapping(value = "publishRequire", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String publishRequire(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		UserRecord record=JSON.parseObject(text, UserRecord.class);
		//lzx 判断司机发布行程是否在 之前发布时间的前后  半个小时后
		List<UserRecord> list = userService.checkUserStartRuntime(record);
		if(list != null && list.size() > 0){
			resMap.put("retCode", "400");
			resMap.put("retMsg", "发布时间半个小时内已经发布订单，不能发布");
			System.out.println("判断乘客发布行程是否在 之前发布时间的前后  半个小时已经发布订单，不能发布");
		}else{
			userService.publishInfo(record);
			resMap.put("retCode", "200");
			resMap.put("retMsg", "发布成功！");
			System.out.println("乘客发布信息成功！");
		}
		
		
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
//		List<UserRecord> list=userService.queryUserRecord(map);
		List<UserRecord> list=userService.queryUserRecordqLike(map);
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
	@RequestMapping(value = "queryPhoneUser", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryPhoneUser(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String phone=request.getParameter("phone");
		User user=userService.queryUserByPhone(phone);
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
//		String uid="o_UN0wlMQJSRpJ2wdqXIgRTxLBeg";//(String)request.getSession().getAttribute("uid");
		String uid=(String)request.getSession().getAttribute("uid");
		System.out.println("uid=="+uid);
		if(uid == null){
			return RRUtil.getJsonRes(request,RRUtil.getUnAuth());
		}
		User user=userService.queryUserByUId(uid);
		if(user == null){
			return RRUtil.getJsonRes(request,RRUtil.getUnAuth());
		}
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
		String path="/usr/share/nginx/elutx/images/";
		response.setHeader("Access-Control-Allow-Origin", "*");
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		Car car=JSON.parseObject(text, Car.class);
		String carImgName=car.getUserId()+"_car.jpg";
		String liceneceImgName=car.getUserId()+"_licence.jpg";
		String carImgCode=request.getParameter("carImgCode");
		if(carImgCode!=null && !"".equals(carImgCode)){
			Base64ImgUtil.GenerateImage(carImgCode, path + carImgName);
		}
		String liceneceImgCode=request.getParameter("liceneceImgCode");
		if(liceneceImgCode!=null && !"".equals(liceneceImgCode)){
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
		String path="/usr/share/nginx/elutx/images/";
		response.setHeader("Access-Control-Allow-Origin", "*");
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		Car car=JSON.parseObject(text, Car.class);
		String carImgName=car.getUserId()+"_car.jpg";
		String liceneceImgName=car.getUserId()+"_licence.jpg";
		String carImgCode=request.getParameter("carImgCode");
		int carId=Integer.valueOf(request.getParameter("carId"));
		car.setId(carId);
		logger.info(carImgCode);
		System.out.println(carImgCode);
		if(carImgCode!=null && !"".equals(carImgCode)){
			Base64ImgUtil.GenerateImage(carImgCode, path + carImgName);
		}
		String liceneceImgCode=request.getParameter("liceneceImgCode");
		logger.info(liceneceImgCode);
		System.out.println(liceneceImgCode);
		if(liceneceImgCode!=null && !"".equals(liceneceImgCode)){
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
		User userInfo = userService.queryUserByPhone(telNum);
		if(userInfo != null){
			resMap.put("retCode", "400");
			resMap.put("retMsg", "telNum号码已注册，不能重复注册。");
		    return RRUtil.getJsonRes(request,resMap);
		}
		
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

	@RequestMapping(value = "updateIdCard", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateIdCard(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap = RRUtil.getStandardMap();
//		String uid="o_UN0wlMQJSRpJ2wdqXIgRTxLBeg";
		String uid = (String) request.getSession().getAttribute("uid");
		System.out.println("uid==" + uid);
		if(uid == null){
			return RRUtil.getJsonRes(request,RRUtil.getUnAuth());
		}
		User user=userService.queryUserByUId(uid);
		if(user == null){
			return RRUtil.getJsonRes(request,RRUtil.getUnAuth());
		}
		if(user.getIdCard()!=null &&  !("".equals(user.getIdCard()))){
			HashMap<String, Object> map = RRUtil.getErrorMap("400", "该身份证号已经参加过此活动");
			return RRUtil.getJsonRes(request, map);
		}
		Map<String,String> actQMap=new HashMap<String, String>();
		actQMap.put("userId",uid);
		List<ActUser> actList=actUserService.selectByValue(actQMap);
		if(actList.size()>0){
			HashMap<String, Object> map = RRUtil.getErrorMap("400", "此用户已经参加过此活动");
			return RRUtil.getJsonRes(request, map);
		}
		String idCard=request.getParameter("idCard");
		Map<String,String> qMap=new HashMap<String, String>();
		qMap.put("idCard",idCard);
		List<User> list=userService.queryUserList(qMap);
		if(list.size()>0){
			HashMap<String, Object> map = RRUtil.getErrorMap("400", "此身份证号已使用");
			return RRUtil.getJsonRes(request, map);
		}
		user.setIdCard(idCard);
		userService.updateUser(user);
		if(user.getCreateTime()>1493136000000l){
//		if(user.getCreateTime()>1){
			ActUser actUser=new ActUser();
			actUser.setActId(1);
			actUser.setUserId(uid);
			actUser.setCreateTime(System.currentTimeMillis());
			actUserService.insert(actUser);
			int queue=actUser.getId();
			resMap.put("queue",queue);
		}
		return RRUtil.getJsonRes(request,resMap);
	}

	@RequestMapping(value = "queryQueueId", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryQueueId(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap = RRUtil.getStandardMap();
		String uid=request.getParameter("userId");
		Map<String,String> actQMap=new HashMap<String, String>();
		actQMap.put("userId",uid);
		List<ActUser> actList=actUserService.selectByValue(actQMap);
		if(actList.size()==1){
			ActUser actUser=actList.get(0);
			resMap.put("queue",actUser.getId());
		}
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "queryRefer", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryRefer(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap = RRUtil.getStandardMap();
		List<VRefer> list=vReferService.selectLimit();
	    resMap.put("list",list);
		return RRUtil.getJsonRes(request,resMap);
	}

	@RequestMapping(value = "getTicket", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getTicket(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap = RRUtil.getStandardMap();
		
	    resMap.put("ticket",TicktCache.getTicket());
		return RRUtil.getJsonRes(request,resMap);
	}
	
	
	@RequestMapping(value = "getSign", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getSign(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap = RRUtil.getStandardMap();
	    String url = request.getParameter("url");
		if(StringUtils.isEmpty(url)){
			url = "www.elutx.cn/elutx/index.html";
		}
		url = "http://" + url;
		
		System.out.println("签名URL====="+url);
		
		String ticket = TicktCache.getTicket();
		Map<String, String> ret = Sign.sign(ticket, url);
	    for (Map.Entry entry : ret.entrySet()) {
            resMap.put(entry.getKey().toString(), entry.getValue());
            System.out.println("签名属性："+entry.getKey() + "--------" + entry.getValue());
        }
	    
		return RRUtil.getJsonRes(request,resMap);
	}
	
}
