package com.wilburt.fileloader.di.component

import com.wilburt.fileloader.di.module.app.AppModule
import com.wilburt.fileloader.photos.viewmodels.PhotoViewModel
import com.wilburt.fileloader.photos.views.PhotoFragment
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Wilberforce on 2020-02-25 at 00:44.
 */

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    fun inject(photoFragment: PhotoFragment)

    fun inject(viewModel: PhotoViewModel)
}