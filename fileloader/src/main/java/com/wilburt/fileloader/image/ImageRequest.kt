package com.wilburt.fileloader.image

import com.wilburt.fileloader.FileRequest


/**
 * Created by Wilberforce on 2020-02-25 at 00:17.
 */

class ImageRequest(url: String, key: Any, var transformations: List<Transformations>?) : FileRequest(url, key)