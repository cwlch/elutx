package elu.controller;

import java.io.IOException;
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

import elu.util.ToolUtil;
import elu.util.HttpRequestUtil;
import com.alibaba.fastjson.*;

/**
 * Created by Ch on 17/2/20.
 */

@Controller
@RequestMapping(value = "weixin")
public class WeixinController {
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
        System.out.println("2222222");
        return null;
    }

    @RequestMapping(value = "authWx", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String authWx(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> resMap=RRUtil.getStandardMap();
        Map<String,Object> map=RRUtil.para2Map(request);
        String code = String.valueOf(map.get("code"));
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
        String userInfo = HttpRequestUtil.sendGet("https://api.weixin.qq.com/sns/userinfo",infoPar);
        System.out.println("http://api.weixin.qq.com/sns/userinfo?"+infoPar);
        System.out.println("--------------------------------------");
        System.out.println(userInfo);

        System.out.println("-------");
        try {
            response.sendRedirect("http://www.elutx.cn/index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
