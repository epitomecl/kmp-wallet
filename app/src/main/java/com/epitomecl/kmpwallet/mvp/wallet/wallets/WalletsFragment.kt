package com.epitomecl.kmpwallet.mvp.wallet.wallets

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_wallets.*
import kotlinx.android.synthetic.main.item_wallet.view.*

class WalletsFragment : BaseFragment<WalletsContract.View, WalletsPresenter>(),
        WalletsContract.View {

    var mPresenter: WalletsPresenter = WalletsPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_wallets, container, false)
        return view
    }

    override fun createPresenter() = mPresenter

    override fun getMvpView() = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val anim = TranslateAnimation(1000f, // fromXDelta
                0f, // toXDelta
                0f, // fromYDelta
                0f)// toYDelta
        anim.duration = 300
        view.startAnimation(anim)

        rvWallets.layoutManager = LinearLayoutManager(context)
        rvWallets.adapter = WalletItemAdapter(mPresenter.initWallets(), context!!)
    }

    class WalletItemAdapter(private val items : List<HDWalletData>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_wallet, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.tvWalletLabel?.text = items[position].label
            holder?.tvCryptoType?.text = items[position].cryptoType.toString()
        }
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // hold ui elements
        val tvWalletLabel = view.tvWalletLabel
        val tvCryptoType = view.tvCryptoType
    }
}