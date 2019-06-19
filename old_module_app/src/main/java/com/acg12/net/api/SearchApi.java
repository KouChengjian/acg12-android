package com.acg12.net.api;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchApi {

    @GET("api/search/key")
    Observable<ResponseBody> searchKeyList(@Query("key") String key);

    @FormUrlEncoded
    @POST("api/app/search/subject.json")
    Observable<ResponseBody> searchSubjectList(@Field("key") String key);

    @FormUrlEncoded
    @POST("api/app/search/albumList.json")
    Observable<ResponseBody> searchAlbum(@Field("key") String key , @Field("page") String page);

    @FormUrlEncoded
    @POST("api/app/search/paletteList.json")
    Observable<ResponseBody> searchPalette(@Field("key") String key , @Field("page") String page);

    @FormUrlEncoded
    @POST("api/app/search/caricatureList.json")
    Observable<ResponseBody> searchCaricatureList(@Field("key") String key , @Field("page") String page);
}
