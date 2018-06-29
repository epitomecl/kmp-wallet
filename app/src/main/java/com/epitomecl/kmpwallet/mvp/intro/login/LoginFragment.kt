package com.epitomecl.kmpwallet.mvp.intro.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.mvp.intro.IntroActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<LoginContract.View,
        LoginContract.Presenter>(),
        LoginContract.View {

    override var mPresenter: LoginContract.Presenter = LoginPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_login, container, false)
        var component = getActivityComponent()
        if(component != null){
            //component.inject(this)
            mPresenter.attachView(this)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val anim = TranslateAnimation(1000f, // fromXDelta
                0f, // toXDelta
                0f, // fromYDelta
                0f)// toYDelta
        anim.duration = 300
        view.startAnimation(anim)

        btnLogin.setOnClickListener { onLogin() }
        btnLoginCancel.setOnClickListener { onLoginCancel() }
        btnChangeRegist.setOnClickListener { onChangeRegist() }
    }

    fun onLogin() {
        if(isvalidLoginData()){
            var id = etLoginId.text.toString()
            var pw = etLoginPass.text.toString()

            //TODO checck login result
            mPresenter.loginUser(id, pw)
                .subscribe { s ->
                    s.session
                    (getContext() as IntroActivity).onChangeWalletActivity()
                }
        }
    }

    fun onLoginCancel() {
        (getContext() as IntroActivity).onRegist()
    }

    fun onChangeRegist() {
        (getContext() as IntroActivity).onRegist()
    }

    private fun isvalidLoginData(): Boolean {
        if (etLoginId.text.length < 4) {
            Toast.makeText(context, "user id is too short.", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etLoginPass.text.length < 4) {
            Toast.makeText(context, "password is too short.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}