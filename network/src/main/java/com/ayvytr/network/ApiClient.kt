package com.ayvytr.network

import com.ayvytr.network.interceptor.CacheInterceptor
import com.ayvytr.network.interceptor.CacheNetworkInterceptor
import com.ayvytr.okhttploginterceptor.LoggingInterceptor
import com.ayvytr.okhttploginterceptor.LoggingLevel
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @author ayvytr
 */
class ApiClient private constructor() {
    lateinit var okHttpClient: OkHttpClient
        private set
    private lateinit var defaultRetrofit: Retrofit
    lateinit var baseUrl: String
        private set

    private val retrofitMap: HashMap<String, Retrofit> = hashMapOf()

    @JvmOverloads
    fun init(
        baseUrl: String,
        hasCache: Boolean = false,
        cachePath: String = "",
        cacheSize: Long = 1024 * 1024 * 10,
        cacheMaxStaleSeconds: Int = 3600,
        interceptorList: List<Interceptor> = listOf()
    ) {
        val builder = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor(LoggingLevel.SINGLE))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)

        if (hasCache) {
            val cache = Cache(File(ContextProvider.globalContext.cacheDir, "okhttp"), cacheSize)
            builder.cache(cache)
//            builder.addInterceptor(HttpCacheInterceptor2())
//                .addInterceptor(CacheInterceptor())
//                .addNetworkInterceptor(CacheNetworkInterceptor())
//                .addInterceptor(HttpCacheInterceptor(cacheMaxStaleSeconds))
                .addInterceptor { chain ->
                    val originalRequest: Request = chain.request()
                    val cacheHeaderValue = if (isNetworkAvailable()) "public, max-age=2419200" else "public, only-if-cached, max-stale=2419200"
                    val request: Request = originalRequest.newBuilder().build()
                    val response: Response = chain.proceed(request)
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheHeaderValue)
                        .build()
                }
                .addNetworkInterceptor(object : Interceptor {
                    @kotlin.jvm.Throws(/*@@ntzmll@@*/IOException::class)
                    override open fun /*@@jpakex@@*/intercept(
                        chain:/*@@gubixf@@*/Interceptor.Chain): /*@@joltpp@@*/Response? {
                        val originalRequest: /*@@omcgos@@*/Request = chain.request()
                        val cacheHeaderValue: /*@@xdcpbx@@*/kotlin.String? = if (isNetworkAvailable()) "public, max-age=2419200" else "public, only-if-cached, max-stale=2419200"
                        val request: /*@@whzmvc@@*/Request = originalRequest.newBuilder().build()
                        val response: /*@@dozrjl@@*/Response = chain.proceed(request)
                        return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", cacheHeaderValue)
                            .build()
                    }
                })
        }

        interceptorList.map {
            builder.addInterceptor(it)
        }
        okHttpClient = builder.build()

        defaultRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
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
            retrofit = this.defaultRetrofit.newBuilder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
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
        fun getInstance(): ApiClient {
            return SingletonHolder.NETWORK
        }
//        val instance: ApiClient
//            get() = SingletonHolder.NETWORK
    }
}

