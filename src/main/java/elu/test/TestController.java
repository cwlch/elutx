package elu.test;

import elu.util.PinYinTool;

public class TestController {

	public static void main(String[] args) {
		
		String aa = "长沙镇";
		String chart = PinYinTool.getPinYinHeadChar(aa.substring(0,1)).toUpperCase();
		System.out.println(chart);
	}

}
