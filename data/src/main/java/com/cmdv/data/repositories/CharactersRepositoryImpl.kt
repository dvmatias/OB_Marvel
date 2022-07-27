package com.cmdv.data.repositories

import com.cmdv.data.networking.ServiceCallHandler
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.ResponseWrapper

class CharactersRepositoryImpl(
    private val serviceCallHandler: ServiceCallHandler
) : CharactersRepository {
    override fun getTotalCharactersCount(): ResponseWrapper<Int> {
        // TODO
        return ResponseWrapper(ResponseWrapper.Status.SUCCESS, 1, null)
    }
}