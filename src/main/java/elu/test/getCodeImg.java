package elu.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import elu.util.HttpRequestUtil;
import elu.util.RRUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ch on 17/5/1.
 */
public class getCodeImg {
    public static String get(){
        String token = HttpRequestUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token",
                "grant_type=client_credential&appid=wx66ffeb28c23fa9fb&secret=8c26eb56a87fb2826051562fa9a9fd34");
        JSONObject tokenObj = JSON.parseObject(token);
        String access_token = tokenObj.getString("access_token");
        String str = "{\"action_name\": \"QR_LIMIT_SCENE\",\"action_info\": {\"scene\": {\"scene_id\": 110}}}";
        System.out.println(str);
        JSONObject ticket = JSON.parseObject(HttpRequestUtil.sendPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+access_token,
                str));
        System.out.println(ticket);
        return ticket.getString("ticket");
//        return null;
    }

    public static  void main(String[] args){
        get();
    }
}
