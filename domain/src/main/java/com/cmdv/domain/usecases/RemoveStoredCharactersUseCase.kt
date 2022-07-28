package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.repositories.CharactersRepository

class RemoveStoredCharactersUseCase(
    private val characterRepository: CharactersRepository
) : BaseUseCase<Unit, RemoveStoredCharactersUseCase.Params>() {

    override suspend fun executeUseCase(params: Params) = characterRepository.removeStoredCharacters()

    class Params
}