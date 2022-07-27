package com.cmdv.data.repositories

import com.cmdv.data.networking.ApiHandler
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Repository class implementation. This class implements functions to handle Characters requests.
 */
class CharactersRepositoryImpl(
    private val apiHandler: ApiHandler
) : CharactersRepository {
    override fun getTotalCharactersCount(): ResponseWrapper<Int> {
        // TODO
        return ResponseWrapper(ResponseWrapper.Status.SUCCESS, 1, null)
    }
}