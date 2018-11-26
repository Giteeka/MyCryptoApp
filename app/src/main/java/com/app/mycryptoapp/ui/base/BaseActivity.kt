package com.app.mycryptoapp.ui.base

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import com.app.mycryptoapp.R
import com.app.mycryptoapp.utils.PreferenceHelper


/**
 *
 * Created by admin on 11/23/2018.
 */
abstract class BaseActivity : AppCompatActivity() {

    private val TAG = "BaseActivity"

    // All child class can use the context dinitialize by @BaseAppCompatActivity class
    private var mContext: Context? = null

    abstract fun initUI();

    abstract fun setListener();

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mContext = this
    }

    protected fun getPrefHelper() : SharedPreferences? {
        return mContext?.let { PreferenceHelper.defaultPrefs(it) };
    }

    fun showError(message: String?) {
        if (!message.isNullOrBlank())
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, getString(R.string.oops_something_went_wrong), Toast.LENGTH_LONG).show()
    }
}