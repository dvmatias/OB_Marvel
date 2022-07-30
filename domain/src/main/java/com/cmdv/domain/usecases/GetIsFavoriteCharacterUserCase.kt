package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: User want to see if a character is favorite.
 */
class GetIsFavoriteCharacterUserCase(
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) : BaseUseCase<ResponseWrapper<Boolean>, GetIsFavoriteCharacterUserCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<Boolean> =
        favoriteCharacterRepository.isFavorite(params.characterId)

    data class Params(val characterId: Int)
}