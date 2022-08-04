package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: To remove all stored characters in DB.
 */
class RemoveStoredCharactersUseCase(
    private val characterRepository: CharactersRepository
) : BaseUseCase<ResponseWrapper<Int>, RemoveStoredCharactersUseCase.Params>() {

    override suspend fun executeUseCase(params: Params) = characterRepository.removeStoredCharacters()

    class Params
}