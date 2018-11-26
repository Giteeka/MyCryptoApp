package com.app.mycryptoapp.network

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    object API_STATUS {
        val SUCCESS = "Success"
    }

    @GET("all/coinlist")
    fun allCoinApi(): Call<ApiResponse>


    @GET("price")
    fun getCoin(@Query("fsym") baseCoin : String, @Query("tsyms") tsyms : String ): Call<JsonElement>

}