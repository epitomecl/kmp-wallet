package com.epitomecl.kmpwallet.mvp.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.di.Injector
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import javax.inject.Inject

class AccountFragment : BaseFragment<AccountContract.View, AccountPresenter>(),
        AccountContract.View {

    companion object {
        fun newInstance(): AccountFragment {
            val fragment = AccountFragment()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            return fragment
        }
    }

    @Inject
    lateinit var mPresenter : AccountPresenter

    init {
        Injector.getInstance().getPresenterComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_send, container, false)
        return view
    }

    override fun createPresenter() = mPresenter

    override fun getMvpView() = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}