package com.app.mycryptoapp.network

import com.app.mycryptoapp.data.Coin

data class ApiResponse(
        val Response: String?,
        val Message: String?,
        val Data: Map<String, Coin>?,
        val BaseImageUrl: String?,
        val BaseLinkUrl: String?
)