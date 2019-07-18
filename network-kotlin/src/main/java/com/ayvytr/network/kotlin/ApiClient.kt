package com.ayvytr.network.kotlin

import com.ayvytr.okhttploginterceptor.LoggingInterceptor
import com.ayvytr.okhttploginterceptor.LoggingLevel
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author ayvytr
 */
class ApiClient private constructor() {
    lateinit var okHttpClient: OkHttpClient

    lateinit var defaultRetrofit: Retrofit
    lateinit var baseUrl: String

    private val retrofitMap: HashMap<String, Retrofit> = hashMapOf()

    @JvmOverloads
    fun init(baseUrl: String,
             hasCache: Boolean = false,
             cachePath: String = "",
             cacheSize: Long = 1024 * 1024 * 64,
             interceptorList: List<Interceptor> = listOf()) {
        val builder = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor(LoggingLevel.SINGLE))
            //                .addInterceptor(new CacheInterceptor(context))
            //                .addNetworkInterceptor(new CacheNetworkInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)

        if (hasCache) {
            val cache = Cache(File(cachePath), cacheSize)
            builder.cache(cache)
        }

        interceptorList.map {
            builder.addInterceptor(it)
        }
        okHttpClient = builder.build()

        defaultRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        this.baseUrl = baseUrl
        retrofitMap[baseUrl] = defaultRetrofit
    }

    fun getRetrofit(baseUrl: String = this.baseUrl): Retrofit {
        if (baseUrl == this.baseUrl) {
            return defaultRetrofit
        }

        var retrofit = retrofitMap[baseUrl]
        if (retrofit == null) {
            retrofit = defaultRetrofit.newBuilder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitMap[baseUrl] = retrofit
        }

        return retrofit!!
    }

    fun <T> create(service: Class<T>, baseUrl: String = this.baseUrl): T {
        val retrofit = getRetrofit(baseUrl)
        return retrofit.create(service)
    }


    private object SingletonHolder {
        val NETWORK = ApiClient()
    }

    companion object {
        val instance: ApiClient
            get() = SingletonHolder.NETWORK
    }
}

