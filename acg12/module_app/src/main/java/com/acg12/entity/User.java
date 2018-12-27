package com.acg12.entity;

import android.content.Context;

import com.acg12.lib.utils.AppUtil;
import com.acg12.lib.utils.MD5Util;
import com.litesuits.orm.db.annotation.Table;

import com.acg12.constant.Constant;


@Table("user")
public class User extends Param{;

    // header
    private String p = ""; // 手机系统平台（1:安卓 2:ios 3:未知）
    private String s = ""; // 随机字符串（未获取到默认'unknown'）
    private String n = ""; // 手机名称（未获取到默认'unknown'）
    private String d = ""; // 手机设备ID（未获取到默认'unknown'）
    private String v = ""; // 手机系统版本号（未获取到默认'unknown'）
    private String a = ""; // app系统版本号（未获取到默认'unknown'）
    private String t = ""; // 时间戳（未获取到默认'unknown'）
    private String g = ""; // 签名（未获取到默认'unknown'）

    // 额外信息
    private Boolean isUpdataToken;
    private String newPassword;
    private String verify;

    private int uid = 0;
	private String username;
	private String password;
	private String avatar;
	private String signature; // 签名
	private int sex; // 性别 0 男 1 女
	private String nick;

    public User(){}

	public User(Context context){
		uid = 0;
		p = "1";
		s = "unknown";
		n = "unknown";
		d = "unknown"; //AppUtil.getTelephonyMgr(context);
		v = "unknown";
		a = new AppUtil().getPackageInfo(context).versionName;
		t = String.valueOf(System.currentTimeMillis() / 1000).toString();
		g = MD5Util.encryption(p + s + n + d + v + a + t + uid + Constant.USER_KEY);
	}

	public void updataSign(){
        g = MD5Util.encryption(p + s + n + d + v + a + t + uid + Constant.USER_KEY);
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


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getUpdataToken() {
        return isUpdataToken;
    }

    public void setUpdataToken(Boolean updataToken) {
        isUpdataToken = updataToken;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }
}
