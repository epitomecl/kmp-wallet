package com.epitomecl.kmpwallet.mvp.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.di.Injector
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.util.DialogUtils
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_account_list.*

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
        var view = inflater.inflate(R.layout.fragment_account_list, container, false)
        return view
    }

    override fun createPresenter() = mPresenter

    override fun getMvpView() = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCreateAccount.setOnClickListener {
            createNewAccount()
        }
    }

    private fun createNewAccount() {
        if(etAccountLabel.text.toString().length==0){
            DialogUtils.setAlertDialog(context, "account label 을 확인해주세요!")
            return;
        }

        mPresenter.createNewAccount(etAccountLabel.text.toString())
    }

    override fun onSuccessAccountCreated() {
        Toast.makeText(context, "Successfully created!!", Toast.LENGTH_LONG).show()
    }

    override fun onError(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}