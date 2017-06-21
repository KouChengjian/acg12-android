package org.acg12.bean;

import android.content.Context;

import com.litesuits.orm.db.annotation.Table;

@Table("user")
public class User extends Param{;

    // header

    // 额外信息
    private Boolean isUpdataToken;
    private String newPassword;

    private int uid = 0;
	private String username;
	private String password;
	private String avatar;
	private String signature; // 签名
	private int sex; // 性别 0 男 1 女
	private String nick;

    public User(){}

	public User(Context context){
		this.uid = 0;
//		p ="1";
//		s = "unknown";
//		n = "unknown";
//		d = AppUtil.getTelephonyMgr(context);
//		v = "unknown";
//		a = new AppUtil().getPackageInfo(context).versionName;
//		t = String.valueOf(System.currentTimeMillis() / 1000).toString();
//		g = "";
//		c = "";
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
}
