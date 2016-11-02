package com.kcj.animationfriend.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * 检验邮箱格式是否正确
 * 
 * @param target
 * @return
 */
public class StringUtils {

	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat DATE_FORMAT_PART = new SimpleDateFormat(
			"HH:mm");

	public static String currentTimeString() {
		return DATE_FORMAT_PART.format(Calendar.getInstance().getTime());
	}

	/**
	 * 转换时间显示
	 * 
	 * @param time
	 *            毫秒
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String generateTime(long time) {
		int totalSeconds = (int) (time / 1000);
		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;

		return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes,
				seconds) : String.format("%02d:%02d", minutes, seconds);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 数字装换
	 */

	public static String numswitch(String num) {
		if (StringUtils.isEmpty(num))
			return "";
		if(isNumber(num))
		    return numswitch(Integer.valueOf(num).intValue());
		else
			return "";
	}

	public static String numswitch(int num) {
		String s = "";
		// Log.e("num",num+"======");
		if (num / 10000 > 0) {
			int i = num / 10000;
			s = s + i + "";
			// Log.e("i",i+"======");
			if (num % 10000 != 0 && num % 10000 >= 100) {
				int i2 = num % 10000;
				int i3 = i2 % 100;
				// Log.e("i3",i3+"======");
				s = s + "." + i3 + "";
			}
			s = s + "万";
		} else {
			s = String.valueOf(num).toString();
		}
		return s;
	}

	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}

	/**
	 * 检验邮箱格式是否正确
	 * 
	 * @param target
	 * @return
	 */
	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

	public static SpannableString setStringColor(String text, String name,
			String palette) {
		SpannableString sp = new SpannableString(text);
		sp.setSpan(new ForegroundColorSpan(Color.BLACK), 1, name.length() + 1,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(Color.BLACK), text.length()
				- palette.length(), text.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sp;
	}
	
	/**
	 * 去除视频标题前的"【】"
	 */
	public static String substringBetween(String fileName){
		String str = "";
		String[] strSp = fileName.split("】");
		if(strSp.length == 1 ){
			str = strSp[0];
		}else if(strSp.length > 1){
			for(int i = 0 ; i < strSp.length ; i++){
				String str1 = strSp[i];
				if(!str1.substring(0,2).trim().contains("【") ){
					str = str1;
				}
			}
		}else{
			str = strSp[0];
		}
		
//		String str1 = "】";
//		String str2 = "【";
//		String str3 = org.apache.commons.lang3.StringUtils.substringBetween(fileName,str1, str2);
//		if(str3 == null || str3.isEmpty()){
//			String[] str4 = fileName.split("】");
//			if(str4.length == 2){
//				str = str4[1];
//			}else{
//				str = fileName;
//			}
//		}else{
//			str = fileName;
//		}
		return str;
	}
}
