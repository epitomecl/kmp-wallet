package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.di.Injector
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_createaccount.*
import javax.inject.Inject

class CreateAccountFragment : BaseFragment<CreateAccountContract.View,
        CreateAccountPresenter>(),
        CreateAccountContract.View {

    companion object {
        fun newInstance(): CreateAccountFragment {
            val fragment = CreateAccountFragment()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            return fragment
        }
    }

    @Inject
    lateinit var mPresenter: CreateAccountPresenter

    init {
        Injector.getInstance().getPresenterComponent().inject(this)
    }

    override fun createPresenter() = mPresenter

    override fun getMvpView() = this

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_createaccount, container, false)
//        var component = getActivity()
//        if(component != null){
//            //component.inject(this)
//            mPresenter.attachView(this)
//        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val anim = TranslateAnimation(1000f, // fromXDelta
                0f, // toXDelta
                0f, // fromYDelta
                0f)// toYDelta
        anim.duration = 300
        view.startAnimation(anim)

        btnCreateAccount.setOnClickListener { onCreateAccounts() }
    }

    override fun onCreateAccounts()
    {
        if (isValidLabel()) {
            mPresenter.createAccount(context, etAccountLabel.text.toString())
            fragmentManager?.popBackStack()
        }
    }

    override fun isValidLabel(): Boolean {
        if (etAccountLabel.text.length > 4) {
            return true
        }

        Toast.makeText(context, getString(R.string.msg_wallet_label_short), Toast.LENGTH_SHORT).show()

        return false
    }
}