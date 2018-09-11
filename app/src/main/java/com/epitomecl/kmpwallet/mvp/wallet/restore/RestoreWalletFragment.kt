package com.epitomecl.kmpwallet.mvp.wallet.create

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epitomecl.kmpwallet.R
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.Toast
import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.WalletsFragment
import kotlinx.android.synthetic.main.fragment_backupwallet.*
import kotlinx.android.synthetic.main.fragment_restorewallet.*
import kotlinx.android.synthetic.main.item_restoredwallet.view.*
import kotlinx.android.synthetic.main.item_wallet.view.*

class RestoreWalletFragment : BaseFragment<RestoreWalletContract.View, RestoreWalletPresenter>(),
        RestoreWalletContract.View {

    var mPresenter: RestoreWalletPresenter = RestoreWalletPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_restorewallet, container, false)
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

        rvRestoredWallets.layoutManager = LinearLayoutManager(context)
        rvRestoredWallets.adapter = RestoredWalletsItemAdapter(mPresenter.restoredWallets(), context!!, this)
    }

    override fun onClickRestore() {
        if (isValidLabel()) {
            //mPresenter.restoreWallet(CryptoType.BITCOIN_TESTNET, etRestoreSeed.text.toString())
            fragmentManager?.popBackStack()
        }
    }

    override fun isValidLabel(): Boolean {
//        if (etRestoreSeed.text.length > 0) {
//            return true
//        }

        Toast.makeText(context, getString(R.string.msg_wallet_label_short), Toast.LENGTH_SHORT).show()

        return false
    }


    class RestoredWalletsItemAdapter(private val items : List<String>, private val context: Context, private val fragment: RestoreWalletFragment) : RecyclerView.Adapter<ViewHolder>() {

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restoredwallet, parent, false), fragment)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            val label: String = items[position]
            holder?.tvWalletLabel?.text = label
            holder?.bind()
        }

    }

    class ViewHolder (view: View, fragment: RestoreWalletFragment) : RecyclerView.ViewHolder(view) {
        val view = view
        val fragment = fragment
        // hold ui elements
        val tvWalletLabel = view.tvRestoredWalletLabel
        val btnRestore: Button = view.btnRestore

        fun bind() {
            btnRestore.setOnClickListener {
                //tvWalletLabel
            }
        }
    }
}
