package com.app.mycryptoapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 *
 * Created by admin on 11/23/2018.
 */
object ApiClient {
    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"
    fun getClient(): Retrofit {
        val clientBuilder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()

        // set your desired log level
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // add logging as last interceptor
        clientBuilder.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}