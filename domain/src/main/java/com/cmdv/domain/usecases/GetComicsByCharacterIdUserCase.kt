package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.models.ComicModel
import com.cmdv.domain.repositories.CharacterDetailsRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: User want to see a specific character's details.
 */
class GetComicsByCharacterIdUserCase(
    private val charactersRepository: CharacterDetailsRepository
) : BaseUseCase<ResponseWrapper<List<ComicModel>>, GetComicsByCharacterIdUserCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<List<ComicModel>> =
        charactersRepository.getComics(params.characterId)

    data class Params(val characterId: Int)
}