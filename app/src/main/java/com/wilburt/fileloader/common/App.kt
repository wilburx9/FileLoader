package com.wilburt.fileloader.common

import android.app.Application
import com.wilburt.fileloader.BuildConfig
import timber.log.Timber


/**
 * Created by Wilberforce on 2020-02-21 at 18:37.
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        // We want to show logs in Debug only
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}