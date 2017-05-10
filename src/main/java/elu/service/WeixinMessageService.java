package elu.service;

import elu.model.User;
import elu.model.WeixinMessage;
import elu.util.RegUtil;
import elu.util.xmlToMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by Ch on 17/5/9.
 */
@Service("userService")
public class WeixinMessageService {

    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger("eluLogger");

    public String genXml(String xml){
        Map<String, String> xmlMap = xmlToMapUtil.xmlToMap(xml);
        System.out.println(xmlMap.get("MsgId"));
        System.out.println("System get");
        System.out.println("body:"+xmlMap);
        logger.info(xmlMap.get("MsgId"));
        logger.info("get");
        logger.info("body:"+xmlMap);
        String ToUserName = xmlMap.get("ToUserName");
        String FromUserName = xmlMap.get("FromUserName");
//        {Ticket=gQFt8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyUDQtUnNnWjNkUTIxMDAwMGcwM1cAAgT69BJZAwQAAAAA, FromUserName=o_UN0wrhiB1xDYBBFoEWR8RQcSV0, EventKey=qrscene_110, Event=subscribe, CreateTime=1494414616, ToUserName=gh_16a82caf289b, MsgType=event}

        WeixinMessage message=new WeixinMessage();
        String openId=xmlMap.get("FromUserName");
        if("subscribe".equals(xmlMap.get("subscribe"))){
            if(xmlMap.containsKey("EventKey")){
                User userModel = userService.queryUserByUId(openId);
                String referId=xmlMap.get("EventKey").replace("qrscene_","");
                if(userModel == null && !StringUtils.isEmpty(openId)){
                    System.out.println("用户未注册，需保存用户信息");
                    //新增用户
                    User user = new User();
                    user.setUid(openId);
                    user.setReferId(referId);
                    userService.addUser(user);
                    System.out.println("保存成功");
                }


            }

            message=new WeixinMessage(FromUserName,ToUserName,String.valueOf(System.currentTimeMillis()),"text","欢迎关注e鹿同行公众号！e鹿同行平台免费为用户提供发布顺风车资讯发布服务，乘客、司机均可发布以及寻找顺风出行信息。" +
                    "温馨提示：e鹿同行平台免费提供发布顺风车信息服务,不与用户有任何利益关系。搭车前请自行协商,e鹿同行平台不负担任何责任!");

        }

        return message.toXml();
    }
}
