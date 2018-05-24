package com.epitomecl.kmpwallet.mvp

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.epitomecl.kmpwallet.KMPWalletApp
import com.epitomecl.kmpwallet.di.component.ActivityComponent
import com.epitomecl.kmpwallet.di.component.DaggerActivityComponent
import com.epitomecl.kmpwallet.di.module.ActivityModule

abstract class BaseActivity<in V : BaseView, T : BasePresenter<V>>
    : AppCompatActivity(), BaseView {

    private lateinit var mActivityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun getContext(): Context = this

    protected abstract var mPresenter: T

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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    fun getActivityComponent() : ActivityComponent {
        if(mActivityComponent == null){
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(ActivityModule(this))
                    .appComponent( KMPWalletApp.getAppComponent(this) )
                    .build();
        }

        return mActivityComponent
    }
}