package com.cmdv.domain.usecases

import com.cmdv.domain.base.BaseUseCase
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.ResponseWrapper

class RemoveFavoriteCharacterUseCase : BaseUseCase<ResponseWrapper<Event<Int>>, RemoveFavoriteCharacterUseCase.Params>() {

    override suspend fun executeUseCase(params: Params): ResponseWrapper<Event<Int>> = TODO()

    class Params
}