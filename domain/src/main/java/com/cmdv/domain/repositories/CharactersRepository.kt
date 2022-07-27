package com.cmdv.domain.repositories

import com.cmdv.domain.utils.ResponseWrapper

interface CharactersRepository {
    /**
     * API call to get the total characters available.
     */
    fun getTotalCharactersCount(): ResponseWrapper<Int>
}