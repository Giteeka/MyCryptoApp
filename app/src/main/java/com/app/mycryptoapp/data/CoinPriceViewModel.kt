package com.app.mycryptoapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.mycryptoapp.network.ApiClient
import com.app.mycryptoapp.network.ApiService
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Giteeka on 11/27/2018.
 */
class CoinPriceViewModel(app: Application) : AndroidViewModel(app) {

    var currencyValueMap: MutableLiveData<Map<String, Double>?>? = null
    var error: MutableLiveData<String?>? = null


    /**
     * Get Coin values in difference fiat/crypto currency using API
     */
    fun loadCurrencyValueMap(baseCoin: Coin?, list: ArrayList<Coin>): MutableLiveData<Map<String, Double>> {
        if (currencyValueMap == null) {
            currencyValueMap = MutableLiveData()
            error = MutableLiveData()
            val apiService = ApiClient.getClient().create(ApiService::class.java)
            // convert coin object array list to coin symbol comma separated string for api
            val getCoinApi = baseCoin?.Symbol?.let { apiService.getCoin(it, list.asSequence().map { coin -> coin.Symbol }.joinToString(",")) }
            getCoinApi?.enqueue(object : Callback<JsonElement> {

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    currencyValueMap!!.postValue(null)
                    error?.postValue(t.message)
                }

                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    if (response.isSuccessful) {
                        try {
                            val element = response.body()
                            val type = object : TypeToken<Map<String, Double>>() {}.type
                            currencyValueMap!!.postValue(GsonBuilder().create().fromJson(element?.asJsonObject, type))
                        } catch (e: Exception) {
                            error?.postValue("");
                        }

                    } else {
                        error?.postValue(response.errorBody().toString())
                    }
                }
            })
        }
        return currencyValueMap as MutableLiveData<Map<String, Double>>
    }

}
