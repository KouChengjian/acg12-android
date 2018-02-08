package org.acg12.net.base;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by DELL on 2016/11/29.
 */
public interface ApiService {

    @GET("res/index")
    Observable<ResponseBody> index();

    @GET("res/news")
    Observable<ResponseBody> newsList(@Query("page") String page);

    @GET("res/p/album")
    Observable<ResponseBody> albumList(@Query("action") String action , @Query("max") String pinId);

    @GET("res/p/boards")
    Observable<ResponseBody> paletteList(@Query("action") String action , @Query("max") String pinId);

    @GET("res/v/dangumi")
    Observable<ResponseBody> bangumiList(@Query("page") String page);

    @GET("res/v")
    Observable<ResponseBody> videoList(@Query("action") String action , @Query("type") String type ,@Query("page") String page);

    @GET("res/p/boards/album")
    Observable<ResponseBody> palettePreview(@Query("action") String action , @Query("max") String pinId ,@Query("boardId") String boardId);

    @GET("res/v/dangumi/info")
    Observable<ResponseBody> bangumiPreview(@Query("bmId") String bmId);

    @GET("res/v/playurl")
    Observable<ResponseBody> playUrl(@Query("action") String action ,@Query("av") String av);

    @GET("res/search/album")
    Observable<ResponseBody> searchAlbum(@Query("key") String key , @Query("page") String page);

    @GET("res/search/palette")
    Observable<ResponseBody> searchPalette(@Query("key") String key , @Query("page") String page);

    @GET("res/v/search/bangunmi")
    Observable<ResponseBody> searchBangumi(@Query("key") String key , @Query("page") String page);

    @GET("res/v/search/video")
    Observable<ResponseBody> searchVideo(@Query("key") String key , @Query("page") String page);

    @GET("res/search/key")
    Observable<ResponseBody> searchKeyList(@Query("key") String key);


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
