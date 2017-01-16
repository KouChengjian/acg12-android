package org.acg12.net;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by DELL on 2016/11/29.
 */
public interface ApiService {


    @GET("home/more")
    Observable<ResponseBody> albumList(@Query("action") String action , @Query("max") String pinId);

    @GET("home/more")
    Observable<ResponseBody> paletteList(@Query("action") String action , @Query("max") String pinId);

    @GET("find")
    Observable<ResponseBody> bangumiList(@Query("page") String page);

    @GET("home/more")
    Observable<ResponseBody> videoList(@Query("action") String action , @Query("type") String type ,@Query("page") String page);

    @GET("home/more")
    Observable<ResponseBody> palettePreview(@Query("action") String action , @Query("max") String pinId ,@Query("boardId") String boardId);

    @GET("find/info")
    Observable<ResponseBody> bangumiPreview(@Query("av") String av);

    @GET("playurl")
    Observable<ResponseBody> playUrl(@Query("action") String action ,@Query("av") String av);


//    @FormUrlEncoded
//    @ApiConverter(converter = LoginConverter.class)
//    @POST("/LoginPhone")
//    Observable<User> login(@Field("username") String username, @Field("password") String password);
//
//    // @FormUrlEncoded
//    @ApiConverter(converter = LoginConverter.class)
//    @GET("/Token/{tokenKey}")
//    Observable<User> updataToken(@Path("tokenKey") String tokenKey);
//
//    @ApiConverter(converter = UserInfoConverter.class)
//    @GET("/UserInfo/{u}")
//    Observable<User> userinfo(@Path("u") String u);



//    @FormUrlEncoded
//    @GET("/Token/{tokenKey}")
//    Call<ResponseBody> updataToken(@Path("tokenKey") String tokenKey);
//

//
//    @FormUrlEncoded
//    @POST("/LoginWx")
//    Call<ResponseBody> loginWX(@Field("openId") String openId, @Field("unionId") String unionId, @Field("sex") String sex,
//                               @Field("nick") String nick, @Field("avatar") String avatar);
//
//    @FormUrlEncoded
//    @POST("/BindWx")
//    Call<ResponseBody> bindWx(@Field("openId") String openId, @Field("unionId") String unionId, @Field("sex") String sex,
//                              @Field("nick") String nick, @Field("avatar") String avatar);
//
//    @FormUrlEncoded
//    @POST("/Register")
//    Call<ResponseBody> register(@Field("phone") String phone, @Field("password") String password, @Field("code") String code);
//
//    @GET("/UserInfo/{u}")
//    Call<ResponseBody> userinfo(@Path("u") String u);
//
//    @GET("/PhoneCode/{phone}/{verifytype}")
//    Call<ResponseBody> verify(@Path("phone") String phone, @Path("verifytype") String verifyType);
//
//    @FormUrlEncoded
//    @POST("/LookPwd")
//    Call<ResponseBody> resetPwd(@Field("phone") String phone, @Field("password") String password, @Field("code") String code);
//
//    @FormUrlEncoded
//    @POST("/UserAlter")
//    Call<ResponseBody> userAlter(@Field("type") String type, @Field("param1") String param1, @Field("param2") String param2);
//
//    @FormUrlEncoded
//    @POST("/BindPhone")
//    Call<ResponseBody> bindPhone(@Field("phone") String phone, @Field("code") String code);
//
//    @FormUrlEncoded
//    @POST("/UserFeedback")
//    Call<ResponseBody> feedback(@Field("content") String content);



//    @FormUrlEncoded
//    @POST("/LoginPhone")
//    Observable<ResponseBody> login(@Field("username") String username, @Field("password") String password);
//
//    // @FormUrlEncoded
//    @GET("/Token/{tokenKey}")
//    Observable<ResponseBody> updataToken(@Path("tokenKey") String tokenKey);
//
//    @GET("/UserInfo/{u}")
//    Observable<ResponseBody> userinfo(@Path("u") String u);

}
