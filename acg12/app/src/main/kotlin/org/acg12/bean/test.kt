package org.acg12.bean

import android.content.Context

import com.litesuits.orm.db.annotation.Ignore
import com.litesuits.orm.db.annotation.Table

import org.acg12.utlis.AppUtil

@Table("user")
class test : Param {

    var p = "" // 手机系统平台（1:安卓 2:ios 3:未知）
    var s = "" // 随机字符串（未获取到默认'unknown'）
    var n = "" // 手机名称（未获取到默认'unknown'）
    var d = "" // 手机设备ID（未获取到默认'unknown'）
    var v = "" // 手机系统版本号（未获取到默认'unknown'）
    var a = "" // app系统版本号（未获取到默认'unknown'）
    var t = "" // 时间戳（未获取到默认'unknown'）
    var g = "" // 签名（未获取到默认'unknown'）
    var c = "" // access_token
    @Ignore
    var password: String? = null
    @Ignore
    var newpassword: String? = null
    @Ignore
    var verify: String? = null
    @Ignore
    var verifyType: String? = null

    var uid = 0
    var tokenKey: String? = null
    var username: String? = null
    var expired: Int = 0
    var updateTime: Long? = null

    constructor() {
    }

    constructor(context: Context) {
        this.uid = 0
        p = "1"
        s = "unknown"
        n = "unknown"
        d = AppUtil.getTelephonyMgr(context)
        v = "unknown"
        a = AppUtil().getPackageInfo(context).versionName
        t = (System.currentTimeMillis() / 1000).toString().toString()
        g = ""
        c = ""
    }


}
