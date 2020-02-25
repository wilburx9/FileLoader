package com.wilburt.fileloader.common

import android.app.Application
import com.wilburt.fileloader.BuildConfig
import com.wilburt.fileloader.di.component.ApplicationComponent
import com.wilburt.fileloader.di.component.DaggerApplicationComponent
import timber.log.Timber


/**
 * Created by Wilberforce on 2020-02-21 at 18:37.
 */
class App: Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        // We want to show logs in Debug only
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        appComponent = DaggerApplicationComponent.builder().build()
    }
}