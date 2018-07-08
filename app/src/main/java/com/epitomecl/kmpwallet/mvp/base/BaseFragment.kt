package com.epitomecl.kmpwallet.mvp.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast

abstract class BaseFragment<V : BaseView, P : BasePresenterImpl<V>>
    : Fragment(), BaseView {


    private lateinit var presenter: P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = createPresenter()
        presenter.attachView(getMvpView())
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

//    fun getActivityComponent() : ActivityComponent? {
//        if(mActivity != null){
//            return mActivity.getActivityComponent()
//        }
//        return null
//    }

    protected abstract fun createPresenter() : P

    protected abstract fun getMvpView() : V
}
