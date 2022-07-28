package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Use Case: User add a new character as favorite to the DB.
 */
class AddFavoriteCharacterUseCase : BaseUseCase<ResponseWrapper<Event<Int>>, AddFavoriteCharacterUseCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<Event<Int>> = TODO()

    data class Params(val characterId: Int, val position: Int)
}