package com.app.mycryptoapp.ui.base

import android.content.Context
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.app.mycryptoapp.R

/**
 * Empty Fragment
 */
abstract class BaseFragment : Fragment() {
    protected var mContext: Context? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    abstract fun initiateUI()

    abstract fun setListener()

    protected fun showError(message: String?) {
        if (!message.isNullOrBlank())
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        else
            Toast.makeText(activity, getString(R.string.oops_something_went_wrong), Toast.LENGTH_LONG).show()
    }
}