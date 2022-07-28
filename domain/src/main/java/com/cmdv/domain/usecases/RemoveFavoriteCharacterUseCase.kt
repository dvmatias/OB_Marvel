package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: To remove a single favorite character stored in DB.
 */
class RemoveFavoriteCharacterUseCase(
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) : BaseUseCase<ResponseWrapper<Event<Int>>, RemoveFavoriteCharacterUseCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<Event<Int>> =
        favoriteCharacterRepository.remove(params.characterId, params.position)

    data class Params(val characterId: Int, val position: Int)
}