package com.epitomecl.kmpwallet.mvp.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import android.view.ViewGroup
import com.epitomecl.kmpwallet.mvp.send.SendFragment

class MainPagerAdapter : FragmentPagerAdapter {

    private val TAB_IDX_SEND = 0
    private val TAB_IDX_WALLET = 1
    private val TAB_IDX_ACCOUNT = 2

    private var mTabCount: Int = 0

    private val mActivity: AppCompatActivity

    private val mFragmentInstances = SparseArray<Any>()

    constructor(activity : AppCompatActivity) : super(activity.supportFragmentManager) {
        mActivity = activity
    }

    fun setTabCount(tabCount: Int) {
        this.mTabCount = tabCount
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position)

        mFragmentInstances.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        mFragmentInstances.remove(position)
        super.destroyItem(container, position, `object`)
    }

    fun getFragment(position: Int) : Any {
        return mFragmentInstances.get(position)
    }

    override fun getItem(position: Int): Fragment {
        if (mActivity is BaseActivity) {
            mActivity.hideKeyboard()
        }

        var fragment = Fragment()

        when (position) {
            TAB_IDX_SEND -> fragment = SendFragment.newInstance()

//            TAB_IDX_ACCOUNT -> fragment = AccountMainFragment.newInstance()
//
//            TAB_IDX_WALLET -> fragment = WalletFragment.newInstance()
        }

        return fragment
    }

    override fun getCount(): Int {
        return mTabCount
    }
}