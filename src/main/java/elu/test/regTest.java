package elu.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ch on 17/4/21.
 */
public class regTest {

    public static String replaceSpecStr(String orgStr){

        if (null!=orgStr&&!"".equals(orgStr.trim())) {
            String regEx="[^\\d\\w\\u2E80-\\u9FFF]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(orgStr);
            return m.replaceAll("");
        }
        return null;
    }

    public static  void main(String[] args){
        System.out.println(replaceSpecStr("<U+1F341>枫<U+1F341>"));
    }

}
