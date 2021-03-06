package com.app.mycryptoapp.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


/**
 *
 * Created by Giteeka on 11/22/2018.
 * Database CRUD operation like insert deleted select
 *
 *
 */
@Dao
interface CoinDao {
    @Query("SELECT * FROM Coin ORDER BY cId ASC")
    fun allCoinsByName(): DataSource.Factory<Int, Coin>

    @Insert
    fun insert(coins: List<Coin>)

    @Insert
    fun insert(coin: Coin)

    @Delete
    fun delete(coin: Coin)
}