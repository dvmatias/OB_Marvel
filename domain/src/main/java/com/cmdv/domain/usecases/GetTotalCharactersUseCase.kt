package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: To get total characters amount available in Marvel's API.
 */
class GetTotalCharactersUseCase(
    private val charactersRepository: CharactersRepository
) : BaseUseCase<ResponseWrapper<Int>, GetTotalCharactersUseCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<Int> =
        charactersRepository.getTotalCharactersCount()

    /**
     * This request doesn't need params.
     */
    class Params
}