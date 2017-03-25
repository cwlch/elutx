package elu.util;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinYinTool {
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		if(null==str){
			return convert;
		}
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			//特殊处理银行的多音字组合
			if('行'== word){
				if(j-1 >= 0){
					char ch = str.charAt(j-1);
					if('银' == ch
						|| '支' == ch
						|| '分' == ch){
						convert += "h";
						continue;
					}
				}
			}
			// 提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}
	
}
