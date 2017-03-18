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

import elu.model.CarInfo;
import elu.service.CarService;
import elu.util.RRUtil;
import elu.util.ToolUtil;
 

/**
 * lzx
 * @author zhixinli5
 *
 */
@Controller
@RequestMapping(value = "carController")
public class CarController {
	
	@Autowired
	private CarService carService;
 	
	/**
	 * 查询汽车品牌列表
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = "queryCarBrandList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String queryCarList(HttpServletRequest request, HttpServletResponse response) {
    	HashMap<String, Object> resMap=RRUtil.getStandardMap();
        List<CarInfo> list = carService.queryCarBrandList();
        resMap.put("carList", list);
		return RRUtil.getJsonRes(request,resMap);
    }
    
	/**
	 * 查询汽车品牌分类列表
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = "queryCarTypeList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String queryCarTypeList(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> resMap=RRUtil.getStandardMap();
        Map<String,Object> map=RRUtil.para2Map(request);
        String makeName = String.valueOf(map.get("makeName"));
        List<CarInfo> list = carService.queryCarTypeList(makeName);
        resMap.put("carTypeList", list);
		return RRUtil.getJsonRes(request,resMap);
    }

  
}
