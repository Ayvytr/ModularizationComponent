package com.ayvytr.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author ayvytr
 */
public class CacheNetworkInterceptor implements Interceptor {
    private int maxAge;

    public CacheNetworkInterceptor() {
        this(600);
    }

    public CacheNetworkInterceptor(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                                  .removeHeader("Pragma")
                                  .header("Cache-Control", "public, max-age=" + maxAge)
                                  .build();
        return chain.proceed(newRequest);
    }
}
