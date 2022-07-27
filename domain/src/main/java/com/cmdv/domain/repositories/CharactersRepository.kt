package com.cmdv.domain.repositories

import com.cmdv.domain.utils.ResponseWrapper

/**
 * Repository class interface. This class declares functions to handle Characters requests.
 */
interface CharactersRepository {
    /**
     * API call to get the total characters available.
     */
    fun getTotalCharactersCount(): ResponseWrapper<Int>
}