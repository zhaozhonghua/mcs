package com.fjsmu.tools;


import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * rem: ���ù��� aut: ��ȸ��
 */
@SuppressWarnings("rawtypes")
public class PublicUtil {

	private static Logger logger = Logger.getLogger(PublicUtil.class);	
	/**
	 * ���ڸ�ʽ��yyyy-MM-dd
	 */
	public static final String DATA_FORMAT = "yyyy-MM-dd";
	
	/**
	 * ���ڸ�ʽ��yyyyMMdd
	 */
	public static final String DATA_FORMAT_CLEAR = "yyyyMMdd";

	/**
	 * ʱ���ʽ��yyyy-MM-dd HH:mm:ss
	 */
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * �󻪱�������  1����Ƶ��ʧ  2���ⲿ���� ...
	 */
	public static Map<String, String> DH_BJ_TYPE = new HashMap<String, String>();
	/**
	 * �󻪴���״̬
	 */
	public static Map<String, String> DH_CL_STATUS = new HashMap<String, String>();
	
	/*
	 * fn-hd rem: �������ַ�ת����Ϊ�����ҳ���html��ʽ���ַ� rem: �޸������ַ� "&", "<", ">" �����?�з�
	 * "\n" par: str -- Ҫת�����ַ� ret: ת����ϵ��ַ� sep: pub: exp: aut: ��� log:
	 * 2004-08-25 ����
	 */
	public static String toHtml(String str)
	/* fn-tl */
	{
		if (str == null)
			return null;

		String html = new String(str);
		html = Replace(html, "&", "&amp;");
		html = Replace(html, "<", "&lt;");
		html = Replace(html, ">", "&gt;");
		html = Replace(html, "\n", "<br>"); // ���?�з�

		return html;
	}

	/**
	 * ���ܣ��ַ��滻���� source �е� oldString ȫ������ newString ����@param source Դ�ַ�
	 * 
	 * @param oldString
	 *            �ϵ��ַ�
	 * @param newString
	 *            �µ��ַ� ����ֵ���滻����ַ� �������ڣ�2004-7-12 ���ߣ��̻��� ����޸ģ� �޸��ˣ�
	 */
	public static String Replace(String source, String oldString,
			String newString) {
		StringBuffer output = new StringBuffer();

		if (source.equals("") || source == null)
			return "";
		int lengthOfSource = source.length(); // Դ�ַ���
		int lengthOfOld = oldString.length(); // ���ַ���

		int posStart = 0; // ��ʼ����λ��
		int pos; // ���������ַ��λ��

		while ((pos = source.indexOf(oldString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));

			output.append(newString);
			posStart = pos + lengthOfOld;
		}

		if (posStart < lengthOfSource) {
			output.append(source.substring(posStart));
		}

		return output.toString();
	}

	/*
	 * fn-hd rem: ���������ַ�#+*�Ȳ�����String��replaceAll����. ȡ��String���replaceAll
	 * 
	 * par: newStr -- Ҫ�滻���ַ� ret: �滻��ϵ��ַ� sep: pub: exp: aut: ��ȸ�� log:
	 * 2004-09-29 ����
	 */
	public static String replaceAll(String str, String oldStr, String newStr) {
		int length = str.indexOf(oldStr);
		int j = 0;
		String temp = "";
		while (length != -1) {
			j++;
			if (j == 10)
				break;
			temp = str.substring(length + 1);
			str = str.substring(0, length);
			str = str + newStr + temp;
			length = str.indexOf(oldStr);
		}
		return str;
	}

	/**
	 * author: michael.yang time: 2006-2-13
	 * 
	 * @param value
	 * @param defaultValue
	 * @return the value parsed, defaultValue returned if error happens
	 */
	public static int parseInt(Object value, Integer defaultValue) {
		try {
			return Integer.parseInt(String.valueOf(value));
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return defaultValue;
		}
	}
	
	public static long parseLong(Object value, long defaultValue) {
		try {
			return Long.parseLong(String.valueOf(value));
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return defaultValue;
		}
	}

	public static Date parseDate(String date) {
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(TIME_FORMAT);
			return fmt.parse(date);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
	}
	
