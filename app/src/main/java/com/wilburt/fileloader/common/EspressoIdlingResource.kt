package com.wilburt.fileloader.common

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource


/**
 * Created by Wilberforce on 01/03/2020 at 12:12.
 */

object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    private val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() = countingIdlingResource.increment()

    fun decrement() = countingIdlingResource.decrement()

    fun getIdlingResource(): IdlingResource =
        countingIdlingResource
}