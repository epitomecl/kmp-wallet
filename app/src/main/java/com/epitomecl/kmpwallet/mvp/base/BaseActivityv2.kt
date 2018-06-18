package com.epitomecl.kmpwallet.mvp.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.epitomecl.kmpwallet.KMPWalletApp
import com.epitomecl.kmpwallet.di.component.ActivityComponent
import com.epitomecl.kmpwallet.di.component.DaggerActivityComponent
import com.epitomecl.kmpwallet.di.module.ActivityModule

abstract class BaseActivityv2
    : AppCompatActivity(), BaseView {

    private lateinit var mActivityComponent: ActivityComponent

    override fun getContext(): Context = this

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
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

    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun getActivityComponent() : ActivityComponent {
        if(!::mActivityComponent.isInitialized){
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(ActivityModule(this))
                    .appComponent( KMPWalletApp.getAppComponent(this) )
                    .build();
        }

        return mActivityComponent
    }
}