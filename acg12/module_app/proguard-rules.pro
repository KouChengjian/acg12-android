-dontskipnonpubliclibraryclasses # 不忽略非公共的库类
-optimizationpasses 5            # 指定代码的压缩级别
-dontusemixedcaseclassnames      # 是否使用大小写混合
-dontpreverify                   # 混淆时是否做预校验
-verbose                         # 混淆时是否记录日志
-keepattributes *Annotation*     # 保持注解
-ignorewarning                   # 忽略警告
-dontoptimize                    # 优化不优化输入的类文件
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/* # 混淆时所采用的算法

#保持哪些类不被混淆
-keep public class * extends android.app.Activity                               # 保持哪些类不被混淆
-keep public class * extends android.app.Application                            # 保持哪些类不被混淆
-keep public class * extends android.app.Service                                # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver                  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider                    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper               # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference                      # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService              # 保持哪些类不被混淆
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.app.Fragment

#生成日志数据，gradle build时在本项目根目录输出
-dump class_files.txt            #apk包内所有class的内部结构
-printseeds seeds.txt            #未混淆的类和成员
-printusage unused.txt           #打印未被使用的代码
-printmapping mapping.txt        #混淆前后的映射

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {      # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {      # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {             # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {         # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable       #不混淆Serializable
-keepattributes Signature                 #不混淆泛型
-keep public class * extends android.support.** #如果有引用v4或者v7包，需添加

-keepattributes *Annotation*,Signature,Exceptions,InnerClasses

#------------------other------------------
-keep class com.acg12.lib.ui.base.** { *; }
-keep class com.acg12.lib.ui.activity.** { *; }
-keep class com.acg12.lib.ui.fragment.** { *; }
-keep class com.acg12.lib.utils.premission.** { *; }
-keep public class * extends com.acg12.lib.ui.base.ViewImpl
-keep public class * extends com.acg12.lib.ui.base.PresenterActivityImpl
-keep public class * extends com.acg12.lib.ui.base.PresenterFragmentImpl

-keep class com.acg12.cache.** { *; }
-keep class com.acg12.entity.** { *; }
-keep class com.acg12.net.** { *; }
-keep class com.acg12.utils.** { *; }
-keep public class * extends com.acg12.entity.DBModel

-keep @com.acg12.lib.utils.premission.FailPermission class * {*;}
-keep class * {
    @com.acg12.lib.utils.premission.FailPermission <fields>;
}
-keepclassmembers class * {
    @com.acg12.lib.utils.premission.FailPermission <methods>;
}
-keep @com.acg12.lib.utils.premission.SuccessPermission class * {*;}
-keep class * {
    @com.acg12.lib.utils.premission.SuccessPermission <fields>;
}
-keepclassmembers class * {
    @com.acg12.lib.utils.premission.SuccessPermission <methods>;
}

# eventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
# OKhttp
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# butterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
# Gson
-keep class com.google.gson.** { *; }
# liteOrm
-keepattributes *Annotation*,Signature,Exceptions,InnerClasses
-keep public class com.litesuits.orm.LiteOrm { *; }
-keep public class com.litesuits.orm.db.* { *; }
-keep public class com.litesuits.orm.db.model.** { *; }
-keep public class com.litesuits.orm.db.annotation.** { *; }
-keep public class com.litesuits.orm.db.enums.** { *; }
-keep public class com.litesuits.orm.db.assit.* { *; }
-keep public class com.litesuits.orm.log.* { *; }
-keep public class com.litesuits.orm.kvdb.* { *; }
