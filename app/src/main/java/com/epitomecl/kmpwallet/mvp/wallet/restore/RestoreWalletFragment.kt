package com.epitomecl.kmpwallet.mvp.wallet.restore

import android.content.Context
import android.content.DialogInterface
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
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.mvp.wallet.WalletActivity
import com.epitomecl.kmpwallet.util.DialogUtils
import kotlinx.android.synthetic.main.fragment_restorewallet.*
import kotlinx.android.synthetic.main.item_restoredwallet.view.*

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

        val restoredWallets: List<String> = mPresenter.restoredWallets()
        if(restoredWallets.isNotEmpty()) {
            rvRestoredWallets.layoutManager = LinearLayoutManager(context)
            rvRestoredWallets.adapter = RestoredWalletsItemAdapter(restoredWallets, context!!, this)
        }
        else {
            DialogUtils.setAlertDialog(context, getString(R.string.msg_restored_wallet_not_exist), DialogInterface.OnClickListener { dialog, which ->
                (context as WalletActivity).onCancelRestore()
            })
        }
    }

    override fun restoreWallet(label: String) {
        if(mPresenter.restoreWallet(label)){
            (context as WalletActivity).onShowWalletList()
        }
    }

    class RestoredWalletsItemAdapter(private val items : List<String>, private val context: Context, private val fragment: RestoreWalletFragment) : RecyclerView.Adapter<ViewHolder>() {

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restoredwallet, parent, false), fragment)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
                val label: String = tvWalletLabel.text.toString()
                fragment.restoreWallet(label)
            }
        }
    }
}
