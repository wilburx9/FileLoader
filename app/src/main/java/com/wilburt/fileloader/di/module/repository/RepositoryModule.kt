package com.wilburt.fileloader.di.module.repository

import com.wilburt.fileloader.photos.datasource.RemoteDataSource
import com.wilburt.fileloader.photos.repositories.PhotoRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Wilberforce on 2020-02-25 at 03:02.
 */

@Module
class RepositoryModule {

    @Singleton
    @Provides
    internal fun photosRepository() = PhotoRepository(RemoteDataSource())

}