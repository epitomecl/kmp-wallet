package com.epitomecl.kmpwallet.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.epitomecl.kmpwallet.di.component.ApplicationComponent
import com.epitomecl.kmpwallet.di.component.DaggerApplicationComponent
import com.epitomecl.kmpwallet.di.component.PresenterComponent
import com.epitomecl.kmpwallet.di.module.ApiModule
import com.epitomecl.kmpwallet.di.module.AppModule

enum class Injector {

    INSTANCE;

    companion object {
        fun getInstance(): Injector {
            return INSTANCE
        }
    }

    private lateinit var appComponent: ApplicationComponent
    private lateinit var presenterComponent: PresenterComponent

    fun init(applicationContext: Context, sharedPreferences: SharedPreferences) {
        val applicationModule = AppModule(applicationContext as Application, sharedPreferences)
        val apiModule = ApiModule()

        initAppComponent(applicationModule, apiModule)
    }

    protected fun initAppComponent(applicationModule: AppModule, apiModule: ApiModule) {
        appComponent = DaggerApplicationComponent.builder()
                .appModule(applicationModule)
                .apiModule(apiModule)
                .build()

        getPresenterComponent()
    }

    fun getAppComponent(): ApplicationComponent {
        return appComponent
    }

    fun getPresenterComponent(): PresenterComponent {
        presenterComponent = appComponent.presenterComponent()
        return presenterComponent
    }

}
