package com.ayvytr.network

import android.os.Environment
import com.ayvytr.network.interceptor.CacheInterceptor
import com.ayvytr.network.interceptor.CacheNetworkInterceptor
import com.ayvytr.network.provider.ContextProvider
import com.ayvytr.okhttploginterceptor.LoggingInterceptor
import com.ayvytr.okhttploginterceptor.LoggingLevel
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Entry class of this library, use [ApiClient.getInstance] init, default, OkHttp has 10 seconds
 * timeout, default cache, and default cache max age by 3600 seconds.
 * @author ayvytr
 */
class ApiClient private constructor() {
    lateinit var okHttpClient: OkHttpClient
        private set
    private lateinit var defaultRetrofit: Retrofit
    lateinit var baseUrl: String
        private set

    private val retrofitMap: HashMap<String, Retrofit> = hashMapOf()

    /**
     * @param cache if null, no cache
     */
    @JvmOverloads
    fun init(
        baseUrl: String,
        okHttpTimeoutSeconds: Int = 10,
        interceptorList: List<Interceptor> = listOf(),
        cache: Cache? = DEFAULT_CACHE,
        cacheMaxAgeSeconds: Int = 3600
    ) {
        val longOkHttpTimeoutSeconds = okHttpTimeoutSeconds.toLong()
        val builder = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor(LoggingLevel.SINGLE))
            .connectTimeout(longOkHttpTimeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(longOkHttpTimeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(longOkHttpTimeoutSeconds, TimeUnit.SECONDS)

        if (cache != null) {
            builder.cache(cache)
                .addInterceptor(CacheInterceptor(cacheMaxAgeSeconds))
                .addNetworkInterceptor(CacheNetworkInterceptor(cacheMaxAgeSeconds))
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
        @JvmField
        val DEFAULT_CACHE: Cache = Cache(File(getDiskCacheDir(), "okhttp"), 1024 * 1024 * 64)

        @JvmStatic
        fun getDiskCacheDir(): File {
            val context = ContextProvider.globalContext
            return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
                    !Environment.isExternalStorageRemovable()) {
                context.externalCacheDir!!
            } else {
                context.cacheDir
            }
        }

        @JvmStatic
        fun getInstance(): ApiClient {
            return SingletonHolder.NETWORK
        }
    }
}

