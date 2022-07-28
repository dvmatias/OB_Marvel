package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.repositories.FavoriteCharacterRepository
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: To remove all favorite characters stored in DB.
 */
class RemoveAllFavoriteCharacterUseCase(
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) : BaseUseCase<ResponseWrapper<Event<Int>>, RemoveAllFavoriteCharacterUseCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<Event<Int>> =
        favoriteCharacterRepository.removeAll()

    class Params
}