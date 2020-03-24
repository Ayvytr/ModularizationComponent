package com.ayvytr.network.interceptor

import com.ayvytr.network.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class CacheNetworkInterceptor(val maxAge: Int = 3600) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //无缓存,进行缓存
        return chain.proceed(chain.request()).newBuilder()
            .removeHeader("Pragma")
            .addHeader("Cache-Control", "max-age=$maxAge")
            .build()
    }
}

