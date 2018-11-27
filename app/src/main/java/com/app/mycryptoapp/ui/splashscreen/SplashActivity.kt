package com.app.mycryptoapp.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.mycryptoapp.R
import com.app.mycryptoapp.data.CoinViewModel
import com.app.mycryptoapp.network.ApiResponse
import com.app.mycryptoapp.network.ApiService
import com.app.mycryptoapp.ui.base.BaseActivity
import com.app.mycryptoapp.ui.homescreen.MainActivity
import com.app.mycryptoapp.utils.PreferenceHelper
import com.app.mycryptoapp.utils.PreferenceHelper.PREF_IS_DATA_SAVED
import com.app.mycryptoapp.utils.PreferenceHelper.get
import com.app.mycryptoapp.utils.PreferenceHelper.set
import kotlinx.android.synthetic.main.activity_splash.*


/**
 *
 * Created by Giteeka on 11/22/2018.
 *
 * Splash screen
 *
 */
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
            loadCoinList()
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
    private fun loadCoinList() {
        progress_horizontal.visibility = View.VISIBLE
        tv_fetching_list.visibility = View.VISIBLE

        viewModel.getApiResponse().observe(this, Observer<ApiResponse> { apiResponse ->
            if (apiResponse != null) {
                progress_horizontal.visibility = View.GONE
                if (apiResponse.Response == ApiService.API_STATUS.SUCCESS) {
                    tv_fetching_list.text = getString(R.string.list_fetched_successfully)
                    saveDataToPreferences(apiResponse)
                } else
                    tv_fetching_list.text = apiResponse.Message
            } else {
                tv_fetching_list.text = getString(R.string.oops_something_went_wrong)
            }
        })
    }

    /**
     * Save base url and  and image url in shared preference
     * */
    private fun saveDataToPreferences(body: ApiResponse) {
        val prefs = getPrefHelper()
        prefs?.set(PreferenceHelper.PREF_BASE_LINK_URL, body.BaseLinkUrl)
        prefs?.set(PreferenceHelper.PREF_BASE_IMAGE_URL, body.BaseImageUrl)
        prefs?.set(PREF_IS_DATA_SAVED, true)
        startHomeScreen()
    }


}
