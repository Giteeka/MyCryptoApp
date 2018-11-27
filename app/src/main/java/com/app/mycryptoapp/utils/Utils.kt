package com.app.mycryptoapp.utils

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 *
 * Created by giteeka on 11/27/2018.
 *
 * common Utility functions which will be used to across whole application
 */
object Utils {

    // e.g. convert 1 BTC to USD
    // coinQty = 1
    //  1BTC = 3,805 USD
    // 25 BTC = (?) USD
    // so this method will return roundUpToTwoDecimalPlaces(25*3805)
    // Output on screen 25 BTC = 95125 USD


    fun cryptoConverter(coinQty: Double, toCoinValue: Double?): Double {
        var roundUpToTwoDecimal: Double
        try {
            val convertedValueOfCoin = coinQty * (toCoinValue ?: 0.0)
            val df = DecimalFormat("#.#########")
            df.roundingMode = RoundingMode.CEILING
            roundUpToTwoDecimal = df.format(convertedValueOfCoin).toDouble()
        } catch (e: Exception) {
            roundUpToTwoDecimal = 0.0
        }
        return roundUpToTwoDecimal
    }

}