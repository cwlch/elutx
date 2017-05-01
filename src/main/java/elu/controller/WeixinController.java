package elu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elu.util.RegUtil;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import elu.model.User;
import elu.service.UserService;
import elu.util.HttpRequestUtil;
import elu.util.RRUtil;
import elu.util.ToolUtil;

/**
 * Created by Ch on 17/2/20.
 */



@Controller
@RequestMapping(value = "weixin")
public class WeixinController {
	
	@Autowired
	private UserService userService;
 	
	
    @RequestMapping(value = "checkWx", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkWx(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> resMap=RRUtil.getStandardMap();
        Map<String,Object> map=RRUtil.para2Map(request);
        String signature = String.valueOf(map.get("signature"));
        String timestamp = String.valueOf(map.get("timestamp"));
        String nonce = String.valueOf(map.get("nonce"));
        String echostr = String.valueOf(map.get("echostr"));
        if (ToolUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        System.out.println("2222222666");
        return null;
    }

    @RequestMapping(value = "authWx", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String authWx(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> resMap=RRUtil.getStandardMap();
        Map<String,Object> map=RRUtil.para2Map(request);
        String code = String.valueOf(map.get("code"));
        String state = String.valueOf(map.get("state"));
        System.out.println(map);
        //获取access_token openId
        String appInfo=HttpRequestUtil.sendGet("https://api.weixin.qq.com/sns/oauth2/access_token",
                "appid=wx66ffeb28c23fa9fb&secret=8c26eb56a87fb2826051562fa9a9fd34&code="+ code +"&grant_type=authorization_code ");

        JSONObject appInfoObj = JSON.parseObject(appInfo);
 
        System.out.println(appInfo);
        System.out.println("--------------------------------------");
        System.out.println(appInfoObj);
        
        String openId = appInfoObj.getString("openid");
        String accessToken = appInfoObj.getString("access_token");
        String infoPar = "access_token="+ accessToken +"&openid="+ openId +"&lang=zh_CN";
        
     
        //获取用户信息
        System.out.println(infoPar);
        String userInfo = HttpRequestUtil.sendGet("https://api.weixin.qq.com/sns/userinfo",infoPar );
        System.out.println("http://api.weixin.qq.com/sns/userinfo?"+infoPar );
        System.out.println("--------------------------------------");
        System.out.println(userInfo);
        JSONObject obj = JSONObject.parseObject(userInfo);
        
        //用户注册
        User userModel = userService.queryUserByUId(openId);
        
        if(userModel == null && !StringUtils.isEmpty(openId)){
        	System.out.println("用户未注册，需保存用户信息");
        	//新增用户
            User user = new User();
            user.setUid(obj.getString("openid"));
            user.setUserName(RegUtil.replaceSpecStr(obj.getString("nickname")));
            user.setGender(obj.getInteger("sex"));
            String address = obj.getString("province") +"-" +obj.getString("city") + "-"+obj.getString("country");
            user.setHomeStr(address);
            user.setPhotoUrl( obj.getString("headimgurl"));
            userService.addUser(user);
            System.out.println("保存成功");
        }
        
        request.getSession().setAttribute("uid", openId);
        
        System.out.println("-------");
        try {
            if(state.equals("passenger")){
                response.sendRedirect("http://www.elutx.cn/index.html#!/passenger");
            }else{
                response.sendRedirect("http://www.elutx.cn/index.html#!/driver");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
