package com.ayvytr.network;

import android.content.Context;
import android.support.annotation.Nullable;

import com.ayvytr.logger.L;
import com.ayvytr.okhttploginterceptor.LoggingInterceptor;
import com.ayvytr.okhttploginterceptor.LoggingLevel;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ayvytr
 */
public class ApiClient {
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private LoggingInterceptor loggingInterceptor;
    private Gson gson;

    public static ApiClient getInstance() {
        return SingletonHolder.NETWORK;
    }

    public void init(final Context context, String baseUrl) {
        init(context, baseUrl, null);
    }

    public void init(final Context context, String baseUrl, @Nullable Interceptor interceptor) {
        gson = new Gson();
        loggingInterceptor = new LoggingInterceptor();
        loggingInterceptor.setLevel(LoggingLevel.SINGLE);
        L.e(context.getCacheDir(), context.getExternalCacheDir());
        Cache cache = new Cache(new File(context.getExternalCacheDir(), "okhttp"), 1024 * 1024 * 1024 * 64L);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new CacheInterceptor(context))
                .addNetworkInterceptor(new CacheNetworkInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        if(interceptor != null) {
            builder.addInterceptor(interceptor);
        }
        okHttpClient = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static class SingletonHolder {
        private static final ApiClient NETWORK = new ApiClient();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public Gson getGson() {
        return gson;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
