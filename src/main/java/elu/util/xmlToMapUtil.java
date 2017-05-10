package elu.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ch on 17/5/8.
 */
public class xmlToMapUtil {
    /**
     * 将xml转化为Map集合
     *
     * @param str
     * @return
     */
    public static Map<String, String> xmlToMap(String str) {
        Map<String, String> map = new HashMap<String, String>();

        Document doc = null;
        try {
            doc = DocumentHelper.parseText(str);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element root = doc.getRootElement();
        @SuppressWarnings("unchecked")
        List<Element> list = root.elements();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }

        return map;
    }
}
