package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.models.ComicModel
import com.cmdv.domain.repositories.CharacterDetailsRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: User want to see a specific character's details.
 */
class GetComicsByCharacterIdUserCase(
    private val characterDetailsRepository: CharacterDetailsRepository
) : BaseUseCase<ResponseWrapper<List<ComicModel>>, GetComicsByCharacterIdUserCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<List<ComicModel>> =
        characterDetailsRepository.getComics(params.characterId, params.offset)

    data class Params(val characterId: Int, val offset: Int)
}