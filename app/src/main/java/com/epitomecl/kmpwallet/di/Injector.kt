package com.epitomecl.kmpwallet.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.epitomecl.kmpwallet.di.component.ApplicationComponent
import com.epitomecl.kmpwallet.di.component.DaggerApplicationComponent
import com.epitomecl.kmpwallet.di.component.PresenterComponent
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

        initAppComponent(applicationModule)
    }

    protected fun initAppComponent(applicationModule: AppModule) {
        appComponent = DaggerApplicationComponent.builder()
                .appModule(applicationModule)
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
