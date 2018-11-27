package com.app.mycryptoapp.ui.homescreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.mycryptoapp.R
import com.app.mycryptoapp.data.Coin
import com.app.mycryptoapp.data.CoinViewModel
import com.app.mycryptoapp.ui.base.BaseActivity
import com.app.mycryptoapp.ui.detailscreen.DetailActivity
import com.app.mycryptoapp.utils.PreferenceHelper
import com.app.mycryptoapp.utils.PreferenceHelper.get
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * Created by Giteeka on 11/22/2018.
 *
 * Home screen showing list of coins
 *
 */
class MainActivity : BaseActivity() {

    var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        setListener()
    }


    /**
     * Initialize view model for fetching coins from database using dao
     * */
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(CoinViewModel::class.java)
    }

    /**
     * Initialize UI - set adapter and list to recycler view with paging library
     * */
    override fun initUI() {
        val adapter = CoinListAdapter { it ->
            Log.e(TAG, it.toString())
            startDetailScreen(it)
        }
        val prefs = getPrefHelper()
        adapter.baseImageUrl = prefs?.get(PreferenceHelper.PREF_BASE_IMAGE_URL, "") ?: ""
        rv_list.layoutManager = LinearLayoutManager(this,
                RecyclerView.VERTICAL, false)
        rv_list.adapter = adapter
        viewModel.getAllCoins.observe(this, Observer(adapter::submitList))
    }

    /**
     * On click of any coin show detail screen
     * */
    private fun startDetailScreen(it: Coin?) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("coin", it)
        startActivity(intent)
    }

    override fun setListener() {
    }
}
