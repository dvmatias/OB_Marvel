package com.cmdv.common.tests

import java.io.InputStream

/**
 * Helper class. This class is intended to read a file.
 *
 * @author matias.delv.dom@gmail.com
 */
class AssetsTestHelper {
    /**
     * Reads a file with [fileName] name and returns a string version of it.
     */
    fun readFileAsString(clazz: Class<*>, fileName: String): String {
        return String(readFileAsBytes(clazz, fileName))
    }

    /**
     * Reads a file as bytes name and returns a byte array version of it.
     */
    private fun readFileAsBytes(clazz: Class<*>, fileName: String): ByteArray {
        return StreamUtil.streamToBytes(readFile(clazz, fileName)) ?: throw java.lang.IllegalStateException()
    }

    /**
     * Reads a file as bytes name and returns a input stream version of it.
     */
    private fun readFile(clazz: Class<*>, fileName: String): InputStream =
        clazz.getResourceAsStream(fileName) ?: throw IllegalStateException("File $fileName do not exists.")
}