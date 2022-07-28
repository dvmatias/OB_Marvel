package com.cmdv.domain.base

/**
 * Base mapper class. Every data mapper in the app should extend (be a subclass) of this [BaseMapper] class.
 *
 * Mapper classes are used to transform business model classes into ui model classes and vice versa.
 *
 * @param E Business model type of class.
 * @param M UI model type of class.
 */
abstract class BaseMapper<E, M> {

    open fun transformEntityToModel(e: E): M {
        throw UnsupportedOperationException()
    }

    open fun transformModelToEntity(m: M): E {
        throw UnsupportedOperationException()
    }

}