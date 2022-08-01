package com.cmdv.common.tests

import com.google.gson.Gson

/**
 * Data mapper class. Util class to read json assets for API service responses and requests and map it to app model and
 * entity classes.
 *
 * @author matias.delv.dom@gmail.com
 */
class DataMapperTestHelper {
    /**
     * Gson instance.
     */
    private val gson = Gson()

    /**
     * From a json raw file, creates and returns an object of type T.
     *
     * @param jsonString Json  file in String presentation.
     * @param clazz Type of object to map the json string file.
     *
     * @return An class instance of type [T] where [jsonString] data has been mapped.
     */
    fun <T> fromJson(jsonString: String, clazz: Class<T>): T {
        return gson.fromJson(jsonString, clazz)
    }
}