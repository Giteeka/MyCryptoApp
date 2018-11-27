package com.app.mycryptoapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import com.app.mycryptoapp.R
import com.app.mycryptoapp.network.ApiClient
import com.app.mycryptoapp.network.ApiResponse
import com.app.mycryptoapp.network.ApiService
import com.app.mycryptoapp.utils.ioThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 *
 * Created by Giteeka on 11/22/2018.
 *
 * View model observer to fetch and insert data to database
 *
 *
 */
class CoinViewModel(app: Application) : AndroidViewModel(app) {
    val dao = CoinDatabase.get(app).coinDao()
    private var apiResponse: MutableLiveData<ApiResponse>? = null

    /***
     * get coin list from database using paging lib
     */
    val getAllCoins = LivePagedListBuilder(
            dao.allCoinsByName(), 10)
            .build()

    /**
     * live data object of api response
     */
    fun getApiResponse(): MutableLiveData<ApiResponse> {
        if (apiResponse == null) {
            apiResponse = MutableLiveData()
            fetchCoinListFromApi()
        }

        return apiResponse as MutableLiveData<ApiResponse>
    }

    /**
     * Api for fetching list
     */
    private fun fetchCoinListFromApi() {
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        val getAllCoinApi = apiService.allCoinApi()
        getAllCoinApi.enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val body = response.body()
                if (body != null) {
                    if (body.Response.equals(ApiService.API_STATUS.SUCCESS, ignoreCase = true)) {
                        if (body.Data != null) {
                            val listOfCoins = ArrayList(body.Data.values)
                            insertAll(listOfCoins)
                            apiResponse?.postValue(body)
                        } else {
                            apiResponse?.postValue(getErrorResponse((getApplication() as Application).getString(R.string.no_coins_list_found))
                            )
                        }
                    } else {
                        apiResponse?.postValue(getErrorResponse(body.Message))
                    }
                } else {
                    apiResponse?.postValue(getErrorResponse((getApplication() as Application).getString(R.string.oops_something_went_wrong)))

                }
            }

        })
    }

    /**
     * In case of error/failure this ApiResponse will be generate to show error view in activity
     */
    private fun getErrorResponse(string: String?): ApiResponse {
        val apiResponse = ApiResponse(ApiService.API_STATUS.ERROR, string, null, null, null)
        return apiResponse

    }


    fun insert(coin: Coin) = ioThread {
        dao.insert(coin)
    }

    fun insertAll(coinList: List<Coin>) = ioThread {
        dao.insert(coinList)
    }

    fun remove(coin: Coin) = ioThread {
        dao.delete(coin)
    }
}