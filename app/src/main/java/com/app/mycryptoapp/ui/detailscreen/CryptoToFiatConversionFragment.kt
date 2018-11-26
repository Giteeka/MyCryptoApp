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
import com.app.mycryptoapp.R
import com.app.mycryptoapp.data.Coin
import com.app.mycryptoapp.network.ApiClient
import com.app.mycryptoapp.network.ApiService
import com.app.mycryptoapp.ui.base.BaseFragment
import com.app.mycryptoapp.utils.Utils
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_currency_convertor.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * First tab fragment
 * Convert the selected cryto coin value in top 10 Fiat currency like, USD, INR etc.
 * Calculator for crypto currency to fiat currency
 */
class CryptoToFiatConversionFragment : BaseFragment() {

    private var baseCoin: Coin? = null
    private var currencyValueMap: Map<String, Double>? = null
    private val TAG = "CryptoToFiatFragment"

    companion object {
        /**
         * Fragment newInstance() method to initialize fragment
         * */
        @JvmStatic
        fun newInstance(coin: Coin): Fragment {
            val fragment = CryptoToFiatConversionFragment()
            val bundle = Bundle()
            bundle.putParcelable("coin", coin)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * Load array list of top 10 fiat currency
     * */
    private fun getList(): ArrayList<Coin> {
        val list = ArrayList<Coin>()
        list.add(Coin("USD", "United States Dollar"))
        list.add(Coin("AUD", "Australian dollar"))
        list.add(Coin("BRL", "Brazilian real"))
        list.add(Coin("CNY", "Chinese yuan"))
        list.add(Coin("GBP", "Pound sterling"))
        list.add(Coin("INR", "Indian rupee"))
        list.add(Coin("JPY", "Japanese yen"))
        list.add(Coin("KRW", "South Korean won"))
        list.add(Coin("RUB", "Russian ruble"))
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


    override fun initiateUI() {
        initApi()
    }

    override fun setListener() {
        //Adapter for spinner
        spinner.adapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_dropdown_item,
                // map object array list to string array to load dropdown data
                getList().map { coin -> "${coin.Name} (${coin.Symbol})" })

        //item selected listener for spinner
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
        et_price.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                convertValue(spinner.selectedItemPosition)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    private fun convertValue(s: Int) {
        val coin = getList().get(s)

        try {
            val baseValue = et_price.text.toString().toDouble()
            val selectedFiatValue = currencyValueMap?.get(coin.Symbol)
            tv_value.text = "${et_price.text} ${baseCoin?.CoinName} (${baseCoin?.Symbol})= ${Utils.cryptoConverter(baseValue, selectedFiatValue)} ${coin.Name} (${coin.Symbol})"
        } catch (e: NumberFormatException) {
            tv_value.text = getString(R.string.label_error_output)
        }
    }


    private fun initApi() {
        baseCoin = arguments?.getParcelable<Coin>("coin")
        tv_from_coin.text = "${baseCoin?.CoinName} (${baseCoin?.Symbol})"
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        // convert coin object array list to coin symbol comma separated string for api
        val getCoinApi = baseCoin?.Symbol?.let { apiService.getCoin(it, getList().asSequence().map { coin -> coin.Symbol }.joinToString(",")) }
        getCoinApi?.enqueue(object : Callback<JsonElement> {

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                showError(t.message)
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val element = response.body()
                    val type = object : TypeToken<Map<String, Double>>() {}.type
                    currencyValueMap = GsonBuilder().create().fromJson(element?.asJsonObject, type)
                } else {
                    showError(response.errorBody().toString())
                }
            }
        })
    }

}