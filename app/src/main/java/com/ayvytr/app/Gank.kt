package com.ayvytr.app

import retrofit2.http.GET


/**
 * @author EDZ
 */
interface Gank {
    @GET("day/2020/03/23")
    suspend fun getData(): String

    @GET("v2/data/category/GanHuo/type/Android/page/1/count/10")
    suspend fun getAndroid(): String
}