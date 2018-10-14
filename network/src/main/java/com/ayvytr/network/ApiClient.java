package com.ayvytr.network;

import android.content.Context;

import com.ayvytr.okhttploginterceptor.LoggingInterceptor;
import com.ayvytr.okhttploginterceptor.LoggingLevel;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author admin
 */
public class ApiClient {
    public static final String BASE_URL = "http://gank.io/api/";
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private LoggingInterceptor loggingInterceptor;
    private Gson gson;

    public static ApiClient getInstance() {
        return SingletonHolder.NETWORK;
    }

    public void init(final Context context) {
        gson = new Gson();
        loggingInterceptor = new LoggingInterceptor();
        loggingInterceptor.setLevel(LoggingLevel.URL_BODY);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //TODO 确认请求是否已经终止
                        if(!NetworkUtils.isAvailable(context.getApplicationContext())) {
                            throw new UnknownHostException("Network not available");
                        }

                        return chain.proceed(chain.request());
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
