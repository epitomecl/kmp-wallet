package com.epitomecl.kmpwallet.mvp.intro.regist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.mvp.intro.IntroActivity
import com.epitomecl.kmpwallet.util.DialogUtils
import kotlinx.android.synthetic.main.fragment_regist.*

class RegistFragment : BaseFragment<RegistContract.View, RegistPresenter>(),
        RegistContract.View {

    var mPresenter: RegistPresenter = RegistPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_regist, container, false)
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

        btnRegist.setOnClickListener { onRegist() }
        btnRegistCancel.setOnClickListener { onRegistCancel() }
        btnChangeLogin.setOnClickListener { onChangeLogin() }
    }

    fun onRegist() {
        if (isvalidRegistData()) {
            var id = etRegistId.text.toString()
            var pw = etRegistPass.text.toString()

            mPresenter.registUser(id, pw)
                    .subscribe { s ->
                        if(s.session != null) {
                            AppData.setUserIndex(s.index)
                            onChangeWalletActivity()
                        }
                        else {
                            DialogUtils.setAlertDialog(context, getString(R.string.msg_alert_regist_error))
                        }
                    }
        }
    }

    fun onRegistCancel() {
        (getContext() as IntroActivity).onLogin()
    }

    fun onChangeLogin() {
        (getContext() as IntroActivity).onLogin()
    }

    fun onChangeWalletActivity() {
        (getContext() as IntroActivity).onChangeWalletActivity()
    }

    private fun isvalidRegistData(): Boolean {
        if (etRegistId.text.length < 4) {
            DialogUtils.setAlertDialog(context, getString(R.string.msg_alert_userid_short))
            return false
        }
        else if (etRegistPass.text.length < 4) {
            DialogUtils.setAlertDialog(context, getString(R.string.msg_alert_password_short))
            return false
        }

        return true
    }
}