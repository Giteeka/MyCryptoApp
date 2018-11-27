package com.app.mycryptoapp.data

import com.app.mycryptoapp.data.Coin

/**
 * Created by Giteeka on 11/23/2018.
 *
 * Dynamic key -value json parsing map values
 *
 */
data class Data (val coinMap: Map<String, Coin>? = null)
