package com.epitomecl.kmpwallet.mvp.backup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.di.Injector
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_backup_simple.*
import javax.inject.Inject

class BackupFragment : BaseFragment<BackupContract.View, BackupPresenter>(),
        BackupContract.View {
    companion object {
        fun newInstance(): BackupFragment {
            val fragment = BackupFragment()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            return fragment
        }
    }

    @Inject
    lateinit var mPresenter: BackupPresenter

    init {
        Injector.getInstance().getPresenterComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_backup_simple, container, false)
        return view
    }

    override fun createPresenter() = mPresenter

    override fun getMvpView() = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvWords.text = mPresenter.getWords()

        val hints = mPresenter.showWordHints()
        val mnemonicRequestHint = resources.getStringArray(R.array.mnemonic_word_requests)
        etFirstWord.hint = mnemonicRequestHint[hints[0]]
        etSecondWord.hint = mnemonicRequestHint[hints[1]]
        etThirdWord.hint = mnemonicRequestHint[hints[2]]

        btnBackup.setOnClickListener {
            mPresenter.onVerifyClicked(etFirstWord.text.toString(), etSecondWord.text.toString(), etThirdWord.text.toString())
        }
    }

    override fun onCompletedBackup() {
        Toast.makeText(context, "Backup completed...!", Toast.LENGTH_LONG).show()
    }

}