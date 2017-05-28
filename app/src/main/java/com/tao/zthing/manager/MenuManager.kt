package com.tao.zthing.manager

import android.os.Environment
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


/**
 * author:zhangtao on 2017/5/24 19:05
 */
object MenuManager {
    const val MENU_URL = "http://apicloud.mob.com/"
    const val TIMEOUT_CONNECT = 60 * 60
    const val TIMEOUT_DISCONNECT = 60 * 60 * 24
    const val MOB_KEY = "1e38efbbdea48"

    val CACHE_PATH = Environment.getExternalStorageDirectory().absolutePath + "/ZThing/http/menu/"

    var menuService: RetrofitMenuService? = null

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(MENU_URL)
                .client(OkHttpClient.Builder()
                        .addInterceptor(RetrofitManager.NoNetworkInterceptor(TIMEOUT_DISCONNECT))
                        .addNetworkInterceptor(RetrofitManager.NetworkInterceptor(TIMEOUT_CONNECT))
                        .cache(Cache(File(CACHE_PATH), 10 * 1024 * 1024))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        menuService = retrofit.create(RetrofitMenuService::class.java)
    }
}