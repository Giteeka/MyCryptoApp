package com.app.mycryptoapp.data

import androidx.room.*
import android.content.Context

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

