package com.cmdv.data.repositories

import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.ResponseWrapper

class CharactersRepositoryImpl : CharactersRepository {
    override fun getTotalCharacters(): ResponseWrapper<Int> {
        // TODO
        return ResponseWrapper(ResponseWrapper.Status.SUCCESS, 1, null)
    }
}