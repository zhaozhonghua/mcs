package com.fjsmu.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * MD5加密工具类
 * @author th
 *
 */
public class Md5Utils {

	private static Md5Utils md5Utils;
	private static Logger logger = Logger.getLogger(Md5Utils.class);	
	
	private Md5Utils() {}
	
	public static Md5Utils getInstance() {
		if(md5Utils == null) {
			md5Utils = new Md5Utils();
		} 
		
		return md5Utils;
	}
	
	public static String getMD5(String info)
	{
	    try
	    {
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        md5.update(info.getBytes("UTF-8"));
	        byte[] encryption = md5.digest();
	      
	        StringBuffer strBuf = new StringBuffer();
	        for (int i = 0; i < encryption.length; i++)
	        {
	        	if (Integer.toHexString(0xff & encryption[i]).length() == 1)
	        	{
	        		strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
	        	}
	        	else
	        	{
	        		strBuf.append(Integer.toHexString(0xff & encryption[i]));
	        	}
	        }
	      
	        return strBuf.toString();
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	    	return "";
	    }
	    catch (UnsupportedEncodingException e)
	    {
	    	return "";
	    }
	}  
  
    // 测试主函数  
    public static void main(String args[]) {  
    	String phone = "13778969354";
    	logger.info(phone.substring(5, phone.length()));
        String s = phone + phone.substring(5, phone.length());
        logger.info("原始：" + s);  
        logger.info("MD5后：" + getMD5(s));  
  
    }  
	
}
