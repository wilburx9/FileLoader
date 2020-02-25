package com.wilburt.fileloader.photos.common

import java.io.InputStreamReader


/**
 * Created by Wilberforce on 2020-02-25 at 01:51.
 */

class MockResponseFileReader(path: String) {
    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}