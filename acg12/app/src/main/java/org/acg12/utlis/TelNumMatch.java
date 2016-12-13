package org.acg12.utlis;

import android.util.Log;

/**
 * @ClassName: TelNumMatch
 * @Description: 用于判断一串数字是否是手机号
 */
public class TelNumMatch {

	/**
	 * 手机号码
	 * 移动：134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
	 * 联通：130,131,132,145,152,155,156,1709,171,176,185,186
	 * 电信：133,134,153,1700,177,180,181,189
	 */

	/**
	 * 中国移动：China Mobile
	 * 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
	 */
	static String YD1 = "^1(3[4-9]|4[7]|5[0-27-9]|7[0]|7[8]|8[2-478])\\d{8}$";
	/**
	 * 中国联通：China Unicom
	 * 130,131,132,145,152,155,156,1709,171,176,185,186
	 */
	static String LT1 = "^1(3[0-2]|4[5]|5[56]|709|7[1]|7[6]|8[56])\\d{8}$";
	/**
	 * 中国电信：China Telecom
	 * 133,134,153,1700,177,180,181,189
	 */
	static String DX1 = "^1(3[34]|53|77|700|8[019])\\d{8}$";

	/*
	 * 移动: 2G号段(GSM网络)有139,138,137,136,135,134,159,158,152,151,150,
	 * 3G号段(TD-SCDMA网络)有157,182,183,188,187 147是移动TD上网卡专用号段. 
	 * 联通:2G号段(GSM网络)有130,131,132,155,156 
	 * 3G号段(WCDMA网络)有186,185 
	 * 电信:2G号段(CDMA网络)有133,153 
	 * 3G号段(CDMA网络)有189,180
	 */
	static String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[2378]{1})|([4]{1}[7]{1}))[0-9]{8}$";
	static String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1}))[0-9]{8}$";
	static String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1}))[0-9]{8}$";

	String mobPhnNum;

	public static TelNumMatch build(String phone){
		return new TelNumMatch(phone);
	}

	public TelNumMatch(String mobPhnNum) {
		this.mobPhnNum = mobPhnNum;
		Log.d("tool", mobPhnNum);
	}

	public int matchNum() {
		/**
		 * flag = 1 YD 2 LT 3 DX 
		 */
		int flag;// 存储匹配结果
		// 判断手机号码是否是11位
		if (mobPhnNum.length() == 11) {
			// 判断手机号码是否符合中国移动的号码规则
			if (mobPhnNum.matches(YD1)) {
				Log.d("flag","移动");
				flag = 1;
			}
			// 判断手机号码是否符合中国联通的号码规则
			else if (mobPhnNum.matches(LT1)) {
				Log.d("flag","联通");
				flag = 2;
			}
			// 判断手机号码是否符合中国电信的号码规则
			else if (mobPhnNum.matches(DX1)) {
				Log.d("flag","电信");
				flag = 3;
			}
			// 都不合适 未知
			else {
				Log.d("flag","都不是");
				flag = 4; // 4
			}
		}
		// 不是11位
		else {
			flag = 5;
		}
		Log.d("TelNumMatch", "flag"+flag);
		return flag;
	}

	public boolean hasPhone(){
		int type = matchNum();
		if(type == 4 || type == 5){
            return false;
		}else {
			return true;
		}
	}
}
