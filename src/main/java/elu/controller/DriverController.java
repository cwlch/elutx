package elu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import elu.model.Areas;
import elu.model.DriverRecord;
import elu.model.UserRecord;
import elu.service.AreasService;
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
	
	@Autowired
	private AreasService areasService;
	
	@RequestMapping(value = "publishCars", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String publishRequire(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String text=RRUtil.para2Json(request);
		DriverRecord record=JSON.parseObject(text, DriverRecord.class);
		
		//lzx 判断司机发布行程是否在 之前发布时间的前后  半个小时后
		List<DriverRecord> list = userService.checkStartRuntime(record);
		if(list != null && list.size() > 0){
			resMap.put("retCode", "400");
			resMap.put("retMsg", "发布时间半个小时内已经发布订单，不能发布");
			System.out.println("判断司机发布行程是否在 之前发布时间的前后  半个小时已经发布订单，不能发布");
		}else{
			userService.publishInfo(record);
			resMap.put("retCode", "200");
			resMap.put("retMsg", "发布成功！");
			System.out.println("司机发布信息成功！");
		}
		
		return RRUtil.getJsonRes(request,resMap);
	}
	
	@RequestMapping(value = "queryCar", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryCar(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
//		Map<String,Object> map=RRUtil.para2Map(request);
		Map<String,Object> map=RRUtil.para2LimitMap(request);
		if(map.containsKey("uid")){
			User user=userService.queryUserByUId((String) map.get("uid"));
			map.put("userId",user.getId());
		}
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
	
	
	/**
	 * 修改司机行程状态
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateDriverRecordStatus", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateDriverRecordStatus(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		int id=Integer.valueOf(request.getParameter("id"));
		int status = Integer.valueOf(request.getParameter("status"));
		DriverRecord driverRecord = new DriverRecord();
		driverRecord.setId(id);
		driverRecord.setdStatus(status);
	    boolean flag = userService.updateDriverRecordStatus(driverRecord);
	    resMap.put("flag", flag);
		return RRUtil.getJsonRes(request,resMap);
	}
	
	/**
	 * 查询司机的匹配任务列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryDriverRecordMatchList", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryDriverRecordMatchList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		String d_start = request.getParameter("d_start");
		String d_end = request.getParameter("d_end");
		String runtime = request.getParameter("runtime");
		Areas a_area =  areasService.queryParentById(d_start);
		Areas d_area =  areasService.queryParentById(d_end);
		List list = userService.queryDriverRecordMatchList(d_start,d_end,runtime);
		
		resMap.put("list", list);
		return RRUtil.getJsonRes(request,resMap);
		
	}
	
}
