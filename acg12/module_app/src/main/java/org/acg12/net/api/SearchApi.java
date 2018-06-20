package org.acg12.net.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchApi {

    @GET("api/search/key")
    Observable<ResponseBody> searchKeyList(@Query("key") String key);

    @GET("api/search/albums")
    Observable<ResponseBody> searchAlbum(@Query("key") String key , @Query("page") String page);

    @GET("api/search/palettes")
    Observable<ResponseBody> searchPalette(@Query("key") String key , @Query("page") String page);
}
