package com.acg12.entity.po;

import com.acg12.entity.DBModel;
import com.litesuits.orm.db.annotation.Table;

@Table("user")
public class UserEntity extends DBModel {

    // 额外信息
    private Boolean isUpdataToken;
    private String newPassword;
    private String verify;

    private int uid = 0;
    private String username;
    private String sessionId;
    private String password;
    private String avatar;
    private String signature; // 签名
    private int sex; // 性别 0 男 1 女
    private String nick;

    public UserEntity() {
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
