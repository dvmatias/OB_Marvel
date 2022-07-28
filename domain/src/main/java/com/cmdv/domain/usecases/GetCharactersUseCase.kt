package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.repositories.CharactersRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: To get a list of Marvel's characters.
 */
class GetCharactersUseCase(
    private val charactersRepository: CharactersRepository
) : BaseUseCase<ResponseWrapper<List<CharacterModel>>, GetCharactersUseCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<List<CharacterModel>> =
        charactersRepository.getCharacters(params.fetch, params.limit, params.offset)

    data class Params(val fetch: Boolean, val limit: Int, val offset: Int)
}