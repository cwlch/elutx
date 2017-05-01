package elu.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/** 
* @author liangt 
* @email tianliang211@sina.com
* @createTime：2016年12月15日 下午11:54:43 
* @desc 
*/
public class RRUtil {
	
	private static Logger logger = LoggerFactory.getLogger("eluLogger");
	
	public static void output(HttpServletResponse response,String str){
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, Object> para2Map(HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Map<String, String[]> requstMap = request.getParameterMap();
		Iterator<Entry<String, String[]>> it = requstMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			if (entry.getValue().length > 0) {
				try {
					map.put(entry.getKey(),entry.getValue()[0]);
//					map.put(entry.getKey(), new String(entry.getValue()[0].getBytes("iso8859-1"), "UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
	
	public static String para2Json(HttpServletRequest request) {
		HashMap<String, Object> map =para2Map(request);
		String jsonStr=JSON.toJSONString(map);
//		JSONObject json=JSON.parseObject(jsonStr);
		return jsonStr;
	}
	

	public static HashMap<String, Object> getStandardMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", "200");
		map.put("retMsg", "");
		return map;
	}
	

	
	public static void outResponse(HttpServletResponse response,String res){
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			StringWriter errorsWriter = new StringWriter();  
	        e.printStackTrace(new PrintWriter(errorsWriter));
	        logger.debug(errorsWriter.toString());
			e.printStackTrace();
		}
		out.print(res);
		out.flush();
		out.close();
	}
		
	public static HashMap<String, Object> getValidateNullMap() {
		HashMap<String, Object> map = getErrorMap("101", "非空参数不可为空");
		return map;
	}
	
	public static HashMap<String, Object> getUnAuth() {
		HashMap<String, Object> map = getErrorMap("400", "未登录,请使用微信访问");
		return map;
	}
	
	public static HashMap<String, Object> getErrorMap(String code,String errorMsg) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", code);
		map.put("retMsg",errorMsg);
		return map;
	}
	
	public static String getErrorReturn(String result,String errorMsg){
		return "{\"retCode\":\""+result+"\",\"retMsg\":\""+errorMsg+"\"}";
	}
	
	public static String getJsonRes(HttpServletRequest request,HashMap<String, Object> map){
		String jsonStr=JSON.toJSONString(map);
		if(request.getParameter("callback")!=null){
			String callback=request.getParameter("callback");
			jsonStr=callback+"("+jsonStr+")";
		}
		logger.debug("反馈为："+jsonStr);
		return jsonStr;
	}
	
	public static HashMap<String, Object> para2LimitMap(HttpServletRequest request) {
		HashMap<String, Object> map =para2Map(request);
		if(map.containsKey("page")&&map.containsKey("per")){
			int per=Integer.valueOf((String) map.get("per"));
			int page=Integer.valueOf((String) map.get("page"))*per;
			map.put("page", page);
			map.put("per", per);
		}
		
//		JSONObject json=JSON.parseObject(jsonStr);
		return map;
	}


}
