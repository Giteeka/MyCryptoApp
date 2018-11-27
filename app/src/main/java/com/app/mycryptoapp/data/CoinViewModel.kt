package com.app.mycryptoapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.LivePagedListBuilder
import com.app.mycryptoapp.utils.ioThread

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

    val getAllCoins = LivePagedListBuilder(
            dao.allCoinsByName(), 10)
            .build()

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