package com.app.mycryptoapp.data

import androidx.room.*
import android.content.Context
/**
 *
 * Created by Giteeka on 11/22/2018.
 *
 * Room database class instance
 *
 */
@Database(entities = [Coin::class], version = 1)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao

    companion object {
        private var instance: CoinDatabase? = null
        @Synchronized
        fun get(context: Context): CoinDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        CoinDatabase::class.java, "CoinDatabase")
                        .build()
            }
            return instance!!
        }

    }
}

