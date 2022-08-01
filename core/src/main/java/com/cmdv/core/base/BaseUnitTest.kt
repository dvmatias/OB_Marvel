package com.cmdv.core.base

import com.cmdv.common.tests.AssetsHelpTestHelper
import com.cmdv.common.tests.DataMapperTestHelper
import com.cmdv.data.entities.GetCharactersResponseEntity

/**
 * Base unit test class.
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
    open val mapper = DataMapperTestHelper()

    /**
     * Assets helper. User this instance to transform json raw files to json objects.
     */
    open val assets = AssetsHelpTestHelper()

    fun <O> getObject( fileJsonName: String, objectClazz: Class<O>): O {
        return mapper.fromJson(
            assets.readFileAsString(javaClass, fileJsonName),
            objectClazz
        )
    }
}
