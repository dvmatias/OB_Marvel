package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.models.SerieModel
import com.cmdv.domain.repositories.CharacterDetailsRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: User want to see a specific character's details.
 */
class GetSeriesByCharacterIdUserCase(
    private val characterDetailsRepository: CharacterDetailsRepository
) : BaseUseCase<ResponseWrapper<List<SerieModel>>, GetSeriesByCharacterIdUserCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<List<SerieModel>> =
        characterDetailsRepository.getSeries(params.characterId, params.offset)

    data class Params(val characterId: Int, val offset: Int)
}