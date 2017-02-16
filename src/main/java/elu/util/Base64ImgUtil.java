package elu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64ImgUtil {


	
	
	/**
	 * 将文件转为BASE64
	 * @param imgFilePath 文件路径
	 * @return BASE64 String
	 */
	@SuppressWarnings("restriction")
	public static String GetImageStr(String imgFilePath) {
		byte[] data = null;
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	/**
	 * @param imgStr 二级制转为BASE64串
	 * @param imgFilePath 存储文件的路径
	 * @return 成功true
	 */
	@SuppressWarnings("restriction")
	public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) 
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			imgStr=imgStr.replace("data:image/jpg;base64,", "");
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {
					bytes[i] += 256;
				}
			}
			
			File f = new File(imgFilePath);
			
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}

			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}