package com.ayvytr.app

import retrofit2.Call
import retrofit2.http.GET


/**
 * @author EDZ
 */
interface Gank {
    @GET("day/2020/03/23")
    fun getData(): Call<String>
}