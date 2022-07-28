package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: User want to see a specific character's details.
 */
class GetCharacterByIdUserCase : BaseUseCase<ResponseWrapper<CharacterModel>, GetCharacterByIdUserCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<CharacterModel> = TODO()

    data class Params(val characterId: Int)
}