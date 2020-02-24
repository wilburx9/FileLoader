package com.wilburt.fileloader

import android.os.Process
import java.util.concurrent.ThreadFactory


/**
 * Created by Wilberforce on 2020-02-25 at 00:06.
 */

// Thread factor to set thread priority to background
internal class FileDownThreadFactory : ThreadFactory {
    override fun newThread(r: Runnable): Thread {
        return Thread(r).apply {
            name = "ImageLoader Thread"
            priority = Process.THREAD_PRIORITY_BACKGROUND
        }
    }

}