package com.cctv.ssfs.netdata;


import com.cctv.ssfs.entity.BounghtBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * Retrofit依赖的借口
 */

public interface RetrofitService {
    /**
     * 首页数据
     * <p>
     * label id
     * page  页数
     *
     * @return
     */
    @FormUrlEncoded
    @POST("index")
    Observable<ResponseBody> getHomeData(@FieldMap() Map<String, Object> map);

    /**
     * 个人中心 红包数据
     *
     * @return
     */
    @POST("money")
    Observable<ResponseBody> getUserMoneyData();

    /**
     * 搜索
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Index/search")
    Observable<ResponseBody> getSearchData(@Field("code") String cdoe);

    /**
     * 股吧短评数据
     * <p>
     * code （股票代码）
     * flag （可选，评论状态，0（首页5条默认），1精彩，2最新）
     * page （可选，分页，默认1）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("havelist")
    Observable<ResponseBody> getCommentData(@FieldMap() Map<String, Object> map);

    /**
     * 股吧数据
     *
     * @param code    （股票代码）
     * @param user_id user_id（可选，用户ID）
     * @return
     */
    @FormUrlEncoded
    @POST("stock")
    Observable<ResponseBody> getStockData(@Field("code") String code, @Field("user_id") String user_id);

    /**
     * 股吧相关干货数据
     *
     * @param cdoe （股票代码）
     * @return
     */
    @FormUrlEncoded
    @POST("stockArt")
    Observable<ResponseBody> getStockArtData(@Field("code") String cdoe);

    /**
     * 搜索短评数据
     * <p>
     * page 页数
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("search")
    Observable<ResponseBody> getSearchCommentData(@FieldMap() Map<String, Object> map);

    /**
     * 登录接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/login")
    Observable<ResponseBody> getLoginData(@FieldMap() Map<String, Object> map);

    /**
     * 个人中心原创干货数据
     * page 页数
     *
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("original")
    Observable<ResponseBody> getOriginalData(@Field("page") int page);

    /**
     * 个人中心我的邀请数据
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("invite")
    Observable<ResponseBody> getInviteData(@Field("user_id") String user_id);

    /**
     * 用户信息
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("user")
    Observable<ResponseBody> getUserData(@Field("user_id") String user_id);

    /**
     * 个人中心想买个股数据
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("user/want")
    Observable<ResponseBody> getUserWantData(@Field("user_id") String user_id);

    /**
     * 个人中心已买个股数据
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("user/have")
    Observable<ResponseBody> getUserHaveData(@Field("user_id") String user_id);

    /**
     * 买过接口
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("have")
    Observable<ResponseBody> getBoughtData(@Body RequestBody body);

    /**
     * 想买接口
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("want")
    Observable<ResponseBody> getInterstedData(@Body RequestBody body);

    /**
     * 短评详情评论列表数据
     *
     * @param haveId
     * @return
     */
    @FormUrlEncoded
    @POST("have/info")
    Observable<ResponseBody> getHaveInfoData(@Field("have_id") String haveId);

    /**
     * 短评详情评论发送接口
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("shave")
    Observable<ResponseBody> getSend(@Body RequestBody body);

    /**
     * 文章详情评论列表接口（Web）
     * article_id（文章id），page（分页）
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("comment/list")
    Observable<ResponseBody> getCommentListData(@FieldMap Map<String, Object> map);

    /**
     * 帖子详情接口
     * article_id（文章id 必传 其他选传），user_id
     * code（股票代码），label_id（分类标签id）
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/detail")
    Observable<ResponseBody> getDetailData(@FieldMap Map<String, Object> map);

    /**
     * 文章评论点赞接口
     * comment_id（文章评论id）
     *
     * @param commentId
     * @return
     */
    @FormUrlEncoded
    @POST("comment/praise")
    Observable<ResponseBody> getPraiseData(@Field("comment_id") String commentId);

    /**
     * 短评论点赞接口
     * have_id（短评id）
     *
     * @param haveId
     * @return
     */
    @FormUrlEncoded
    @POST("have/praise")
    Observable<ResponseBody> getHavePraiseData(@Field("have_id") String haveId);

    /**
     * 文章详情评论接口
     * post传递article_id（文章ID）user_id（用户id）
     * content（评论内容）
     *
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("comment/add")
    Observable<ResponseBody> getSendComment(@Body RequestBody body);

    /**
     * 提现接口
     * post传递article_id（文章ID）user_id（用户id）
     * content（评论内容）
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("encode")
    Observable<ResponseBody> getEncodeId(@Field("user_id") String userId);

    /**
     * 文章收藏
     * post传递article_id（文章ID）user_id（用户id）
     *
     * @param userId
     * @param articleId
     * @return
     */
    @FormUrlEncoded
    @POST("artCollect")
    Observable<ResponseBody> getArtCollect(@Field("article_id") String articleId, @Field("user_id") String userId);

    /**
     * 文章收藏列表
     * post user_id（用户id）
     *         page 页数
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("collect")
    Observable<ResponseBody> getCollect(@Field("user_id") String userId,@Field("page") int page);

    /**
     * 文章分享成功回调
     * post传递   article_id（文章ID）user_id（用户id）
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("artUser")
    Observable<ResponseBody> getArtUser(@Field("user_id") String userId,@Field("article_id") String articleId);



}
