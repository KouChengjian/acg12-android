package org.acg12.bean;

import android.content.Context;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import org.acg12.utlis.AppUtil;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

@Table("user")
public class User extends BmobUser{

    private static final long serialVersionUID = -7180816291844644837L;

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//	private String username;
//	private String password;
	private String avatar;
	private String signature; // 签名
	private String sex; // 性别
	private String nick;
	private BmobGeoPoint location;

    public User(){}

	public User(Context context){
//		this.uid = 0;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public BmobGeoPoint getLocation() {
        return location;
    }

    public void setLocation(BmobGeoPoint location) {
        this.location = location;
    }
}
