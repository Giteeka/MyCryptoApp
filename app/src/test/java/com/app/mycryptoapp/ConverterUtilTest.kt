package com.app.mycryptoapp

import com.app.mycryptoapp.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Test


class ConverterUtilTest {

    @Test
    fun testConvertBTCToUSD() {
        val actual = Utils.cryptoConverter(100.0, 3779.75)
        // expected value is 212
        val expected = 377975
        // use this method because float is not precise
        assertEquals("Conversion from BTC to USD", expected.toDouble(), actual, 0.001)
    }

    @Test
    fun testConvertBTCToUSDWithNull() {
        val actual = Utils.cryptoConverter(1.0, null)
        // expected value is 212
        val expected = 0
        // use this method because float is not precise
        assertEquals("Conversion from BTC to USD", expected.toDouble(), actual, 0.001)
    }
    @Test
    fun testConvertBTCToUSDWithNull2() {
        val actual = Utils.cryptoConverter(1.0, null)
        // expected value is 212
        val expected = null
        // use this method because float is not precise
        assertEquals("Conversion from BTC to USD", expected?.toDouble()!!, actual, 0.001)
    }

    //    1 Bitcoin equals
//    34.87 Ether
    @Test
    fun testConvertBTCToETHWithNull() {
        val actual = Utils.cryptoConverter(100.0, null)
        // expected value is 100
        val expected = 0
        // use this method because float is not precise
        assertEquals("Conversion from BTC to ETH", expected.toDouble(), actual, 0.001)
    }

    @Test
    fun testConvertBTCToETH() {
        val actual = Utils.cryptoConverter(100.0, 34.87)
        // expected value is 100
        val expected = 3487
        // use this method because float is not precise
        assertEquals("Conversion from BTC to ETH", expected.toDouble(), actual, 0.001)
    }

}