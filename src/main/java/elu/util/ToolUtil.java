package elu.util;

/**
 * Created by Ch on 17/2/20.
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import java.util.HashMap;
//import  net.sf.json.JSONObject;
//import java.util.Iterator;

public class ToolUtil {
    /**
     * @param str 需要加密的字符串
     * @param encName 加密种类  MD5 SHA-1 SHA-256
     * @return
     * @Author:lulei
     * @Description: 实现对字符串的加密
     */
    public static String encrypt(String str, String encName){
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance(encName);
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes){
                int bt = b&0xff;
                if (bt < 16){
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }
    /**
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     * @Author:lulei
     * @Description: 微信权限验证
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { /*Config.TOKEN*/"123456", timestamp, nonce };
        //按字典排序
//        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        //加密并返回验证结果
        return signature == null ? false : signature.equals(ToolUtil.encrypt(content.toString(), "SHA-1"));
    }

    /**
     * 将json格式的字符串解析成Map对象 <li>
     * json格式：{"name":"admin","retries":"3fff","testname"
     * :"ddd","testretries":"fffffffff"}
     */
//    public static HashMap<String, String> jsonToMap(Object object){
//        HashMap<String, String> data = new HashMap<String, String>();
//        // 将json字符串转换成jsonObject
//        JSONObject jsonObject = JSONObject.fromObject(object);
//        Iterator it = jsonObject.keys();
//        // 遍历jsonObject数据，添加到Map对象
//        while (it.hasNext())
//        {
//            String key = String.valueOf(it.next());
//            String value = (String) jsonObject.get(key);
//            data.put(key, value);
//        }
//        return data;
//    }

}
