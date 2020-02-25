package com.wilburt.fileloader.di.module.app

import com.wilburt.fileloader.di.module.repository.RepositoryModule
import com.wilburt.fileloader.di.module.viewmodel.CoroutineContextProvider
import com.wilburt.fileloader.di.module.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides


/**
 * Created by Wilberforce on 2020-02-25 at 14:20.
 */

@Module(includes = [RepositoryModule::class, ViewModelModule::class] )
class AppModule {

    @Provides
    internal fun contextProvider() = CoroutineContextProvider()
}