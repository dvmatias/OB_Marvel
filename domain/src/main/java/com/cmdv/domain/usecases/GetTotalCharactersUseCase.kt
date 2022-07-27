package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.ResponseWrapper

class GetTotalCharactersUseCase(
    private val characterRepository: CharactersRepository
) : BaseUseCase<ResponseWrapper<Int>, GetTotalCharactersUseCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<Int> =
        characterRepository.getTotalCharacters()

    /**
     * This request doesn't need params.
     */
    class Params
}