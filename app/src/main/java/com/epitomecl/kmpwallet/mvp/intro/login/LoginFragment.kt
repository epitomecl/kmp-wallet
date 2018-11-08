package com.epitomecl.kmpwallet.mvp.intro.login

import android.content.DialogInterface
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
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<LoginContract.View, LoginPresenter>(),
        LoginContract.View {

    var mPresenter: LoginPresenter = LoginPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_login, container, false)
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

        etLoginId.setText(AppData.getLoginId())

        btnLogin.setOnClickListener { onLogin() }
        btnLoginCancel.setOnClickListener { onLoginCancel() }
        btnChangeRegist.setOnClickListener { onChangeRegist() }
        btnWipeData.setOnClickListener { onWipeData() }
    }

    fun onLogin() {
        if(isvalidLoginData()){
            var id = etLoginId.text.toString()
            var pw = etLoginPass.text.toString()

            //TODO checck login result
            mPresenter.loginUser(id, pw)
                .subscribe { s ->
                    if(s.session != null) {
                        AppData.setUserIndex(s.index)
                        (getContext() as IntroActivity).onChangeWalletActivity()
                    }
                    else {
                        DialogUtils.setAlertDialog(context, getString(R.string.msg_login_error))
                    }
                }
        }
    }

    fun onLoginCancel() {
        (getContext() as IntroActivity).onRegist()
    }

    fun onChangeRegist() {
        (getContext() as IntroActivity).onRegist()
    }

    fun onWipeData() {
        DialogUtils.setConfirmDialog(context, getString(R.string.msg_alert_wipedata), DialogInterface.OnClickListener { dialog, which ->
                onConfirmWipeData()
            }
        )
    }

    private fun isvalidLoginData(): Boolean {
        if (etLoginId.text.length < 4) {
            DialogUtils.setAlertDialog(context, getString(R.string.msg_alert_userid_short))
            return false
        }
        else if (etLoginPass.text.length < 4) {
            DialogUtils.setAlertDialog(context, getString(R.string.msg_alert_password_short))
            return false
        }

        return true
    }

    private fun onConfirmWipeData() {
        AppData.wipeData()
        etLoginId.setText("")
        etLoginPass.setText("")
        DialogUtils.setAlertDialog(context,getString(R.string.msg_alert_finish_wipedata))
    }
}
