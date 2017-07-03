package elu.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by Ch on 17/5/2.
 */
public class MenuUtil {

    public static void main(String args[]){
        String token = HttpRequestUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token",
                "grant_type=client_credential&appid=wx66ffeb28c23fa9fb&secret=8c26eb56a87fb2826051562fa9a9fd34");
        JSONObject tokenObj = JSON.parseObject(token);
        String access_token = tokenObj.getString("access_token");
        String menu="{\n" +
                "    \"button\":[\n" +
                "        {\n" +
                "            \"type\":\"view\",\n" +
                "            \"name\":\"我是乘客\",\n" +
                "            \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx66ffeb28c23fa9fb&redirect_uri=http://www.elutx.cn/elu/weixin/authWx&response_type=code&scope=snsapi_userinfo&state=passenger#wechat_redirect\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\":\"我是司机\",\n" +
                "\"sub_button\" : [\n" +
                "   {\t\n" +
                "       \"type\":\"view\",\n" +
                "       \"name\":\"发布找人\",\n" +
                "       \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx66ffeb28c23fa9fb&redirect_uri=http://www.elutx.cn/elu/weixin/authWx&response_type=code&scope=snsapi_userinfo&state=driver#wechat_redirect\"\n" +
                "    }, {\t\n" +
                "       \"type\":\"view\",\n" +
                "       \"name\":\"我的名片\",\n" +
                "       \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx66ffeb28c23fa9fb&redirect_uri=http://www.elutx.cn/elu/weixin/authWx&response_type=code&scope=snsapi_userinfo&state=myInvitation#wechat_redirect\"\n" +
                "    }\n" +
                "]"+
//                "        },\n" +
//                "        {\n" +
//                "            \"type\":\"view\",\n" +
//                "            \"name\":\"邀请活动\",\n" +
//                "            \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx66ffeb28c23fa9fb&redirect_uri=http://www.elutx.cn/elu/weixin/authWx&response_type=code&scope=snsapi_userinfo&state=invitation#wechat_redirect\"\n" +
//                "        }\n" +
//                "    ]\n" +
                "}";
        String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
        String result=HttpRequestUtil.sendPost(url,menu);
        System.out.print(result);

    }
}
