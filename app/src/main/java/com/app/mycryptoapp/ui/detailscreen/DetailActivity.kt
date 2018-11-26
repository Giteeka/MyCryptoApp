package com.app.mycryptoapp.ui.detailscreen

import android.os.Bundle
import android.util.Log
import com.app.mycryptoapp.R
import com.app.mycryptoapp.utils.PreferenceHelper.get
import com.app.mycryptoapp.data.Coin
import com.app.mycryptoapp.ui.base.BaseActivity
import com.app.mycryptoapp.utils.GlideApp
import com.app.mycryptoapp.utils.PreferenceHelper
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : BaseActivity() {

    private val TAG = "DetailActivity"

    lateinit var coin: Coin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initUI()
        setListener()

    }

    override fun initUI() {
        coin = intent.getParcelableExtra<Coin>("coin")
        Log.e(TAG, " ********  $coin ********")
        toolbar.title = coin.CoinName
        setSupportActionBar(toolbar)
        with(coin) {

            //Log.e(TAG, "**************coin $coin")
            tv_name.text = "Name : ${this?.Name}"
            tv_full_name.text = "Full name : ${this?.CoinName}"
            tv_algorithm.text = "Algorithm : ${this?.Algorithm}"
            val baseImageUrl = getPrefHelper()?.get(PreferenceHelper.PREF_BASE_IMAGE_URL, "")
            if (!this.ImageUrl.isNullOrBlank())
                GlideApp.with(iv_coin.context).load("$baseImageUrl${this?.ImageUrl}").placeholder(R.mipmap.ic_placeholder_coin).apply(RequestOptions.bitmapTransform(CircleCrop())).into(iv_coin)
            else
                GlideApp.with(iv_coin.context).load(R.mipmap.ic_placeholder_coin).into(iv_coin)
        }
        setListener()

        val fragmentAdapter = TabViewPagerAdapter(supportFragmentManager, coin)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)

    }

    override fun setListener() {
        toolbar.setNavigationOnClickListener { super.onBackPressed() }
    }
}
