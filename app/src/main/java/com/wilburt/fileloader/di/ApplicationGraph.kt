package com.wilburt.fileloader.di

import com.wilburt.fileloader.photos.views.PhotoFragment
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Wilberforce on 2020-02-25 at 00:44.
 */

@Singleton
@Component
interface ApplicationGraph {

    fun inject(photoFragment: PhotoFragment)
}