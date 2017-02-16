package elu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import elu.model.Areas;
import elu.service.AreasService;
import elu.util.RRUtil;

/** 
* @author liangt 
* @email tianliang211@sina.com
* @createTime：2017年1月2日 下午3:07:40 
* @desc 
*/
@Controller
@RequestMapping(value = "areas")
public class AreasController {
	
	
	private static Logger logger = LoggerFactory.getLogger("eluLogger");
	
	@Autowired
	private AreasService areasService;
	
	@RequestMapping(value = "queryAreas", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryAreas(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> resMap=RRUtil.getStandardMap();
		Map<String,Object> map=RRUtil.para2Map(request);
		List<Areas> list=areasService.queryAreas(map);
		resMap.put("result", list);
		return RRUtil.getJsonRes(request,resMap);
	}

}