	public static Date parseDate(String date, String format) {
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(format);
			return fmt.parse(date);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
	}
	/**
	 * author: michael.yang time: 2006-2-14
	 */
	public static String parseString(Object value, String defaultValue) {
		if (null == value || "null".equals(String.valueOf(value).toLowerCase())) {
			return defaultValue;
		}

		return String.valueOf(value);
	}
	/**
	 * ��ȡ��ǰʱ�� Date����
	 */
	public static Date getDateTime(){
		Date nowdate = null;
		try {
			nowdate = new SimpleDateFormat(TIME_FORMAT).parse(getCurrentTime());
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return nowdate;
	}
	/**
	 * Dateת��ΪString����
	 * @param date
	 * @return ʱ���ַ�
	 */
	public static String Date2String(Date date){
		
		return new SimpleDateFormat(TIME_FORMAT).format(date);
	}

	/**
	 * ��ȡ��ǰ���� String����
	 */
	public static String getCurrentDate() {
		return getCurrentTime(DATA_FORMAT);
	}
	
	/**
	 * ��ȡ��ǰʱ�� String����
	 */
	public static String getCurrentTime() {
	    return getCurrentTime(TIME_FORMAT);
	}

	/**
	 * Return current time in the pattern specified by arguement 'format'.
	 * 
	 * @param format
	 * @return the formatted current time
	 */
	public static String getCurrentTime(String format) {
		Date nowTime = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(nowTime);
	}

	public static String getMySQLDateTimeFormat() {
		return TIME_FORMAT;
	}

	public static String fmtDate(Date date, String fmt) {
		if (null == date)
			return "";
		SimpleDateFormat f = new SimpleDateFormat(fmt);
		return f.format(date);
	}
	
	public static String fmtDate(Date date) {
		if (null == date)
			return "";
		SimpleDateFormat f = new SimpleDateFormat(TIME_FORMAT);
		return f.format(date);
	}

	/**
	 * ��ȡϵͳΨһID
	 * 
	 * @return
	 */
	public static String getUUID() {
		String uid = "0";
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String tempId = sf.format(new Date());
		if (Long.parseLong(uid) >= Long.parseLong(tempId))
			uid = (Long.parseLong(uid) + 1) + "";
		else
			uid = tempId;

		return uid + getRandomString(10);
	}

	public static String getRandomString(int size) {
		char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q',
				'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
				'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; ++i)
			sb.append(c[(Math.abs(random.nextInt()) % c.length)]);

		return sb.toString();
	}

	/**
	 * �ж�ĳ�������Ƿ�Ϊ�� �����ࡢ���������⴦��
	 * 
	 * @param obj
	 * @return ��Ϊ���գ�����size>0|�ַ�Ϊ�մ�|����length>0    ����true,����false
	 * @author lijie
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	
	/**
	 * �ж�ĳ�������Ƿ�Ϊ�� �����ࡢ���������⴦��
	 * 
	 * @param obj
	 * @return ��Ϊ�գ�����true,����false
	 * @author lijie
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		// ���Ϊnull����Ҫ���?�������������
		if (obj instanceof String) {
			return obj.equals("");
		} else if (obj instanceof Collection) {
			// ����Ϊ����
			Collection coll = (Collection) obj;
			return coll.size() == 0;
		} else if (obj instanceof Map) {
			// ����ΪMap
			Map map = (Map) obj;
			return map.size() == 0;
		} else if (obj.getClass().isArray()) {
			// ����Ϊ����
			return Array.getLength(obj) == 0;
		} else {
			// �������ͣ�ֻҪ��Ϊnull������Ϊempty
			return false;
		}
	}

	/**
	 * �ַ��Ƿ�Ϊ����
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDigitalString(String str) {
		if (isEmpty(str)) {
			return false;
		} else {
			try {
				Double.parseDouble(str);
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
	}
	/**
	 * �ַ���true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean equalsFlag(Object str) {
		try {
			if (str != null && "true".equals(str)) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * �ַ��Ƿ�Ϊ����
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		try {
			if (str != null && str.matches("\\d+\\.?\\d+")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ������String.indexOf�����ȡ�����������е�λ�ã����ޣ�����-1
	 * 
	 * @param array
	 * @param value
	 * @return
	 */
	public static int arrayIndexOf(Object array, Object value) {
		if (array == null || value == null) {
			return -1;
		}
		int len = Array.getLength(array);
		for (int i = 0; i < len; i++) {
			if (value.equals(Array.get(array, i))) {
				return i;
			}
		}
		return -1;
	}


	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					logger.error(ex.getMessage(), ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static String sicenToComm(Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof Double) {
			return sicenToCommDouble((Double) value);
		} else {
			return String.valueOf(value);
		}
	}

	// �����ѧ�����������⣬�������뱣����λС��
	public static String sicenToCommDouble(double value) {

		String retValue = null;

		DecimalFormat df = new DecimalFormat(); // DecimalFormat df = new
												// DecimalFormat("0.00");
												// //�������Ļ�����ʡ���±�����

		df.setMinimumFractionDigits(0);

		df.setMaximumFractionDigits(5);

		retValue = df.format(value);

		// System.out.println(retValue);

		retValue = retValue.replaceAll(",", "");

		return retValue;

	}

	public static Double parseDouble(String value) {
		Double r = null;
		if (value != null) {
			value = value.trim();

			if (value.endsWith("%")) {
				value = value.substring(0, value.length() - 1);
			}

			r = Double.parseDouble(value);
		}
		return r;
	}
	 /**
     * ����ĸתСд
     * @param s
     * @return
     */
	public static String toLowerCaseFirstOne(String s) {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    /**
     * ����ĸת��д
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
	
    /**
     * ƴ���ַ�
     * @param s
     * @return
     */
    public static String toAppendStr(Object... strs) {
        StringBuffer sb = new StringBuffer();
        for(Object str : strs){
        	if (isNotEmpty(str)) {
        		sb.append(str);
			}
        }
        return sb.toString();
    }
    /**
     * ��λ �� ����ystr = 1 number = 3 �򷵻� 001
     * @param ystr ���Ϊ���򷵻� 000
     * @param number λ��
     * @return
     */
    public static String AppendZero(String ystr,int number){
    	if(PublicUtil.isEmpty(ystr))return "000";
		int f = number - ystr.length();
		for(int i = 0 ; i < f;i++){
			ystr = "0"+ystr;
		}
		return ystr;
    }
    
	/**
	 * ִ��������ʽ
	 * 
	 * @param pattern ���ʽ
	 * @param str ����֤�ַ�
	 * @return ���� <b>true </b>,����Ϊ <b>false </b>
	 */
	public static boolean match(String pattern, String str) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.find();
	}
	
	public static String object2String(Object object) {
		if(object==null) {
			return "";
		} else {
			return String.valueOf(object);
		}
	}
	
	public static Object null2Object(Object value) {
		if(value == null) {
			if(value instanceof Integer) {
				return 0;
			} else if(value instanceof Date) {
				return "";
			} else {
				return "";
			}
		} else {
			return value;
		}
	}
	
}// class end
