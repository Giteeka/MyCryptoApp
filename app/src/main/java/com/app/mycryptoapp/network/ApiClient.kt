package com.app.mycryptoapp.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 *
 * Created by Giteeka on 11/22/2018.
 *
 * Retrofit client for Retrofit API
 */
object ApiClient {

    var apiClient: Retrofit? = null
    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"
    fun getClient(): Retrofit {
        if (apiClient == null) {
            val clientBuilder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()

            // set your desired log level
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            // add logging as last interceptor
            clientBuilder.addInterceptor(loggingInterceptor)
            apiClient = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return apiClient as Retrofit
    }
}
