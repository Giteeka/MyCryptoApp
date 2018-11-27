package com.app.mycryptoapp.ui.detailscreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.mycryptoapp.R
import com.app.mycryptoapp.data.Coin
import com.app.mycryptoapp.data.CoinPriceViewModel
import com.app.mycryptoapp.ui.base.BaseFragment
import com.app.mycryptoapp.utils.Utils
import kotlinx.android.synthetic.main.fragment_currency_convertor.*

/**
 * Created by Giteeka
 * Second tab fragment
 * Convert the selected crypto coin value in top 10 crypto currency like, BTC, ETH etc.
 * Calculator for crypto currency to crypto currency
 */
class CryptoToCryptoConversionFragment : BaseFragment() {
    /**
     * selected coin from coin list
     * */
    private var baseCoin: Coin? = null


    /**
     * currency values map fetched from API for 1 base coin
     * Like selected coin is bit coin then
     * 1 BTC = 3540 USD
     * 1 BTC = 2,50,455 INR
     * etc...
     * */
    private var currencyValueMap: Map<String, Double>? = null

    companion object {
        /**
         * Fragment newInstance() method to initialize fragment
         * */
        @JvmStatic
        fun newInstance(coin: Coin): Fragment {
            val fragment = CryptoToCryptoConversionFragment()
            val bundle = Bundle()
            bundle.putParcelable("coin", coin)
            fragment.arguments = bundle
            return fragment
        }
    }


    /**
     * Load array list of top 10 crypto currency
     * */
    private fun getList(): ArrayList<Coin> {
        val list = ArrayList<Coin>()
        list.add(Coin("BTC", "Bitcoin"))
        list.add(Coin("ETH", "Ethereum"))
        list.add(Coin("XRP", "Ripple"))
        list.add(Coin("BCH", "Bitcoin cash"))
        list.add(Coin("XLM", "Stellar Lumens"))
        list.add(Coin("TRX", "TRON"))
        list.add(Coin("LTC", "Litecoin"))
        list.add(Coin("ADA", "Cardano"))
        list.add(Coin("XMR", "Monero"))
        list.add(Coin("USDT", "Tether"))
        return list
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency_convertor, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initiateUI()
        setListener()
    }


    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(CoinPriceViewModel::class.java)
    }

    override fun initiateUI() {
        baseCoin = arguments?.getParcelable<Coin>("coin")
        tv_from_coin.text = "${baseCoin?.CoinName} (${baseCoin?.Symbol})"

        viewModel.loadCurrencyValueMap(baseCoin, getList()).observe(this, Observer { map ->
            currencyValueMap = map
        })
        viewModel.error?.observe(this, Observer { errorMessage ->
            showError(errorMessage)
        })

    }

    override fun setListener() {

        spinner.adapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_dropdown_item,
                // map object array list to string array to load dropdown data
                getList().map { coin -> "${coin.Name} (${coin.Symbol})" })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                convertValue(p2)
            }
        }

        /**
         * Calculate currency value as user changes amount in edit text field
         * */
        et_price.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                        convertValue(spinner.selectedItemPosition)
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                })
    }

    /**
     * Convert crypto currency to another crypto currency
     */
    private fun convertValue(s: Int) {
        val coin = getList().get(s)
        if (et_price.text.toString().isBlank()) {
            tv_value.text = ""
            return
        }
        try {
            val coinQty = et_price.text.toString().toDouble()
            val selectedFiatValue = currencyValueMap?.get(coin.Symbol)
            val convertedValue = Utils.cryptoConverter(coinQty, selectedFiatValue)
            tv_value.text = "${et_price.text} ${baseCoin?.CoinName} (${baseCoin?.Symbol}) = ${Utils.getValueUpToSixDecimal(convertedValue)} ${coin.Name} (${coin.Symbol})"

        } catch (e: NumberFormatException) {
            tv_value.text = getString(R.string.label_error_output)
        }
    }


}