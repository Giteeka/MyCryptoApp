package com.app.mycryptoapp.ui.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.app.mycryptoapp.R
import com.app.mycryptoapp.utils.PreferenceHelper


/**
 * Created by Giteeka on 11/23/2018.
 *
 * Base Activity with common functions
 *
 */
abstract class BaseActivity : AppCompatActivity() {

    private val TAG = "BaseActivity"

    // All child class can use the context dinitialize by @BaseAppCompatActivity class
    private var mContext: Context? = null

    /**
     * initiateUI function gives feature of abstraction to perform uniform patterns for sub class of activities
     * entry point for initialize UI and call APIs
     */
    abstract fun initUI();

    /**
     * setListener function gives feature of abstraction to perform uniform patterns for sub class of fragments
     * set click listeners or observers here
     */
    abstract fun setListener();

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mContext = this
    }

    /**
     * Common utility function to create instance of shared preferences
     */
    protected fun getPrefHelper(): SharedPreferences? {
        return mContext?.let { PreferenceHelper.defaultPrefs(it) };
    }

    /**
     * Common utility function to show error toasts
     */
    fun showError(message: String?) {
        if (!message.isNullOrBlank())
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, getString(R.string.oops_something_went_wrong), Toast.LENGTH_LONG).show()
    }
}