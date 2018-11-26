package com.app.mycryptoapp.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


/***
 * Database crud operation like insert deleted select
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