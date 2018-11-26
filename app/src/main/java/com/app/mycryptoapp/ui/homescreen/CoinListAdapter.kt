package com.app.mycryptoapp.ui.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.mycryptoapp.R
import com.app.mycryptoapp.data.Coin
import com.app.mycryptoapp.utils.GlideApp
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

// create click listner for click item of coin
open class CoinListAdapter(val onItemClick: (coin: Coin?) -> Unit) : PagedListAdapter<Coin, CoinListAdapter.CoinViewHolder>(diffCallback) {

    internal var baseImageUrl: String = ""

    private val TAG = "CoinListAdapter"
    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder =
            CoinViewHolder(parent)

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Coin>() {
            override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean =
                    oldItem.cId == newItem.cId

            override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean =
                    oldItem == newItem

        }
    }

    inner class CoinViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_coin_view, parent, false)) {

        private val cvRoot = itemView.findViewById<CardView>(R.id.cv_root)
        private val nameView = itemView.findViewById<AppCompatTextView>(R.id.tv_name)
        private val fullNameView = itemView.findViewById<AppCompatTextView>(R.id.tv_full_name)
        private val algorithmView = itemView.findViewById<AppCompatTextView>(R.id.tv_algorithm)
        private val coinImage = itemView.findViewById<AppCompatImageView>(R.id.iv_coin)

        /**
         * Bind Row data with view
         * */
        fun bindTo(coin: Coin?) {
            with(coin) {
                nameView.text = "Name : ${this?.Name}"
                fullNameView.text = "Full name : ${this?.CoinName}"
                algorithmView.text = "Algorithm : ${this?.Algorithm}"
                if (!this?.ImageUrl.isNullOrBlank())
                    GlideApp.with(coinImage.context).load("$baseImageUrl${this?.ImageUrl}").placeholder(R.mipmap.ic_placeholder_coin).apply(RequestOptions.bitmapTransform(CircleCrop())).into(coinImage)
                else
                    GlideApp.with(coinImage.context).load(R.mipmap.ic_placeholder_coin).into(coinImage)
                cvRoot.setOnClickListener { onItemClick(this) }
            }
        }

    }
}
