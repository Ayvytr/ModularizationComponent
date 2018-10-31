package com.ayvytr.network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author ayvytr
 */
public class CacheInterceptor implements Interceptor {

    private Context context;
    private int maxStaleSeconds;

    public CacheInterceptor(Context context) {
        this(context, 7 * 24 * 60 * 60);
    }

    public CacheInterceptor(Context context, int maxStaleSeconds) {
        this.context = context;
        this.maxStaleSeconds = maxStaleSeconds;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(NetworkUtils.isConnected(context)) {
            return chain.proceed(chain.request());
        } else { // 如果没有网络，则返回缓存数据
            Request newRequest = chain.request().newBuilder()
                                      .removeHeader("Pragma")
                                      .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStaleSeconds)
                                      .build();
            return chain.proceed(newRequest);
        }
    }
}
