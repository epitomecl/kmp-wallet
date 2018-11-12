package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.accounts

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import com.epitomecl.kmp.core.wallet.AccountData
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.di.Injector
import com.epitomecl.kmpwallet.model.ActiveAddress
import com.epitomecl.kmp.core.wallet.UTXO
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.InfoActivity
import kotlinx.android.synthetic.main.fragment_accounts.*
import kotlinx.android.synthetic.main.item_account.view.*
import java.math.BigInteger
import javax.inject.Inject


class AccountsFragment : BaseFragment<AccountsContract.View, AccountsPresenter>(),
            AccountsContract.View {

    companion object {
        fun newInstance(): AccountsFragment {
            val fragment = AccountsFragment()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            return fragment
        }
    }

    @Inject
    lateinit var mPresenter: AccountsPresenter

    init {
        Injector.getInstance().getPresenterComponent().inject(this)
    }

    override fun createPresenter() = mPresenter

    override fun getMvpView() = this

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_accounts, container, false)
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

        rvAccounts.layoutManager = LinearLayoutManager(context)
        rvAccounts.adapter = AccountItemAdapter((context as InfoActivity).getHDWalletData().accounts, context!!, this)
    }

    override fun onChangeSendTxOFragment(item: AccountData) {
        (context as InfoActivity).setAccount(item)
        (context as InfoActivity).onShowSendTxO()
    }

    class AccountItemAdapter(private val items : List<AccountData>, val context: Context, private val fragment: AccountsFragment) : RecyclerView.Adapter<ViewHolder>() {

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_account, parent, false), fragment)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            val accountData: AccountData = items[position]
            holder?.tvAccountLabel?.text = accountData.cache.receiveAccount
            holder?.tvAccountBalance?.text = context.getString(R.string.zero_balance)
            holder?.bind(accountData)
        }
    }

    class ViewHolder (view: View, fragment: AccountsFragment) : RecyclerView.ViewHolder(view) {
        val view = view
        val fragment = fragment
        // hold ui elements
        val tvAccountLabel = view.tvAccountLabel
        val tvAccountBalance = view.tvAccountBalance
        val btnAccountSync = view.btnAccountSync

        fun bind(item: AccountData) {
            tvAccountLabel.setOnClickListener {
                fragment.onChangeSendTxOFragment(item)
            }

            tvAccountLabel.text = getActiveReceiveAddress(item)
            tvAccountBalance.text = getBalance(item)

            btnAccountSync.setOnClickListener {
                val ft = fragment.fragmentManager?.beginTransaction()
                ft?.detach(fragment)
                ft?.attach(fragment)
                ft?.commit()
            }
        }

        fun getBalance(item: AccountData) : String {
            item.utxos = APIManager.balance(item.xpub,"api_code")
            var balance: BigInteger = item.balance

            return UTXO.satoshiToCoin(balance).toString()
        }

        fun getActiveReceiveAddress(item: AccountData) : String {
            val result : ActiveAddress = APIManager.activeReceiveAddress(item.xpub,"api_code")
            item.cache.receiveAccount = result.address

            return item.cache.receiveAccount
        }
    }
}
