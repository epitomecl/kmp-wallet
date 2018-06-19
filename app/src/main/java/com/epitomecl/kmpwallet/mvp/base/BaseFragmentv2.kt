package com.epitomecl.kmpwallet.mvp.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.epitomecl.kmpwallet.di.component.ActivityComponent

abstract class BaseFragmentv2
    : Fragment(), BaseView {

    private lateinit var mActivity: BaseActivityv2

    override fun getContext(): Context = mActivity

    override fun onAttach(context : Context) {
        super.onAttach(context)
        if(context is BaseActivityv2) {
            this.mActivity = context
        }
    }

    override fun showLoading() {
        //
    }

    override fun hideLoading() {
        //
    }

    override fun onFailureRequest(msg: String) {
        //
    }

    override fun showError(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun hideKeyboard() {
        mActivity.hideKeyboard()
    }

    fun getActivityComponent() : ActivityComponent? {
        if(mActivity != null){
            return mActivity.getActivityComponent()
        }
        return null
    }

}
