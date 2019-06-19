package com.acg12.net.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HomeApi {

    @GET("api/app/common/index.json")
    Observable<ResponseBody> index();

    @GET("api/app/common/albumList.json")
    Observable<ResponseBody> albumList(@Query("action") String action, @Query("max") String pinId);

    @GET("api/app/common/newList.json")
    Observable<ResponseBody> newsList(@Query("page") String page);

    @GET("api/app/common/calendarList.json")
    Observable<ResponseBody> calendarList();

    @GET("api/app/common/subject.json")
    Observable<ResponseBody> subjectInfo(@Query("id") int id, @Query("type") int type, @Query("key") String key);

    @GET("res/p/boards")
    Observable<ResponseBody> paletteList(@Query("action") String action, @Query("max") String pinId);

    @GET("res/v/dangumi")
    Observable<ResponseBody> bangumiList(@Query("page") String page);

    @GET("res/v")
    Observable<ResponseBody> videoList(@Query("action") String action, @Query("type") String type, @Query("page") String page);

    @GET("api/app/common/boardList/albums.json")
    Observable<ResponseBody> palettePreview(@Query("action") String action, @Query("max") String pinId, @Query("boardId") String boardId);

    @GET("res/v/dangumi/info")
    Observable<ResponseBody> bangumiPreview(@Query("bmId") String bmId);

    @GET("res/v/playurl")
    Observable<ResponseBody> playUrl(@Query("action") String action, @Query("av") String av);

    @GET("res/v/search/bangunmi")
    Observable<ResponseBody> searchBangumi(@Query("key") String key, @Query("page") String page);

    @GET("res/v/search/video")
    Observable<ResponseBody> searchVideo(@Query("key") String key, @Query("page") String page);

    @FormUrlEncoded
    @POST("api/app/caricature/chapters.json")
    Observable<ResponseBody> caricatureChapters(@Field("id") int id, @Field("type") int type);

    @FormUrlEncoded
    @POST("api/app/caricature/chapters/list.json")
    Observable<ResponseBody> caricatureChaptersPage(@Field("id") int id, @Field("index") int index, @Field("type") int type);

    @FormUrlEncoded
    @POST("api/app/collect/subject/list.json")
    Observable<ResponseBody> collectSubjectList(@Field("pageNumber") int pageNumber, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("api/app/collect/subject/add.json")
    Observable<ResponseBody> collectSubjectAdd(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/app/collect/subject/del.json")
    Observable<ResponseBody> collectSubjectDel(@Field("relevanceId") int relevanceId);

    @FormUrlEncoded
    @POST("api/app/collect/album/list.json")
    Observable<ResponseBody> collectAlbumList(@Field("pageNumber") int pageNumber, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("api/app/collect/album/add.json")
    Observable<ResponseBody> collectAlbumAdd(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/app/collect/album/del.json")
    Observable<ResponseBody> collectAlbumDel(@Field("pinId") String pinId);

    @FormUrlEncoded
    @POST("api/app/collect/palette/list.json")
    Observable<ResponseBody> collectPaletteList(@Field("pageNumber") int pageNumber, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("api/app/collect/palette/add.json")
    Observable<ResponseBody> collectPaletteAdd(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/app/collect/palette/del.json")
    Observable<ResponseBody> collectPaletteDel(@Field("boardId") String boardId);

    @FormUrlEncoded
    @POST("api/app/collect/caricature/list.json")
    Observable<ResponseBody> collectCaricatureList(@Field("pageNumber") int pageNumber, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("api/app/collect/caricature/add.json")
    Observable<ResponseBody> collectCaricatureAdd(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/app/collect/caricature/del.json")
    Observable<ResponseBody> collectCaricatureDel(@Field("comicId") int comicId);
}
