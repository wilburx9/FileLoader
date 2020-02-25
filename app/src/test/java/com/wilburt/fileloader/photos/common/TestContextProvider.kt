package com.wilburt.fileloader.photos.common

import com.wilburt.fileloader.di.module.viewmodel.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


/**
 * Created by Wilberforce on 2020-02-25 at 14:14.
 */
class TestContextProvider : CoroutineContextProvider() {
    override val Main: CoroutineContext get() = Dispatchers.Unconfined
    override val IO: CoroutineContext get() = Dispatchers.Unconfined
}