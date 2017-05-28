package com.tao.zthing.manager

import com.tao.zthing.model.MenuCategory
import com.tao.zthing.model.MenuDetails
import com.tao.zthing.model.MenuSearch
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * author:zhangtao on 2017/5/24 18:27
 */

interface RetrofitMenuService {

    /**
     * 搜索食谱
     */
    @GET("v1/cook/menu/search?key=${MenuManager.MOB_KEY}")
    fun getMenuSearch(@Query("cid") cid: String?, @Query("name") name: String?, @Query("page") page: Int, @Query("size") size: Int): Observable<MenuSearch>

    /**
     * 食谱类型
     */
    //    @Headers("Cache-Control: public, max-age=3600")
    @GET("v1/cook/category/query?key=${MenuManager.MOB_KEY}")
    fun getMenuCategory(): Observable<MenuCategory>

//    @GET("v1/cook/category/query?key=${MenuManager.MOB_KEY}")
//    fun getMenuCategory1(): Call<ResponseBody>

    /**
     * 得到食谱详情
     */
    @GET("v1/cook/menu/query?key=${MenuManager.MOB_KEY}")
    fun getMenuDetails(@Query("id") id: String): Observable<MenuDetails>

}
