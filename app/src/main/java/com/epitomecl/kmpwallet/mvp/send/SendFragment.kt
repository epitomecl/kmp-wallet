package com.epitomecl.kmpwallet.mvp.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.di.Injector
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import javax.inject.Inject

class SendFragment : BaseFragment(),
        SendContract.View {

    companion object {
        fun newInstance(): SendFragment {
            val fragment = SendFragment()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            return fragment
        }
    }

    @Inject
    lateinit var mPresenter: SendPresenter

    init {
        Injector.getInstance().getPresenterComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_send, container, false)
//        var component = getActivityComponent()
//        if(component != null){
//            component.inject(this)
//            mPresenter.attachView(this)
//        }
        return view
    }



}