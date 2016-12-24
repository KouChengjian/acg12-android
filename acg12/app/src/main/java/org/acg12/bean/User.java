package org.acg12.bean;

import android.content.Context;

import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.Table;

import org.acg12.utlis.AppUtil;

@Table("user")
public class User extends Param{

	private String p = ""; // 手机系统平台（1:安卓 2:ios 3:未知）
	private String s = ""; // 随机字符串（未获取到默认'unknown'）
	private String n = ""; // 手机名称（未获取到默认'unknown'）
	private String d = ""; // 手机设备ID（未获取到默认'unknown'）
	private String v = ""; // 手机系统版本号（未获取到默认'unknown'）
	private String a = ""; // app系统版本号（未获取到默认'unknown'）
	private String t = ""; // 时间戳（未获取到默认'unknown'）
	private String g = ""; // 签名（未获取到默认'unknown'）
	private String c = ""; // access_token
	@Ignore
	private String password;
	@Ignore
	private String newpassword;
	@Ignore
	private String verify;
	@Ignore
	private String verifyType;

	private int uid = 0;
	private String tokenKey;
	private String username;
	private int expired;
	private Long updateTime;
//	private String openid;
//	private String unionid;
//	private String accessToken;
//	private String tokenKey;
//	private int updateTime;

//	private String avatar;
//	private String nick;
//	private String adder;
//	private String email;
//	private int sex;
//	private String phone;
//	private String wxOpenId;
//	private String inviteCode;
//	private int userType;
//	private String cityId;

    public User(){}
	
	public User(Context context){
		this.uid = 0;
		p ="1";
		s = "unknown";
		n = "unknown";
		d = AppUtil.getTelephonyMgr(context);
		v = "unknown";
		a = new AppUtil().getPackageInfo(context).versionName;
		t = String.valueOf(System.currentTimeMillis() / 1000).toString();
		g = "";
		c = "";
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public int getExpired() {
		return expired;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}


}
