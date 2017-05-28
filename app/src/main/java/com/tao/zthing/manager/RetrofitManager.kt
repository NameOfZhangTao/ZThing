package com.tao.zthing.manager

import com.blankj.utilcode.util.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * author:zhangtao on 2017/5/26 15:25
 */
class RetrofitManager {

    class NoNetworkInterceptor(private val TIMEOUT_DISCONNECT: Int) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            if (!NetworkUtils.isAvailableByPing()) {
                request = request.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$TIMEOUT_DISCONNECT")
                        .build()
            }
            return chain.proceed(request)
        }
    }

    class NetworkInterceptor(private val TIMEOUT_CONNECT: Int) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val originalResponse = chain.proceed(request)
            if (request.cacheControl() == null) {
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$TIMEOUT_CONNECT")
                        .build()
            } else {
                return originalResponse
            }
        }
    }
}