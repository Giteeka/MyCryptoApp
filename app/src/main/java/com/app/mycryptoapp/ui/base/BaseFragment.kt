package com.app.mycryptoapp.ui.base

import android.content.Context
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.app.mycryptoapp.R

/**
 * Created by Giteeka on 11/23/2018.
 *
 * Base Fragment with common functions
 *
 * */
abstract class BaseFragment : Fragment() {
    protected var mContext: Context? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    /**
     * initiateUI function gives feature of abstraction to perform uniform patterns for sub class of fragments
     * entry point for initialize UI and call APIs
     */
    abstract fun initiateUI()

    /**
     * setListener function gives feature of abstraction to perform uniform patterns for sub class of fragments
     * set click listeners or observers here
     */
    abstract fun setListener()

    /**
     * Common utility function to show error toasts
     */
    protected fun showError(message: String?) {
        if (!message.isNullOrBlank())
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        else
            Toast.makeText(activity, getString(R.string.oops_something_went_wrong), Toast.LENGTH_LONG).show()
    }
}