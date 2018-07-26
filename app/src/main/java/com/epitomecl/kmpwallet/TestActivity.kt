package com.epitomecl.kmpwallet

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.epitomecl.kmpwallet.mvp.base.*

class TestActivity : BaseActivity() {

//    @Inject
//    lateinit var mDataManager: KmpDataManager

//    @Inject
//    lateinit var mPagerAdapter: MainPagerAdapter

    var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        getActivityComponent().inject(this)

        setContentView(R.layout.activity_test)
        setSupportActionBar(findViewById(R.id.toolbar))

        val tabLayout : TabLayout = findViewById(R.id.tabs)
        tabLayout.addTab(tabLayout.newTab().setText("BACKUP"))
        tabLayout.addTab(tabLayout.newTab().setText("SEND"))
        tabLayout.addTab(tabLayout.newTab().setText("WALLET"))

        val mPagerAdapter = MainPagerAdapter(this)
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


