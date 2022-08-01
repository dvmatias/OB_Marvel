package com.cmdv.common.tests

import java.io.ByteArrayOutputStream
import java.io.InputStream

/**
 * Stream utils class. This class provides functions to manage input stream for reading file purposes.
 *
 * @author matias.delv.dom@gmail.com
 */
object StreamUtil {
    /**
     *
     */
    fun streamToBytes(inputStream: InputStream?): ByteArray? {
        if (inputStream == null) return null
        return try {
            streamToBytes(inputStream, Int.MAX_VALUE)
        } catch (e: Exception) {
            null
        }
    }

    /**
     *
     */
    private fun streamToBytes(inputStream: InputStream, limit: Int): ByteArray? {
        return readToByteArrayStream(inputStream, limit).toByteArray()
    }

    /**
     *
     */
    private fun readToByteArrayStream(inputStream: InputStream, limit: Int): ByteArrayOutputStream {
        val readBuffer = ByteArray(4096)

        var initialSize = limit
        var remaining = limit

        if (limit == Int.MAX_VALUE) {
            initialSize = readBuffer.size
            remaining = Int.MAX_VALUE
        }

        val buffer = ByteArrayOutputStream(initialSize)

        var readAmount = 0

        while (remaining > 0 && inputStream.read(
                readBuffer,
                0,
                Math.min(remaining, readBuffer.size)).also { readAmount = it } != -1
        ) {
            buffer.write(readBuffer, 0, readAmount)
            remaining -= readAmount
        }

        return buffer
    }
}