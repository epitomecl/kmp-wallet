package com.epitomecl.kmpwallet.mvp.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.epitomecl.kmpwallet.di.component.ActivityComponent

abstract class BaseFragment<in V : BaseView, T : BasePresenter<in V>>
    : Fragment(), BaseView {

    protected abstract var mPresenter: T
    private lateinit var mActivity: BaseActivity<V, T>

    override fun getContext(): Context = mActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun onAttach(context : Context) {
        super.onAttach(context)
        if(context is BaseActivity<*, *>) {
            this.mActivity = context as BaseActivity<V, T>
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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    fun getActivityComponent() : ActivityComponent? {
        if(mActivity != null){
            return mActivity.getActivityComponent()
        }
        return null
    }

}
