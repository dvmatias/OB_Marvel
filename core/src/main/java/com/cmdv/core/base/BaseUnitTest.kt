package com.cmdv.core.base

import com.cmdv.common.tests.AssetsTestHelper
import com.cmdv.common.tests.DataMapperTestHelper

/**
 * Base unit test class. Every unit test class should extend this base class.
 *
 * @author matias.delv.dom@gmail.com
 */
open class BaseUnitTest<T> {
    /**
     * Unit under testing instance.
     */
    var uut: T? = null

    /**
     * Data mapper helper. Use this instance to map a json file to an object.
     */
    private val mapper = DataMapperTestHelper()

    /**
     * Assets helper. User this instance to transform json raw files to json objects.
     */
    open val assets = AssetsTestHelper()

    fun <O> fromJson(fileJsonName: String, objectClazz: Class<O>): O {
        return mapper.fromJson(
            assets.readFileAsString(javaClass, fileJsonName),
            objectClazz
        )
    }
}
