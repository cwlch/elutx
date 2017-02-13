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

import elu.model.DriverRecord;
import elu.model.UserRecord;
import elu.service.UserService;
import elu.util.RRUtil;

/** 
* @author liangt 
* @email tianliang211@sina.com
* @createTime：2017年1月2日 下午3:40:53 
* @desc 
*/
@Controller
@RequestMapping(value = "driver")
public class DriverController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "publishCars", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String publishRequire(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		DriverRecord record=JSON.parseObject(text, DriverRecord.class);
		userService.publishInfo(record);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "queryCar", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryCar(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
//		Map<String,Object> map=RRUtil.para2Map(request);
		Map<String,Object> map=RRUtil.para2LimitMap(request);
		List<DriverRecord> list=userService.queryDriverRecord(map);
		resMap.put("result", list);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "queryCarDetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryDetail(HttpServletRequest request, HttpServletResponse response) {
		int userId=Integer.valueOf(request.getParameter("userId"));
		int carId=Integer.valueOf(request.getParameter("carId"));
		int recordId=Integer.valueOf(request.getParameter("recordId"));
		HashMap<String, Object> resMap=userService.queryCarDetail(userId, recordId, carId);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "queryCarBrand", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryCarBrand(HttpServletRequest request, HttpServletResponse response) {
		int userId=Integer.valueOf(request.getParameter("userId"));
		int carId=Integer.valueOf(request.getParameter("carId"));
		int recordId=Integer.valueOf(request.getParameter("recordId"));
		HashMap<String, Object> resMap=userService.queryCarDetail(userId, recordId, carId);
		return RRUtil.getJsonRes(request,resMap);
	}

}
