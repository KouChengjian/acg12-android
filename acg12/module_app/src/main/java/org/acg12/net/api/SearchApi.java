package org.acg12.net.api;

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

    @GET("api/app/search/albumList.json")
    Observable<ResponseBody> searchAlbum(@Query("key") String key , @Query("page") String page);

    @GET("api/app/search/paletteList.json")
    Observable<ResponseBody> searchPalette(@Query("key") String key , @Query("page") String page);
}
