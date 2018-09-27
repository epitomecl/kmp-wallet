package com.epitomecl.kmpwallet.mvp.wallet.restore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epitomecl.kmpwallet.R
import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.mvp.wallet.WalletActivity
import kotlinx.android.synthetic.main.fragment_backupwallet.*

class WalletFromSeedFragment : BaseFragment<WalletFromSeedContract.View, WalletFromSeedPresenter>(),
        WalletFromSeedContract.View {

    var mPresenter: WalletFromSeedPresenter = WalletFromSeedPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_backupwallet, container, false)
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

        btnRestoreWallet.setOnClickListener { onClickRestore() }
    }

    override fun onClickRestore() {
        mPresenter.restoreWallet(CryptoType.BITCOIN_TESTNET, etRestoreSeed.text.toString())
        (context as WalletActivity).onShowWalletList()
    }

    override fun isValidLabel(): Boolean {
        if (etRestoreSeed.text.length == 32) {
            return true
        }

        Toast.makeText(context, getString(R.string.msg_wallet_label_short), Toast.LENGTH_SHORT).show()

        return false
    }

}
