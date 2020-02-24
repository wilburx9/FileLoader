package com.wilburt.fileloader

import android.os.Handler
import android.util.Log
import android.util.LruCache
import java.io.BufferedInputStream
import java.lang.Exception
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * Created by Wilberforce on 2020-02-24 at 16:13.
 */
 abstract class FileLoader<T, R: FileRequest> {

    private val tag = FileRequest::class.java.name
    private val maxCacheSize: Int = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 8
    private val memoryCache: LruCache<String, T>
    private val executorService: ExecutorService

    private val handler: Handler

    init {
        memoryCache = object : LruCache<String, T>(maxCacheSize) {
            override fun sizeOf(key: String, value: T) = cacheSizeOf(key, value)
        }

        executorService = Executors.newFixedThreadPool(5, FileDownThreadFactory())
        handler = Handler()

    }


    @Synchronized
    protected fun checkFileInCache(url: String): T? = memoryCache.get(url)

    protected fun  submitRequest(request: R) {
        executorService.submit(Downloader(request))
    }

    internal fun downloadFile(imageUrl: String): BufferedInputStream? {
        val url = URL(imageUrl)
        return BufferedInputStream(url.openConnection().getInputStream())
    }


    protected abstract fun inputStreamToType(inputStream: BufferedInputStream): T?


    internal abstract fun shouldDownload(request: R): Boolean


    protected abstract fun consumeFile(request: R)

    abstract fun cacheSizeOf(key: String, value: T): Int


    private inner class ConsumeFile(private var request: R) : Runnable {
        override fun run() = consumeFile(request)

    }


    private inner class Downloader(private var request: R) : Runnable {
        override fun run() {
            if (shouldDownload(request)) return

           try {
               val inputStream = downloadFile(request.url)
               memoryCache.put(request.url, inputStreamToType(inputStream!!))

               val consumeFile = ConsumeFile(request)
               handler.post(consumeFile)
           } catch (e: Exception) {
               Log.e(tag,"Download failed for ${request.url}", e)
           }
        }

    }
}
