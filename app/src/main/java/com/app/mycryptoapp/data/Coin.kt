package com.app.mycryptoapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 *
 * Created by Giteeka on 11/22/2018.
 */
@Entity
@Parcelize
data class Coin(@PrimaryKey(autoGenerate = true) val cId: Int
                , val Id: Int
                , val Url: String?
                , val ImageUrl: String?
                , val Name: String?
                , val Symbol: String?
                , val CoinName: String?
                , val Algorithm: String?) : Parcelable {
    override fun toString(): String {
        return "Id :: $Id ---------------- Sysmbol $Symbol"
    }

    constructor(Symbol: String?, Name: String?) : this(0, 0, "", "", Name, Symbol, Name, "")


}

//git remote add origin https://gitr does not match a