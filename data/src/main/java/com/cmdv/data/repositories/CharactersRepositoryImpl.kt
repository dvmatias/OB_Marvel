package com.cmdv.data.repositories

import com.cmdv.data.networking.ApiHandler
import com.cmdv.data.sources.apiservices.CharactersApi
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Repository class implementation. This class implements functions to handle Characters requests.
 */
class CharactersRepositoryImpl(
    private val charactersApi: CharactersApi,
    private val apiHandler: ApiHandler
) : CharactersRepository {
    /**
     * Get the total amount of characters available in Marvel's API.
     */
    override fun getTotalCharactersCount(): ResponseWrapper<Int> =
        apiHandler.doNetworkRequest(charactersApi.getCharacters(1, 0)) {
            it.data?.total ?: 0
        }
}