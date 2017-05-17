package elu.common;

import java.util.HashMap;
import java.util.Map;

public class TicktCache {

	public static Map map = new HashMap();
	
	public static void setTicket(String ticket){
		map.put("ticket", ticket);
	}
	
	public static String getTicket(){
		return (String) map.get("ticket");
	}
	
}
