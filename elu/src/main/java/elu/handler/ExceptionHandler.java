package elu.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import elu.util.RRUtil;

/**
 * @author liangt
 * @email tianliang211@sina.com
 * @createTime：2016年8月15日 上午10:45:30
 * @desc
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger("leaWebLogger");

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("异常信息：", ex);
		ex.printStackTrace();
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
	        HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("retCode", "500");
			map.put("retMsg", "网络错误");
			String res=RRUtil.getJsonRes(request, map);
			out.print(res);
			out.flush();
			out.close();
			StringWriter errorsWriter = new StringWriter();  
	        ex.printStackTrace(new PrintWriter(errorsWriter));
	        logger.debug(errorsWriter.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
