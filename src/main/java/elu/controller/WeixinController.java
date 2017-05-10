package elu.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elu.model.WeixinMessage;
import elu.service.WeixinMessageService;
import elu.util.*;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import elu.model.User;
import elu.service.UserService;

/**
 * Created by Ch on 17/2/20.
 */



@Controller
@RequestMapping(value = "weixin")
public class WeixinController {
    private static Logger logger = LoggerFactory.getLogger("eluLogger");
	
	@Autowired
	private UserService userService;
	private WeixinMessageService weixinMessageService;

	
    @RequestMapping(value = "checkWx", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkWx(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> resMap=RRUtil.getStandardMap();
        Map<String,Object> map=RRUtil.para2Map(request);

        BufferedReader br= null;
        try {
            br = request.getReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
//        Map<String, String> xmlMap = xmlToMapUtil.xmlToMap(str);
//        System.out.println(xmlMap.get("MsgId"));
//        System.out.println("System get");
//        System.out.println("body:"+xmlMap);
//        logger.info(xmlMap.get("MsgId"));
//        logger.info("get");
//        logger.info("body:"+xmlMap);
//        String ToUserName = xmlMap.get("ToUserName");
//        String FromUserName = xmlMap.get("FromUserName");
//
//        String signature = String.valueOf(map.get("signature"));
//        String timestamp = String.valueOf(map.get("timestamp"));
//        String nonce = String.valueOf(map.get("nonce"));
//        String echostr = String.valueOf(map.get("echostr"));
//        if (ToolUtil.checkSignature(signature, timestamp, nonce)) {
//            return echostr;
//        }
//        WeixinMessage message=new WeixinMessage();
//        if("1".equals("1")){
//            message=new WeixinMessage(FromUserName,ToUserName,String.valueOf(System.currentTimeMillis()),"text","欢迎关注e鹿同行公众号！e鹿同行平台免费为用户提供发布顺风车资讯发布服务，乘客、司机均可发布以及寻找顺风出行信息。" +
//                    "温馨提示：e鹿同行平台免费提供发布顺风车信息服务,不与用户有任何利益关系。搭车前请自行协商,e鹿同行平台不负担任何责任!");
//
//        }


        return weixinMessageService.genXml(str);
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
        }else{
            userModel.setUserName(RegUtil.replaceSpecStr(obj.getString("nickname")));
            userModel.setGender(obj.getInteger("sex"));
            String address = obj.getString("province") +"-" +obj.getString("city") + "-"+obj.getString("country");
            userModel.setHomeStr(address);
            userModel.setPhotoUrl( obj.getString("headimgurl"));
            userService.updateUser(userModel);
            System.out.println("更新成功");
        }
        
        request.getSession().setAttribute("uid", openId);
        
        System.out.println("-------");
        try {
            if(state.equals("passenger")){
                response.sendRedirect("http://www.elutx.cn/index.html#!/passenger");
            }else if(state.equals("invitation")){
                response.sendRedirect("http://www.elutx.cn/invitation.html");
            }else{
                response.sendRedirect("http://www.elutx.cn/index.html#!/driver");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


//    @RequestMapping(value = "getInvitationImg", produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public String getInvitationImg(HttpServletRequest request, HttpServletResponse response) {
//        String id=request.getParameter("id");
//        String token = HttpRequestUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token",
//                "grant_type=client_credential&appid=wx66ffeb28c23fa9fb&secret=8c26eb56a87fb2826051562fa9a9fd34");
//        JSONObject tokenObj = JSON.parseObject(token);
//        String access_token = tokenObj.getString("access_token");
//        String str = "{\"action_name\": \"QR_LIMIT_SCENE\",\"action_info\": {\"scene\": {\"scene_id\": "+id+"}}}";
//        JSONObject ticket = JSON.parseObject(HttpRequestUtil.sendPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+access_token,
//                str));
//        HashMap<String, Object> resMap=RRUtil.getStandardMap();
//        resMap.put("ticket",ticket.getString("ticket"));
//        return RRUtil.getJsonRes(request,resMap);
////        return ticket.getString("ticket");
//    }
}
