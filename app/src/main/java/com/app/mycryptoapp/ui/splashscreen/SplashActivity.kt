package com.app.mycryptoapp.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.app.mycryptoapp.R
import com.app.mycryptoapp.data.CoinViewModel
import com.app.mycryptoapp.network.ApiClient
import com.app.mycryptoapp.network.ApiResponse
import com.app.mycryptoapp.network.ApiService
import com.app.mycryptoapp.ui.base.BaseActivity
import com.app.mycryptoapp.ui.homescreen.MainActivity
import com.app.mycryptoapp.utils.PreferenceHelper
import com.app.mycryptoapp.utils.PreferenceHelper.PREF_IS_DATA_SAVED
import com.app.mycryptoapp.utils.PreferenceHelper.get
import com.app.mycryptoapp.utils.PreferenceHelper.set
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class SplashActivity : BaseActivity() {

    private val SPLASH_TIME_OUT = 2000L
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(CoinViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initUI()
        setListener()
    }

    // check if coin list has already been saved in database
    // if yes then open home screen
    // else call API and save coin list to database
    override fun initUI() {
        val prefs = getPrefHelper()
        val isDataSaved = prefs?.get(PREF_IS_DATA_SAVED, false)
        if (isDataSaved == true) {
            startHomeScreen()
        } else {
            fetchDataFromApi()
        }
    }

    private fun startHomeScreen() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)

    }

    override fun onBackPressed() {

    }

    override fun setListener() {
    }


    /**
     * Api call for fetching coin list using retrofit client
     *
     * */
    private fun fetchDataFromApi() {
        progress_horizontal.visibility = View.VISIBLE
        tv_fetching_list.visibility = View.VISIBLE
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        val getAllCoinApi = apiService.allCoinApi()
        getAllCoinApi.enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                progress_horizontal.visibility = View.GONE
                showError(t.message)
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                progress_horizontal.visibility = View.GONE
                val body = response.body()
                if (body != null) {
                    if (body.Response.equals(ApiService.API_STATUS.SUCCESS, ignoreCase = true)) {
                        if (body.Data != null) {
                            tv_fetching_list.text = getString(R.string.list_fetched_successfully)
                            saveDataToDatabase(body)
                        } else {
                            tv_fetching_list.text = getString(R.string.no_coins_list_found)
                        }
                    } else {
                        tv_fetching_list.text = body.Message
                    }
                } else {
                    tv_fetching_list.text = getString(R.string.oops_something_went_wrong)
                }
            }

        })
    }

    /**
     * Save Api response data to Room database using view model
     * */
    private fun saveDataToDatabase(body: ApiResponse) {
        val listOfCoins = ArrayList(body.Data?.values)
        viewModel.insertAll(listOfCoins)
        val prefs = getPrefHelper()
        prefs?.set(PreferenceHelper.PREF_BASE_LINK_URL, body.BaseLinkUrl)
        prefs?.set(PreferenceHelper.PREF_BASE_IMAGE_URL, body.BaseImageUrl)
        prefs?.set(PREF_IS_DATA_SAVED, true)
        startHomeScreen()
    }


}
