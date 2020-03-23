package com.ayvytr.network.kotlin

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author ayvytr
 */
class HttpCacheInterceptor @JvmOverloads constructor(
    private val context: Context,
    private val maxStaleSeconds: Int = 3600
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        /**
         * App第一次启动，且没有网络时，会返回504
         * @see okhttp3.internal.cache.CacheInterceptor
         */
        val response = chain.proceed(chain.request().newBuilder().build())
        if (response.code() == 504) {
            return chain.proceed(chain.request())
        }

        if (context.isAvailable()) {
            return chain.proceed(chain.request())
        } else { // 如果没有网络，则返回缓存未过期一个月的数据
            val newRequest = chain.request().newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "only-if-cached, max-stale=$maxStaleSeconds")
                .build()

            return chain.proceed(newRequest)
        }
    }
}

