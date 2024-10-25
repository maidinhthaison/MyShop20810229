package com.ql2.myshop.main

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.ql2.myshop.BuildConfig
import com.ql2.myshop.config.AppConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyShopApp : MultiDexApplication() {
    companion object {
        lateinit var instance: MyShopApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        AppConfig.setBackendEnvironment(BuildConfig.FLAVOR)
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

}