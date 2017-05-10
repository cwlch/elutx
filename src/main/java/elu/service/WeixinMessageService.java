package elu.service;

import elu.model.WeixinMessage;
import elu.util.xmlToMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Ch on 17/5/9.
 */
@Service("userService")
public class WeixinMessageService {

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
        WeixinMessage message=new WeixinMessage();
        if("subscribe".equals(xmlMap.get("subscribe"))){
            message=new WeixinMessage(FromUserName,ToUserName,String.valueOf(System.currentTimeMillis()),"text","欢迎关注e鹿同行公众号！e鹿同行平台免费为用户提供发布顺风车资讯发布服务，乘客、司机均可发布以及寻找顺风出行信息。" +
                    "温馨提示：e鹿同行平台免费提供发布顺风车信息服务,不与用户有任何利益关系。搭车前请自行协商,e鹿同行平台不负担任何责任!");

        }

        return message.toXml();
    }
}
