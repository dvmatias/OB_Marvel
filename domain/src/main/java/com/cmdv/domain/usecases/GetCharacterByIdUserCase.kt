package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.repositories.CharacterDetailsRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: User want to see a specific character's details.
 */
class GetCharacterByIdUserCase(
    private val charactersRepository: CharacterDetailsRepository
) : BaseUseCase<ResponseWrapper<CharacterModel>, GetCharacterByIdUserCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<CharacterModel> =
        charactersRepository.getCharacterById(params.characterId)

    data class Params(val characterId: Int)
}