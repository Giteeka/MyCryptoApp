package com.app.mycryptoapp.network

import com.app.mycryptoapp.data.Coin

/**
 *
 * Created by Giteeka on 11/22/2018.
 *
 *Retrofit response object
 */
data class ApiResponse(
        val Response: String?,
        val Message: String?,
        val Data: Map<String, Coin>?,
        val BaseImageUrl: String?,
        val BaseLinkUrl: String?
)