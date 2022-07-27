package com.cmdv.domain.repositories

import com.cmdv.domain.uimodels.CharacterUiModel
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Repository class interface. This class declares functions to handle Characters requests.
 */
interface CharactersRepository {
    /**
     * API call to get the total characters available in Marvel's API..
     */
    fun getTotalCharactersCount(): ResponseWrapper<Int>

    /**
     * Returns a list with Marvel's characters.
     */
    fun getCharacters(fetch: Boolean, limit: Int, offset: Int): ResponseWrapper<List<CharacterUiModel>>
}