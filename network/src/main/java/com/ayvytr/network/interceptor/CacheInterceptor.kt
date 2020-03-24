package com.ayvytr.network.interceptor

import com.ayvytr.network.isNetworkAvailable
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

internal class CacheInterceptor(val maxAgeSeconds: Int = 3600) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response
        val request: Request = if (isNetworkAvailable()) {
            //有网络,检查缓存
            chain.request()
                .newBuilder()
                .cacheControl(CacheControl.Builder()
                                  .maxAge(maxAgeSeconds, TimeUnit.SECONDS)
                                  .build())
                .build()
        } else {
            //无网络,检查缓存,即使是过期的缓存（这里和maxAge一致，有需要再改）
            chain.request().newBuilder()
                .cacheControl(CacheControl.Builder()
                                  .onlyIfCached()
                                  .maxStale(maxAgeSeconds, TimeUnit.SECONDS)
                                  .build())
                .build()
        }

        response = chain.proceed(request)
        return response.newBuilder().build()
    }
}