package com.greatdayhr.support.core.data

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * Modified from: https://stackoverflow.com/questions/33338181/is-it-possible-to-show-progress-bar-when-upload-image-via-retrofit-2
 * */
class ProgressRequestBody(
    private val file: File,
    private val mediaType: String,
    private val ignoreFirstNumberOfWriteToCalls: Int = 0,
    private val progressListener: ((Float) -> Unit)
) : RequestBody() {

    private val defaultBufferSize = 2048
    private var numWriteToCalls = 0

    override fun contentType(): MediaType? {
        return MediaType.parse(mediaType)
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return file.length()
    }

    override fun writeTo(sink: BufferedSink) {
        numWriteToCalls++

        val fileLength = file.length()
        val buffer = ByteArray(defaultBufferSize)
        val fis = FileInputStream(file)
        var uploaded: Long = 0

        fis.use { fis ->
            var read: Int
            var lastProgressPercentUpdate = 0.0f
            read = fis.read(buffer)
            while (read != -1) {

                uploaded += read.toLong()
                sink.write(buffer, 0, read)
                read = fis.read(buffer)

                // when using HttpLoggingInterceptor it calls writeTo and passes data into a local buffer just for logging purposes.
                // the second call to write to is the progress we actually want to track
                if (numWriteToCalls > ignoreFirstNumberOfWriteToCalls) {
                    val progress = (uploaded.toFloat() / fileLength.toFloat()) * 100f
                    //prevent publishing too many updates, which slows upload, by checking if the upload has progressed by at least 1 percent
                    if (progress - lastProgressPercentUpdate > 1 || progress == 100f) {
                        // publish progress
                        progressListener(progress)
                        lastProgressPercentUpdate = progress
                    }
                }
            }
        }
    }
}