package com.epitomecl.kmpwallet

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.epitomecl.kmpwallet.data.KmpDataManager
import com.epitomecl.kmpwallet.mvp.base.BaseActivity
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView
import com.epitomecl.kmpwallet.mvp.base.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_test.*
import javax.inject.Inject

class TestActivity : BaseActivity<BaseView, BasePresenter<BaseView>>() {

    override var mPresenter: BasePresenter<BaseView>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    @Inject
    lateinit var mDataManager: KmpDataManager

    @Inject
    lateinit var mPagerAdapter: MainPagerAdapter

    var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getActivityComponent().inject(this)

        setContentView(R.layout.activity_test)
        setSupportActionBar(findViewById(R.id.toolbar))

        val tabLayout : TabLayout = findViewById(R.id.tabs)
        tabLayout.addTab(tabLayout.newTab().setText("SEND"))
        tabLayout.addTab(tabLayout.newTab().setText("WALLET"))

        mViewPager = findViewById(R.id.container)
        mPagerAdapter.setTabCount(tabLayout.tabCount)
        mViewPager!!.adapter = mPagerAdapter
        mViewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mViewPager!!.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                hideKeyboard()
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


}


