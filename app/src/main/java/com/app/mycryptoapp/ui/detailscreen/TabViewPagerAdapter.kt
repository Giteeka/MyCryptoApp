package com.app.mycryptoapp.ui.detailscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.mycryptoapp.data.Coin

/**
 * Fragment pager adapter
 * */

class TabViewPagerAdapter(fm: FragmentManager, var coin: Coin) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CryptoToFiatConversionFragment.newInstance(coin)
            }
            1 -> CryptoToCryptoConversionFragment.newInstance(coin)
            else -> {
                CryptoToFiatConversionFragment.newInstance(coin)
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "To Fiat"
            1 -> "To Crypto Currency"
            else -> {
                return "To Fiat"
            }
        }
    }
}