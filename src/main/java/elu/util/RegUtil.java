package elu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ch on 17/4/21.
 */
public class RegUtil {

    public static String replaceSpecStr(String orgStr){

        if (null!=orgStr&&!"".equals(orgStr.trim())) {
            String regEx="[^\\d\\w\\u2E80-\\u9FFF]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(orgStr);
            return m.replaceAll("");
        }
        return "";
    }

}
