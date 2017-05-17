package elu.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import elu.common.TicktCache;
import elu.util.HttpRequestUtil;

@Component  
public class UpdateCacheTask {
	

	
    @Scheduled(cron="0 0 0/2 * * ?") //间隔2小时执行  
    public void taskCycle(){  
    	
    	 //获取token
    	 String token = HttpRequestUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token","grant_type=client_credential&appid=wx66ffeb28c23fa9fb&secret=8c26eb56a87fb2826051562fa9a9fd34");
    	 JSONObject tokenObj = JSON.parseObject(token);
    	 String access_token = tokenObj.getString("access_token");
    	 System.out.println("获取token的url==https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx66ffeb28c23fa9fb&secret=8c26eb56a87fb2826051562fa9a9fd34");
    	 System.out.println("返回token结果=="+access_token);
    	 //获取ticket
    	 String ticketStr = HttpRequestUtil.sendGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket","access_token="+access_token+"&type=jsapi");
    	 JSONObject ticketObj = JSON.parseObject(ticketStr);
    	 String ticket = ticketObj.getString("ticket");
    	 System.out.println("获取ticketUrl==https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi");
    	 System.out.println("返回tikcet结果=="+ticket);
    	 
    	 TicktCache.setTicket(ticket);
    } 
   
  
 
}  
