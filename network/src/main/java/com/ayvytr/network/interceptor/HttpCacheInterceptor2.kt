package com.ayvytr.network.interceptor

import com.ayvytr.network.isNetworkAvailable
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author ayvytr
 */
class HttpCacheInterceptor2 @JvmOverloads constructor(
    private val maxAge: Int = 3600
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        /**
         * App第一次启动，且没有网络时，会返回504
         * @see okhttp3.internal.cache.CacheInterceptor
         */
        //        var response = chain.proceed(chain.request().newBuilder().build())
        //        if (response.code() == 504) {
        //            return chain.proceed(chain.request())
        //        }

        var request = chain.request()
        if (!isNetworkAvailable()) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }

        //        response = chain.proceed(request)
        val response = chain.proceed(request)
        if (!isNetworkAvailable()) {
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxAge")
                .build()
        }

        return response
        //        val response = chain.proceed(chain.request().newBuilder().build())
        //        if (response.code() == 504) {
        //            return chain.proceed(chain.request())
        //        }
        //
        //        if (isNetworkAvailable()) {
        //            return chain.proceed(chain.request())
        //        } else { // 如果没有网络，则返回缓存未过期的数据
        //            val newRequest = chain.request().newBuilder()
        //                .removeHeader("Pragma")
        //                .header("Cache-Control", "only-if-cached, max-stale=$maxAge")
        //                .build()
        //
        //            return chain.proceed(newRequest)
        //        }
    }
}

